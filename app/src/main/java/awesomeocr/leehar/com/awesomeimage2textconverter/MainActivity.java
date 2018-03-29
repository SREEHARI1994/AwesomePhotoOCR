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
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements OnClickListener{

    String mCurrentPhotoPath;
    File photoFile;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (mCurrentPhotoPath == null && savedInstanceState.getString("uri_file_path") != null) {
                mCurrentPhotoPath = savedInstanceState.getString("uri_file_path");
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-9549090787426255~9354952677");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Button one = (Button) findViewById(R.id.button);
        one.setOnClickListener(this);
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
                dispatchTakePictureIntent();
                break;

            case R.id.button2:
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST);
                break;

            case R.id.button3:
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=awesomeocr.leehar.com.awesomeimage2textconverter");

                Intent intentR = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intentR);
                break;

            default:
                break;
        }

    }


   static final int REQUEST_TAKE_PHOTO = 1;


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        MainActivity.this);

                alertDialog.setTitle("Error");

                alertDialog.setMessage("Not able to save photo.Check if Storage space is available");



                alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
             ".jpg",
             storageDir
     );

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

      @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO  && resultCode == RESULT_OK) {
            File f = new File(mCurrentPhotoPath);
            Uri image = Uri.fromFile(f);
            Intent intent =new Intent(this,MainDisplay.class);
            intent.putExtra("imageUri", image.toString());
            startActivity(intent);

        }
          if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
              Uri urigallery = data.getData();
              Intent intent2 =new Intent(this,MainDisplay.class);
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


}

