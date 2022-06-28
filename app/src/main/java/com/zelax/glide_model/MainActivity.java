package com.zelax.glide_model;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zelax.glidelibrary.progress.CircleProgressView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView iv;
    private CircleProgressView circleProgress;
    private String url = "http://fyj.maxvision.com.cn:9091/tpgl/004/20220610103900d39d.jpg";
    private String circleUrl = "https://goss2.cfp.cn/creative/vcg/800/new/VCG41N696159464.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.iv);
        circleProgress = findViewById(R.id.circleProgress);
    }

    public void btnClick(View view) {
        MyGlideUtils.loadProgressImg(this, iv, url, circleProgress,5);
//        MyGlideUtils.loadBlurImg(this,iv,circleUrl);
    }

    public void circleClick(View view) {
//        MyGlideUtils.loadCircleImg(this, iv, circleUrl);
//        MyGlideUtils.loadBlurImg(this, iv , circleUrl,25);
        MyGlideUtils.loadRoundImg(this, iv , circleUrl , 10,R.drawable.ic_launcher_background);
    }


}