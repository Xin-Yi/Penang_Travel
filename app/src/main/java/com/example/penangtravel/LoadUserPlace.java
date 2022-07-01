package com.example.penangtravel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LoadUserPlace extends AppCompatActivity implements UserPlaceAdapter.UserClickListener {
    public final static String EXTRA_ID =
            "com.example.penangtravel.id";

    private RecyclerView mRecyclerView;
    private UserPlaceAdapter mAdapter;
    private ArrayList<Place> places;
    private DatabaseReference ref;

    private Place place;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_place_list);

        //Recycler View
        places = new ArrayList<>();
        mAdapter = new UserPlaceAdapter(this, places, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvPlace);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    protected void onStart() {
        super.onStart();
        places.clear(); //Clear the array list before adding new data in it

        Intent load = getIntent();
        String option = load.getStringExtra(MainActivity.option);
        String placeName = load.getStringExtra(MainActivity.name);

        if(option.equals("island")){
            Place.loadUserIslandPlace(mAdapter, places); //Load data for user place recycler view(Island)
        }
        else if(option.equals("mainland")){
            Place.loadUserMainlandPlace(mAdapter, places); //Load data for user place recycler view(Mainland)
        }
        else if(option.equals("search")){
            Place.loadUserSearchPlace(mAdapter, places, placeName); //Load data for user place recycler view(Mainland)
        }

    }

    //When recycler view item is clicked
    @Override
    public void placeClicked(int position) {
        String id = places.get(position).getId(); //ID of item selected

        //Edit place details
        Intent view = new Intent(this, ViewPlaceDetails.class);
        view.putExtra(EXTRA_ID, id);
        startActivity(view);
    }
}
