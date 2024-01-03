package dev.ai4j.alephalpha;

import static dev.ai4j.alephalpha.ObjectOperations.getOrDefault;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import dev.ai4j.alephalpha.completion.CompletionRequest;
import dev.ai4j.alephalpha.completion.CompletionResponse;
import dev.ai4j.alephalpha.embeddings.BatchedSemanticEmbeddingsRequest;
import dev.ai4j.alephalpha.embeddings.BatchedSemanticEmbeddingsResponse;
import dev.ai4j.alephalpha.embeddings.EmbeddingsRequest;
import dev.ai4j.alephalpha.embeddings.EmbeddingsResponse;
import dev.ai4j.alephalpha.embeddings.SemanticEmbeddingsRequest;
import dev.ai4j.alephalpha.embeddings.SemanticEmbeddingsResponse;
import dev.ai4j.alephalpha.evaluate.EvaluateRequest;
import dev.ai4j.alephalpha.evaluate.EvaluateResponse;
import dev.ai4j.alephalpha.explanation.ExplanationRequest;
import dev.ai4j.alephalpha.explanation.ExplanationResponse;
import dev.ai4j.alephalpha.models.Model;
import dev.ai4j.alephalpha.tokenize.DetokenizationRequest;
import dev.ai4j.alephalpha.tokenize.DetokenizationResponse;
import dev.ai4j.alephalpha.tokenize.TokenizationRequest;
import dev.ai4j.alephalpha.tokenize.TokenizationResponse;
import dev.ai4j.alephalpha.tokens.ApiToken;
import dev.ai4j.alephalpha.tokens.NewApiTokenRequest;
import dev.ai4j.alephalpha.tokens.NewApiTokenResponse;
import dev.ai4j.alephalpha.users.ChangeUserSettingsRequest;
import dev.ai4j.alephalpha.users.UserSettingsResponse;
import java.io.IOException;
import java.net.Proxy;
import java.time.Duration;
import java.util.List;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
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
    @NotNull String apiKey,
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

  public EvaluateResponse evaluate(Boolean nice, EvaluateRequest request) {
    return executeRequest(api.evaluate(nice, request));
  }

  public EvaluateResponse evaluate(EvaluateRequest request) {
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
