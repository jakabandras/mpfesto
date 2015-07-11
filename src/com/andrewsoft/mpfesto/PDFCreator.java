package com.andrewsoft.mpfesto;

import com.itextpdf.text.Document;

public class PDFCreator {

  private final PdfPage  myPage;
  private final Document myDoc;

  public PDFCreator( PdfPage mypage ) {
    // TODO Auto-generated constructor stub
    myPage = mypage;
    myDoc = new Document(myPage.getPageSize());
    myDoc.addAuthor("Jakab András");
  }

}
