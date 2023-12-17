package dev.ai4j.alephalpha;

import dev.ai4j.alephalpha.completion.CompletionRequest;
import dev.ai4j.alephalpha.completion.CompletionResponse;
import dev.ai4j.alephalpha.embeddings.EmbeddingsRequest;
import dev.ai4j.alephalpha.embeddings.EmbeddingsResponse;
import dev.ai4j.alephalpha.embeddings.SemanticEmbeddingsRequest;
import dev.ai4j.alephalpha.embeddings.SemanticEmbeddingsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

interface Api {
  @GET("version")
  @Headers("Accept: text/plain")
  Call<String> version();

  @GET("openapi.yaml")
  @Headers("Accept: text/yaml")
  Call<String> specification();

  @POST("complete")
  @Headers("Accept: application/json")
  Call<CompletionResponse> complete(@Body CompletionRequest request);

  @POST("embed")
  @Headers("Accept: application/json")
  Call<EmbeddingsResponse> embed(@Body EmbeddingsRequest request);

  @POST("semantic_embed")
  @Headers("Accept: application/json")
  Call<SemanticEmbeddingsResponse> semanticEmbed(@Body SemanticEmbeddingsRequest request);
}
