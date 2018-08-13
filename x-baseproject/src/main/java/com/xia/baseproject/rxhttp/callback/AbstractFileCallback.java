package com.xia.baseproject.rxhttp.callback;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.CloseUtils;
import com.blankj.utilcode.util.ThreadUtils;
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
@SuppressWarnings("WeakerAccess")
public abstract class AbstractFileCallback extends Callback<File> {
    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;
    private DownloadTask mDownloadTask;

    public AbstractFileCallback(@NonNull LifecycleOwner lifecycleOwner, String destFileDir, String destFileName) {
        super(lifecycleOwner);
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    @SuppressWarnings("unchecked")
    @Override
    public File parseNetworkResponse(final ResponseBody responseBody) {
        if (mDownloadTask == null) {
            mDownloadTask = new DownloadTask(responseBody);
            ThreadUtils.executeByIo(mDownloadTask);
        }
        return null;
    }

    private class DownloadTask extends ThreadUtils.SimpleTask<File> {
        private ResponseBody mResponseBody;

        DownloadTask(ResponseBody responseBody) {
            mResponseBody = responseBody;
        }

        @Nullable
        @Override
        public File doInBackground() {
            return saveFile(mResponseBody);
        }

        @Override
        public void onSuccess(@Nullable File result) {
            onResponse(result);
        }
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
            CloseUtils.closeIO(responseBody, bos, fos, bis, is);
        }
        return file;
    }
}
