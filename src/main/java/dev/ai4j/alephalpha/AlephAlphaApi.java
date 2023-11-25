package dev.ai4j.alephalpha;

import dev.ai4j.alephalpha.completion.CompletionRequest;
import dev.ai4j.alephalpha.completion.CompletionResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

interface AlephAlphaApi {
  @POST("completions")
  @Headers("Content-Type: application/json")
  Call<CompletionResponse> complete(@Body CompletionRequest request);
}
