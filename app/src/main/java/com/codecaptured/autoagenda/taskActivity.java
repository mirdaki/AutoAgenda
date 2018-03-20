package com.codecaptured.autoagenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class taskActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);

		android.util.DisplayMetrics dm = new android.util.DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		int w = dm.widthPixels;
		int h = dm.heightPixels;

		getWindow().setLayout(((int)(w*.75)), ((int)(h*.8)));
	}
}
