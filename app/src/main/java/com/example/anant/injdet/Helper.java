package com.example.anant.injdet;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Helper extends AppCompatActivity {
    private FileDialog fileDialog;
    private int RESULT_LOAD_IMAGE = 1;
    private DatabaseReference mDatabase;
    private FirebaseDatabase database;
    private Bitmap resized;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);
        Button cp = (Button)findViewById(R.id.cp);
        cp.setOnClickListener(buttonClickListener);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(buttonClickListener);

        //Address adress = new Address(this);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.imageView);

            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            resized = Bitmap.createScaledBitmap(bmp, 100, 100, true);
            imageView.setImageBitmap(resized);

        }


    }



    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (v.getId() == R.id.cp) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent,1);
            }else if(v.getId() == R.id.button){
                EditText name = (EditText)findViewById(R.id.editText2);
                AutoCompleteTextView desc =(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
                mDatabase.push().setValue(name.getText().toString());
                mDatabase.child(name.getText().toString()).push().setValue(desc.getText().toString());
                //myRef.child("Post").child(nameAni.getText().toString()).push().setValue(descAddress.getText().toString());
                //myRef.child("Post").child(nameAni.getText().toString()).push().setValue(severity);
                mDatabase.child(name.getText().toString()).push().setValue(resized);
                //myRef.child("Post").child(nameAni.getText().toString()).push().setValue(locations);
                Log.d("Woof","Posted");
                //TextView a = (TextView)findViewById(R.id.textView2);
                //a.setText("Welp");
            }
        }
    };

}
