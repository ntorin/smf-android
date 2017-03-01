package com.inami.smf.personal.messages;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inami.smf.R;
import com.inami.smf.utils.ItemAdapter;
import com.inami.smf.utils.ItemTypes;
import com.inami.smf.utils.MessagePreview;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessagesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessagesFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private ListView mListView;

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseRef;
    private String Uid;
    private String groupID;

    private ItemAdapter mItemAdapter;

    private ArrayList<MessagePreview> mMessagesList;
    private ArrayList<String> mKeys;

    public MessagesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MessagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessagesFragment newInstance() {
        MessagesFragment fragment = new MessagesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.groupID = getArguments().getString("groupid");
        }

        mMessagesList = new ArrayList<>();
        mKeys = new ArrayList<>();


        firebaseRef = FirebaseAuth.getInstance();
        Uid = firebaseRef.getCurrentUser().getUid();
        if(groupID != null){
            mDatabase = FirebaseDatabase.getInstance().getReference().child("groups").child(groupID);
        }else{
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }

        mDatabase.child("messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                mKeys.add(key);

                MessagePreview messagePreview;
                if(dataSnapshot.child("unixstamp").getValue() != null) {
                    messagePreview = MessagePreview.createMessagePreview(dataSnapshot);
                }else{
                    messagePreview = new MessagePreview();
                }
                mMessagesList.add(messagePreview);
                mItemAdapter.notifyDataSetChanged();
                Log.d("onChildAdded", "" + dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                int i = mKeys.indexOf(key);
                MessagePreview messagePreview = MessagePreview.createMessagePreview(dataSnapshot);
                mMessagesList.set(i, messagePreview);
                mItemAdapter.notifyDataSetChanged();

                Log.d("onChildChanged", "" + dataSnapshot.getValue());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int i = mKeys.indexOf(key);
                mMessagesList.remove(i);
                mItemAdapter.notifyDataSetChanged();

                Log.d("onChildRemoved", "" + dataSnapshot.getValue());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("onChildMoved", "" + dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_messages, container, false);
        ImageButton b = (ImageButton) v.findViewById(R.id.btn_new_message);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CreateMessage.class);
                if(groupID != null){
                    i.putExtra("groupid", groupID);
                }
                startActivity(i);
            }
        });
        mListView = (ListView) v.findViewById(R.id.messages_list);
        mItemAdapter = new ItemAdapter<>(getContext(), R.layout.item_list, mMessagesList, inflater, ItemTypes.MESSAGE_PREVIEW, mDatabase);
        mListView.setAdapter(mItemAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessagePreview mp = (MessagePreview) parent.getItemAtPosition(position);
                Intent i = new Intent(MessagesFragment.this.getContext(), ShowMessage.class);
                Bundle b = new Bundle();
                b.putString("messageid", mp.getMessageID());
                b.putString("messagetitle", mp.getMessageTitle());
                b.putStringArray("messagetags", mp.getMessageTags());
                b.putString("opid", mp.getOpID());
                b.putLong("unixstamp", mp.getUnixStamp());
                i.putExtras(b);
                startActivity(i);
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
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
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "htmp://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void onMessageFocus();
    }
}
