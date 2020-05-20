package com.dimeno.downloadpicture.interf;

import com.dimeno.downloadpicture.bean.BaseBean;

public interface IDownload {

    //后期继续完善，添加进度和成功失败个数回调
    void start();//开始下载

    void progress(int progress);//下载进度

    void complete();//全部完成，包含即使失败的也算，主控下载列表走完

    void fail(BaseBean baseBean);//失败回调,返回当前失败的bean

    void end();//下载结束

    void error(Exception e);

}
