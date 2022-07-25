package com.example.penangtravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String EXTRA_ID =
            "com.example.penangtravel.id";

    public Context mContext;
    private Button mIsland, mMainland,mSearch;
    private EditText mSearchBar;
    private ImageButton mAdd;
    public final static String option = "";
    public final static String name = "";

    BottomNavigationView btmNavigation;

    //MeowBottomNavigation btmNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        mIsland = findViewById(R.id.island);
        mMainland = findViewById(R.id.mainland);
        mSearch = findViewById(R.id.search);
        mSearchBar = findViewById(R.id.searchBar);
        mAdd = findViewById(R.id.add);
        btmNavigation = findViewById(R.id.btmNavigation);

        mIsland.setOnClickListener(this);
        mMainland.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mAdd.setOnClickListener(this);

        btmNavigation.setSelectedItemId(R.id.home); //Set "Home" selected
        btmNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.notification:
                        startActivity(new Intent(getApplicationContext(),Notification.class));
                        //Call immediately after one of the flavors of #startActivity(Intent) or #finish to specify an explicit transition animation to perform next.
                        //use for the incoming/outgoing activity. Use 0 for no animation.
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        mContext = getApplicationContext(); //Is only available on the Activity class and in the Service
        MyGlobalIdentity globalIdentity = (MyGlobalIdentity) getApplicationContext();

        //Check identity whether to show add button
        //Normal User View (No add button)
        if(!(globalIdentity.getMyIdentity().equals("admin"))){
            mAdd.setVisibility(View.GONE);
        }
        //Admin User View (Have add button)
        else if(globalIdentity.getMyIdentity().equals("admin")){
            mAdd.setVisibility(View.VISIBLE);
        }

        //showBottomNavigation();

//        if(!(globalIdentity.getMyIdentity().equals("admin"))){
//            mAdd.setVisibility(View.GONE);
//        }

//        Intent test = getIntent();
//        String id = test.getStringExtra(LoadUserPlace.EXTRA_ID);
//
//        if(id.equals("")){
//
//        }

    }

    @Override
    public void onClick(View view) {

        String place = mSearchBar.getText().toString();
        MyGlobalIdentity globalIdentity = (MyGlobalIdentity) getApplicationContext();

        switch (view.getId()){
            case R.id.island:
                //Check identity for edit and delete access
                //Normal User View (No access)
                if(!(globalIdentity.getMyIdentity().equals("admin"))){
                    Intent island = new Intent(this,LoadUserPlace.class);
                    island.putExtra(option,"island");
                    startActivity(island);
                }
                //Admin User View (Have access)
                else if(globalIdentity.getMyIdentity().equals("admin")){
                    Intent island = new Intent(this,LoadAdminPlace.class);
                    island.putExtra(option,"island");
                    startActivity(island);
                }
//                Intent island = new Intent(this,LoadUserPlace.class);
//                island.putExtra(option,"island");
//                startActivity(island);
                break;
            case R.id.mainland:
                //Check identity for edit and delete access
                //Normal User View (No access)
                if(!(globalIdentity.getMyIdentity().equals("admin"))){
                    Intent mainland = new Intent(this,LoadUserPlace.class);
                    mainland.putExtra(option,"mainland");
                    startActivity(mainland);
                }
                //Admin User View (Have access)
                else if(globalIdentity.getMyIdentity().equals("admin")){
                    Intent mainland = new Intent(this,LoadAdminPlace.class);
                    mainland.putExtra(option,"mainland");
                    startActivity(mainland);
                }
//                Intent mainland = new Intent(this,LoadUserPlace.class);
//                mainland.putExtra(option,"mainland");
//                startActivity(mainland);
                break;
            case R.id.search:
                //Check identity
                //Normal User View
                if(!(globalIdentity.getMyIdentity().equals("admin"))){
                    Intent search = new Intent(this,LoadUserPlace.class);
                    search.putExtra(option,"search");
                    search.putExtra(name,place);
                    startActivity(search);
                }
                //Admin User View
                else if(globalIdentity.getMyIdentity().equals("admin")){
                    Intent search = new Intent(this,LoadAdminPlace.class);
                    search.putExtra(option,"search");
                    search.putExtra(name,place);
                    startActivity(search);
                }
//                Intent search = new Intent(this,LoadUserPlace.class);
//                search.putExtra(option,"search");
//                search.putExtra(name,place);
//                startActivity(search);
                break;
            case R.id.add:
                Intent add = new Intent(this,AddPlace.class);
                startActivity(add);
                break;
        }
    }

//    public void showBottomNavigation(){
//        //Add menu item
//        btmNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_notification));
//        btmNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_home));
//        btmNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_profile));
//
//        btmNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
//            @Override
//            public Unit invoke(MeowBottomNavigation.Model model) {
//                // YOUR CODES
//                return null;
//            }
//        });

        //btmNavigation.setOnShowListener();
//
//        btmNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
//            @Override
//            public void onShow(MeowBottomNavigation.Model.item) {
//                switch(item.getId()){
//                    case 1:
//                        break;
//                }
//            }
//        });
//
//        //Set Homepage as default selected page
//        btmNavigation.show(2,true);
//
//        btmNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener(){
//
//            @Override
//            public void onClickItem(MeowBottomNavigation.Model item) {
//                switch (item.getId()){
//                    case 1:
//                        break;
//                    case 2:
//                        Toast.makeText(getApplicationContext(),"Home", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 3:
//                        //break;
//                }
//            }
//        });
//
//    }
}