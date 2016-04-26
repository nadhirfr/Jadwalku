package id.ardpratama.arth.jadwalku;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by de_arth on 08/04/2016.
 */
public class JadwalActivity extends ListActivity {
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Menentukan aksi saat suatu menu diklik
        switch (id){
            case android.R.id.home:
                finish();
            case R.id.action_help_about:
                //Dialog dialog = new Dialog(this);
                //dialog.setTitle("Dibuat oleh Novian Agung");
                //dialog.show();
                Intent i = new Intent(JadwalActivity.this, AboutActivity.class);
                startActivity(i);
                finish();
                return true;

            case R.id.action_tugas_full:
                //Dialog dialog = new Dialog(this);
                //dialog.setTitle("Dibuat oleh Novian Agung");
                //dialog.show();
                Intent ik = new Intent(JadwalActivity.this, TugasFull.class);
                startActivity(ik);
                finish();
                return true;

            case R.id.action_jadwal_full:

                Intent j = new Intent(JadwalActivity.this, JadwalfullActivity.class);
                startActivity(j);
                finish();
                return true;

            case R.id.action_tambah_tugas:

                Intent k = new Intent(JadwalActivity.this, TambahTugas.class);
                startActivity(k);
                finish();
                return true;
            case R.id.action_tugas_besok:

                Intent kl = new Intent(JadwalActivity.this, TugasHar.class);
                startActivity(kl);
                finish();
                return true;
            case R.id.action_exit:
                //metode untuk mengakhiri aplikasi
                finish();

                return true;

        }

        return super.onOptionsItemSelected(item);
    } */




    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "http://ardpratama.web.id/asro/jadwalku/getdata.php";
    //private static String url = "http://192.168.137.1/android/jadwalku/getdata.php";

    // JSON Node names
    private static final String TAG_JADWAL = "jadwal";
    private static final String TAG_ID = "id";
    private static final String TAG_NAMA = "nama_jadwal";
    private static final String TAG_HARI = "hari";
    private static final String TAG_RUANG = "ruang";
    private static final String TAG_MULAI = "jam_mulai";
    private static final String TAG_SELESAI = "jam_selesai";
    private static final String TAG_DOSEN = "dosen";


    // contacts JSONArray
    JSONArray jadwal = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> jadwalList;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        ActionBar actionBar = getActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        jadwalList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();

        // Listview on item click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String name = ((TextView) view.findViewById(R.id.nama))
                        .getText().toString();
                String mulai = ((TextView) view.findViewById(R.id.mulai))
                        .getText().toString();
                String ruang = ((TextView) view.findViewById(R.id.ruang))
                        .getText().toString();
                String dosen = ((TextView) view.findViewById(R.id.dosen))
                        .getText().toString();
                String selesai = ((TextView) view.findViewById(R.id.selesai))
                        .getText().toString();

                // Starting single contact activity
                Intent in = new Intent(getApplicationContext(), SingleContactActivity.class);
                in.putExtra(TAG_NAMA, name);
                in.putExtra(TAG_MULAI, mulai);
                in.putExtra(TAG_RUANG, ruang);

                in.putExtra(TAG_DOSEN, dosen);
                in.putExtra(TAG_SELESAI, selesai);


                startActivity(in);

            }
        });

        // Calling async task to get json
        new GetContacts().execute();
    }



    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(JadwalActivity.this);
            pDialog.setMessage("Mengambil Data...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServerHandler sh = new ServerHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServerHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    jadwal = jsonObj.getJSONArray(TAG_JADWAL);

                    // looping through All Contacts
                    for (int i = 0; i < jadwal.length(); i++) {
                        JSONObject c = jadwal.getJSONObject(i);

                        String id = c.getString(TAG_ID);
                        String nama = c.getString(TAG_NAMA);
                        String hari = c.getString(TAG_HARI);
                        String ruang = c.getString(TAG_RUANG);
                        String mulai = c.getString(TAG_MULAI);
                        String selesai = c.getString(TAG_SELESAI);
                        String dosen = c.getString(TAG_DOSEN);
                        //String  = c.getString(TAG_GENDER);

                        // Phone node is JSON Object
                        //   JSONObject phone = c.getJSONObject(TAG_PHONE); // jika ada cabang lagi. db head
                        // String mobile = phone.getString(TAG_PHONE_MOBILE);
                        //;/ String home = phone.getString(TAG_PHONE_HOME);
                        //String office = phone.getString(TAG_PHONE_OFFICE);

                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        contact.put(TAG_ID, id);
                        contact.put(TAG_NAMA, nama);
                        contact.put(TAG_HARI, hari);
                        contact.put(TAG_RUANG, ruang);
                        contact.put(TAG_MULAI, mulai);
                        contact.put(TAG_SELESAI, selesai);
                        contact.put(TAG_DOSEN, dosen);

                        // adding contact to contact list
                        jadwalList.add(contact);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");


            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    JadwalActivity.this, jadwalList,
                    R.layout.list_item, new String[] { TAG_NAMA,TAG_MULAI,
                    TAG_RUANG, TAG_SELESAI, TAG_DOSEN}, new int[]{R.id.nama, R.id.mulai,
                    R.id.ruang, R.id.selesai, R.id.dosen});

            setListAdapter(adapter);
        }

    }



    @Override
    public void onContentChanged() {
        super.onContentChanged();

        View empty = findViewById(R.id.empty);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setEmptyView(empty);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(JadwalActivity.this, MainActivity.class);
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
                Intent i = new Intent(JadwalActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


