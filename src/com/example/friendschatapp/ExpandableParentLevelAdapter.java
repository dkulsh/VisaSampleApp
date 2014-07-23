package com.example.friendschatapp;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ExpandableParentLevelAdapter extends BaseExpandableListAdapter {
	
	String[] groupTypes = {"Friends", "Family", "Others"};
	int[] drawables = {R.drawable.friends, R.drawable.family_icon, R.drawable.others};
	
	List<HashMap<String, HashSet<String>>> friendGroupList;
	List<HashMap<String, HashSet<String>>> familyGroupList;
	List<HashMap<String, HashSet<String>>> othersGroupList;
	
	Object[] mapsFriend;
	Object[] mapsFamily;
	Object[] mapsOther;
	
	LayoutInflater inflater;
	Context context;

	public ExpandableParentLevelAdapter(Context context) {
		
		this.context = context;
		
		Gson gson = new Gson();
		Type collectionType = new TypeToken<List<HashMap<String, HashSet<String>>>>(){}.getType();
		
		SharedPreferences prefFriend = context.getSharedPreferences(groupTypes[0], Context.MODE_PRIVATE);
		String friendGroupSetString = prefFriend.getString(groupTypes[0], "");
		friendGroupList = new Gson().fromJson(friendGroupSetString, collectionType);
		mapsFriend = friendGroupList.toArray();
		
		SharedPreferences prefFamily = context.getSharedPreferences(groupTypes[1], Context.MODE_PRIVATE);
		String familyGroupSetString = prefFamily.getString(groupTypes[1], "");
		familyGroupList = new Gson().fromJson(familyGroupSetString, collectionType);
		mapsFamily = familyGroupList.toArray();
		
		SharedPreferences prefOther = context.getSharedPreferences(groupTypes[2], Context.MODE_PRIVATE);
		String othersGroupSetString = prefOther.getString(groupTypes[2], "");
		othersGroupList = new Gson().fromJson(othersGroupSetString, collectionType);
		mapsOther = othersGroupList.toArray();
	}

	@Override
	public int getGroupCount() {

		return groupTypes.length;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		
		switch (groupPosition) {
		
		case 1:
			return friendGroupList.size();
		case 2:
			return familyGroupList.size();
		case 3:
			return othersGroupList.size();
		default:
			break;
		}

		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		final int groupPositionView = groupPosition;

		View view = convertView == null ? inflater.inflate(R.layout.manage_groups_row, null) : convertView;
		
		ImageView parentIcon = (ImageView) view.findViewById(R.id.imgItemIcon);
		TextView parentText = (TextView) view.findViewById(R.id.tvRow);
		
		CheckBox chkBox = (CheckBox) view.findViewById(R.id.chkBox);
		//chkBox.setVisibility(View.GONE);
		chkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(
					CompoundButton buttonView,
					boolean isChecked) {
				
				GroupManager.groupTypeSelectionList.add(groupPositionView, isChecked);
			}
		});
		
		parentIcon.setImageResource(drawables[groupPosition]);
		parentText.setText(groupTypes[groupPosition]);
		
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		ListView secondLevelList = new ListView(context);
		secondLevelList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		secondLevelList.setAdapter(new SecondLevelAdapter(groupPosition));
		return secondLevelList;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public class SecondLevelAdapter extends BaseAdapter {

		int groupPosition;
		
		public SecondLevelAdapter(int groupPosition) {
			
			this.groupPosition = groupPosition;
		}

		@Override
		public int getCount() {
			
			switch (groupPosition) {
			
			case 1:
				return friendGroupList.size();
			case 2:
				return familyGroupList.size();
			case 3:
				return othersGroupList.size();
			default:
				break;
			}

			return 0;
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
			
			final int positionView = position;
			final ViewGroup parentViewGroup = parent;
			
			View view = convertView == null ? inflater.inflate(R.layout.manage_groups_row, null) : convertView;
			
			ImageView childIcon = (ImageView) view.findViewById(R.id.imgItemIcon);
			childIcon.setVisibility(View.GONE);
			
			TextView childText = (TextView) view.findViewById(R.id.tvRow);
			
			CheckBox chkBox = (CheckBox) view.findViewById(R.id.chkBox);
			//chkBox.setVisibility(View.GONE);
			chkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(
						CompoundButton buttonView,
						boolean isChecked) {
					
					TextView text = (TextView) ((View) parentViewGroup).findViewById(R.id.tvRow);
					String gpType = text.getText().toString();
					
					int groupIndex = Arrays.asList(groupTypes).indexOf(gpType);
					GroupManager.groupSelectionList[groupIndex].add(positionView, isChecked);
					
					GroupManager.groupTypeSelectionList.add(groupIndex, isChecked);
				}
			});
			
			String childName = "";
			
			switch (groupPosition) {
			
			case 1:
				childName = ((HashMap)mapsFriend[position]).keySet().toString();				
				break;
				
			case 2:
				childName = ((HashMap)mapsFamily[position]).keySet().toString();				
				break;
				
			case 3:
				childName = ((HashMap)mapsOther[position]).keySet().toString();				
				break;
				
			default:
				childName = "";
			}
			
			childText.setText(childName);
			
			return view;
		}
		
	}

}
