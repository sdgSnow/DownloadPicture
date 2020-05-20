package com.dimeno.downloadpicture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dimeno.downloadpicture.bean.BaseBean;
import com.dimeno.downloadpicture.interf.IDownload;
import com.dimeno.downloadpicture.server.DownloadPicture;

import java.util.ArrayList;
import java.util.List;

import static com.dimeno.downloadpicture.contants.Constans.PICTURE_01;
import static com.dimeno.downloadpicture.contants.Constans.PICTURE_02;
import static com.dimeno.downloadpicture.contants.Constans.PICTURE_03;
import static com.dimeno.downloadpicture.contants.Constans.PICTURE_04;
import static com.dimeno.downloadpicture.contants.Constans.PICTURE_05;
import static com.dimeno.downloadpicture.contants.Constans.PNG;

public class MainActivity extends AppCompatActivity {

    String[] paths = new String[]{PICTURE_01,PICTURE_02,PICTURE_03,PICTURE_04,PICTURE_05};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void download(View view) {
        List<BaseBean> baseBeanList = new ArrayList<>();
        for (int i = 0;i < paths.length; i++){
            BaseBean baseBean = new BaseBean();
            baseBean.setUrl(paths[i]);
            baseBean.setFilename("picture" + i);
            baseBeanList.add(baseBean);
        }
        new DownloadPicture.DownloadPictureBuilder(baseBeanList)
                .setLocalDir("/0000/")
                .setPictureFormat(PNG)
                .build()
                .download(MainActivity.this, new IDownload() {
            @Override
            public void start() {
                Log.i("download","start");
            }

            @Override
            public void progress(int progress) {
                Log.i("download","当前进度为：" + progress);
            }

            @Override
            public void complete() {
                Toast.makeText(MainActivity.this,"下载完成",Toast.LENGTH_SHORT).show();
                Log.i("download","complete");
            }

            @Override
            public void fail(BaseBean baseBean) {
                Toast.makeText(MainActivity.this,"下载失败" + baseBean.getFilename(),Toast.LENGTH_SHORT).show();
                Log.i("download","当前失败图片为：" + baseBean.getFilename());
            }

            @Override
            public void end() {
                Log.i("download","end");
            }

            @Override
            public void error(Exception e) {
                Log.i("download","下载异常：" + e.getMessage());
            }
        });
    }
}
