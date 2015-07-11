/**
 * 
 */
package com.andrewsoft.mpfesto;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

/**
 * @author Andrew
 * 
 */
public class TaroloObject {

  private final Map<String, Object> tarolok     = new HashMap<>();
  private final String              dbfName;
  public static final String        HDR_GYARDAT = "Gyártási dátum";
  public static final String        HDR_GYRSZ   = "Gyártási szám";
  public static final String        HDR_TAG     = "Tag";
  public static final String        HDR_AZON    = "Azonosító";
  public static final String        HDR_MEGNEV  = "Megnevezés";
  public static final String        HDR_MENNY   = "Mennyiség";

  private final String[]            myheaders   = new String[] {
      HDR_GYARDAT , HDR_GYRSZ , HDR_TAG , HDR_AZON , HDR_MEGNEV , HDR_MENNY
                                                };

  public String[] getMyheaders() {
    return myheaders;
  }

  public String getDbfName() {
    return dbfName;
  }

  /**
   * 
   */
  public TaroloObject( String dbfname ) {
    // TODO Auto-generated constructor stub
    dbfName = dbfname;
  }

  public boolean isStored(String key) {
    return tarolok.containsKey(key);
  }

  public String generateKey(Map<String, Object> record) {
    return (String) record.get(HDR_GYRSZ) + (String) record.get(HDR_TAG);
  }

  public void readData() throws IOException {
    ICsvMapReader mapReader = null;
    try {
      mapReader = new CsvMapReader(new FileReader(this.getDbfName()),
          CsvPreference.STANDARD_PREFERENCE);
      final String[] header = mapReader.getHeader(true);
      final CellProcessor[] processors = getTaroloProcessors();
      Map<String, Object> record;
      while ((record = mapReader.read(header, processors)) != null) {
        tarolok.put(generateKey(record), record);
      }
    }
    finally {
      if (mapReader != null) mapReader.close();
    }

  }

  public void writeData() {

  }

  private static CellProcessor[] getTaroloProcessors() {
    final CellProcessor[] myProcessors = new CellProcessor[] {
        new ParseDate("yyyy.mm.dd.") , // Gyártási dátum
        new NotNull() , // Gyártási szám
        new NotNull() , // Tag
        new NotNull() , // Azonosító
        new NotNull() , // Megnevezés
        new ParseInt()
    // Mennyiség
    };
    return myProcessors;
  }

}
