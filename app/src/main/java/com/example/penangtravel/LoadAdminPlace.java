package com.example.penangtravel;

//import static com.example.penangtravel.MainActivity.EXTRA_ID;

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

public class LoadAdminPlace extends AppCompatActivity implements AdminPlaceAdapter.AdminClickListener{

    public final static String EXTRA_ID =
            "com.example.penangtravel.id";

    private RecyclerView mRecyclerView;
    private AdminPlaceAdapter mAdapter;
    private ArrayList<Place> places;
    private DatabaseReference ref;

    private Place place;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_place_list);

        //Recycler View
        places = new ArrayList<>();
        mAdapter = new AdminPlaceAdapter(this, places, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvPlace);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    protected void onStart() {
        super.onStart();
        places.clear(); //Clear the array list before adding new data in it
        Place.loadPlace(mAdapter, places); //Load data for recycler view
    }

    //When recycler view item is clicked
    @Override
    public void placeClicked(int position, int choice) {
        String id = places.get(position).getId(); //ID of item selected

        if (choice == 1) {
            place = Place.searchPlaceById(id);

            if (place == null)
                finish();

            //Display dialog for confirmation to delete
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Confirmation");
            alert.setMessage("Are you sure you want to delete ? ");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {      //Positive btn is for yes/ok,... , Negative btn is for no/...
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Delete record from database
                    ref = FirebaseDatabase.getInstance().getReference("Place").child(place.getId());
                    ref.removeValue();
                    places.clear();
                    Place.loadPlace(mAdapter, places); //Rerun the recycler view
                    Toast.makeText(LoadAdminPlace.this, "The place is deleted!", Toast.LENGTH_SHORT).show();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(LoadAdminPlace.this, "The place is remained", Toast.LENGTH_SHORT).show();
                }
            });
            alert.show();
        }

        //Edit place details
        else {
            Intent edit = new Intent(this, EditPlace.class);
            edit.putExtra(EXTRA_ID, id);
            startActivity(edit);
        }
    }
}
