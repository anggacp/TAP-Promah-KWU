package com.project.ocr;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TranslateAPI {

    @FormUrlEncoded
    @Headers({
            "content-type: application/x-www-form-urlencoded",
            "x-rapidapi-key: e1f1604750mshbc9db302cfd0586p16f7b8jsnc40d373a514e",
            "x-rapidapi-host: nlp-translation.p.rapidapi.com"
    })

    /*@GET("translate")
    Call<Post> getJSON();*/

    @POST("translate")
    Call<Post> createPost(
            @Field("text") String text,
            @Field("to") String to
    );
}
