package com.andrewsoft.mpfesto;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.StrNotNullOrEmpty;
import org.supercsv.cellprocessor.constraint.Unique;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

public class KocsiDbf implements MyDbf {

  private static final String       HDR_AZON  = "Azonosító";

  private static final String       HDR_SORSZ = "Kocsi sorszám";

  private static final String[]     myHeader  =
                                              {
      HDR_AZON , HDR_SORSZ
                                              };

  public final Map<String, Integer> kocsik    = new HashMap<>();

  public final Map<String, Integer> records   = new HashMap<>();

  private String                    dbfName   = null;

  private String                    myKeyField;

  public KocsiDbf( String path ) {

    setDbfName(path + "/" + genDbfName());
    setKeyField(HDR_AZON);
    File f = new File(getDbfName());
    if (f.exists()) {
      try {
        readRecords();
      }
      catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

  }

  @Override
  public void readRecords() throws IOException {

    ICsvMapReader mapReader = null;
    try {
      mapReader = new CsvMapReader(new FileReader(getDbfName()), CsvPreference.STANDARD_PREFERENCE);
      Map<String, Object> tmpRec;
      String[] headers = mapReader.getHeader(true);
      kocsik.clear();
      while ((tmpRec = mapReader.read(headers, getProcessors())) != null) {
        kocsik.put(tmpRec.get(HDR_AZON).toString(), (Integer) tmpRec.get(HDR_SORSZ));
      }
    }
    finally {
      if (mapReader != null) mapReader.close();
    }
  }

  @Override
  public String genDbfName() {

    Calendar cal = Calendar.getInstance();
    final StringBuilder builder = new StringBuilder("Kocsik_").append(cal.get(Calendar.YEAR)).append(cal.get(Calendar.MONTH) + 1).append(cal.get(Calendar.DAY_OF_MONTH)).append(".txt");
    return builder.toString();
  }

  @Override
  public String getDbfName() {

    return dbfName;
  }

  @Override
  public void setDbfName(String name) {

    dbfName = name;
  }

  @Override
  public void writeRecords() throws IOException {

    ICsvMapWriter mapWriter = null;
    try {
      mapWriter = new CsvMapWriter(new FileWriter(getDbfName()), CsvPreference.STANDARD_PREFERENCE);
      mapWriter.writeHeader(myHeader);
      for (Entry<String, Integer> rec : kocsik.entrySet()) {
        Map<String, Object> tmp = new HashMap<>();
        tmp.put(HDR_AZON, rec.getKey());
        tmp.put(HDR_SORSZ, rec.getValue());
        mapWriter.write(tmp, getHeaders(), getProcessors());
      }

    }
    finally {
      if (mapWriter != null) mapWriter.close();
    }
  }

  @Override
  public void setKeyField(String keyname) {

    this.myKeyField = keyname;
  }

  @Override
  public String getKeyField() {

    return this.myKeyField;
  }

  @Override
  public void addRecord(Map<String, Object> record) {

    kocsik.put(record.get(HDR_AZON).toString(), (Integer) record.get(HDR_SORSZ));

  }

  public void addRecord(String azon, Integer sorsz) {

    kocsik.put(azon, sorsz);
  }

  public void addRecord(String azon) {

    addRecord(azon, 1);
  }

  @Override
  public boolean isStored(String key) {

    return kocsik.containsKey(key);
  }

  @Override
  public String[] getHeaders() {

    return myHeader;
  }

  @Override
  public CellProcessor[] getProcessors() {

    final CellProcessor[] processors =
    {
        new Unique(new StrNotNullOrEmpty()) , new ParseInt()
    };
    return processors;
  }

  @Override
  public int Sum(String fieldName) {

    // TODO Auto-generated method stub
    return 0;
  }

  public void increment(String azon) {

    final boolean found = kocsik.containsKey(azon);
    if (found) {
      Integer tmp = kocsik.get(azon);
      tmp = tmp + 1;
      kocsik.remove(azon);
      kocsik.put(azon, tmp);
    }
    else {
      Integer tmp = 1;
      kocsik.put(azon, tmp);
    }
    try {
      writeRecords();
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
