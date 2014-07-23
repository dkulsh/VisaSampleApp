package com.example.friendschatapp;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link GroupManager.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link GroupManager#newInstance} factory method
 * to create an instance of this fragment.
 * 
 */
public class GroupManager extends Fragment {
	
	static List<Boolean> groupTypeSelectionList = new ArrayList<Boolean>();
	static List[] groupSelectionList = {new ArrayList<Boolean>(), new ArrayList<Boolean>(), new ArrayList<Boolean>()};

	String[] groupTypes = {"Friends", "Family", "Others"};

	ExpandableListView listView;
	Button addButton, removeButton;
	static LayoutInflater inflater;

	private OnFragmentInteractionListener mListener;

	public static GroupManager newInstance() {
		GroupManager fragment = new GroupManager();

		/*Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);*/

		return fragment;
	}

	public GroupManager() {
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Context context = getActivity().getApplicationContext();

		listView = (ExpandableListView) getView().findViewById(R.id.lvTypeGroups);
		listView.setAdapter(new ExpandableParentLevelAdapter(context));

		addButton = (Button) getView().findViewById(R.id.btnAdd);
		removeButton = (Button) getView().findViewById(R.id.btnRemove);

		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
				builder.setTitle("Please enter group name");

				final EditText view = new EditText(getActivity().getApplicationContext());
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.MATCH_PARENT);
				builder.setView(view);

				builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(
							DialogInterface dialog,
							int which) {

						String groupName = view.getEditableText().toString();
						HashMap<String, HashSet<String>> group = new HashMap<String, HashSet<String>>();
						group.put(groupName, new HashSet<String>());

						SharedPreferences pref = getActivity().getSharedPreferences(getGroupType(), Context.MODE_PRIVATE);
						String groupListString = pref.getString(getGroupType(), "");
						
						Type collectionType = new TypeToken<List<HashMap<String, HashSet<String>>>>(){}.getType();
						List<HashMap<String, HashSet<String>>> groupList = new Gson().fromJson(groupListString, collectionType);
						groupList.add(group);
						
						Editor editor = pref.edit();
						//editor.remove(getGroupType());
						editor.putString(getGroupType(), new Gson().toJson(groupList));
						editor.commit();

					}
				});

				builder.create().show();
			}
		});

		removeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
				//builder.setTitle("Do you want to remove the selected group.");

				final TextView view = new TextView(getActivity().getApplicationContext());
				view.setText("Do you want to remove the selected group.");
				view.setBackgroundColor(Color.RED);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.MATCH_PARENT);
				builder.setView(view);

				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(
							DialogInterface dialog,
							int which) {
						
						SharedPreferences pref = getActivity().getSharedPreferences(getGroupType(), Context.MODE_PRIVATE);
						String groupSetString = pref.getString(getGroupType(), "");
						
						Type collectionType = new TypeToken<List<HashMap<String, HashSet<String>>>>(){}.getType();
						List<HashMap<String, HashSet<String>>> groupList = new Gson().fromJson(groupSetString, collectionType);
						Iterator<HashMap<String, HashSet<String>>> iterator = groupList.iterator();
						
						while (iterator.hasNext()) {
							
							HashMap<String, HashSet<String>> hashMapGroup = iterator.next();
							
							if(hashMapGroup.containsKey("" /* Pending*/ )){
								
								groupList.remove(hashMapGroup);
								break;
							}
						}
						
						//groupSet.remove("" /* Pending */);
						
						Editor editor = pref.edit();
						//editor.remove(getGroupType());
						editor.putString(getGroupType(), new Gson().toJson(groupList));
						editor.commit();

					}
				});

				builder.create().show();
			}
		});

	}

	public String getGroupType(){

		return groupTypes[groupTypeSelectionList.indexOf(true)];
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.manage_groups, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

}
