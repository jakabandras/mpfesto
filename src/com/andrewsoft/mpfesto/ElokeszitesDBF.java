package com.andrewsoft.mpfesto;

import java.util.*;

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

  public ElokeszitesDBF( ) {
    // TODO Auto-generated constructor stub
  }

  public static void readRecords() {

  }

  public static void writeRecords() {

  }
}
