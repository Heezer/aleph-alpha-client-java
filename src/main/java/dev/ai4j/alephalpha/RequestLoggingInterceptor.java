package dev.ai4j.alephalpha;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@NoArgsConstructor
@Slf4j
class RequestLoggingInterceptor implements Interceptor {

  private static final Pattern BEARER_PATTERN = Pattern.compile("(Bearer\\s)(.{2})(.+)(.{2})");

  public Response intercept(Chain chain) throws IOException {
    val request = chain.request();
    log(request);
    return chain.proceed(request);
  }

  private static void log(Request request) {
    try {
      log.debug(
        "Request:\n- method: {}\n- url: {}\n- headers: {}\n- body: {}",
        request.method(),
        request.url(),
        inOneLine(request.headers()),
        getBody(request)
      );
    } catch (Exception ex) {
      log.warn("Failed to log request", ex);
    }
  }

  static String inOneLine(Headers headers) {
    return StreamSupport
      .stream(headers.spliterator(), false)
      .map(header -> {
        String headerKey = header.component1();
        String headerValue = header.component2();
        if (headerKey.equals("Authorization")) {
          headerValue = maskAuthorizationHeaderValue(headerValue);
        }

        return String.format("[%s: %s]", headerKey, headerValue);
      })
      .collect(Collectors.joining(", "));
  }

  private static String maskAuthorizationHeaderValue(String authorizationHeaderValue) {
    try {
      val matcher = BEARER_PATTERN.matcher(authorizationHeaderValue);
      val sb = new StringBuffer();

      while (matcher.find()) {
        matcher.appendReplacement(sb, matcher.group(1) + matcher.group(2) + "..." + matcher.group(4));
      }

      matcher.appendTail(sb);
      return sb.toString();
    } catch (Exception var3) {
      return "Failed to mask the API key.";
    }
  }

  private static String maskApiKeyHeaderValue(String apiKeyHeaderValue) {
    try {
      return apiKeyHeaderValue.length() <= 4
        ? apiKeyHeaderValue
        : apiKeyHeaderValue.substring(0, 2) + "..." + apiKeyHeaderValue.substring(apiKeyHeaderValue.length() - 2);
    } catch (Exception var2) {
      return "Failed to mask the API key.";
    }
  }

  private static String getBody(Request request) {
    if (request.body() == null) {
      return "<empty>";
    }

    try {
      Buffer buffer = new Buffer();
      request.body().writeTo(buffer);
      return buffer.readUtf8();
    } catch (Exception ex) {
      log.warn("Exception happened while reading request body", ex);
      return "[Exception happened while reading request body. Check logs for more details.]";
    }
  }
}
