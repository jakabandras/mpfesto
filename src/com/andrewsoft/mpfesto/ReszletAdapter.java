package com.andrewsoft.mpfesto;

import java.util.*;

import android.view.*;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ReszletAdapter extends BaseAdapter {

  public static final String        OSSZ_AZON   = "Azonosító";

  public static final String        OSSZ_MEGNEV = "Megnevezés";

  public static final String        OSSZ_KOCSI  = "Kocsiszám";

  public static final String        OSSZ_MENNY  = "Mennyiség";

  private List<Map<String, String>> myitems     = new ArrayList<>();

  private final FeladasDbf          mydbf;

  public ReszletAdapter( FeladasDbf dbf ) {

    mydbf = dbf;
    myitems = mydbf.Summary();
  }

  @Override
  public int getCount() {

    return myitems.size();
  }

  @Override
  public Map<String, String> getItem(int position) {

    return myitems.get(position);
  }

  @Override
  public long getItemId(int position) {

    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    final View result;

    if (convertView == null) {
      result = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_summary_kesz, parent, false);
    }
    else {
      result = convertView;
    }

    Map<String, String> item = getItem(position);

    ((TextView) result.findViewById(R.id.txt_lbl_sum_termnev)).setText(item.get(OSSZ_MEGNEV));
    ((TextView) result.findViewById(R.id.txt_lbl_sum_azon)).setText(item.get(OSSZ_AZON));
    ((TextView) result.findViewById(R.id.txt_lbl_sum_kocsidb)).setText(OSSZ_KOCSI);
    ((TextView) result.findViewById(R.id.txt_lbl_sum_osszdb)).setText(item.get(OSSZ_MENNY));

    return result;
  }
}
