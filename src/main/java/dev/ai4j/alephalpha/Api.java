package dev.ai4j.alephalpha;

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
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

  @GET("models_available")
  @Headers("Accept: application/json")
  Call<List<Model>> models();

  @GET("models/{model}/tokenizer")
  @Headers("Accept: application/json")
  Call<Object> tokenizerForModel(@Path("model") String model);

  @POST("complete")
  @Headers("Accept: application/json")
  Call<CompletionResponse> complete(@Query("nice") Boolean nice, @Body CompletionRequest request);

  @POST("embed")
  @Headers("Accept: application/json")
  Call<EmbeddingsResponse> embed(@Query("nice") Boolean nice, @Body EmbeddingsRequest request);

  @POST("semantic_embed")
  @Headers("Accept: application/json")
  Call<SemanticEmbeddingsResponse> semanticEmbed(@Query("nice") Boolean nice, @Body SemanticEmbeddingsRequest request);

  @POST("batch_semantic_embed")
  @Headers("Accept: application/json")
  Call<BatchedSemanticEmbeddingsResponse> batchSemanticEmbed(
    @Query("nice") Boolean nice,
    @Body BatchedSemanticEmbeddingsRequest request
  );

  @POST("evaluate")
  @Headers("Accept: application/json")
  Call<EvaluateResponse> evaluate(@Query("nice") Boolean nice, @Body EvaluateRequest request);

  @POST("explain")
  @Headers("Accept: application/json")
  Call<ExplanationResponse> explain(@Query("nice") Boolean nice, @Body ExplanationRequest request);

  @POST("tokenize")
  @Headers("Accept: application/json")
  Call<TokenizationResponse> tokenize(@Body TokenizationRequest request);

  @POST("detokenize")
  @Headers("Accept: application/json")
  Call<DetokenizationResponse> detokenize(@Body DetokenizationRequest request);

  @GET("users/me")
  @Headers("Accept: application/json")
  Call<UserSettingsResponse> userSettings();

  @PATCH("users/me")
  @Headers("Accept: application/json")
  Call<UserSettingsResponse> changeUserSettings(@Body ChangeUserSettingsRequest request);
}
