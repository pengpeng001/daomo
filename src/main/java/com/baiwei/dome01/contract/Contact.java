package com.baiwei.dome01.contract;

import java.io.File;

public interface Contact {

    public interface IMOdel{
        public void upLoding(ICllackBank iCllackBank , File file);
        public interface ICllackBank{
            public void onSucee(Object o);
            public void onFail(String error);
        }
    }
    public interface IPresenter{
        public void upLoding(File file);
        public void detash();
    }
    public interface IView{
        public void onSucce(Object o);
        public void onFail(String eroor);
    }


}
