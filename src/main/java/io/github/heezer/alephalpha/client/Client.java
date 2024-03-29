package io.github.heezer.alephalpha.client;

import static io.github.heezer.alephalpha.client.ObjectOperations.getOrDefault;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import io.github.heezer.alephalpha.client.completion.CompletionRequest;
import io.github.heezer.alephalpha.client.completion.CompletionResponse;
import io.github.heezer.alephalpha.client.embeddings.BatchedSemanticEmbeddingsRequest;
import io.github.heezer.alephalpha.client.embeddings.BatchedSemanticEmbeddingsResponse;
import io.github.heezer.alephalpha.client.embeddings.EmbeddingsRequest;
import io.github.heezer.alephalpha.client.embeddings.EmbeddingsResponse;
import io.github.heezer.alephalpha.client.embeddings.SemanticEmbeddingsRequest;
import io.github.heezer.alephalpha.client.embeddings.SemanticEmbeddingsResponse;
import io.github.heezer.alephalpha.client.evaluation.EvaluationRequest;
import io.github.heezer.alephalpha.client.evaluation.EvaluationResponse;
import io.github.heezer.alephalpha.client.explanation.ExplanationRequest;
import io.github.heezer.alephalpha.client.explanation.ExplanationResponse;
import io.github.heezer.alephalpha.client.models.Model;
import io.github.heezer.alephalpha.client.tokenization.DetokenizationRequest;
import io.github.heezer.alephalpha.client.tokenization.DetokenizationResponse;
import io.github.heezer.alephalpha.client.tokenization.TokenizationRequest;
import io.github.heezer.alephalpha.client.tokenization.TokenizationResponse;
import io.github.heezer.alephalpha.client.tokens.ApiToken;
import io.github.heezer.alephalpha.client.tokens.NewApiTokenRequest;
import io.github.heezer.alephalpha.client.tokens.NewApiTokenResponse;
import io.github.heezer.alephalpha.client.users.ChangeUserSettingsRequest;
import io.github.heezer.alephalpha.client.users.UserPriviledge;
import io.github.heezer.alephalpha.client.users.UserSettingsResponse;
import java.io.IOException;
import java.net.Proxy;
import java.time.Duration;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Slf4j
public class Client {

  private final String ALEPH_ALPHA_BASE_URL = "https://api.aleph-alpha.com/";

  private final OkHttpClient client;
  private final Api api;
  private final Api textApi;

  @Builder
  public Client(
    @NonNull String apiKey,
    Duration callTimeout,
    Duration connectTimeout,
    Duration readTimeout,
    Duration writeTimeout,
    Proxy proxy,
    boolean logRequests,
    boolean logResponses
  ) {
    val builder = new OkHttpClient.Builder()
      .callTimeout(getOrDefault(callTimeout, Duration.ofSeconds(60)))
      .connectTimeout(getOrDefault(connectTimeout, Duration.ofSeconds(60)))
      .readTimeout(getOrDefault(readTimeout, Duration.ofSeconds(60)))
      .writeTimeout(getOrDefault(writeTimeout, Duration.ofSeconds(60)))
      .addInterceptor(new AuthorizationHeaderInjector(apiKey));

    if (proxy != null) {
      builder.proxy(proxy);
    }

    if (logRequests) {
      builder.addInterceptor(new RequestLoggingInterceptor());
    }

    if (logResponses) {
      builder.addInterceptor(new ResponseLoggingInterceptor());
    }

    client = builder.build();

    val retrofitBuilder =
      (new Retrofit.Builder()).baseUrl(ALEPH_ALPHA_BASE_URL)
        .client(client)
        .addConverterFactory(ScalarsConverterFactory.create());

    val retrofit = retrofitBuilder
      .addConverterFactory(
        GsonConverterFactory.create(
          new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
        )
      )
      .build();

    textApi = retrofitBuilder.build().create(Api.class);
    api = retrofit.create(Api.class);
  }

  private static <T> RuntimeException toException(Response<T> response) throws IOException {
    return new RuntimeException(
      String.format("status code: %s; body: %s", response.code(), response.errorBody().string())
    );
  }

  public String version() {
    return executeRequest(textApi.version());
  }

  public String specification() {
    return executeRequest(textApi.specification());
  }

  public List<String> openApiVersions() {
    return executeRequest(textApi.openApiVersions());
  }

  public String openApiDescription(String version) {
    return executeRequest(textApi.openApiDescription(version));
  }

  public List<ApiToken> apiTokens() {
    return executeRequest(api.apiTokens());
  }

  public NewApiTokenResponse createApiToken(NewApiTokenRequest request) {
    return executeRequest(api.createApiToken(request));
  }

  public void deleteApiToken(Integer tokenId) {
    executeRequest(api.deleteApiToken(tokenId));
  }

  public List<Model> models() {
    return executeRequest(api.models());
  }

  public Object tokenizerForModel(String model) {
    return executeRequest(api.tokenizerForModel(model));
  }

  public CompletionResponse complete(Boolean nice, CompletionRequest request) {
    return executeRequest(api.complete(nice, request));
  }

  public CompletionResponse complete(CompletionRequest request) {
    return complete(null, request);
  }

  public EmbeddingsResponse embed(Boolean nice, EmbeddingsRequest request) {
    return executeRequest(api.embed(nice, request));
  }

  public EmbeddingsResponse embed(EmbeddingsRequest request) {
    return embed(null, request);
  }

  public SemanticEmbeddingsResponse semanticEmbed(Boolean nice, SemanticEmbeddingsRequest request) {
    return executeRequest(api.semanticEmbed(nice, request));
  }

  public SemanticEmbeddingsResponse semanticEmbed(SemanticEmbeddingsRequest request) {
    return semanticEmbed(null, request);
  }

  public BatchedSemanticEmbeddingsResponse batchSemanticEmbed(Boolean nice, BatchedSemanticEmbeddingsRequest request) {
    return executeRequest(api.batchSemanticEmbed(nice, request));
  }

  public BatchedSemanticEmbeddingsResponse batchSemanticEmbed(BatchedSemanticEmbeddingsRequest request) {
    return batchSemanticEmbed(null, request);
  }

  public EvaluationResponse evaluate(Boolean nice, EvaluationRequest request) {
    return executeRequest(api.evaluate(nice, request));
  }

  public EvaluationResponse evaluate(EvaluationRequest request) {
    return evaluate(null, request);
  }

  public ExplanationResponse explain(Boolean nice, ExplanationRequest request) {
    return executeRequest(api.explain(nice, request));
  }

  public ExplanationResponse explain(ExplanationRequest request) {
    return explain(null, request);
  }

  public TokenizationResponse tokenize(TokenizationRequest request) {
    return executeRequest(api.tokenize(request));
  }

  public DetokenizationResponse detokenize(DetokenizationRequest request) {
    return executeRequest(api.detokenize(request));
  }

  public UserSettingsResponse userSettings() {
    return executeRequest(api.userSettings());
  }

  public UserSettingsResponse changeUserSettings(ChangeUserSettingsRequest request) {
    return executeRequest(api.changeUserSettings(request));
  }

  public List<UserPriviledge> checkPrivileges() {
    return executeRequest(api.checkPrivileges());
  }

  private <T> T executeRequest(Call<T> call) {
    try {
      Response<T> response = call.execute();

      if (response.isSuccessful()) {
        return response.body();
      } else {
        throw toException(response);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static class ClientBuilder {

    public ClientBuilder logRequests() {
      logRequests = true;
      return this;
    }

    public ClientBuilder logResponses() {
      logResponses = true;
      return this;
    }
  }
}
