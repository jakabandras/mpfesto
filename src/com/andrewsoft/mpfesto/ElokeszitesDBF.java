package com.andrewsoft.mpfesto;

import java.util.*;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.io.ICsvMapWriter;

public class ElokeszitesDBF {

  public static final String               HDR_GYARDAT = "Gyártási dátum";
  public static final String               HDR_GYRSZAM = "Gyártási szám";
  public static final String               HDR_TAG     = "Tag";
  public static final String               HDR_AZON    = "Azonosító";
  public static final String               HDR_MEGNEV  = "Megnevezés";
  public static final String               HDR_MENNY   = "Mennyiség";
  public static final String               HDR_SELEJT  = "Fröccs selejt";
  public static final String               HDR_NETTO   = "Nettó mennyiség";

  private static List<Map<String, Object>> records     = new ArrayList<>();

  private final String[]                   myheader    = {
      HDR_GYARDAT , HDR_GYRSZAM , HDR_TAG , HDR_AZON , HDR_MEGNEV , HDR_MENNY ,
      HDR_SELEJT , HDR_NETTO
                                                       };

  private String                           dbfName     = "";

  public String getDbfName() {
    return dbfName;
  }

  public void setDbfName(String dbfName) {
    this.dbfName = dbfName;
  }

  public ElokeszitesDBF( String path ) {
    // TODO Auto-generated constructor stub
    setDbfName(path + "/" + genDbfName());
  }

  private String genDbfName() {
    // TODO Auto-generated method stub
    Calendar date = Calendar.getInstance();
    StringBuilder builder = new StringBuilder("EloDbf_");
    builder.append(date.get(Calendar.YEAR));
    builder.append(date.get(Calendar.MONTH));
    builder.append(date.get(Calendar.DAY_OF_MONTH));
    builder.append(".txt");
    return builder.toString();
  }

  public static void readRecords() {
    ICsvMapReader mapReader;

  }

  public static void writeRecords() {
    ICsvMapWriter mapWriter;

  }

  private static CellProcessor[] getProcessors() {
    final CellProcessor[] processors = {

    };
    return processors;
  }
}
