package awesomeocr.leehar.com.awesomeimage2textconverter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


public class MainActivity extends AppCompatActivity {
    ImageView mImageView;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*if (savedInstanceState != null) {
            if (mCurrentPhotoPath == null && savedInstanceState.getString("uri_file_path") != null) {
                mCurrentPhotoPath = savedInstanceState.getString("uri_file_path");
            }
        }*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.imageView);
    }

    public void onButtonClick(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
        //dispatchTakePictureIntent();
    }

  /*  static final int REQUEST_TAKE_PHOTO = 1;


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
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

   // String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
              //  ".jpg",         /* suffix */
              //  storageDir      /* directory */
      /*  );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:"+image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO  && resultCode == RESULT_OK) {
            try {
                Bitmap mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                mImageView.setImageBitmap(mImageBitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
            galleryAddPic();
        }
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mCurrentPhotoPath != null)
            outState.putString("uri_file_path", mCurrentPhotoPath);
        super.onSaveInstanceState(outState);
    }
*/
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
          CropImage.ActivityResult result = CropImage.getActivityResult(data);
          if (resultCode == RESULT_OK) {
              Uri resultUri = result.getUri();
          } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
              Exception error = result.getError();
          }
      }
  }

}

