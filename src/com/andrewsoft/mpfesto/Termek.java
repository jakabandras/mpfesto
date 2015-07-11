package com.andrewsoft.mpfesto;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class Termek extends Activity {

  public static String                           HDR_CIKKSZAM = "Cikkszám";
  public static String                           HDR_MEGNEV   = "Megnevezés";
  public static String                           HDR_DEF_CONT = "Tároló db.";
  public static String                           HDR_DEF_KOCS = "Kocsi db.";
  public static String                           HDR_CSOM     = "Csomag db.";

  private final static List<Map<String, Object>> records      = new ArrayList<>();

  private Button                                 btnNew;
  private static String                          dbfName      = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_termek);

    btnNew = (Button) findViewById(R.id.btn_newitem);
    btnNew.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        doRogzit();
      }
    });

    ListView lv = (ListView) findViewById(R.id.lstTermView);
    // TermekListAdapter adapter = new TermekListAdapter(this,
    // (Map<String, Object>[]) records.toArray());
    // lv.setAdapter(adapter);

    InitTermek();
  }

  protected void doRogzit() {
    // TODO Auto-generated method stub
    Intent intent = new Intent(getBaseContext(), NewItem.class);

    startActivityForResult(intent, 0);

  }

  @Override
  protected void onActivityResult(int request, int result, Intent data) {
    SaveList(dbfName, records);
  }

  private void InitTermek() {
    PackageManager pm = getPackageManager();
    try {
      PackageInfo pi = pm.getPackageInfo("com.andrewsoft.mpfesto", 0);
      dbfName = pi.applicationInfo.dataDir + "/Termekek.txt";
      File test = new File(dbfName);
      if (!test.exists()) {
        AddTermek(new HashMap<String, Object>(), "13-259998Z01-A",
            "Panel Rear F3X", 60, 288, 60);
        AddTermek(new HashMap<String, Object>(), "13-25999Z01-D/1",
            "Panel Front F3X", 60, 234, 60);
        // More ...
        //
        SaveList(dbfName, records);

      }
      else {
        readRecords();
      }

    }
    catch (NameNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  public static void readRecords() {
    // TODO Auto-generated method stub
    records.clear();
    ICsvMapReader mapReader = null;
    try {
      mapReader = new CsvMapReader(new FileReader(dbfName),
          CsvPreference.STANDARD_PREFERENCE);
      Map<String, Object> tmpRec;
      final String[] header = mapReader.getHeader(true);
      final CellProcessor[] processors = getTermekProcessor();
      while ((tmpRec = mapReader.read(header, processors)) != null) {
        records.add(tmpRec);
      }

    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    finally {
      if (mapReader != null) try {
        mapReader.close();
      }
      catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  public static void AddTermek(Map<String, Object> record_new, String cikksz,
      String megnev, int tarolo, int kocsi, int csomag) {
    // TODO Auto-generated method stub
    final Map<String, Object> record = record_new;
    record.put(HDR_CIKKSZAM, cikksz);
    record.put(HDR_MEGNEV, megnev);
    record.put(HDR_DEF_CONT, tarolo);
    record.put(HDR_DEF_KOCS, kocsi);
    record.put(HDR_CSOM, csomag);
    records.add(record);
  }

  public static CellProcessor[] getTermekProcessor() {
    final CellProcessor[] processors = new CellProcessor[] {
        new NotNull(), // Cikkszám
        new NotNull(), // Megnevezés
        new ParseInt(), // Tároló db
        new ParseInt(), // Kocsi db
        new ParseInt()
    // Csomagolás db
    };
    return processors;
  }

  public void SaveList(String filename, List<Map<String, Object>> recs) {
    final String[] header = {
        HDR_CIKKSZAM, HDR_MEGNEV, HDR_DEF_CONT, HDR_DEF_KOCS, HDR_CSOM
    };
    ICsvMapWriter mapWriter = null;
    try {
      mapWriter = new CsvMapWriter(new FileWriter(filename),
          CsvPreference.STANDARD_PREFERENCE);
      final CellProcessor[] cikkek = getTermekProcessor();
      mapWriter.writeHeader(header);
      for (Map<String, Object> rec : recs) {
        mapWriter.write(rec, header, cikkek);
      }
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    finally {
      try {
        if (mapWriter != null) mapWriter.close();
      }
      catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

}
