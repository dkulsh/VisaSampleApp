package com.example.friendschatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class CustomQActionBarAdapter extends BaseAdapter {

	private int[] qActionBarItems = {R.drawable.left, R.drawable.first, R.drawable.second, R.drawable.third, R.drawable.fourth, R.drawable.right, R.drawable.setting};

	Context context;
	LayoutInflater infalator;

	public CustomQActionBarAdapter(Context context) {

		infalator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return qActionBarItems.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return qActionBarItems[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ImageView image = new ImageView(context);
		image.setImageResource(qActionBarItems[position]);
		image.setScaleType(ImageView.ScaleType.CENTER_CROP);
		
		return image;
	}

}
