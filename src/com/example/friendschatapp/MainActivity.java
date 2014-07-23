package com.example.friendschatapp;

import com.example.friendschatapp.GroupManager.OnFragmentInteractionListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements OnFragmentInteractionListener{

	private DrawerLayout drawerLayout;
	private ListView navList;
	private GridView qActionBarList;
	
	GoogleMap googleMap;
	
	double latitude = 44.867031;
	double longitude = -93.334518;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initializing home screen variables
		drawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
		navList = (ListView) findViewById(R.id.lvDrawer);
		qActionBarList = (GridView) findViewById(R.id.lvQuickActionBar);
		
		// Creating the navigation drawer
		//navList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, navOptions ));
		navList.setAdapter(new CustomListAdapter(this));
		navList.setOnItemClickListener(new CustomOnItemClickListener(this, getSupportFragmentManager()));
	/*	navList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_drawer, new GroupManager())
                .commit();
				
			}
		});*/
		
		qActionBarList.setAdapter(new CustomQActionBarAdapter(this));
		qActionBarList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SingleChatFragment()).commit();
			}
		});
		
		// Creating marker on the map
		googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		LatLng location = new LatLng(latitude, longitude);
		MarkerOptions marker = new MarkerOptions().position(location);
		//marker.title("Hello Maps");

		googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(this));
		marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_current_location));
		//View markerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_view, null);
		//marker.icon(BitmapDescriptorFactory.fromBitmap(createBitmapFromView(markerView)));
		
		googleMap.addMarker(marker);
		
		/* Click on Info window might have to lead us to the chat window.
		 * That functionality to be implemented below
		 */
		googleMap.setOnInfoWindowClickListener(new CustomOnInfoWindowClickListner());
	}
	
	private Bitmap createBitmapFromView(View view){
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		
		view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
		view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
		view.buildDrawingCache();
		
		Bitmap markerImage = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(markerImage);
		
		view.draw(canvas);
		return markerImage;
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onFragmentInteraction(Uri uri) {
		// TODO Auto-generated method stub
		
	}

}
