package dev.ai4j.alephalpha;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import static dev.ai4j.alephalpha.ObjectOperations.getOrDefault;
import dev.ai4j.alephalpha.completion.CompletionRequest;
import dev.ai4j.alephalpha.completion.CompletionResponse;
import dev.ai4j.alephalpha.embeddings.EmbeddingsRequest;
import dev.ai4j.alephalpha.embeddings.EmbeddingsResponse;
import dev.ai4j.alephalpha.embeddings.SemanticEmbeddingsRequest;
import dev.ai4j.alephalpha.embeddings.SemanticEmbeddingsResponse;
import dev.ai4j.alephalpha.models.Model;
import dev.ai4j.alephalpha.tokens.ApiToken;
import dev.ai4j.alephalpha.tokens.NewApiTokenRequest;
import dev.ai4j.alephalpha.tokens.NewApiTokenResponse;
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

import java.io.IOException;
import java.net.Proxy;
import java.time.Duration;
import java.util.List;

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

  public CompletionResponse complete(CompletionRequest request) {
    return executeRequest(api.complete(request));
  }

  public EmbeddingsResponse embed(EmbeddingsRequest request) {
    return executeRequest(api.embed(request));
  }

  public SemanticEmbeddingsResponse semanticEmbed(SemanticEmbeddingsRequest request) {
    return executeRequest(api.semanticEmbed(request));
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
