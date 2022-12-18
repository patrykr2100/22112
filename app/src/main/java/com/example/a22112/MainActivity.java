package com.example.a22112;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void aktualizacja(View view) {
        WebView dane = (WebView) findViewById(R.id.lebwiu);
        EditText text = (EditText) findViewById(R.id.tekst);
        String wypisz = text.getText() + "";
        dane.loadData("<style> table { border: 1px solid black; background: grey; } td { border: 1px solid black; } </style>" +
                "<table><tr><td>1</td><td>2</td><td>3</td></tr>" +
                "<tr><td>4</td><td>5</td><td>6</td></tr>" +
                "<tr><td>7</td><td>8</td><td>9</td></tr></table>" +
                "" +
                "<p>" + wypisz + "</p>", "text/html", null);
    }

    public void ShowURL(View view) {
        WebView dane = (WebView) findViewById(R.id.lebwiu);
        EditText text = (EditText) findViewById(R.id.tekst);
        String wypisz = text.getText() + "";
        dane.loadUrl(wypisz);


        TextView tekst2 = (TextView) findViewById(R.id.drugiTekst);
        OkHttpClient client = new OkHttpClient();

        //String url = "https://reqres.in/api/users?page=2";
        String url = "http://api.nbp.pl/api/exchangerates/rates/c/chf";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                tekst2.setText(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tekst2.setText(myResponse);


                            /*try {
                                JSONObject jsonObject = new JSONObject(myResponse);
                                tekst.setText(jsonObject.getString("a"));
                            }catch (Exception err){

                            }*/

                            try {
                                JSONObject jsonObject = new JSONObject(myResponse);
                                String a = (String) jsonObject.get("table");
                                tekst2.setText(a+"");
                            }catch (Exception err){

                            }
                        }
                    });
                }
            }
        });
    }

    public void Back(View view)
    {
        WebView dane = (WebView) findViewById(R.id.lebwiu);
        dane.goBack();
    }

    public void Refresh(View view)
    {
        WebView dane = (WebView) findViewById(R.id.lebwiu);
        dane.reload();
    }



}