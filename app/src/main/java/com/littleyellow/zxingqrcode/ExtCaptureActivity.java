package com.littleyellow.zxingqrcode;

import android.graphics.Bitmap;

import com.google.zxing.Result;
import com.google.zxing.activity.CaptureActivity;

/**
 * Created by 小黄 on 2017/10/12.
 */

public class ExtCaptureActivity extends CaptureActivity {

    @Override
    public void onClickMore() {
        selectSysAlbumPhoto();
    }

    @Override
    public void handResult(int type, Result result, Bitmap barcode) {
        super.handResult(type, result, barcode);
    }
}
