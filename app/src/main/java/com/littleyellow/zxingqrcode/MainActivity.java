package com.littleyellow.zxingqrcode;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.encoding.EncodingHandler;
import com.google.zxing.utils.CommonUtil;

import static com.google.zxing.activity.CaptureActivity.RESULT_CODE_QR_SCAN;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button openQrCodeScan;
    EditText text;
    Button CreateQrCode;
    ImageView QrCode;
    TextView qrCodeText;

    //打开扫描界面请求码
    private int REQUEST_CODE = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openQrCodeScan = (Button) findViewById(R.id.openQrCodeScan);
        text = (EditText) findViewById(R.id.text);
        CreateQrCode = (Button) findViewById(R.id.CreateQrCode);
        QrCode = (ImageView) findViewById(R.id.QrCode);
        qrCodeText = (TextView) findViewById(R.id.qrCodeText);
        openQrCodeScan.setOnClickListener(this);
        CreateQrCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.openQrCodeScan:
                //打开二维码扫描界面
                if(CommonUtil.isCameraCanUse()){
//                    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                    Intent intent = new Intent(MainActivity.this, ExtCaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }else{
                    Toast.makeText(this,"请打开此应用的摄像头权限！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.CreateQrCode:
                try {
                    //获取输入的文本信息
                    String str = text.getText().toString().trim();
                    if(str != null && !"".equals(str.trim())){
                        //根据输入的文本生成对应的二维码并且显示出来
                        Bitmap mBitmap = EncodingHandler.createQRCode(text.getText().toString(), 500,500,drawableToBitmap(getAppIcon(this)));
                        if(mBitmap != null){
                            Toast.makeText(this,"二维码生成成功！",Toast.LENGTH_SHORT).show();
                            QrCode.setImageBitmap(mBitmap);
                        }
                    }else{
                        Toast.makeText(this,"文本信息不能为空！",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (resultCode == RESULT_CODE_QR_SCAN) { //RESULT_OK = -1
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("qr_scan_result");
            //将扫描出的信息显示出来
            qrCodeText.setText(scanResult);
        }
    }

    public static Drawable getAppIcon(Context context) {
        try {
            return context.getPackageManager().getApplicationIcon(context.getPackageName());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {


        Bitmap bitmap = Bitmap.createBitmap(

                drawable.getIntrinsicWidth(),

                drawable.getIntrinsicHeight(),

                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        //canvas.setBitmap(bitmap);

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        drawable.draw(canvas);

        return bitmap;

    }
}
