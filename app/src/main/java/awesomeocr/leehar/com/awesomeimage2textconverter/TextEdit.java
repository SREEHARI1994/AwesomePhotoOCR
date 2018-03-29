package awesomeocr.leehar.com.awesomeimage2textconverter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;


public class TextEdit extends AppCompatActivity {
String displayText;
String copyText="";
String shareText=null;
EditText test;
Uri taUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_edit);
        test = (EditText) findViewById(R.id.converted_text);


        Intent intent = this.getIntent();

        displayText = intent.getExtras().getString("converted");
        taUri = Uri.parse(intent.getExtras().getString("imageUri3"));
        test.setText(displayText, TextView.BufferType.EDITABLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_white_36dp);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_copy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.copy_icon:
                copyText=test.getText().toString();
                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);

                ClipData clip = ClipData.newPlainText("simple text",copyText );
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this,R.string.shareToast,Toast.LENGTH_LONG).show();

                return true;

            case R.id.menu_item_share:
                shareText=test.getText().toString();
                Intent myShareIntent2 = new Intent(android.content.Intent.ACTION_SEND);
                myShareIntent2.setType("text/plain");
                myShareIntent2.putExtra(Intent.EXTRA_TEXT,shareText);
                startActivity(Intent.createChooser(myShareIntent2, "Share Via!"));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    public void onButtonClick(View view) {
        CropImage.activity(taUri)
                .start(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Intent intent =new Intent(this,PhotoDisplay.class);
                intent.putExtra("imageUri2", resultUri.toString());
                startActivity(intent);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}
