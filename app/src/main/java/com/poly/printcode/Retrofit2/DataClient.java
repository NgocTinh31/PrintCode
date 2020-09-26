package com.poly.printcode.Retrofit2;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataClient {
    @Multipart
    @POST("uploadIMG.php")
    Call<String> uploadphoto(@Part MultipartBody.Part photo);
    @FormUrlEncoded
    @POST("add_product.php")
    Call<String> insertData(@Field("product") String product, @Field("price") String price, @Field("description") String description, @Field("img") String img);
}
