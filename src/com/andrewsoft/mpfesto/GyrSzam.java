/**
 * 
 */
package com.andrewsoft.mpfesto;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

/**
 * @author Andrew
 * 
 */
public class GyrSzam implements CellProcessor {

  /**
   * 
   */
  public GyrSzam( ) {
    // TODO Auto-generated constructor stub
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.supercsv.cellprocessor.ift.CellProcessor#execute(java.lang.Object, org.supercsv.util.CsvContext)
   */
  @Override
  public Object execute(Object arg0, CsvContext arg1) {
    // TODO Auto-generated method stub
    String tmp = arg0.toString();
    return null;
  }

}
