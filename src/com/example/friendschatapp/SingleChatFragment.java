package com.example.friendschatapp;

import java.util.ArrayList;

import org.apache.http.conn.scheme.HostNameResolver;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.sasl.SASLPlainMechanism;
import org.jivesoftware.smack.util.StringUtils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link SingleChatFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link SingleChatFragment#newInstance} factory
 * method to create an instance of this fragment.
 * 
 */
public class SingleChatFragment extends
		Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	
	EditText recepient;
	EditText sendText;
	ListView listView;
	ArrayList<String> messages = new ArrayList<String>();
	LayoutInflater inflater;
	ArrayAdapter<String> adapter;

	//private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment SingleChatFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static SingleChatFragment newInstance(
			String param1, String param2) {
		
		SingleChatFragment fragment = new SingleChatFragment();
		
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1,param1);
		args.putString(ARG_PARAM2,param2);
		fragment.setArguments(args);
		
		return fragment;
	}

	public SingleChatFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		this.inflater = inflater;
		View view = inflater.inflate(R.layout.fragment_single_chat,container,false);

		recepient = (EditText) view.findViewById(R.id.recipient);
		sendText = (EditText) view.findViewById(R.id.sendText);
		listView = (ListView) view.findViewById(R.id.listMessages);
		
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, messages);
		listView.setAdapter(adapter);

		Button button = (Button) view.findViewById(R.id.setup);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dialog dialog = createSettingsDialog();
				dialog.show();
			}
		});
		
		Button buttonSend = (Button) view.findViewById(R.id.send);
		buttonSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return view;
	}

	protected Dialog createSettingsDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Chat Settings");
		final View view = inflater.inflate(R.layout.chat_settings, null);
		builder.setView(view);
		
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				String host = ((EditText) view.findViewById(R.id.host)).getText().toString();
				//int port = Integer.parseInt(((EditText) view.findViewById(R.id.port)).getText().toString());
				String service = ((EditText) view.findViewById(R.id.service)).getText().toString();
				String username = ((EditText) view.findViewById(R.id.username)).getText().toString();
				String password = ((EditText) view.findViewById(R.id.password)).getText().toString();
				

				ConnectionConfiguration config = new ConnectionConfiguration("192.168.1.105", 5222, "dkulsh");
				//config.setSASLAuthenticationEnabled(false);
				//config.setReconnectionAllowed(true);
				//config.setSecurityMode(SecurityMode.required);
				
				XMPPConnection connection = new XMPPConnection(config);
				
				try {
					connection.connect();
					Log.i("XMPPClient", "[SettingsDialog] Connected to " + connection.getHost());
					
				} catch (XMPPException e) {
					
					Log.e("XMPPClient", "[SettingsDialog] Failed to connect to " + connection.getHost());
		            //setConnection(null);
					e.printStackTrace();
				}
				
				try {
					Log.i("XMPPClient", "[SettingsDialog] Username " + connection.getUser());
					Log.i("XMPPClient", "[SettingsDialog] Service name " + connection.getServiceName());
					
					//SASLAuthentication.registerSASLMechanism(0, "PLAIN", SASLPlainMechanism.class);
					SmackConfiguration.setPacketReplyTimeout(10000);
					connection.login("abcd", "abcd", "dkulsh");
					
/*		            Presence presence = new Presence(Presence.Type.available);
		            connection.sendPacket(presence);
		            setConnection(connection);*/
		            
				} catch (XMPPException e) {
					
					Log.e("XMPPClient", "[SettingsDialog] Failed to log in as " + username);
					e.printStackTrace();
				}
				
			}
		});
		
		return builder.create();
	}

	protected void setConnection(XMPPConnection connection) {

        if (connection != null) {
        	
            // Add a packet listener to get messages sent to us
            PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
            
            connection.addPacketListener(new PacketListener() {
            	
                public void processPacket(Packet packet) {
                	
                    Message message = (Message) packet;
                    if (message.getBody() != null) {
                        
                    	String fromName = StringUtils.parseBareAddress(message.getFrom());
                        
                        Log.i("XMPPClient", "Got text [" + message.getBody() + "] from [" + fromName + "]");
                        messages.add(fromName + ":");
                        messages.add(message.getBody());
                        
                        // Add the incoming message to the list view
                        adapter.notifyDataSetChanged();
                    }
                }
            }, filter);
        }
		
	}
	
	

	// TODO: Rename method, update argument and hook method into UI event
/*	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener
					.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(
			Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					activity.toString()
							+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	*//**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 *//*
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(
				Uri uri);
	}*/

}
