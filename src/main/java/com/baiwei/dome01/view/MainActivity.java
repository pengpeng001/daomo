package com.baiwei.dome01.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.baiwei.dome01.R;
import com.baiwei.dome01.contract.Contact;
import com.baiwei.dome01.entity.UpLodingEntity;
import com.baiwei.dome01.presenter.Prsenter;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Contact.IView {

    public Button  mButton;
    public ImageView mImageView;

    public Contact.IPresenter iPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.but_uploading_id);
        mImageView = findViewById(R.id.img_id);
        iPresenter = new Prsenter(this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入相册 以下是例子：用不到的api可以不写
                PictureSelector.create(MainActivity.this)
                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(1)// 最大图片选择数量 int
                        /*.minSelectNum(1)// 最小选择数量 int
                        .imageSpanCount(4)// 每行显示个数 int*/
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .previewImage(true)// 是否可预览图片 true or false
                        .isCamera(true)// 是否显示拍照按钮 true or false
                        .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                        /*.isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填*/
                        .enableCrop(true)// 是否裁剪 true or false
                        .compress(true)// 是否压缩 true or false
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);


                    String compresspath = selectList.get(0).getCompressPath();
                    System.out.println("path==="+compresspath);
                    File file = new File(compresspath);
                    iPresenter.upLoding(file);
                    break;
            }
        }
    }
    @Override
    public void onSucce(Object o) {
        UpLodingEntity upLodingEntity = (UpLodingEntity) o;
        String message = upLodingEntity.message;
        String headPath = upLodingEntity.headPath;
        String status = upLodingEntity.status;
        Log.e("tag", "message: "  +message+ " headPath : " +headPath+ " status " + status+"  ");

        if (status.equals("0000")){
            Toast.makeText(this , ""+ message , Toast.LENGTH_LONG).show();
            Glide.with(this).load(headPath).into(mImageView);

        }else {
            Toast.makeText(this , ""+ message , Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onFail(String eroor) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.detash();
    }
}
