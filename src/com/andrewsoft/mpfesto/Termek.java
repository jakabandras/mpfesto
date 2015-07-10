package com.andrewsoft.mpfesto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Termek extends Activity {
	
	private Button btnNew;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_termek);
		
		btnNew = (Button) findViewById(R.id.btn_newitem);
		btnNew.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getBaseContext(), NewItem.class);
				startActivityForResult(intent, 0);
			}});
	}
	
	public void onActivityResult() {
		
	}
}
