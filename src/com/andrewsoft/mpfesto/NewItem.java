package com.andrewsoft.mpfesto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class NewItem extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_item);

    // Tároló osztály inicializálása
    MyHolder.btnScanner = (Button) findViewById(R.id.btnScanAzon);
    MyHolder.btnSave = (Button) findViewById(R.id.btnSave);
    MyHolder.btnElvet = (Button) findViewById(R.id.btnNullaz);
    MyHolder.cikkszam = (EditText) findViewById(R.id.edt_azon_ut);
    MyHolder.megnevezes = (EditText) findViewById(R.id.edt_tr_megnev);
    MyHolder.kocsi = (EditText) findViewById(R.id.edt_tr_kocsi);
    MyHolder.tarolo = (EditText) findViewById(R.id.edt_tr_cont);
    MyHolder.csomagolas = (EditText) findViewById(R.id.edt_tr_csom);

    MyHolder.btnScanner.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        startScan();
      }
    });
    MyHolder.btnSave.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        doSave();
      }
    });
    MyHolder.btnElvet.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        doCancel();
      }
    });
  }

  protected void doCancel() {
    // TODO Auto-generated method stub

  }

  protected void doSave() {
    // TODO Auto-generated method stub
    Termek.AddTermek(MyHolder.cikkszam.getText().toString(),
        MyHolder.megnevezes.getText().toString(), MyHolder.getTarolo(),
        MyHolder.getKocsi(), MyHolder.getCsomagolas());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.new_item, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) { return true; }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
    case MainActivity.ZBAR_SCANNER_REQUEST:
    case MainActivity.ZBAR_QR_SCANNER_REQUEST:
      if (resultCode == RESULT_OK) {
        Toast.makeText(this,
            "Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT),
            Toast.LENGTH_SHORT).show();
        MyHolder.cikkszam.setText(data
            .getStringExtra(ZBarConstants.SCAN_RESULT));
      }
      else if (resultCode == RESULT_CANCELED && data != null) {
        String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
        if (!TextUtils.isEmpty(error)) {
          Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }
      }
      break;

    }
  }

  public void startScan() {
    Intent intent = new Intent(this, MyScanner.class);
    startActivityForResult(intent, MainActivity.ZBAR_SCANNER_REQUEST);

  }

  public static class MyHolder {

    static EditText cikkszam;
    static EditText megnevezes;
    static EditText kocsi , tarolo , csomagolas;
    static Button   btnScanner , btnSave , btnElvet;

    static int getKocsi() {
      return Integer.parseInt(kocsi.getText().toString());
    }

    static int getTarolo() {
      return Integer.parseInt(tarolo.getText().toString());
    }

    static int getCsomagolas() {
      return Integer.parseInt(csomagolas.getText().toString());
    }

  }

}
