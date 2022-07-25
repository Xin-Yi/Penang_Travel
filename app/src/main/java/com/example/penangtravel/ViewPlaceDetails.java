package com.example.penangtravel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ViewPlaceDetails extends AppCompatActivity implements View.OnClickListener{
    private ImageView mImagePic;
    private TextView mName, mAddress, mContact, mDescription, mArea;
    private TextView mFestival, mFood1, mFood2, mHotel1, mHotel2, mMedical1, mTransport1, mTransport2;
    private ImageButton mFavorite, mShare;
    private DatabaseReference ref;
    private String content;

    private Place place;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_view_place);

        mImagePic = findViewById(R.id.imagePic);
        mName = findViewById(R.id.name);
        mAddress = findViewById(R.id.address);
        mContact = findViewById(R.id.contact);
        mDescription = findViewById(R.id.description);
        mArea = findViewById(R.id.area);

        mFestival = findViewById(R.id.festival);
        mFood1 = findViewById(R.id.food1);
        mFood2 = findViewById(R.id.food2);
        mHotel1 = findViewById(R.id.hotel1);
        mHotel2 = findViewById(R.id.hotel2);
        mMedical1 = findViewById(R.id.medical1);
        mTransport1 = findViewById(R.id.transport1);
        mTransport2 = findViewById(R.id.transport2);

        mFavorite = findViewById(R.id.favorite);
        mShare = findViewById(R.id.share);

        //Get item selected from LoadUserPlace
        Intent view = getIntent();
        String id = view.getStringExtra(LoadUserPlace.EXTRA_ID);
        place = Place.searchPlaceById(id);

        if (place == null) {
            finish();
        }

        //Get data
        //Get the imageURL and display image
        Picasso.get().load(place.getImageURL()).into(mImagePic);
        mName.setText(place.getName());
        mAddress.setText(place.getAddress());
        mContact.setText(place.getContact());
        mDescription.setText(place.getDescription());
        mArea.setText(place.getArea());
        mFestival.setText(place.getFestival());
        mFood1.setText(place.getFood1());
        mFood2.setText(place.getFood2());
        mHotel1.setText(place.getHotel1());
        mHotel2.setText(place.getHotel2());
        mMedical1.setText(place.getMedical1());
        mTransport1.setText(place.getTransport1());
        mTransport2.setText(place.getTransport2());

        if(place.getFavorite().equals("no")){
            mFavorite.setImageResource(R.drawable.ic_love_border);
        }
        else if(place.getFavorite().equals("yes")){
            mFavorite.setImageResource(R.drawable.ic_love_full);
        }

        mFavorite.setOnClickListener(this); //Call onClick() method
        mShare.setOnClickListener(this);
        mFood1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.favorite:
                if(place.getFavorite().equals("no")){
                    place.setFavorite("yes");

                    ref = FirebaseDatabase.getInstance().getReference("Place").child(place.getId());
                    ref.setValue(place);
                    mFavorite.setImageResource(R.drawable.ic_love_full);
                    Toast.makeText(ViewPlaceDetails.this, "Place added to Favorite List!", Toast.LENGTH_SHORT).show();
                }
                else if(place.getFavorite().equals("yes")){
                    place.setFavorite("no");

                    ref = FirebaseDatabase.getInstance().getReference("Place").child(place.getId());
                    ref.setValue(place);
                    mFavorite.setImageResource(R.drawable.ic_love_border);
                    Toast.makeText(ViewPlaceDetails.this, "Place remove from Favorite List!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.share:
                //Share place details to other
                content = "Place Name:  \n" + place.getName() + "\n\nAddress: \n" + place.getAddress() +
                        "\n\n" + "Contact: " + place.getContact() + "\n\n" + "Area: " + place.getArea() +
                        "\n\n" + "Description: \n" + place.getDescription();

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain"); //Sets the content of the email to plain text
                share.putExtra(Intent.EXTRA_TEXT, content);

                if (share.resolveActivity(getPackageManager()) != null) {
                    startActivity(share);
                } else {
                    Log.d("ImplicitIntents", "Can't handle this intent!");
                }
                break;
            case R.id.food1:
                String name = mFood1.getText().toString();

        }
    }
}
