package com.andrewsoft.mpfesto;

import java.io.IOException;
import java.util.*;

import android.app.*;
import android.content.Intent;
import android.content.pm.*;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.*;

public class Elokeszito extends Activity {

  /**
   * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the sections. We use a {@link FragmentPagerAdapter} derivative, which will keep every loaded fragment in memory. If this becomes too memory intensive, it may be best to switch to a {@link android.support.v13.app.FragmentStatePagerAdapter}.
   */
  SectionsPagerAdapter      mSectionsPagerAdapter;

  private ElokeszitesDBF    myDbf;

  private TermekDbf         term;

  private final KocsiDbf    kocsik  = KocsiDbf.getInstance();

  private static FeladasDbf felad;

  private int               actPage = 1;

  /**
   * The {@link ViewPager} that will host the section contents.
   */
  ViewPager                 mViewPager;

  private PackageManager    pm;

  /*
   * (non-Javadoc)
   * 
   * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    switch (requestCode) {
      case MainActivity.ZBAR_SCANNER_REQUEST:
      case MainActivity.ZBAR_QR_SCANNER_REQUEST:
        if (resultCode == RESULT_OK) {
          // Toast.makeText(this, "Itt vagyok!", Toast.LENGTH_LONG).show();
          final String code = data.getStringExtra(ZBarConstants.SCAN_RESULT);
          final String type = data.getStringExtra(ZBarConstants.SCAN_RESULT_TYPE);
          switch (actPage) {
            case 1: // Első oldal
              parseCode(code);
              break;
            case 2:
              parseFeladCode(code, type);
              break;
            case 3:
              break;
          }
        }
        else if (resultCode == RESULT_CANCELED && data != null) {
          String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
          if (!TextUtils.isEmpty(error)) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
          }
        }
        break;

    }
  }

  @SuppressWarnings( "static-access" )
  private void parseFeladCode(String code, String type) {

    if (type != "QRCODE") {
      holderFelad.azon.setText(code);
      if (term.isStored(code)) {
        Map<String, Object> tmp = term.records.get(code);
        holderFelad.termeknev.setText(tmp.get(TermekDbf.HDR_MEGNEV).toString());
      }
    }
    else {
      holderFelad.emberek.setText(code);
    }

  }

  private void parseCode(String code) {

    if (code.startsWith("XM")) {
      myHolder.tag.setText(code);
    }
    else if (code.startsWith("GYR")) {
      myHolder.gyrszam.setText(code);
    }
    else {
      myHolder.cikkszam.setText(code);
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_elokeszito);
    initActivity();
    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.
    mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

    // Set up the ViewPager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.pager);
    mViewPager.setAdapter(mSectionsPagerAdapter);
    mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

      @Override
      public void onPageScrollStateChanged(int arg0) {

        // TODO Auto-generated method stub

      }

      @Override
      public void onPageScrolled(int arg0, float arg1, int arg2) {

        // TODO Auto-generated method stub

      }

      @Override
      public void onPageSelected(int arg0) {

        actPage = arg0 + 1;

      }
    });

  }

  @SuppressWarnings( "unused" )
  private void setReadedCode(String code) {

    if (code.startsWith("XM")) {
      myHolder.tag.setText(code);
    }
    else if (code.startsWith("GYR")) {
      myHolder.gyrszam.setText(code);
    }
    else
      myHolder.cikkszam.setText(code);
  }

  private void initActivity() {

    pm = getPackageManager();
    PackageInfo pi;
    try {
      pi = pm.getPackageInfo("com.andrewsoft.mpfesto", 0);
      myDbf = new ElokeszitesDBF(pi.applicationInfo.dataDir);
      term = new TermekDbf(pi.applicationInfo.dataDir);
      kocsik.setDbfPath(pi.applicationInfo.dataDir);
      kocsik.setDbfName(kocsik.genDbfName());
      try {
        kocsik.readRecords();
      }
      catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      felad = FeladasDbf.getInstance(pi.applicationInfo.dataDir);
    }
    catch (NameNotFoundException e) {
      e.printStackTrace();
      // e.
    }

  }

  public void initFeladHandlers() {

    holderFelad.azon.addTextChangedListener(new TextWatcher() {

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        // TODO Auto-generated method stub

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

        // TODO Auto-generated method stub

      }

      @Override
      public void afterTextChanged(Editable s) {

        final String tmpAzon = s.toString();
        if (term.isStored(tmpAzon)) {
          @SuppressWarnings( "static-access" )
          final String megn = term.records.get(tmpAzon).get(TermekDbf.HDR_MEGNEV).toString();
          holderFelad.termeknev.setText(megn);
          @SuppressWarnings( "static-access" )
          final String txtMenny = term.records.get(tmpAzon).get(TermekDbf.HDR_DEF_KOCS).toString();
          holderFelad.mennyiseg.setText(txtMenny);
        }
      }
    });
    holderFelad.azon.setOnFocusChangeListener(new OnFocusChangeListener() {

      @Override
      public void onFocusChange(View v, boolean hasFocus) {

        // TODO Auto-generated method stub
        if (!hasFocus) {
          EditText tv = (EditText) v;
          String st_azon = tv.getText().toString();
          if (term.isStored(st_azon)) {
            @SuppressWarnings( "static-access" )
            final String megn = term.records.get(st_azon).get(TermekDbf.HDR_MEGNEV).toString();
            holderFelad.termeknev.setText(megn);
            @SuppressWarnings( "static-access" )
            final String txtMenny = term.records.get(st_azon).get(TermekDbf.HDR_DEF_KOCS).toString();
            holderFelad.mennyiseg.setText(txtMenny);

          }
        }

      }
    });
    holderFelad.save.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {

        doFeladSave();
      }
    });

    holderFelad.scanner.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {

        doScanner();
      }
    });
  }

  protected void doFeladSave() {

    final Map<String, Object> rec = new HashMap<>();
    rec.put(FeladasDbf.HDR_AZON, holderFelad.azon.getText().toString());
    rec.put(FeladasDbf.HDR_MEGNEV, holderFelad.termeknev.getText());
    rec.put(FeladasDbf.HDR_MENNY, Integer.valueOf(holderFelad.mennyiseg.getText().toString()));
    String ksz = holderFelad.kocsiszam.getText().toString();
    rec.put(FeladasDbf.HDR_KOCSISZAM, Integer.valueOf(ksz));
    rec.put(FeladasDbf.HDR_MEGJ, holderFelad.megjegyzes.getText().toString());
    rec.put(FeladasDbf.HDR_TIPUS, holderFelad.tipus.getText().toString());
    rec.put(FeladasDbf.HDR_EMBEREK, holderFelad.emberek.getText().toString());
    felad.addRecord(rec);
    try {
      felad.writeRecords();
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  private void InitMyHolderHandlers() {

    myHolder.scan.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {

        doScanner();
      }
    });

    myHolder.ment.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {

        doSave();
      }
    });

    myHolder.cikkszam.addTextChangedListener(new TextWatcher() {

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        // TODO Auto-generated method stub

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

        // TODO Auto-generated method stub

      }

      @SuppressWarnings( "static-access" )
      @Override
      public void afterTextChanged(Editable s) {

        String csz = s.toString();
        if (term.isStored(csz)) {
          myHolder.megnevezes.setText(term.records.get(csz).get(TermekDbf.HDR_MEGNEV).toString());
          if (myHolder.mennyiseg.getText().toString().equals("000")) myHolder.mennyiseg.setText(term.records.get(csz).get(TermekDbf.HDR_DEF_CONT).toString());
        }
      }
    });
  }

  protected void doSave() {

    final Map<String, Object> record = new HashMap<>();
    record.put(ElokeszitesDBF.HDR_AZON, myHolder.cikkszam.getText().toString());
    record.put(ElokeszitesDBF.HDR_GYARDAT, myHolder.gyardat.getText().toString());
    record.put(ElokeszitesDBF.HDR_GYRSZAM, myHolder.gyrszam.getText().toString());
    record.put(ElokeszitesDBF.HDR_MEGNEV, myHolder.megnevezes.getText().toString());
    record.put(ElokeszitesDBF.HDR_MENNY, myHolder.mennyiseg.getText().toString());
    record.put(ElokeszitesDBF.HDR_NETTO, "0");
    record.put(ElokeszitesDBF.HDR_SELEJT, "0");
    record.put(ElokeszitesDBF.HDR_TAG, myHolder.tag.getText().toString());
    myDbf.addRecord(record);
    myDbf.writeData();
    myHolder.cikkszam.setText("");
    myHolder.gyardat.setText("");
    myHolder.gyrszam.setText("");
    myHolder.megnevezes.setText("...");
    myHolder.mennyiseg.setText(R.string.txt_000);
    myHolder.tag.setText("");
  }

  public void doScanner() {

    Intent intent = new Intent(this, MyScanner.class);

    startActivityForResult(intent, MainActivity.ZBAR_SCANNER_REQUEST);

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.elokeszito, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) { return true; }
    return super.onOptionsItemSelected(item);
  }

  /**
   * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the sections/tabs/pages.
   */
  public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter( FragmentManager fm ) {

      super(fm);
    }

    @Override
    public Fragment getItem(int position) {

      // getItem is called to instantiate the fragment for the given page.
      // Return a PlaceholderFragment (defined as a static inner class below).
      return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {

      // Show 3 total pages.
      return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

      Locale l = Locale.getDefault();
      switch (position) {
        case 0:
          return getString(R.string.title_section1).toUpperCase(l);
        case 1:
          return getString(R.string.title_section2).toUpperCase(l);
        case 2:
          return getString(R.string.title_section3).toUpperCase(l);
      }
      return null;
    }
  }

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends Fragment {

    public final int            myFrags[]          =
                                                   {
                                                       R.layout.fragment_elokeszito , R.layout.fragment_kocsikesz , R.layout.fragment_ek_reszlet
                                                   };

    /**
     * The fragment argument representing the section number for this fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {

      PlaceholderFragment fragment = new PlaceholderFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER, sectionNumber);
      fragment.setArguments(args);
      return fragment;
    }

    public PlaceholderFragment( ) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      int sect = getArguments().getInt(ARG_SECTION_NUMBER, 0);
      final Elokeszito myActivity = (Elokeszito) getActivity();
      if (sect < 0) sect = 0;
      View rootView;
      if (sect <= myFrags.length) rootView = inflater.inflate(myFrags[sect - 1], container, false);
      else
        rootView = inflater.inflate(myFrags[0], container, false);
      if (sect == 1) {
        myHolder.initWidgets(rootView, (Elokeszito) this.getActivity());
        myActivity.InitMyHolderHandlers();
      }
      if (sect == 2) {
        holderFelad.initWidgets(rootView, (Elokeszito) this.getActivity());
        myActivity.initFeladHandlers();
      }
      if (sect == 3) {
        holderReszlet.initWidgets(rootView, (Elokeszito) this.getActivity());
        myActivity.initReszletHandlers();
      }
      switch (sect) {
        case 1:
          doInitMainFragment(myActivity, rootView);
          break;
      }
      return rootView;
    }
  }

  public static void doInitMainFragment(Activity myActivity, View rootView) {

    // TODO Auto-generated method stub

  }

  public void initReszletHandlers() {

    // TODO Auto-generated method stub

  }

  private static class myHolder {

    static EditText           gyardat , gyrszam , tag , cikkszam , mennyiseg;

    static TextView           megnevezes;

    static Button             scan , ment;

    private static Elokeszito mActivity;

    public static void initWidgets(View rootView, Elokeszito activity) {

      setmActivity(activity);
      gyardat = (EditText) rootView.findViewById(R.id.edt_elo_gyardat);
      gyrszam = (EditText) rootView.findViewById(R.id.edt_elo_gyrsz);
      tag = (EditText) rootView.findViewById(R.id.edt_elo_tag);
      cikkszam = (EditText) rootView.findViewById(R.id.edt_elo_azon);
      mennyiseg = (EditText) rootView.findViewById(R.id.edt_elo_menny);
      megnevezes = (TextView) rootView.findViewById(R.id.tv_elo_Megnevez);
      scan = (Button) rootView.findViewById(R.id.btn_scan_elokesz);
      ment = (Button) rootView.findViewById(R.id.btn_sv_elokesz);
    }

    @SuppressWarnings( "unused" )
    public static Elokeszito getmActivity() {

      return mActivity;
    }

    public static void setmActivity(Elokeszito mActivity) {

      myHolder.mActivity = mActivity;
    }

  }

  private static class holderFelad {

    // TODO tipus elkészítése a beviteli képernyőn
    static EditText           azon , kocsiszam , mennyiseg , megjegyzes , emberek , tipus;

    static TextView           termeknev;

    static Button             scanner , save;

    private static Elokeszito mActivity;

    public static void initWidgets(View rootView, Elokeszito activity) {

      setmActivity(activity);
      azon = (EditText) rootView.findViewById(R.id.edt_kk_azon);
      kocsiszam = (EditText) rootView.findViewById(R.id.edt_kk_kocsiszam);
      mennyiseg = (EditText) rootView.findViewById(R.id.edt_kesz_menny);
      megjegyzes = (EditText) rootView.findViewById(R.id.edt_megj);
      emberek = (EditText) rootView.findViewById(R.id.edt_emberek);
      tipus = (EditText) rootView.findViewById(R.id.edt_kk_altip);

      termeknev = (TextView) rootView.findViewById(R.id.txt_lbl_termnev);

      scanner = (Button) rootView.findViewById(R.id.btn_kk_scan);
      save = (Button) rootView.findViewById(R.id.btn_kk_save);
    }

    @SuppressWarnings( "unused" )
    public static Elokeszito getmActivity() {

      return mActivity;
    }

    public static void setmActivity(Elokeszito mActivity) {

      holderFelad.mActivity = mActivity;
    }
  }

  @SuppressWarnings( "unused" )
  private static class holderReszlet {

    static ReszletAdapter rAdapter;

    static ListView       osszesit;

    static Elokeszito     mActivity;

    public static void initWidgets(View rootView, Elokeszito activity) {

      mActivity = activity;
      osszesit = (ListView) rootView.findViewById(R.id.lv_eloreszlet);
      rAdapter = new ReszletAdapter(felad);
      osszesit.setAdapter(rAdapter);
    }
  }

}
