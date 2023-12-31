package dev.ai4j.alephalpha;

import dev.ai4j.alephalpha.completion.CompletionRequest;
import dev.ai4j.alephalpha.completion.CompletionResponse;
import dev.ai4j.alephalpha.embeddings.EmbeddingsRequest;
import dev.ai4j.alephalpha.embeddings.EmbeddingsResponse;
import dev.ai4j.alephalpha.embeddings.SemanticEmbeddingsRequest;
import dev.ai4j.alephalpha.embeddings.SemanticEmbeddingsResponse;
import dev.ai4j.alephalpha.tokens.ApiToken;
import dev.ai4j.alephalpha.tokens.NewApiTokenRequest;
import dev.ai4j.alephalpha.tokens.NewApiTokenResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

interface Api {
  @GET("version")
  @Headers("Accept: text/plain")
  Call<String> version();

  @GET("openapi.yaml")
  @Headers("Accept: text/yaml")
  Call<String> specification();

  @GET("openapi-description")
  @Headers("Accept: text/yaml")
  Call<List<String>> openApiVersions();

  @GET("openapi-description/{version}")
  @Headers("Accept: text/yaml")
  Call<String> openApiDescription(@Path("version") String version);

  @GET("users/me/tokens")
  @Headers("Accept: application/json")
  Call<List<ApiToken>> apiTokens();

  @POST("users/me/tokens")
  @Headers("Accept: application/json")
  Call<NewApiTokenResponse> createApiToken(@Body NewApiTokenRequest request);

  @DELETE("users/me/tokens/{tokenId}")
  @Headers("Accept: application/json")
  Call<Void> deleteApiToken(@Path("tokenId") Integer tokenId);

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
