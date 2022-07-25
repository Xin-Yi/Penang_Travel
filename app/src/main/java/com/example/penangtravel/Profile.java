package com.example.penangtravel;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    public Context mContext;
    private Button mFavorite, mLogin;
    private ImageButton mLogout;
    private TextView mUser, mAdminLogin, mLogOutWord;
    private EditText mName, mPassword;
    private View mView, root;
    BottomNavigationView btmNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        //getSupportActionBar().hide();

        mView = findViewById(R.id.profile);
        root = mView.getRootView();
        mFavorite = findViewById(R.id.favorite);
        mLogin = findViewById(R.id.login);
        mLogout = findViewById(R.id.logout);
        mLogOutWord = findViewById(R.id.logoutWord);
        mUser = findViewById(R.id.user);
        mAdminLogin = findViewById(R.id.adminLogin);
        mName = findViewById(R.id.name);
        mPassword = findViewById(R.id.password);
        btmNavigation = findViewById(R.id.btmNavigation);

        mLogin.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        mFavorite.setOnClickListener(this);

        //Bottom Navigation Bar
        btmNavigation.setSelectedItemId(R.id.profile); //Set "Profile" selected
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
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });

        mContext = getApplicationContext(); //Is only available on the Activity class and in the Service
        MyGlobalIdentity globalIdentity = (MyGlobalIdentity) getApplicationContext();

        //Check identity to see which profile view to show
        //Normal User View
        if(!(globalIdentity.getMyIdentity().equals("admin"))){
            mUser.setText("Guest");
            mAdminLogin.setVisibility(View.VISIBLE);
            mName.setVisibility(View.VISIBLE);
            mName.setText("");
            mPassword.setText("");
            mPassword.setVisibility(View.VISIBLE);
            mLogin.setVisibility(View.VISIBLE);
            mFavorite.setVisibility(View.VISIBLE);
            mLogout.setVisibility(View.GONE);
            mLogOutWord.setVisibility(View.GONE);
            root.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        //Admin User View
        else if(globalIdentity.getMyIdentity().equals("admin")){
            mUser.setText("Admin");
            mAdminLogin.setVisibility(View.GONE);
            mName.setVisibility(View.GONE);
            mPassword.setVisibility(View.GONE);
            mLogin.setVisibility(View.GONE);
            mFavorite.setVisibility(View.GONE);
            mLogout.setVisibility(View.VISIBLE);
            mLogOutWord.setVisibility(View.VISIBLE);
            root.setBackgroundColor(Color.parseColor("#f8fcc0"));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //admin login
            case R.id.login:
                String name, password;
                name = mName.getText().toString();
                password = mPassword.getText().toString();
                //Validate username and password correct and proceed
                if(validate(name,password)){
                    MyGlobalIdentity globalIdentity = (MyGlobalIdentity) getApplicationContext();
                    globalIdentity.setMyIdentity("admin"); //Update identity to admin after login admin account

                    //Profile in admin view
                    mUser.setText("Admin");
                    mAdminLogin.setVisibility(View.GONE);
                    mName.setVisibility(View.GONE);
                    mPassword.setVisibility(View.GONE);
                    mLogin.setVisibility(View.GONE);
                    mFavorite.setVisibility(View.GONE);
                    mLogout.setVisibility(View.VISIBLE);
                    mLogOutWord.setVisibility(View.VISIBLE);
                    root.setBackgroundColor(Color.parseColor("#f8fcc0"));
                    Toast.makeText(Profile.this, "Login to admin account successfully!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Profile.this, "Wrong username or password input!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.logout:

                //Display dialog for confirmation to logout
                final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Confirmation");
                alert.setMessage("Are you sure you want to logout ? ");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {      //Positive btn is for yes/ok,... , Negative btn is for no/...
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyGlobalIdentity globalIdentity = (MyGlobalIdentity) getApplicationContext();
                        globalIdentity.setMyIdentity("user"); //Update identity to user after log out admin account

                        //Update profile to normal user view
                        mUser.setText("Guest");
                        mAdminLogin.setVisibility(View.VISIBLE);
                        mName.setVisibility(View.VISIBLE);
                        mName.setText("");
                        mPassword.setText("");
                        mPassword.setVisibility(View.VISIBLE);
                        mLogin.setVisibility(View.VISIBLE);
                        mFavorite.setVisibility(View.VISIBLE);
                        mLogout.setVisibility(View.GONE);
                        mLogOutWord.setVisibility(View.GONE);
                        root.setBackgroundColor(Color.parseColor("#ffffff"));
                        Toast.makeText(Profile.this, "Logout from admin account successfully!", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Profile.this, "Logout cancelled!", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();


                break;
            case R.id.favorite:
                Intent favorite = new Intent(this,LoadFavoriteList.class);
                startActivity(favorite);
        }
    }

//    identity = globalIdentity.getMyIdentity();
//                    if(identity.equals("admin")){
//        mFavorite.setVisibility(View.GONE);
//    }

    public static boolean validate(String name, String password){
        if(!name.equals("admin") || !password.equals("admin")){
            return false;
        }
        else{
            return true;
        }
    }
}
