package org.anyrtc.zxing;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.zxing.Result;

import org.anyrtc.activity.BaseActivity;
import org.anyrtc.activity.LiveActivity;
import org.anyrtc.rtcp.R;


public class ScanActivity extends BaseActivity implements ScanListener {


    SurfaceView capturePreview;
    RelativeLayout topMask;
    RelativeLayout bottomMask;
    ImageView leftMask;
    ImageView rightMask;
    ImageView captureScanLine;
    MyImageView scanImage;
    RelativeLayout captureCropView;
    RelativeLayout captureContainer;
    ScanManager scanManager;
    public int QRCODE_MODE = 0X200;
    ImageView ivTopBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        scanManager.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        scanManager.onPause();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_capture;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        capturePreview= (SurfaceView) findViewById(R.id.capture_preview);
        topMask= (RelativeLayout) findViewById(R.id.top_mask);
        bottomMask= (RelativeLayout) findViewById(R.id.bottom_mask);
        leftMask= (ImageView) findViewById(R.id.left_mask);
        rightMask= (ImageView) findViewById(R.id.right_mask);
        captureScanLine= (ImageView) findViewById(R.id.capture_scan_line);
        scanImage= (MyImageView) findViewById(R.id.scan_image);
        captureCropView= (RelativeLayout) findViewById(R.id.capture_crop_view);
        captureContainer= (RelativeLayout) findViewById(R.id.capture_container);
        ivTopBack= (ImageView) findViewById(R.id.iv_top_back);
        ivTopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAnimActivity();
            }
        });

        scanManager = new ScanManager(this, capturePreview, captureContainer, captureCropView, captureScanLine, QRCODE_MODE, this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void scanResult(Result rawResult, Bundle bundle) {


        String content = rawResult.getText();


        if (!TextUtils.isEmpty(content)){
            Intent intent = new Intent(ScanActivity.this, LiveActivity.class);
            intent.putExtra("isPublish", false);
            intent.putExtra("id", content);
            startActivity(intent);
            finishAnimActivity();
        }else {
            Toast.makeText(this,"未找到通道ID",Toast.LENGTH_SHORT).show();
            scanManager.reScan();
        }
    }

    @Override
    public void scanError(Exception e) {

    }

}