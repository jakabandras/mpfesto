package com.andrewsoft.mpfesto;

import java.util.*;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

@SuppressWarnings("unused")
public class MainActivity extends Activity {

  /**
   * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the sections. We use a {@link FragmentPagerAdapter} derivative, which will keep every loaded fragment in memory. If this becomes too memory intensive, it may be best to switch to a {@link android.support.v13.app.FragmentStatePagerAdapter}.
   */
  public static final int           ZBAR_SCANNER_REQUEST    = 0;
  public static final int           ZBAR_QR_SCANNER_REQUEST = 1;

  SectionsPagerAdapter              mSectionsPagerAdapter;
  public final Map<String, View>    frags                   = new HashMap<>();
  public final Map<Integer, Button> btns                    = new HashMap<>();
  private ActionBar                 mAction;

  /**
   * The {@link ViewPager} that will host the section contents.
   */
  ViewPager                         mViewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.
    mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

    // Set up the ViewPager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.pager);
    mViewPager.setAdapter(mSectionsPagerAdapter);
    mViewPager
        .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

          @Override
          public void onPageSelected(int position) {
            Toast.makeText(getApplicationContext(), "Valami változott!!",
                Toast.LENGTH_LONG).show();
          }
        });

    mAction = getActionBar();
    mAction.setSubtitle(mSectionsPagerAdapter.getPageTitle(0));

    // frags.put("main", (View)findViewById(R.layout.fragment_main));
    View v = frags.get("main");
    if (v != null) {
      Button b1 = (Button) v.findViewById(R.id.btn_ScanTeszt);
      b1.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
          // TODO Auto-generated method stub

        }
      });
    }

  }

  public void initMainFrame() {
    if (frags.containsKey("main")) {
      View m = frags.get("main");
      if (m == null) {
        Toast.makeText(null, "Valami gáz van!", Toast.LENGTH_LONG).show();
        return;
      }
    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
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
      return PlaceholderFragment.newInstance(position);
    }

    @Override
    public int getCount() {
      // Show 3 total pages.

      return 2;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      return super.instantiateItem(container, position);
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

    /**
     * The fragment argument representing the section number for this fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private final int[]         frags_id           = {
                                                       R.layout.fragment_main ,
                                                       R.layout.fragment_elokeszit
                                                   };

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
      int sect = getArguments().getInt(ARG_SECTION_NUMBER);
      final MainActivity m = (MainActivity) getActivity();
      if (sect < 0) sect = 0;
      View rootView;
      if (sect < frags_id.length) rootView = inflater.inflate(frags_id[sect],
          container, false);
      else
        rootView = inflater.inflate(frags_id[0], container, false);
      if (sect == 0) {
        m.frags.put("main", rootView);
        Button btn = (Button) rootView.findViewById(R.id.btn_ScanTeszt);
        btn.setOnClickListener(new OnClickListener() {

          @Override
          public void onClick(View v) {
            // TODO Auto-generated method stub
            Toast.makeText(getActivity().getApplicationContext(), "ScanTeszt",
                Toast.LENGTH_LONG).show();
            Intent intent = new Intent(m, MyScanner.class);
            startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
          }
        });
        btn = (Button) rootView.findViewById(R.id.btn_termedit);
        btn.setOnClickListener(new OnClickListener() {

          @Override
          public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(m, Termek.class);
            startActivity(intent);
          }
        });
        btn = (Button) rootView.findViewById(R.id.btn_festes);
        btn.setOnClickListener(new OnClickListener() {

          @Override
          public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(m, Elokeszito.class);
            startActivity(intent);
          }
        });
      }
      else if (sect == 1) {
        m.frags.put("elokeszit", rootView);
      }
      return rootView;
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
    case ZBAR_SCANNER_REQUEST:
    case ZBAR_QR_SCANNER_REQUEST:
      if (resultCode == RESULT_OK) {
        Toast.makeText(this,
            "Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT),
            Toast.LENGTH_SHORT).show();
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
}
