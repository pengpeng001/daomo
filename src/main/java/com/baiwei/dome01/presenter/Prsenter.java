package com.baiwei.dome01.presenter;

import com.baiwei.dome01.contract.Contact;
import com.baiwei.dome01.model.Model;

import java.io.File;

public class Prsenter implements Contact.IPresenter {

    public Contact.IView iView ;
    public Model model;

    public  Prsenter(Contact.IView iView ){
        this.iView = iView;
        model = new Model();

    }


    @Override
    public void upLoding(File file) {
        model.upLoding(new Contact.IMOdel.ICllackBank() {
            @Override
            public void onSucee(Object o) {
                iView.onSucce(o);
            }

            @Override
            public void onFail(String error) {
                iView.onFail(error);
            }
        } , file);
    }

    @Override
    public void detash() {
        if (iView != null){
            iView = null;
        }
        if (model != null){
            model = null;
        }
        System.gc();


    }
}
