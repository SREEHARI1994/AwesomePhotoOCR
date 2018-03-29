package awesomeocr.leehar.com.awesomeimage2textconverter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainDisplay extends AppCompatActivity {
    String displayUri;
    Uri disUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_display);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar3);
        setSupportActionBar(myToolbar);
        ImageView display2=(ImageView)findViewById(R.id.display2);
        Bundle extras = getIntent().getExtras();
        displayUri=extras.getString("imageUri");
        disUri = Uri.parse(displayUri);
        display2.setImageURI(disUri);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.display, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.forward_icon2:
                Intent chintent=new Intent(this,Choice.class);
                chintent.putExtra("imageUriD",displayUri );
                startActivity(chintent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
