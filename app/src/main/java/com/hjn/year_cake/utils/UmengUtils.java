package com.hjn.year_cake.utils;

import android.app.Activity;

import com.hjn.year_cake.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by Year_Cake on 2018/9/4.
 * descripton: umeng分享
 */

public class UmengUtils {

    public static void shareUrl(final Activity activity, String url, String title, String msg, SHARE_MEDIA shareMedia){

        UMImage image = new UMImage(activity, R.mipmap.app_icon);

        UMWeb umWeb = new UMWeb(url);
        umWeb.setTitle(title);
        umWeb.setDescription(msg);
        umWeb.setThumb(image);

        new ShareAction(activity)
                .withMedia(umWeb)
                .setPlatform(shareMedia)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                     //   ToastUtils.show(activity,"分享成功",Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//                        ToastUtils.show(activity,"请安装"+share_media,Toast.LENGTH_SHORT);

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                   //     ToastUtils.show(activity,"亲，分享取消",Toast.LENGTH_SHORT);
                    }
                }).share();

    }

    public static void shareImg(final Activity activity, String image, SHARE_MEDIA shareMedia){
        UMImage umImage = new UMImage(activity,image);
        new ShareAction(activity)
                .withMedia(umImage)
                .setPlatform(shareMedia)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                      //  Toast.makeText(activity,"图片分享成功", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//                        Toast.makeText(activity,throwable.toString(), Toast.LENGTH_LONG).show();
//                        Toast.makeText(activity,"图片分享失败", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                    //    Toast.makeText(activity,"图片分享取消", Toast.LENGTH_LONG).show();
                    }
                }).share();

    }

}
