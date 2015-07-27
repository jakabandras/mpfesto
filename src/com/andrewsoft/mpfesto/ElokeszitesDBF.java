package com.andrewsoft.mpfesto;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

public class ElokeszitesDBF {

  public static final String                      HDR_GYARDAT = "Gyártási dátum";
  public static final String                      HDR_GYRSZAM = "Gyártási szám";
  public static final String                      HDR_TAG     = "Tag";
  public static final String                      HDR_AZON    = "Azonosító";
  public static final String                      HDR_MEGNEV  = "Megnevezés";
  public static final String                      HDR_MENNY   = "Mennyiség";
  public static final String                      HDR_SELEJT  = "Fröccs selejt";
  public static final String                      HDR_NETTO   = "Nettó mennyiség";

  private static Map<String, Map<String, Object>> tarolok     = new HashMap<>();
  private final String[]                          myheader    = {
      HDR_GYARDAT , HDR_GYRSZAM , HDR_TAG , HDR_AZON , HDR_MEGNEV , HDR_MENNY ,
      HDR_SELEJT , HDR_NETTO
                                                              };

  private String                                  dbfName     = "";

  public String getDbfName() {
    return dbfName;
  }

  public void setDbfName(String dbfName) {
    this.dbfName = dbfName;
  }

  public ElokeszitesDBF( String path ) {
    // TODO Auto-generated constructor stub
    setDbfName(path + "/" + genDbfName());
    File f = new File(getDbfName());
    if (f.exists()) {
      try {
        readData();
      }
      catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  private String genDbfName() {
    // TODO Auto-generated method stub
    Calendar date = Calendar.getInstance();
    StringBuilder builder = new StringBuilder("EloDbf_");
    builder.append(date.get(Calendar.YEAR));
    builder.append(date.get(Calendar.MONTH) + 1);
    builder.append(date.get(Calendar.DAY_OF_MONTH));
    builder.append(".txt");
    return builder.toString();
  }

  public void readData() throws IOException {
    ICsvMapReader mapReader = null;
    try {
      mapReader = new CsvMapReader(new FileReader(this.getDbfName()),
          CsvPreference.STANDARD_PREFERENCE);
      final String[] header = mapReader.getHeader(true);
      final CellProcessor[] processors = getProcessors();
      Map<String, Object> record;
      while ((record = mapReader.read(header, processors)) != null) {
        tarolok.put(generateKey(record), record);
      }
    }
    finally {
      if (mapReader != null) mapReader.close();
    }

  }

  public boolean isStored(String key) {
    return tarolok.containsKey(key);
  }

  public String generateKey(Map<String, Object> record) {
    return (String) record.get(HDR_GYRSZAM) + (String) record.get(HDR_TAG);
  }

  public void writeData() {
    ICsvMapWriter mapWriter = null;
    try {
      mapWriter = new CsvMapWriter(new FileWriter(getDbfName()),
          CsvPreference.STANDARD_PREFERENCE);
      mapWriter.writeHeader(myheader);
      for (Entry<String, Map<String, Object>> rec : tarolok.entrySet()) {
        Map<String, Object> tmp = rec.getValue();
        mapWriter.write(tmp, myheader, getProcessors());
      }
      mapWriter.flush();
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    finally {
      if (mapWriter != null) try {
        mapWriter.close();
      }
      catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  public void addRecord(Map<String, Object> rec) {
    if (rec.containsKey(HDR_GYRSZAM) && rec.containsKey(HDR_TAG)) {
      final Map<String, Object> record = rec;
      final String kulcs = generateKey(record);
      if (!isStored(kulcs)) {
        tarolok.put(kulcs, record);
      }
    }
  }

  private static CellProcessor[] getProcessors() {
    final CellProcessor[] processors = {
        new NotNull() , // Gyártási dátum
        new NotNull() , // Gyártási szám
        new NotNull() , // Tag
        new NotNull() , // Azonosító
        new NotNull() , // Megnevezés
        new ParseInt() , // Mennyiség
        new ParseInt() , // Fröccs selejt
        new ParseInt()
    // Nettó mennyiség

    };
    return processors;
  }
}
