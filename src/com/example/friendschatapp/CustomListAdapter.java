package com.example.friendschatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {
	
	private String[] navOptions = {"Family", "Friends", "Others", "Manage Groups", "Car Pooling"};
	private int[] navIcons = {R.drawable.family_icon, R.drawable.friends, R.drawable.others, R.drawable.groups, R.drawable.carpool};
	
	TextView navOptionText;
	ImageView navOptionIcon;
	//View view;
	
	LayoutInflater inflater;

	public CustomListAdapter(Context context) {
		
		//view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_row, null);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return navOptions.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView == null ? inflater.inflate(R.layout.list_row, null) : convertView;

		navOptionIcon = (ImageView) view.findViewById(R.id.imgItemIcon);
		navOptionText = (TextView) view.findViewById(R.id.tvRow);
		
		navOptionIcon.setImageResource(navIcons[position]);
		navOptionText.setText(navOptions[position]);
		return view;
	}

}
