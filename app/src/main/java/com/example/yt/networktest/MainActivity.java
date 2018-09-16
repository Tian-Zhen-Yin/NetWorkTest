package com.example.yt.networktest;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView responseText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendRequest=(Button)findViewById(R.id.send_request);
        responseText=(TextView)findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.send_request)
        {
            sendRequsetWithOkHttp();
        }//123
    }
    private void sendRequsetWithOkHttp()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
             /*   try
                {
                    URL url=new URL("https://www.baidu.com");
                    connection=(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in=connection.getInputStream();

                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null)
                    {
                        response.append(line);
                    }
                    showResponse(response.toString());
                }*/
             try{
                 OkHttpClient client=new OkHttpClient();
                /* Request request=new Request.Builder()
                         .url("http://10.0.2.2/get_data.json")//注意是http,不是https.
                         .build();*/
                 Request requset = new Request.Builder()
                         // 指定服务器的地址 - 10.0.2.2对于模拟器来说就是电脑本机的IP地址
                         .url("https://sf.bitzo.cn/api/users/avatar/:id")
                         .build();
                 Response response=client.newCall(requset).execute();
                 String responseData=response.body().string();
               /*  paraseJSONWithJSONObject(responseData);*/
               paraseJSONWithGSON(responseData);
                 showResponse(responseData);
             }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
               /* finally {
                    if(reader!=null)
                    {
                        try{
                            reader.close();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    if (connection!=null)
                    {
                        connection.disconnect();
                    }
                }*/
            }
        }).start();
    }
    private void showResponse(final String response)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示到界面
                responseText.setText(response);
            }
        });
    }
   /* private  void  paraseJSONWithJSONObject(String jsonData)
    {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");
                Log.d("MainActivity", "id is:" + id);
                Log.d("MainActivity", "version is:" + version);
                Log.d("MainActivity", "name is:" + name);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }*/
   private void  paraseJSONWithGSON(String jsonData)
   {
       Gson gson=new Gson();
       List<App>appList=gson.fromJson(jsonData, new TypeToken<List<App>>(){}.getType());
       for (App app:appList)
       {
           Log.d("MainActivity", "id is:" + app.getId());
           Log.d("MainActivity", "version is:" + app.getVersion());
           Log.d("MainActivity", "name is:" + app.getName());
       }
   }
}
