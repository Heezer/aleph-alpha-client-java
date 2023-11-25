package dev.ai4j.alephalpha;

import static dev.ai4j.alephalpha.ObjectOperations.getOrDefault;

import dev.ai4j.alephalpha.completion.CompletionRequest;
import dev.ai4j.alephalpha.completion.CompletionResponse;
import java.io.IOException;
import java.net.Proxy;
import java.time.Duration;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Slf4j
public class AlephAlphaClient {

  private final String ALEPH_ALPHA_BASE_URL = "https://api.aleph-alpha.com/";

  private final OkHttpClient client;
  private final AlephAlphaApi api;

  @Builder
  public AlephAlphaClient(
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
    val retrofit =
      (new Retrofit.Builder()).baseUrl(ALEPH_ALPHA_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    api = retrofit.create(AlephAlphaApi.class);
  }

  CompletionResponse complete(CompletionRequest request) {
    try {
      val response = api.complete(request).execute();

      if (response.isSuccessful()) {
        return response.body();
      } else {
        throw toException(response);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static RuntimeException toException(retrofit2.Response<?> response) throws IOException {
    return new RuntimeException(
      String.format("status code: %s; body: %s", response.code(), response.errorBody().string())
    );
  }
}
