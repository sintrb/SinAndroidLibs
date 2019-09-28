package com.sin.android.sinlibs.utils;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Robin on 2018/1/15.
 */

public class Downloader {
    private static final String TAG = "Downloader";
    private Context context;
    private String dirType = Environment.DIRECTORY_DOWNLOADS;
    private DownloadManager downloadManager = null;

    private Map<Long, TaskHolder> taskMap = new HashMap<>();

    public Downloader(Context context) {
        this.context = context;
        this.context.registerReceiver(downloadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        this.downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public void destory() {
        this.context.unregisterReceiver(downloadReceiver);
    }

    public interface DownloadListener {
        public void onFinished(long taskid, String filePath, TaskHolder holder);

        public void onFailed(long taskid, String filePath, TaskHolder holder);
    }


    public class TaskHolder {
        public DownloadListener listener;
        public long taskId;
        public String filePath;
        public String url;
    }

    //广播接受者，接收下载状态
    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive");
            checkDownloadStatus();
        }
    };

    private void checkDownloadStatus() {
        Iterator<Map.Entry<Long, TaskHolder>> it = this.taskMap.entrySet().iterator();
        List<Long> delTaskIds = new ArrayList<>(this.taskMap.size());
        while (it.hasNext()) {
            Map.Entry<Long, TaskHolder> itv = it.next();
            if (checkDownloadStatusByTaskId(itv.getValue())) {
                delTaskIds.add(itv.getKey());
            }
        }
        if (delTaskIds.size() > 0) {
            for (Long taskId : delTaskIds) {
                Log.i(TAG, "remove task " + taskId);
                TaskHolder th = this.taskMap.get(taskId);
                if (th != null) {
                    if (th.listener != null) {
                        th.listener.onFailed(taskId, th.filePath, th);
                    }
                    this.taskMap.remove(taskId);
                }
            }
        }
    }

    private boolean checkDownloadStatusByTaskId(TaskHolder holder) {
        long taskId = holder.taskId;
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(taskId);//筛选下载任务，传入任务ID，可变参数
        Cursor c = downloadManager.query(query);
        boolean remove = false;
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            int reason = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_REASON));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.i(TAG, ">>>下载暂停 " + reason);
                case DownloadManager.STATUS_PENDING:
                    Log.i(TAG, ">>>下载延迟 " + reason);
                case DownloadManager.STATUS_RUNNING:
                    Log.i(TAG, ">>>正在下载 " + reason);
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    Log.i(TAG, ">>>下载完成 " + holder.filePath);
                    remove = true;
                    holder.listener.onFinished(taskId, holder.filePath, holder);
                    break;
                case DownloadManager.STATUS_FAILED:
                    Log.i(TAG, ">>>下载失败 " + reason);
                    remove = true;
                    holder.listener.onFailed(taskId, holder.filePath, holder);
                    break;
            }
        } else {
            remove = true;
        }
        return remove;
    }

    public long addTask(String url, String title, String name, String mimeType, DownloadListener listener, boolean unique) {
        if (unique) {
            for (TaskHolder holder : this.taskMap.values()) {
                if (url.equals(holder.url)) {
                    Log.i(TAG, ">>>已经存在 " + url);
                    return 0;
                }
            }
        }
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return 0;
//        }
        TaskHolder holder = new TaskHolder();
        holder.url = url;
        holder.listener = listener;
        holder.filePath = Environment.getExternalStoragePublicDirectory(this.dirType).getAbsolutePath() + File.separator + name;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        Log.i(TAG, "add download " + url + " -> " + holder.filePath);
        request.setDestinationInExternalPublicDir(this.dirType, name);
        request.setTitle(title);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setMimeType(mimeType);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(true);
        request.setVisibleInDownloadsUi(true);
        holder.taskId = downloadManager.enqueue(request);
        this.taskMap.put(holder.taskId, holder);
        return holder.taskId;
    }
}
