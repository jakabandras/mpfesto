/**
 * 
 */
package com.andrewsoft.mpfesto;

import java.io.IOException;
import java.util.Map;

import org.supercsv.cellprocessor.ift.CellProcessor;

/**
 * @author Andrew
 * 
 */
public interface MyDbf {

  // MyDbf(String path);

  public void readRecords() throws IOException;

  public String genDbfName();

  public String getDbfName();

  public void setDbfName(String name);

  public void writeRecords() throws IOException;

  public void setKeyField(String keyname);

  public String getKeyField();

  public void addRecord(Map<String, Object> record);;

  public boolean isStored(String key);

  public String[] getHeaders();

  public CellProcessor[] getProcessors();

  public int Sum(String fieldName);

}
