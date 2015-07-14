package com.andrewsoft.mpfesto;

import java.util.Locale;

import android.app.*;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.*;

public class Elokeszito extends Activity {

  /**
   * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the sections. We use a {@link FragmentPagerAdapter} derivative, which will keep every loaded fragment in memory. If this becomes too memory intensive, it may be best to switch to a {@link android.support.v13.app.FragmentStatePagerAdapter}.
   */
  SectionsPagerAdapter   mSectionsPagerAdapter;
  private ElokeszitesDBF myDbf;
  /**
   * The {@link ViewPager} that will host the section contents.
   */
  ViewPager              mViewPager;

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
        // TODO Auto-generated method stub

      }
    });

  }

  private void initActivity() {
    // TODO Auto-generated method stub

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
      return 2;
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

    public final int            myFrags[]          = {
                                                       R.layout.fragment_elokeszito ,
                                                       R.layout.fragment_ek_reszlet
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
      int sect = getArguments().getInt(ARG_SECTION_NUMBER, 0);
      final Activity myActivity = getActivity();

      if (sect < 0) sect = 0;
      View rootView;
      if (sect < myFrags.length) rootView = inflater.inflate(myFrags[sect - 1],
          container, false);
      else
        rootView = inflater.inflate(myFrags[0], container, false);
      myHolder.initWidgets(rootView, (Elokeszito) this.getActivity());
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

  private static class myHolder {

    static EditText   gyardat , gyrszam , tag , cikkszam , mennyiseg;
    static TextView   megnevezes;
    static Button     scan , ment;
    static Elokeszito mActivity;

    public static void initWidgets(View rootView, Elokeszito activity) {

      // TODO Auto-generated method stub
      mActivity = activity;
      gyardat = (EditText) rootView.findViewById(R.id.edt_elo_gyardat);
      gyrszam = (EditText) rootView.findViewById(R.id.edt_elo_gyrsz);
      tag = (EditText) rootView.findViewById(R.id.edt_elo_tag);
      cikkszam = (EditText) rootView.findViewById(R.id.edt_elo_azon);
      mennyiseg = (EditText) rootView.findViewById(R.id.edt_elo_menny);
      megnevezes = (TextView) rootView.findViewById(R.id.tv_elo_Megnevez);
      scan = (Button) rootView.findViewById(R.id.btn_scan_elokesz);
      ment = (Button) rootView.findViewById(R.id.btn_sv_elokesz);

      cikkszam.addTextChangedListener(new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
            int after) {
          // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
            int count) {
          // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
          // TODO Auto-generated method stub
          String csz = s.toString();
          if (findAzon(csz)) {

          }
          else {

          }
        }
      });
    }

    protected static boolean findAzon(String csz) {
      // TODO Auto-generated method stub
      return false;
    }
  }

}
