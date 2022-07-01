package com.example.penangtravel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.UUID;

public class AddPlace extends AppCompatActivity implements android.view.View.OnClickListener{
    private ImageView mImagePic;
    private ImageButton mCamera;
    private TextInputLayout mName, mAddress, mContact, mDescription;
    private TextInputLayout mFestival, mFood1, mFood2, mHotel1, mHotel2, mMedical1, mTransport1, mTransport2;
    private RadioGroup mRGArea;
    private RadioButton mRBIsland, mRBMainland;
    private Button mAdd;
    private DatabaseReference ref;

    private Uri uri; // Uri indicates, where the image will be picked from
    private StorageReference storageRef;
    private final int PICK_IMAGE_REQUEST = 1; //request code

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
        mRBIsland.setChecked(true);

        mAdd = findViewById(R.id.add);

        mAdd.setOnClickListener(this);


//        mCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Toast.makeText(AddPlace.this, "Select camera", Toast.LENGTH_SHORT).show();
//                uploadPlaceImage();
//            }
//        });
    }

    @Override
    public void onClick(android.view.View view) {
        //Toast.makeText(AddPlace.this, "Click success", Toast.LENGTH_SHORT).show();
        //Get place details
        String name = mName.getEditText().getText().toString();
        String address = mAddress.getEditText().getText().toString();
        String contact = mContact.getEditText().getText().toString();
        String description = mDescription.getEditText().getText().toString();
        String festival = mFestival.getEditText().getText().toString();
        String food1 = mFood1.getEditText().getText().toString();
        String food2 = mFood2.getEditText().getText().toString();
        String hotel1 = mHotel1.getEditText().getText().toString();
        String hotel2 = mHotel2.getEditText().getText().toString();
        String medical1 = mMedical1.getEditText().getText().toString();
        String transport1 = mTransport1.getEditText().getText().toString();
        String transport2 = mTransport2.getEditText().getText().toString();
        String area;
        String favorite = "no";

        if(mRBIsland.isChecked()){
            area = "Island";
        }
        else{
            area = "Mainland";
        }

        if(validate()){
            //Push new place to database
            Place place = new Place("", name, address, contact, description, area, festival,
                    food1, food2, hotel1, hotel2, medical1, transport1, transport2, favorite);
            ref = FirebaseDatabase.getInstance().getReference("Place");
            ref.push().setValue(place);
            Toast.makeText(this, "Place added successfully!", Toast.LENGTH_SHORT).show();
            //Place.place.clear();
            finish();
        }
        else{
            Toast.makeText(this, "Some field missing!", Toast.LENGTH_SHORT).show();
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

//    public void uploadPlaceImage(){
//        //instance for firebase storage and StorageReference
//        storageRef = FirebaseStorage.getInstance().getReference();
//
//        mCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectImage();
//            }
//        });
//
//
//    }
//
//    //Intent creates an image chooser dialog, allow user to browse through the device gallery to select the image
//    private void selectImage(){
//        Intent image = new Intent();
//        image.setType("image/*");
//        getIntent().setAction(Intent.ACTION_GET_CONTENT);
//        //Received the selected image
//        //startActivityForResult(Intent.createChooser(image,"Select image"), PICK_IMAGE_REQUEST);
//        startActivityForResult(Intent.createChooser(image,"Select image"), PICK_IMAGE_REQUEST);
//
//    }
//
//    //Display the image
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //Check if request code is PICK_IMAGE_REQUEST and resultCode is RESULT_OK
//        //Then set image in the image view
//        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
//                && data != null && data.getData() != null){
//
//            //Get the URI of data
//
//            try {
//                //Setting image on image view using Bitmap
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                mImagePic.setImageBitmap(bitmap);
//            }
//
//            catch (IOException e) {
//                // Log the exception
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void uploadImage(){
//        if(uri != null){
//            //Define child of storageRef
//            StorageReference ref = storageRef.child("images/" + UUID.randomUUID().toString());
//
//
//        }
//    }
}
