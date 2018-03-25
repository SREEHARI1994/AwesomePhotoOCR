package awesomeocr.leehar.com.awesomeimage2textconverter;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
//import android.support.v7.app.ActionBar;

public class PhotoDisplay extends AppCompatActivity {
Bitmap bitmap;
String passUri3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_display);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ImageView  display=(ImageView)findViewById(R.id.display);
        Bundle extras = getIntent().getExtras();
        passUri3=extras.getString("imageUri2");
        Uri myUri = Uri.parse(passUri3);

        try {
             bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), myUri);
            //bitmap=decodeBitmapUri(this, myUri);

        } catch (IOException e) {
            e.printStackTrace();
        }
        display.setImageURI(myUri);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.forward_icon:
                // User chose the "Settings" item, show the app settings UI...
                recognize();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    public void recognize(){
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        // TODO: Check if the TextRecognizer is operational.
        if (!textRecognizer.isOperational()) {
          //  Log.w(TAG, "Detector dependencies are not yet available.");

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show();
              //  Log.w(TAG, getString(R.string.low_storage_error));
            }
        }
        Frame imageFrame = new Frame.Builder()

                .setBitmap(bitmap)                 // your image bitmap
                .build();

        String imageText = "";


        SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);

        /*for (int i = 0; i < textBlocks.size(); i++) {
            TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
            imageText = textBlock.getValue();                   // return string
        }*/
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<textBlocks.size();i++) {
            TextBlock item = textBlocks.valueAt(i);
            stringBuilder.append(item.getValue());
            stringBuilder.append("\n");
        }
        Intent intent=new Intent(this,TextEdit.class);
       // intent.putExtra("converted",imageText);
        intent.putExtra("converted",stringBuilder.toString());

        intent.putExtra("imageUri3",passUri3);
        startActivity(intent);


    }
   /* private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {
        int targetW = 600;
        int targetH = 600;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(ctx.getContentResolver()
                .openInputStream(uri), null, bmOptions);
    }*/
}
