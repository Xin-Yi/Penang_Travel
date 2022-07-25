package com.example.penangtravel;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Notification extends AppCompatActivity {

    private WebView mWebWeather, mWebFestival;
    private TextView mFestival;
    BottomNavigationView btmNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        mWebWeather = findViewById(R.id.webWeather);
        mFestival = findViewById(R.id.festival);
        mWebFestival = findViewById(R.id.webFestival);
        btmNavigation = findViewById(R.id.btmNavigation);

        //Load the website URL and display the website in the web view
        mWebWeather.loadUrl("https://www.timeanddate.com/weather/malaysia/penang");

        mWebFestival.loadUrl("https://mypenang.gov.my/events/all-events/page/1/?lg=en");

        btmNavigation.setSelectedItemId(R.id.notification); //Set "Notification" selected
        btmNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.notification:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        //Call immediately after one of the flavors of #startActivity(Intent) or #finish to specify an explicit transition animation to perform next.
                        //use for the incoming/outgoing activity. Use 0 for no animation.
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
