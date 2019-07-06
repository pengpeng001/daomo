package com.baiwei.dome01.api;

import com.baiwei.dome01.entity.UpLodingEntity;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface IAPIService {

    @Multipart
    @POST
    Observable<UpLodingEntity> upload(@Url String url,  @Part MultipartBody.Part filepart);

}
