package com.example.penangtravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String EXTRA_ID =
            "com.example.penangtravel.id";

    public Context mContext;
    private Button mIsland, mMainland,mSearch;
    private EditText mSearchBar;
    private ImageButton mAdd;
    public final static String option = "";
    public final static String name = "";

    MeowBottomNavigation btmNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        mIsland = findViewById(R.id.island);
        mMainland = findViewById(R.id.mainland);
        mSearch = findViewById(R.id.search);
        mSearchBar = findViewById(R.id.searchBar);
        mAdd = findViewById(R.id.add);

        mIsland.setOnClickListener(this);
        mMainland.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mAdd.setOnClickListener(this);

        mContext = getApplicationContext(); //Is only available on the Activity class and in the Service
        MyGlobalIdentity globalIdentity = (MyGlobalIdentity) getApplicationContext();

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

        switch (view.getId()){
            case R.id.island:
                Intent island = new Intent(this,LoadUserPlace.class);
                island.putExtra(option,"island");
                startActivity(island);
                break;
            case R.id.mainland:
                Intent mainland = new Intent(this,LoadUserPlace.class);
                mainland.putExtra(option,"mainland");
                startActivity(mainland);
                break;
            case R.id.search:
                Intent search = new Intent(this,LoadUserPlace.class);
                search.putExtra(option,"search");
                search.putExtra(name,place);
                startActivity(search);
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