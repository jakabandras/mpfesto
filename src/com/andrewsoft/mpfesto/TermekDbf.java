/**
 * 
 */
package com.andrewsoft.mpfesto;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

/**
 * @author Andrew
 * 
 */
public class TermekDbf implements MyDbf {

  public static String                                 HDR_CIKKSZAM = "Cikkszám";

  public static String                                 HDR_MEGNEV   = "Megnevezés";

  public static String                                 HDR_DEF_CONT = "Tároló db.";

  public static String                                 HDR_DEF_KOCS = "Kocsi db.";

  public static String                                 HDR_CSOM     = "Csomag db.";

  private final String[]                               myHeader     =
                                                                    {
      HDR_CIKKSZAM , HDR_MEGNEV , HDR_DEF_CONT , HDR_DEF_KOCS , HDR_CSOM
                                                                    };

  public static final Map<String, Map<String, Object>> records      = new HashMap<>();

  private String                                       dbfname      = null;

  private String                                       keyname      = null;

  /**
   * 
   */
  public TermekDbf( String path ) {

    setDbfName(path + "/" + "Termekek.txt");
    setKeyField(HDR_CIKKSZAM);
    initTermek();

  }

  private void initTermek() {

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
    else {
      AddTermek("13-259998Z01-A", "Panel Rear F3X", 60, 288, 60);
      AddTermek("13-25999Z01-D/1", "Panel Front F3X", 60, 234, 60);
      try {
        writeRecords();
      }
      catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.andrewsoft.mpfesto.MyDbf#readRecords()
   */
  @Override
  public void readRecords() throws IOException {

    ICsvMapReader mapReader = null;
    try {
      mapReader = new CsvMapReader(new FileReader(this.getDbfName()), CsvPreference.STANDARD_PREFERENCE);
      final String[] header = mapReader.getHeader(true);
      final CellProcessor[] processors = getProcessors();
      Map<String, Object> record;
      while ((record = mapReader.read(header, processors)) != null) {
        if (record != null) {
          String kf = getKeyField();
          String csz = record.get(kf).toString();
          records.put(csz, record);
        }

      }
    }
    finally {
      if (mapReader != null) mapReader.close();
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.andrewsoft.mpfesto.MyDbf#genDbfNam()
   */
  @Override
  public String genDbfName() {

    return "Termek.txt";
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.andrewsoft.mpfesto.MyDbf#getDbfName()
   */
  @Override
  public String getDbfName() {

    return dbfname;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.andrewsoft.mpfesto.MyDbf#setDbfName(java.lang.String)
   */
  @Override
  public void setDbfName(String name) {

    dbfname = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.andrewsoft.mpfesto.MyDbf#writeRecords()
   */
  @Override
  public void writeRecords() throws IOException {

    ICsvMapWriter mapWriter = null;
    try {
      mapWriter = new CsvMapWriter(new FileWriter(getDbfName()), CsvPreference.STANDARD_PREFERENCE);
      mapWriter.writeHeader(myHeader);
      for (Entry<String, Map<String, Object>> rec : records.entrySet()) {
        Map<String, Object> tmp = rec.getValue();
        mapWriter.write(tmp, myHeader, getProcessors());
      }
    }
    finally {
      if (mapWriter != null) mapWriter.close();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.andrewsoft.mpfesto.MyDbf#setKeyField(java.lang.String)
   */
  @Override
  public void setKeyField(String keyname) {

    this.keyname = keyname;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.andrewsoft.mpfesto.MyDbf#getKeyFiled()
   */
  @Override
  public String getKeyField() {

    return keyname;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.andrewsoft.mpfesto.MyDbf#addRecord(java.util.Map)
   */
  @Override
  public void addRecord(Map<String, Object> record) {

    String key = (String) record.get(getKeyField());
    records.put(key, record);

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.andrewsoft.mpfesto.MyDbf#isStored(java.lang.String)
   */
  @Override
  public boolean isStored(String key) {

    Map<String, Object> tmp = records.get(key);
    return records.containsKey(key);
  }

  public void AddTermek(String cikksz, String megnev, int tarolo, int kocsi, int csomag) {

    final Map<String, Object> record = new HashMap<>();
    record.put(HDR_CIKKSZAM, cikksz);
    record.put(HDR_MEGNEV, megnev);
    record.put(HDR_DEF_CONT, tarolo);
    record.put(HDR_DEF_KOCS, kocsi);
    record.put(HDR_CSOM, csomag);

    records.put(cikksz, record);
  }

  @Override
  public String[] getHeaders() {

    return myHeader;
  }

  @Override
  public CellProcessor[] getProcessors() {

    final CellProcessor[] processors = new CellProcessor[]
    {
        new NotNull() , // Cikkszám
        new NotNull() , // Megnevezés
        new ParseInt() , // Tároló db
        new ParseInt() , // Kocsi db
        new ParseInt()
    // Csomagolás db
    };
    return processors;
  }

  @Override
  public int Sum(String fieldName) {

    return 0;
  }

}
