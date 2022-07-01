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

public class LoadFavoriteList extends AppCompatActivity implements FavoriteListAdapter.FavoriteClickListener{
    public final static String EXTRA_ID =
            "com.example.penangtravel.id";

    private RecyclerView mRecyclerView;
    private FavoriteListAdapter mAdapter;
    private ArrayList<Place> places;
    private DatabaseReference ref;

    private Place place;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_place_list);

        //Recycler View
        places = new ArrayList<>();
        mAdapter = new FavoriteListAdapter(this, places, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvPlace);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    protected void onStart() {
        super.onStart();
        places.clear(); //Clear the array list before adding new data in it
        Place.loadFavoriteList(mAdapter, places); //Load data for recycler view
    }

    //When recycler view item is clicked
    @Override
    public void placeClicked(int position, int choice) {
        String id = places.get(position).getId(); //ID of item selected

        //Remove from favorite list
        if (choice == 1) {
            place = Place.searchPlaceById(id);

            if (place == null)
                finish();

            //Display dialog for confirmation to remove place from favorite list
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Confirmation");
            alert.setMessage("Are you sure you want to remove the place from favorite list ? ");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {      //Positive btn is for yes/ok,... , Negative btn is for no/...
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Update record to database and display recycler view again
                    ref = FirebaseDatabase.getInstance().getReference("Place").child(place.getId());
                    place.setFavorite("no"); //Set favorite variable to no, to remove from favorite list
                    ref.setValue(place); //Update database for variable "favorite"

                    places.clear();
                    Place.loadFavoriteList(mAdapter, places); //Rerun the recycler view
                    Toast.makeText(LoadFavoriteList.this, "The place is removed from favorite list!", Toast.LENGTH_SHORT).show();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(LoadFavoriteList.this, "The place is remained in favorite list", Toast.LENGTH_SHORT).show();
                }
            });
            alert.show();
        }

        //View place details
        else {
            Intent view = new Intent(this, ViewPlaceDetails.class);
            view.putExtra(EXTRA_ID, id);
            startActivity(view);
        }
    }
}
