package id.ardpratama.arth.jadwalku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by de_arth on 28/03/2016.
 */
public class SingleTugas extends Activity {
    // JSON node keys
    private static final String TAG_PELAJARAN = "pelajaran";
    private static final String TAG_ISI = "isi";
    private static final String TAG_SUBMIT = "submit";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_tugas);

// getting intent data
        Intent in = getIntent();

// Get JSON values from previous intent
        String pelajaran = in.getStringExtra(TAG_PELAJARAN);
        String isi = in.getStringExtra(TAG_ISI);
        String submit = in.getStringExtra(TAG_SUBMIT);



// Displaying all values on the screen
        TextView lblPelajaran = (TextView) findViewById(R.id.pelajaran);
        TextView lblIsi = (TextView) findViewById(R.id.isi);
        TextView lblSubmit = (TextView) findViewById(R.id.submit);

        lblPelajaran.setText("Mapel: " +pelajaran);
        lblIsi.setText("Deskripsi: "+isi);
        lblSubmit.setText("Tanggal Kumpul: "+submit);


    }

}
