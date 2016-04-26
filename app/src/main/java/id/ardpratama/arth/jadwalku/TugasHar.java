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
 * Created by de_arth on 28/03/2016.
 */
public class TugasHar extends ListActivity {
    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "http://ardpratama.web.id/asro/jadwalku/gettgsharian.php";
    //private static String url = "http://192.168.137.1/android/jadwalku/jdwfull.php";

    // JSON Node names
    private static final String TAG_TUGAS = "tugas";
    private static final String TAG_PELAJARAN = "pelajaran";
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_ISI = "isi";
    private static final String TAG_ID = "id";



    // contacts JSONArray
    JSONArray tugas = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> tugasList;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tgsfull);
        ActionBar actionBar = getActionBar();
        TextView jdl = (TextView)findViewById(R.id.judulkon);
        jdl.setText("Tugas Besok");
        actionBar.setDisplayHomeAsUpEnabled(true);

        tugasList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();

        // Listview on item click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String pelajaran = ((TextView) view.findViewById(R.id.pelajaran))
                        .getText().toString();
                String isi = ((TextView) view.findViewById(R.id.isi))
                        .getText().toString();
                String submit = ((TextView) view.findViewById(R.id.submit))
                        .getText().toString();


                // Starting single contact activity
                Intent in = new Intent(getApplicationContext(), SingleTugas.class);
                in.putExtra(TAG_PELAJARAN, pelajaran);
                in.putExtra(TAG_ISI, isi);
                in.putExtra(TAG_SUBMIT, submit);



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
            pDialog = new ProgressDialog(TugasHar.this);
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
                    tugas = jsonObj.getJSONArray(TAG_TUGAS);

                    // looping through All Contacts
                    for (int i = 0; i < tugas.length(); i++) {
                        JSONObject c = tugas.getJSONObject(i);

                        String pelajaran = c.getString(TAG_PELAJARAN);
                        String submit = c.getString(TAG_SUBMIT);
                        String isi = c.getString(TAG_ISI);

                        //String  = c.getString(TAG_GENDER);

                        // Phone node is JSON Object
                        //   JSONObject phone = c.getJSONObject(TAG_PHONE); // jika ada cabang lagi. db head
                        // String mobile = phone.getString(TAG_PHONE_MOBILE);
                        //;/ String home = phone.getString(TAG_PHONE_HOME);
                        //String office = phone.getString(TAG_PHONE_OFFICE);

                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();

                        // adding each child node to HashMap key => value

                        contact.put(TAG_PELAJARAN, pelajaran);
                        contact.put(TAG_SUBMIT, submit);
                        contact.put(TAG_ISI, isi);


                        // adding contact to contact list
                        tugasList.add(contact);
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
                    TugasHar.this, tugasList,
                    R.layout.list_tugas, new String[] { TAG_PELAJARAN,TAG_SUBMIT,
                    TAG_ISI}, new int[]{R.id.pelajaran, R.id.submit,
                    R.id.isi});

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
                Intent i = new Intent(TugasHar.this, MainActivity.class);
                startActivity(i);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

