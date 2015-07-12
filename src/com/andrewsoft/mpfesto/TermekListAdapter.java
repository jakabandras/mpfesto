package com.andrewsoft.mpfesto;

import java.util.List;
import java.util.Map;

import android.view.*;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TermekListAdapter extends BaseAdapter {

  private final List<Map<String, Object>> values;

  // private final ArrayList mData;

  public TermekListAdapter( List<Map<String, Object>> map ) {
    values = map;
  }

  @Override
  public int getCount() {
    return values.size();
  }

  @Override
  public Map<String, Object> getItem(int position) {
    return values.get(position);
  }

  @Override
  public long getItemId(int position) {
    // TODO implement you own logic with ID
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    final View result;

    if (convertView == null) {
      result = LayoutInflater.from(parent.getContext()).inflate(
          R.layout.listitem_layout, parent, false);
    }
    else {
      result = convertView;
    }

    Map<String, Object> item = getItem(position);

    // TODO replace findViewById by ViewHolder
    ((TextView) result.findViewById(R.id.lbMegnev)).setText(item.get(
        Termek.HDR_MEGNEV).toString());
    ((TextView) result.findViewById(R.id.lbAzon)).setText(item.get(
        Termek.HDR_CIKKSZAM).toString());

    return result;
  }
}
