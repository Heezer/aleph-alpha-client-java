package dev.ai4j.alephalpha;

import java.net.Proxy;
import java.time.Duration;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class AlephAlphaClient {

  private final String baseUrl;

  @NotNull
  private final String apiKey;

  private final boolean logRequests;
  private final boolean logResponses;

  private final OkHttpClient client;
  private final AlephAlphaApi openAiApi;

  @Builder
  public AlephAlphaClient(
    String baseUrl,
    String apiKey,
    Duration callTimeout,
    Duration connectTimeout,
    Duration readTimeout,
    Duration writeTimeout,
    Proxy proxy,
    boolean logRequests,
    boolean logResponses
  ) {
    this.baseUrl = baseUrl != null ? baseUrl : "https://api.aleph-alpha.com/";
    this.apiKey = apiKey;
    this.logRequests = logRequests;
    this.logResponses = logResponses;

    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
      .callTimeout(callTimeout != null ? callTimeout : Duration.ofSeconds(60))
      .connectTimeout(connectTimeout != null ? connectTimeout : Duration.ofSeconds(60))
      .readTimeout(readTimeout != null ? readTimeout : Duration.ofSeconds(60))
      .writeTimeout(writeTimeout != null ? writeTimeout : Duration.ofSeconds(60));

    if (proxy != null) {
      clientBuilder.proxy(proxy);
    }

    this.client = clientBuilder.build();
    // TBD
    this.openAiApi = null;
  }

  public SyncOrAsync<String> completion(String prompt) {
    // TODO
    return null;
  }
}
