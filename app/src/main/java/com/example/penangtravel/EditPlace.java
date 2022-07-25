package com.example.penangtravel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class EditPlace extends AppCompatActivity implements android.view.View.OnClickListener{
    private ImageView mImagePic;
    private ImageButton mCamera;
    private TextInputLayout mName, mAddress, mContact, mDescription;
    private TextInputLayout mFestival, mFood1, mFood2, mHotel1, mHotel2, mMedical1, mTransport1, mTransport2;
    private RadioGroup mRGArea;
    private RadioButton mRBIsland, mRBMainland;
    private Button mAdd;
    private String area;
    private DatabaseReference ref;

    private Place place;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_place);

        mImagePic = findViewById(R.id.imagePic);
        //mCamera = findViewById(R.id.camera);
        mName = findViewById(R.id.name);
        mAddress = findViewById(R.id.address);
        mContact = findViewById(R.id.contact);
        mDescription = findViewById(R.id.description);

        mFestival = findViewById(R.id.festival);
        mFood1 = findViewById(R.id.food1);
        mFood2 = findViewById(R.id.food2);
        mHotel1 = findViewById(R.id.hotel1);
        mHotel2 = findViewById(R.id.hotel2);
        mMedical1 = findViewById(R.id.medical1);
        mTransport1 = findViewById(R.id.transport1);
        mTransport2 = findViewById(R.id.transport2);

        mRGArea = findViewById(R.id.rgArea);
        mRBIsland = findViewById(R.id.rbIsland);
        mRBMainland = findViewById(R.id.rbMainland);

        Intent edit = getIntent();
        String id = edit.getStringExtra(LoadAdminPlace.EXTRA_ID);
        place = Place.searchPlaceById(id);

        if (place == null) {
            finish();
        }

        //Get data
        //Get the imageURL and display image
        Picasso.get().load(place.getImageURL()).into(mImagePic);
        mName.getEditText().setText(place.getName());
        mAddress.getEditText().setText(place.getAddress());
        mContact.getEditText().setText(place.getContact());
        mDescription.getEditText().setText(place.getDescription());
        mFestival.getEditText().setText(place.getFestival());
        mFood1.getEditText().setText(place.getFood1());
        mFood2.getEditText().setText(place.getFood2());
        mHotel1.getEditText().setText(place.getHotel1());
        mHotel2.getEditText().setText(place.getHotel2());
        mMedical1.getEditText().setText(place.getMedical1());
        mTransport1.getEditText().setText(place.getTransport1());
        mTransport2.getEditText().setText(place.getTransport2());

        if(place.getArea().equals("Island")){
            mRBIsland.setChecked(true);
        }
        else if (place.getArea().equals("Mainland")){
            mRBMainland.setChecked(true);
        }

        mAdd = findViewById(R.id.add);

        mAdd.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        try {
            if(validate()) {
                String area;

                //See which radio button is selected
                if (mRBIsland.isChecked()) {
                    area = "Island";
                } else {
                    area = "Mainland";
                }

                //Update places details
                place.setName(mName.getEditText().getText().toString());
                place.setAddress(mAddress.getEditText().getText().toString());
                place.setContact(mContact.getEditText().getText().toString());
                place.setDescription(mDescription.getEditText().getText().toString());
                place.setArea(area);
                place.setFestival(mFestival.getEditText().getText().toString());
                place.setFood1(mFood1.getEditText().getText().toString());
                place.setFood2(mFood2.getEditText().getText().toString());
                place.setHotel1(mHotel1.getEditText().getText().toString());
                place.setHotel2(mHotel2.getEditText().getText().toString());
                place.setMedical1(mMedical1.getEditText().getText().toString());
                place.setTransport1(mTransport1.getEditText().getText().toString());
                place.setTransport2(mTransport2.getEditText().getText().toString());

                ref = FirebaseDatabase.getInstance().getReference("Place").child(place.getId());
                ref.setValue(place);
                Toast.makeText(this, "Place details edited successfully!", Toast.LENGTH_SHORT).show();
                Place.place.clear();
                finish();
                Intent edit = new Intent(this,MainActivity.class);
                startActivity(edit);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean validate(){
        if(mName.getEditText().getText().toString().isEmpty()){
            mName.setError("This field is required to enter!");
            return false;
        }
        else {
            mName.setError(null);
        }

        if(mAddress.getEditText().getText().toString().isEmpty()){
            mAddress.setError("This field is required to enter!");
            return false;
        }
        else {
            mAddress.setError(null);
        }

        if(mContact.getEditText().getText().toString().isEmpty()){
            mContact.setError("This field is required to enter!");
            return false;
        }
        else {
            mContact.setError(null);
        }

        if(mDescription.getEditText().getText().toString().isEmpty()){
            mDescription.setError("This field is required to enter!");
            return false;
        }
        else {
            mDescription.setError(null);
        }

        if(mFestival.getEditText().getText().toString().isEmpty()){
            mFestival.setError("This field is required to enter!");
            return false;
        }
        else {
            mFestival.setError(null);
        }

        if(mFood1.getEditText().getText().toString().isEmpty()){
            mFood1.setError("This field is required to enter!");
            return false;
        }
        else {
            mFood1.setError(null);
        }

        if(mFood2.getEditText().getText().toString().isEmpty()){
            mFood2.setError("This field is required to enter!");
            return false;
        }
        else {
            mFood2.setError(null);
        }

        if(mHotel1.getEditText().getText().toString().isEmpty()){
            mHotel1.setError("This field is required to enter!");
            return false;
        }
        else {
            mHotel1.setError(null);
        }

        if(mHotel2.getEditText().getText().toString().isEmpty()){
            mHotel2.setError("This field is required to enter!");
            return false;
        }
        else {
            mHotel2.setError(null);
        }

        if(mMedical1.getEditText().getText().toString().isEmpty()){
            mMedical1.setError("This field is required to enter!");
            return false;
        }
        else {
            mMedical1.setError(null);
        }

        if(mTransport1.getEditText().getText().toString().isEmpty()){
            mTransport1.setError("This field is required to enter!");
            return false;
        }
        else {
            mTransport1.setError(null);
        }

        if(mTransport2.getEditText().getText().toString().isEmpty()){
            mTransport2.setError("This field is required to enter!");
            return false;
        }
        else {
            mTransport2.setError(null);
        }

        return true;
    }
}
