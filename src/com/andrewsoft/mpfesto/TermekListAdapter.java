package com.andrewsoft.mpfesto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TermekListAdapter extends ArrayAdapter<Map<String, Object>> {

  private final Context                   context;
  private final List<Map<String, Object>> values;

  public TermekListAdapter( Context context , Map<String, Object>[] objects ) {
    super(context, R.layout.listitem_layout, objects);
    this.context = context;
    values = new ArrayList<>();
    for (Map<String, Object> rec : objects) {
      values.add(rec);
    }
    // TODO Auto-generated constructor stub
  }

  @SuppressLint("ViewHolder")
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.listitem_layout, parent, false);
    TextView tv1 , tv2;
    tv1 = (TextView) rowView.findViewById(R.id.lbMegnev);
    tv2 = (TextView) rowView.findViewById(R.id.lbAzon);

    String txtmegnev , txtazon;
    txtmegnev = values.get(position).get(Termek.HDR_MEGNEV).toString();
    txtazon = values.get(position).get(Termek.HDR_CIKKSZAM).toString();
    tv1.setText(txtmegnev);
    tv2.setText(txtazon);
    return rowView;
  }

}
