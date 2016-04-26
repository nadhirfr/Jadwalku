package id.ardpratama.arth.jadwalku;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by de_arth on 23/03/2016.
 */
public class TambahTugas extends Activity {


    private EditText txtPelajaran;
    private EditText txtIsi;
    private TextView txtStatus;
    private Button btnSimpan;
    private String surl = "http://ardpratama.web.id/asro/jadwalku/postjdw.php";

    /**
     * Method yang dipanggil pada saat aplikaasi dijalankan
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahtugas);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        txtPelajaran = (EditText) findViewById(R.id.txtPelajaran);
        txtIsi = (EditText) findViewById(R.id.txtIsi);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);

        // daftarkan even onClick pada btnSimpan
        btnSimpan.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                if (txtPelajaran.length() == 0) {
                    String result = "";
                    result = "Form pelajaran wajib diisi, spam hajar :v";
                    txtStatus.setText(result);}
                else if(txtIsi.length() == 0) {
                    String result = "";
                    result = "Form Deskripsi wajib diisi, spam hajar :v";
                    txtStatus.setText(result);

                } else {
                    readWebpage(v);
                    txtIsi.setText("");
                    txtPelajaran.setText("");

                }
            }
        });

    }


    /**
     * Method untuk Mengirimkan data keserver
     */
    public String getRequest(String Url) {
        String sret;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(Url);
        try {
            HttpResponse response = client.execute(request);
            sret = request(response);
        } catch (Exception ex) {
            sret = "Gak ono internet";
        }
        return sret;

    }

    /**
     * Method untuk Menerima data dari server
     *
     * @param response
     * @return
     */
    public static String request(HttpResponse response) {
        String result = "";
        try {
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                str.append(line + "\n");
            }
            in.close();
            result = str.toString();

        } catch (Exception ex) {
            result = "Tambah data gagal";
        }
        return result;
    }

    /**
     * Class CallWebPageTask untuk implementasi class AscyncTask
     */
    private class CallWebPageTask extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog;
        protected Context applicationContext;

        @Override
        protected void onPreExecute() {
            this.dialog = ProgressDialog.show(applicationContext, "Sedang Memproses", "Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            response = getRequest(urls[0]);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            this.dialog.cancel();
            txtStatus.setText(result);
        }
    }

    public void readWebpage(View view) {
        CallWebPageTask task = new CallWebPageTask();
        task.applicationContext = TambahTugas.this;

        // String tgl = URLEncoder.encode(tglKumpul.getYear()
        //       .toString(), "utf-8");
        SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd");
        final DatePicker datePicker = (DatePicker) findViewById(R.id.tglKumpul);
        String tgl = df.format(new Date(datePicker.getYear() - 1900, datePicker.getMonth(), datePicker.getDayOfMonth()));
        String url = surl + "?pelajaran=" + txtPelajaran.getText().toString() + "&isi=" + txtIsi.getText().toString() + "&tgl=" + tgl;
        task.execute(new String[]{url});

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
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
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}