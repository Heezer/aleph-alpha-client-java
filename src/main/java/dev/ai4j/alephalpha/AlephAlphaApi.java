package dev.ai4j.alephalpha;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

interface AlephAlphaApi {
    @POST("completions")
    @Headers("Content-Type: application/json")
    Call<AlephAlphaResponse> completions(@Body String prompt);

}
