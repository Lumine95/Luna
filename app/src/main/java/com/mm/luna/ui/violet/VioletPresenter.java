package com.mm.luna.ui.violet;

import android.os.AsyncTask;

import com.mm.luna.base.BasePresenterImpl;
import com.orhanobut.logger.Logger;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ZMM on 2018/2/6.
 */

public class VioletPresenter extends BasePresenterImpl<VioletContract.View> implements VioletContract.Presenter {

    public VioletPresenter(VioletContract.View view) {
        super(view);
    }

    @Override
    public void getVideoList() {
        new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                view.ShowLoadingDialog("正在加载正在加载正在加载正在加载");
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    URL url = new URL("https://raw.githubusercontent.com/moonlove95/Luna/master/json/violet.txt");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.setReadTimeout(5000);
                    BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    byte[] data = new byte[1024 * 2];
                    int current = 0;
                    while ((current = bis.read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, current);
                    }
                    String str = new String(buffer.toByteArray(), "utf-8");
                    String[] split = str.split(",");
                    ArrayList<String> videos = new ArrayList<>();
                    for (int i = 0; i < split.length; i++) {
                        videos.add(split[i]);
                    }
                    view.setData(videos);
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.d("getVideoList:--- " + e.getMessage());
                }
                return null;
            }
        }.execute();
    }
}
