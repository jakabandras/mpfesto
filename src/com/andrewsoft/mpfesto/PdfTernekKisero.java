package com.andrewsoft.mpfesto;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfTernekKisero {

  private PdfDocument doc     = null;

  private PdfWriter   writer;

  private String      pdfname = "";

  public PdfTernekKisero( String name ) {

    // TODO Auto-generated constructor stub
    doc = new PdfDocument();
    doc.setPageSize(PageSize.A7);
    if (name == null) {
      pdfname = generatePdfName();
    }
    else {
      pdfname = name;
    }
    Element el = new Rectangle(3, 3, 53, 53);
    try {

      writer = PdfWriter.getInstance(doc, new FileOutputStream(pdfname));
      doc.open();
      doc.add(el);
      doc.close();
    }
    catch (DocumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private String generatePdfName() {

    // TODO Auto-generated method stub
    return null;
  }

}
