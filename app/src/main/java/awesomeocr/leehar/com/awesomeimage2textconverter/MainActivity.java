package awesomeocr.leehar.com.awesomeimage2textconverter;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements OnClickListener{

    String mCurrentPhotoPath;
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (mCurrentPhotoPath == null && savedInstanceState.getString("uri_file_path") != null) {
                mCurrentPhotoPath = savedInstanceState.getString("uri_file_path");
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button one = (Button) findViewById(R.id.button);
        one.setOnClickListener(this); // calling onClick() method
        Button two = (Button) findViewById(R.id.button2);
        two.setOnClickListener(this);
        Button three = (Button) findViewById(R.id.button3);
        three.setOnClickListener(this);


    }
    public static int PICK_IMAGE_REQUEST = 2;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button:
                // do your code
                dispatchTakePictureIntent();
                break;

            case R.id.button2:
                // do your code
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST);
                break;

            case R.id.button3:
                // do your code
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=awesomeocr.leehar.com.awesomeimage2textconverter");

                Intent intentR = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intentR);
                break;

            default:
                break;
        }

    }
    /*public void onButtonClick(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
        //dispatchTakePictureIntent();
    }*/

   static final int REQUEST_TAKE_PHOTO = 1;


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        MainActivity.this);

                // Setting Dialog Title
                alertDialog.setTitle("Error");

                // Setting Dialog Message
                alertDialog.setMessage("Not able to save photo.Check if Storage space is available");

                // Setting Icon to Dialog

                // Setting OK Button
                alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        dialog.cancel();
                    }
                });
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
             ".jpg",         /* suffix */
             storageDir      /* directory */
     );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

      @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO  && resultCode == RESULT_OK) {
            //Uri image=Uri.parse(mCurrentPhotoPath);
            File f = new File(mCurrentPhotoPath);
            Uri image = Uri.fromFile(f);
           // Uri image= data.getData();
            Intent intent =new Intent(this,Choice.class);
            intent.putExtra("imageUri", image.toString());
            startActivity(intent);
           /* try {
                //Bitmap mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
               // mImageView.setImageBitmap(mImageBitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }*/
          //  galleryAddPic();
        }
          if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
              Uri urigallery = data.getData();
              Intent intent2 =new Intent(this,Choice.class);
              intent2.putExtra("imageUri", urigallery.toString());
              startActivity(intent2);
          }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mCurrentPhotoPath != null)
            outState.putString("uri_file_path", mCurrentPhotoPath);
        super.onSaveInstanceState(outState);
    }

 /* @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
          CropImage.ActivityResult result = CropImage.getActivityResult(data);
          if (resultCode == RESULT_OK) {
              Uri resultUri = result.getUri();
              Intent intent =new Intent(this,PhotoDisplay.class);
              intent.putExtra("imageUri", resultUri.toString());
              startActivity(intent);
          } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
              Exception error = result.getError();
          }
      }
  }*/
  /*private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }*/

}

