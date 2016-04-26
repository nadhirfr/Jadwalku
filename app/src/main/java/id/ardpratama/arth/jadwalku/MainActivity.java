package id.ardpratama.arth.jadwalku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by de_arth on 18/03/2016.
 */

public class MainActivity extends Activity {
    ImageButton ib6, ib1,ib2,ib3,ib4,ib5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ib1 = (ImageButton) findViewById(R.id.imageButton2);
        ib2 = (ImageButton) findViewById(R.id.imageButton1);
        ib3 = (ImageButton) findViewById(R.id.imageButton3);
        ib4 = (ImageButton) findViewById(R.id.imageButton4);
        ib5 = (ImageButton) findViewById(R.id.imageButton5);
        ib6 = (ImageButton) findViewById(R.id.imageButton6);
        //membuat listener ketika objek di-klik
        View.OnClickListener lis1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //button 6
                Intent i = new Intent(MainActivity.this, JadwalActivity.class);
                startActivity(i);
                finish();

            }
        };
        View.OnClickListener lis2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this, JadwalfullActivity.class);
                startActivity(i);
                finish();

            }
        };
        View.OnClickListener lis3 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this, TugasHar.class);
                startActivity(i);
                finish();

            }
        };
        View.OnClickListener lis4 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this, TugasFull.class);
                startActivity(i);
                finish();

            }
        };
        View.OnClickListener lis5 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this, TambahTugas.class);
                startActivity(i);
                finish();

            }
        };

        View.OnClickListener lis6 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(i);
                finish();
                            }
        };
        //menerapkan listener pada objek ib
        ib1.setOnClickListener(lis1);
        ib2.setOnClickListener(lis2);
        ib3.setOnClickListener(lis3);
        ib4.setOnClickListener(lis4);
        ib5.setOnClickListener(lis5);
        ib6.setOnClickListener(lis6);


    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Yakin ingin keluar?");
        alertDialogBuilder.setPositiveButton("Tidak",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        alertDialogBuilder.setNegativeButton("Ya",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}