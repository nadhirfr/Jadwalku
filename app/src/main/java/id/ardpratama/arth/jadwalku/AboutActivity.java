package id.ardpratama.arth.jadwalku;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * Created by de_arth on 21/03/2016.
 */
public class AboutActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ActionBar actionBar = getActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(AboutActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Menentukan aksi saat suatu menu diklik
        switch (id) {
            case android.R.id.home:
                Intent i = new Intent(AboutActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
