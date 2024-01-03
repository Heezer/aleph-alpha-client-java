package io.github.heezer.alephalpha.client;

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
  Call<EvaluationResponse> evaluate(@Query("nice") Boolean nice, @Body EvaluationRequest request);

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

  @POST("check_privileges")
  @Headers("Accept: application/json")
  Call<List<UserPriviledge>> checkPrivileges();
}
