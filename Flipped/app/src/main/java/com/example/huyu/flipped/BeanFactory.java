package com.example.huyu.flipped;

import com.example.huyu.bean.AndroidBean;
import com.example.huyu.bean.BaseBean;
import com.example.huyu.bean.IOSBean;
import com.example.huyu.bean.MeiZhiBean;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by huyu on 2017/3/11.
 */

public class BeanFactory {

    private BaseBean mBean;
    private String type;

    public BeanFactory(String type){
        this.type=type;
    }

    public  BaseBean getBean(){

        switch (type){
            case "Android":
                mBean=new AndroidBean();

                break;
            case "Ios":
                mBean=new IOSBean();
                break;
            case "qianduan":
                break;
            case "福利":
                mBean=new MeiZhiBean();
                break;
            default:break;
        }

return mBean;

    }
}
