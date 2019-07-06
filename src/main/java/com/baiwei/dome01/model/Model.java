package com.baiwei.dome01.model;

import com.baiwei.dome01.api.API;
import com.baiwei.dome01.api.IAPIService;
import com.baiwei.dome01.contract.Contact;
import com.baiwei.dome01.entity.UpLodingEntity;
import com.baiwei.dome01.net.RetrofigManager;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Model implements Contact.IMOdel {
    @Override
    public void upLoding(final ICllackBank iCllackBank , File file) {

        IAPIService iapiService = RetrofigManager.getInstance().reCrest(IAPIService.class);


        RequestBody requestBody = MultipartBody.create(MediaType.parse("image/*"),file);

        MultipartBody.Part part = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
        iapiService.upload(API.BASE_URL + API.UPLODING_URL ,part).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<UpLodingEntity>() {
            @Override
            public void accept(UpLodingEntity upLodingEntity) throws Exception {
                iCllackBank.onSucee(upLodingEntity);


            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                iCllackBank.onFail(throwable.getMessage());

            }
        });
    }
}
