package com.xia.baseproject.rxhttp.callback;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.CloseUtils;
import com.xia.baseproject.rxhttp.utils.FileUtils;
import com.xia.baseproject.rxhttp.utils.Platform;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * @author xia
 * @date 2018/7/28.
 */
public abstract class AbstractFileCallback extends Callback<File> {

    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;

    public AbstractFileCallback(@NonNull AppCompatActivity activity, String destFileDir, String destFileName) {
        super(activity);
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    public AbstractFileCallback(@NonNull Fragment fragment, String destFileDir, String destFileName) {
        super(fragment);
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    @Override
    public File parseNetworkResponse(final ResponseBody responseBody) {
        return saveFile(responseBody);
    }

    private File saveFile(ResponseBody responseBody) {
        final File file = FileUtils.createFile(destFileDir, destFileName);
        final InputStream is = responseBody.byteStream();
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(is);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);

            final long total = responseBody.contentLength();
            final byte[] data = new byte[1024 * 4];
            int count;
            long sum = 0;
            int lastProgress = 0;
            while ((count = bis.read(data)) != -1) {
                bos.write(data, 0, count);
                sum += count;
                final int progress = Math.round(sum * 100f / total);
                if (lastProgress != progress) {
                    Platform.post(() -> inProgress(progress, total));
                }
                lastProgress = progress;
            }
            bos.flush();
            fos.flush();
        } catch (IOException e) {
            onError(e.getMessage());
        } finally {
            CloseUtils.closeIO(bos, fos, bis, is);
        }
        return file;
    }
}
