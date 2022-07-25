package com.example.penangtravel;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

    FirebaseStorage storage;
    Uri imageURI; //Take it as global
    String downloadURL = "";

//    private Uri uri; // Uri indicates, where the image will be picked from
//    private StorageReference storageRef;
//    private final int PICK_IMAGE_REQUEST = 1; //request code

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_place);

        storage = FirebaseStorage.getInstance();
        mImagePic = findViewById(R.id.imagePic);
        mImagePic.setVisibility(View.GONE);
        mCamera = findViewById(R.id.camera);
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

        //Allow admin to select image from device
        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });
    }

    //Start an activity for result
    //Show the selected image
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if(result != null){
                        mImagePic.setVisibility(View.VISIBLE);
                        mImagePic.setImageURI(result);
                        imageURI = result; //Get and set the image URI
                    }
                }
            }
    );

    @Override
    public void onClick(android.view.View view) {
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
            //uploadImage
            if(imageURI != null){
                //Creating a reference to store the image in firebase storage (images folder)
                StorageReference reference = storage.getReference().child("images/" + UUID.randomUUID().toString());

                //Store the file
                reference.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //get image downloadURL
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                downloadURL = uri.toString();
                                Log.d(TAG, "onSuccess: uri= "+ downloadURL);

                                //Push new place to database
                                Place place = new Place("", downloadURL, name, address, contact, description, area, festival,
                                        food1, food2, hotel1, hotel2, medical1, transport1, transport2, favorite);
                                ref = FirebaseDatabase.getInstance().getReference("Place");
                                ref.push().setValue(place);
                            }
                        });
                    }
                });
            }

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
}
