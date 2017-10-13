package com.google.zxing.utils;

import android.hardware.Camera;

public class CommonUtil {

    public static boolean isCameraCanUse() {
            boolean canUse = true;
            Camera mCamera = null;
            try {
                mCamera = Camera.open();
            } catch (Exception e) {
                canUse = false;
            }
            if (canUse) {
                if (mCamera != null)
                    mCamera.release();
                mCamera = null;
            }
            return canUse;
    }
}
