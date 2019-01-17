package com.rezkyaulia.android.network_app;

import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.rezkyaulia.android.light_optimization_data.HttpCore;
import com.rezkyaulia.android.light_optimization_data.NetworkClient;
import com.rezkyaulia.android.light_optimization_data.RequestListener.ParsedCallback;
import com.rezkyaulia.android.network_app.model.ApiResponse;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    String url = "http://api.themoviedb.org/3/movie/popular?api_key=".concat("b77a9c9af1b4434dcbbacdde72879e7c");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new syncOkhttp().execute();


        getNetworkAsync(1);
        getNetworkAsync(2);
        getNetworkAsync(3);
        getNetworkAsync(4);
        getNetworkAsync(5);


    }

    void run(final int i) throws IOException {
        Timber.e("POSITION START : "+i);
        OkHttpClient client = new OkHttpClient();
        setContentView(R.layout.activity_main);

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();
                Timber.e(i+" | "+System.currentTimeMillis());

            }
        });
    }

    private void runLoadTask(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new loadtask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 1);
            new loadtask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 2);
            new loadtask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 3);
            new loadtask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 4);
            new loadtask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 5);
            new loadtask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 6);
        }else{
            new loadtask().execute(1);
            new loadtask().execute(2);
            new loadtask().execute(3);
            new loadtask().execute(4);
            new loadtask().execute(5);
            new loadtask().execute(6);
        }
    }

    private class syncOkhttp extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            getNetworkSync(1);
            getNetworkSync(2);
            getNetworkSync(3);
            getNetworkSync(4);
            getNetworkSync(5);

            return null;
        }
    }

    private class loadtask extends AsyncTask<Integer,Void,Void>{


        @Override
        protected Void doInBackground(Integer... params) {

//            try {
                for (int i = 0;i<20;i++){
                    Timber.e("PROCES "+params[0]);
//                    Thread.sleep(params[0] * 1000);
                }
//            } catch (InterruptedException e) {
//                Timber.e("ERROR : "+e.getMessage());
//            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    private void getNetworkSync(final int i){
        try {
             ApiResponse response = NetworkClient.getInstance()
                    .as(ApiResponse.class)
                     .withURL("http://api.themoviedb.org/3/movie/popular?api_key=".concat("b77a9c9af1b4434dcbbacdde72879e7c".concat("&page="+i)))
                     .getSyncFuture();

            Timber.e("GNS : "+i+ " ("+System.currentTimeMillis()+") ");


        } catch (IOException e) {
            Timber.e("ERROR GNS : "+e.getMessage());
        } catch (Exception e) {
            Timber.e("ERROR GNS : "+e.getMessage());

        }
    }

    private void getNetworkAsync(final int i){
        try {

            NetworkClient.getInstance()
                    .as(ApiResponse.class)
                    .withURL("http://api.themoviedb.org/3/movie/popular?api_key=".concat("b77a9c9af1b4434dcbbacdde72879e7c".concat("&page="+i)))
                    .getAsFuture(new ParsedCallback<ApiResponse>() {
                        @Override
                        public void onCompleted(ApiResponse result) {
                            Timber.e("GNA : "+i+ " ("+System.currentTimeMillis()+") ");

                        }

                        @Override
                        public void onFailure(IOException e) {

                        }
                    });


        } catch (Exception e) {
            Timber.e("ERROR : ".concat(e.getMessage()));
        }
    }
}
