package com.dimeno.downloadpicture.server;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.dimeno.downloadpicture.bean.BaseBean;
import com.dimeno.downloadpicture.utils.FileUtils;
import com.dimeno.downloadpicture.utils.RegexUtils;

import java.io.File;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.dimeno.downloadpicture.contants.CharFormatPattern.DIR;

/**
 * 基于glide和rxjava的图片下载工具
 * 1.可设置本地存储路径，默认为根目录的DownloadPicture文件夹
 * 2.可匹配你的路径是否符合命名规则
 * 3.可设置是否替换掉不符个规则的命名特殊字符
 * */
public class DownloadPicture {

    private int downloadSuccess = 0;//记录下载成功的数量，成功加1
    private int downloadFail = 0;//记录下载失败的数量，失败加1
    private int downloaded = 0;//记录已经下载的数量，成功失败都加1
    private String localDir = "/DownloadPicture/";

    public void download(final Context context, final List<BaseBean> filelist, final IDownload iDownload) {
        if(filelist != null && filelist.size() > 0){
            iDownload.start();
            for (final BaseBean baseBean : filelist) {
                Observable<File> fileObservable = Observable.create(new ObservableOnSubscribe<File>() {
                    @Override
                    public void subscribe(ObservableEmitter<File> e) throws Exception {
                        //通过gilde下载得到file文件,这里需要注意android.permission.INTERNET权限
                        e.onNext(Glide.with(context)
                                .load(baseBean.getUrl())
                                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                .get());
                        e.onComplete();
                    }
                });
                fileObservable.subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Consumer<File>() {
                            @Override
                            public void accept(File file) throws Exception {
                                //获取到下载得到的图片，进行本地保存
                                File pictureFolder = Environment.getExternalStorageDirectory();
                                //第二个参数为你想要保存的目录名称
                                boolean match = RegexUtils.isMatch(DIR, localDir);
                                if (!match) {
                                    localDir = replaceIllegalFileName(localDir);
                                    Toast.makeText(context,"存储目录命名无效",Toast.LENGTH_SHORT).show();
                                }
                                getPhoto(pictureFolder + localDir);
                                File appDir = new File(pictureFolder, localDir);
                                if (!appDir.exists()) {
                                    appDir.mkdirs();
                                }
                                String fileName = baseBean.getFilename();
                                File destFile = new File(appDir, fileName);
                                //把gilde下载得到图片复制到定义好的目录中去
                                try {
                                    boolean b = FileUtils.copyFile(file, destFile);
                                    if (b) {
                                        downloadSuccess++;
                                        downloaded++;
                                        int percent = (100 * downloadSuccess) / filelist.size();
                                        iDownload.progress(percent);
                                        Log.i("tag","保存成功" + fileName);
                                    } else {
                                        downloaded++;
                                        downloadFail++;
                                        iDownload.fail(baseBean);
                                        Log.i("tag","保存失败" + fileName);
                                        //保存失败直接结束下载任务
                                        return;
                                    }
                                    //判断已下载数量和
                                    if (downloaded == filelist.size()) {
                                        downloadSuccess = 0;
                                        downloadFail = 0;
                                        downloaded = 0;
                                        iDownload.complete();
                                        iDownload.end();
                                    }
                                } catch (Exception e) {
                                    iDownload.error(e);
                                }
                            }
                        });
            }
        }
    }

    public interface IDownload {
        //后期继续完善，添加进度和成功失败个数回调
        void start();//开始下载

        void progress(int progress);//下载进度

        void complete();//全部完成，包含即使失败的也算，主控下载列表走完

        void fail(BaseBean baseBean);//失败回调,返回当前失败的bean

        void end();//下载结束

        void error(Exception e);
    }

    public String getLocalDir() {
        return localDir;
    }

    public void setLocalDir(String localDir) {
        this.localDir = localDir;
    }

    public String getPhoto(String path) {
        File f = new File(path);//PccContants.PICTURE_PATH
        if (!f.exists()) {
            f.mkdirs();
        }
        return "";
    }

    /**
     * 用空字符串替换点目录路径的无效特殊字符
     * */
    private static String replaceIllegalFileName(String fileName){
        if(!TextUtils.isEmpty(fileName)) {
            char[] chars = fileName.toCharArray();
            StringBuilder sb = new StringBuilder();
            for (char aChar : chars) {
                String valueOf = String.valueOf(aChar);
                if(" ".equals(valueOf) || "/".equals(valueOf) || ":".equals(valueOf) || "*".equals(valueOf) || "?".equals(valueOf) || "<".equals(valueOf) || ">".equals(valueOf) || "|".equals(valueOf) || "\\".equals(valueOf)){
                    sb.append("");
                }else {
                    sb.append(aChar);
                }
            }
            return sb.toString();
        }else {
            return fileName;
        }
    }
}
