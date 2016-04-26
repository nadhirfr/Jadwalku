package id.ardpratama.arth.jadwalku;

/**
 * Created by de_arth on 18/03/2016.
 */

        import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleContactActivity extends Activity
{

    // JSON node keys
    private static final String TAG_JADWAL = "jadwal";
    private static final String TAG_ID = "id";
    private static final String TAG_NAMA = "nama_jadwal";
    private static final String TAG_HARI = "hari";
    private static final String TAG_RUANG = "ruang";
    private static final String TAG_MULAI = "jam_mulai";
    private static final String TAG_SELESAI = "jam_selesai";
    private static final String TAG_DOSEN = "dosen";
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);

// getting intent data
        Intent in = getIntent();

// Get JSON values from previous intent
        String name = in.getStringExtra(TAG_NAMA);
        String hari = in.getStringExtra(TAG_HARI);
        String mulai = in.getStringExtra(TAG_MULAI);
        String selesai = in.getStringExtra(TAG_SELESAI);
        String ruang = in.getStringExtra(TAG_RUANG);
        String dosen = in.getStringExtra(TAG_DOSEN);


// Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.nama);
        TextView lblHari = (TextView) findViewById(R.id.hari);
        TextView lblMulai = (TextView) findViewById(R.id.mulai);
        TextView lblSelesai = (TextView) findViewById(R.id.selesai);
        TextView lblRuang = (TextView) findViewById(R.id.ruang);
        TextView lblDosen = (TextView) findViewById(R.id.dosen);


        lblName.setText(name);
        lblHari.setText(hari);
        lblMulai.setText("Mulai: " +mulai);
        lblSelesai.setText("Selesai: "+selesai);
        lblRuang.setText("Ruang: "+ruang);
        lblDosen.setText("Dosen: "+dosen);

    }

}

