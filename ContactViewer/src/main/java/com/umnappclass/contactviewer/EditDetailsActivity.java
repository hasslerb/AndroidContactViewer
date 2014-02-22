package com.umnappclass.contactviewer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;

/**
 *
 */
public class EditDetailsActivity extends Activity {

    private static final int SELECT_PHOTO = 1;
    Bitmap photo;
    private Contact _contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_detail);

        Intent i = getIntent();
        _contact = (Contact)i.getSerializableExtra("Contact");

        SetContactFields(_contact);

        Button goToDetailsButton = (Button)findViewById(R.id.go_to_details);
        goToDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Open up the photo selection after pressing on the photo
        ImageButton editPhoto = (ImageButton)findViewById(R.id.edit_photo);
        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);  // ACTION_GET_CONTENT
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    photo = BitmapFactory.decodeFile(filePath);

                    // Alternatively, should be able to do this:
                    //photo = decodeUri(selectedImage);

                    ImageButton editPhoto = (ImageButton)findViewById(R.id.edit_photo);
                    editPhoto.setImageBitmap(photo);
                }
        }
    }


    // Downsample the image to a decent size
    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 140;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
    }

    private void SetContactFields(Contact contact)
    {
        Spinner titleSpinner = (Spinner)findViewById(R.id.edit_title);
        String[] titleArray = getResources().getStringArray(R.array.contact_title_array);
        int index;
        for(index = 0; index < titleArray.length; index ++)
        {
            if(titleArray[index].equals(contact.title)) break;
        }
        // If contact's title is not in the array, select index 0 for no title
        if(index >= titleArray.length) index = 0;
        titleSpinner.setSelection(index);

        EditText nameView = (EditText)findViewById(R.id.edit_name);
        nameView.setText(contact.name);

        EditText phoneView = (EditText)findViewById(R.id.edit_phone);
        phoneView.setText(contact.phone);

        EditText emailView = (EditText)findViewById(R.id.edit_email);
        emailView.setText(contact.email);

        EditText twitterView = (EditText)findViewById(R.id.edit_twitter);
        twitterView.setText(contact.twitterId);
    }
}