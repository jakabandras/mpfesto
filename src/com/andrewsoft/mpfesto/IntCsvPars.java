/**
 * 
 */
package com.andrewsoft.mpfesto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

/**
 * @author Andrew
 * 
 */
public class IntCsvPars
{
  private final List<Map<String, Object>> records      = new ArrayList<>();
  private final String                    termekek;
  public static String                    HDR_CIKKSZAM = "Cikkszám";
  public static String                    HDR_MEGNEV   = "Megnevezés";
  public static String                    HDR_DEF_CONT = "Tároló db.";
  public static String                    HDR_DEF_KOCS = "Kocsi db.";
  public static String                    HDR_CSOM     = "Csomag db.";

  /**
   * 
   */
  public IntCsvPars( String data_path )
  {
    // TODO Auto-generated constructor stub
    termekek = data_path + "/Termekek.txt";

    InitTermek();
  }

  private void InitTermek( )
  {
    // TODO Auto-generated method stub
    final File cikkData = new File(termekek);
    if ( cikkData.exists() ) return;
    final Map<String, Object> record = new HashMap<>();
    AddTermek(new HashMap<String, Object>(), "13-259998Z01-A",
        "Panel Rear F3X", 60, 288, 60);
    AddTermek(new HashMap<String, Object>(), "13-25999Z01-D/1",
        "Panel Front F3X", 60, 234, 60);
    // More ...
    //
    SaveList(termekek, records);
  }

  private void AddTermek( Map<String, Object> record_new , String cikksz ,
      String megnev , int tarolo , int kocsi , int csomag )
  {
    // TODO Auto-generated method stub
    final Map<String, Object> record = record_new;
    record.put(HDR_CIKKSZAM, cikksz);
    record.put(HDR_MEGNEV, megnev);
    record.put(HDR_DEF_CONT, tarolo);
    record.put(HDR_DEF_KOCS, kocsi);
    record.put(HDR_CSOM, csomag);
    records.add(record);
  }

  public static CellProcessor[] getTermekProcessor( )
  {
    final CellProcessor[] processors = new CellProcessor[]
      { new NotNull(), // Cikkszám
          new NotNull(), // Megnevezés
          new ParseInt(), // Tároló db
          new ParseInt(), // Kocsi db
          new ParseInt() // Csomagolás db
      };
    return processors;
  }

  public void SaveList( String filename , List<Map<String, Object>> recs )
  {
    final String[] header = (String[]) recs.get(0).keySet().toArray();
    ICsvMapWriter mapWriter = null;
    try
    {
      mapWriter = new CsvMapWriter(new FileWriter(filename),
          CsvPreference.STANDARD_PREFERENCE);
      final CellProcessor[] cikkek = getTermekProcessor();
      mapWriter.writeHeader(header);
      for ( Map<String, Object> rec : recs )
      {
        mapWriter.write(rec, header, cikkek);
      }
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    finally
    {
      try
      {
        if ( mapWriter != null ) mapWriter.close();
      }
      catch (IOException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}
