package com.example.friendschatapp;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class CustomOnItemClickListener implements OnItemClickListener {
	
	private Context context;
	FragmentManager fragManager;
	
	public CustomOnItemClickListener(Context context, FragmentManager fragmentManager){
		
		this.context = context;
		this.fragManager = fragmentManager;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		Log.d("ChatApp", "Before calling fragment");
		fragManager.beginTransaction().replace(android.R.id.content, new GroupManager()).commit();
		
	}

}
