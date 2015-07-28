/**
 * Festésre előkészített kocsik nyilvántartása
 */
package com.andrewsoft.mpfesto;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

/**
 * @author Andrew
 * 
 */
public class FeladasDbf implements MyDbf {

  private final static FeladasDbf                instance      = new FeladasDbf();

  public static final String                     HDR_AZON      = "Azonosító";

  public static final String                     HDR_MEGNEV    = "Termék neve";

  public static final String                     HDR_TIPUS     = "Típuskód";

  public static final String                     HDR_MENNY     = "Mennyiség";

  public static final String                     HDR_KOCSISZAM = "Kocsi sorszáma";

  public static final String                     HDR_EMBEREK   = "Előkészítők";

  public static final String                     HDR_MEGJ      = "Megjegyzés";

  public final String[]                          myHeaders     =
                                                               {
      HDR_AZON , HDR_MEGNEV , HDR_TIPUS , HDR_MENNY , HDR_KOCSISZAM , HDR_EMBEREK , HDR_MEGJ
                                                               };

  private final CellProcessor[]                  processors    =
                                                               {
      new NotNull() , // Azonosító
      new NotNull() , // Termék neve
      new NotNull() , // Tipus
      new ParseInt() , // Mennyiség
      new ParseInt() , // Kocsi sorszáma
      new NotNull() , // Emberek
      new Optional()
                                                               // Megjegyzés
                                                               };

  private final Map<String, Map<String, Object>> records       = new HashMap<>();

  private String                                 dbfName       = null;

  private String                                 keyName       = null;

  public static FeladasDbf getInstance() {

    return instance;
  }

  public static FeladasDbf getInstance(String path) {

    if (instance.dbfName == null) {

      instance.setDbfName(path + "/" + instance.genDbfName());
      File f = new File(instance.getDbfName());
      if (f.exists()) {
        try {
          instance.readRecords();
        }
        catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    return instance;
  }

  private FeladasDbf( ) {

  }

  /**
   * 
   */
  public FeladasDbf( String path ) {

    setDbfName(path + "/" + genDbfName());
    File f = new File(getDbfName());
    if (f.exists()) {
      try {
        readRecords();
      }
      catch (IOException e) {
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
      mapReader = new CsvMapReader(new FileReader(getDbfName()), CsvPreference.STANDARD_PREFERENCE);
      final String[] header = mapReader.getHeader(true);
      Map<String, Object> rec;
      while ((rec = mapReader.read(header, getProcessors())) != null) {
        String key = generateKey(rec);
        records.put(key, rec);
      }
    }
    finally {
      if (mapReader != null) mapReader.close();
    }

  }

  public String generateKey(Map<String, Object> rec) {

    StringBuilder builder = new StringBuilder();
    builder.append(rec.get(HDR_AZON)).append("**").append(HDR_KOCSISZAM);
    return builder.toString();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.andrewsoft.mpfesto.MyDbf#genDbfNam()
   */
  @Override
  public String genDbfName() {

    Calendar cal = Calendar.getInstance();
    StringBuilder builder = new StringBuilder("FELADAS_").append(cal.get(Calendar.YEAR)).append(cal.get(Calendar.MONTH) + 1).append(cal.get(Calendar.DAY_OF_MONTH)).append(".txt");
    return builder.toString();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.andrewsoft.mpfesto.MyDbf#getDbfName()
   */
  @Override
  public String getDbfName() {

    return dbfName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.andrewsoft.mpfesto.MyDbf#setDbfName(java.lang.String)
   */
  @Override
  public void setDbfName(String name) {

    dbfName = name;
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
      mapWriter.writeHeader(getHeaders());
      for (Entry<String, Map<String, Object>> rec : records.entrySet()) {
        Map<String, Object> tmp = rec.getValue();
        mapWriter.write(tmp, getHeaders(), getProcessors());
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

    keyName = keyname;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.andrewsoft.mpfesto.MyDbf#getKeyField()
   */
  @Override
  public String getKeyField() {

    return keyName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.andrewsoft.mpfesto.MyDbf#addRecord(java.util.Map)
   */
  @Override
  public void addRecord(Map<String, Object> record) {

    String key = generateKey(record);
    records.put(key, record);

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.andrewsoft.mpfesto.MyDbf#isStored(java.lang.String)
   */
  @Override
  public boolean isStored(String key) {

    return records.containsKey(key);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.andrewsoft.mpfesto.MyDbf#getHeaders()
   */
  @Override
  public String[] getHeaders() {

    return myHeaders;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.andrewsoft.mpfesto.MyDbf#getProcessors()
   */
  @Override
  public CellProcessor[] getProcessors() {

    return processors;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.andrewsoft.mpfesto.MyDbf#Sum(java.lang.String)
   */
  @Override
  public int Sum(String fieldName) {

    // TODO Auto-generated method stub
    return 0;
  }

  public List<Map<String, String>> Summary() {

    final List<Map<String, String>> tmplist = new ArrayList<>();
    final Map<String, String> nevek = new HashMap<>();
    final Map<String, String> kocsik = new HashMap<>();
    final Map<String, String> menny = new HashMap<>();
    for (Map<String, Object> rec : records.values()) {
      if (!nevek.containsKey(rec.get(HDR_AZON))) {
        nevek.put(rec.get(HDR_AZON).toString(), rec.get(HDR_MEGNEV).toString());
      }
      if (!kocsik.containsKey(rec.get(HDR_AZON))) {
        kocsik.put(rec.get(HDR_AZON).toString(), "1");
      }
      else {
        Integer ksz = Integer.valueOf(kocsik.get(rec.get(HDR_AZON).toString())) + 1;
        kocsik.remove(rec.get(HDR_AZON));
        kocsik.put(rec.get(HDR_AZON).toString(), ksz.toString());
      }
      if (!menny.containsKey(rec.get(HDR_AZON).toString())) {
        menny.put(rec.get(HDR_AZON).toString(), Integer.valueOf(rec.get(HDR_MENNY).toString()).toString());
      }
      else {
        Integer tmpmenny = Integer.valueOf(menny.get(rec.get(HDR_AZON).toString()));
        tmpmenny = tmpmenny + Integer.valueOf(rec.get(HDR_MENNY).toString());
        menny.remove(rec.get(HDR_AZON).toString());
        menny.put(rec.get(HDR_AZON).toString(), tmpmenny.toString());
      }
    }
    // TODO megírni az összesítőt

    for (Entry<String, String> tmprec : nevek.entrySet()) {
      String tmp_azon = tmprec.getKey();
      if (kocsik.containsKey(tmp_azon) && menny.containsKey(tmp_azon)) {
        final Map<String, String> aRec = new HashMap<>();
        aRec.put(ReszletAdapter.OSSZ_AZON, tmp_azon);
        aRec.put(ReszletAdapter.OSSZ_MEGNEV, nevek.get(tmp_azon));

      }
    }
    return tmplist;
  }

}
