package com.andrewsoft.mpfesto;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;

public class PdfPage {

  private Rectangle pageSize = PageSize.A4;
  private String    fileName;

  public PdfPage( ) {
    // TODO Auto-generated constructor stub
  }

  /**
   * @return the pageSize
   */
  public Rectangle getPageSize() {
    return pageSize;
  }

  /**
   * @param pageSize
   *          the pageSize to set
   */
  public void setPageSize(Rectangle pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * @return the fileName
   */
  public String getFileName() {
    return fileName;
  }

  /**
   * @param fileName
   *          the fileName to set
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

}
