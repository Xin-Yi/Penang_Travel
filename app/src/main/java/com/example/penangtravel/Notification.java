package com.example.penangtravel;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Notification extends AppCompatActivity {

    private WebView mWebWeather, mWebFestival;
    private TextView mFestival;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        mWebWeather = findViewById(R.id.webWeather);
        mFestival = findViewById(R.id.festival);
        mWebFestival = findViewById(R.id.webFestival);

        //Load the website URL and display the website in the web view
        mWebWeather.loadUrl("https://www.timeanddate.com/weather/malaysia/penang");

        mWebFestival.loadUrl("https://mypenang.gov.my/events/all-events/page/1/?lg=en");
    }
}
