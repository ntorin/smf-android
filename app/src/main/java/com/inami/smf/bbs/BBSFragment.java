package com.inami.smf.bbs;

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
import com.google.firebase.database.ValueEventListener;
import com.inami.smf.R;
import com.inami.smf.utils.DummyAdapter;
import com.inami.smf.utils.ItemAdapter;
import com.inami.smf.utils.ItemTypes;
import com.inami.smf.utils.ThreadPreview;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BBSFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BBSFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BBSFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private ListView mListView;

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseRef;
    private String Uid;

    private ItemAdapter mItemAdapter;

    private ArrayList<ThreadPreview> mThreadList;
    private ArrayList<String> mKeys;

    public BBSFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BBSFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BBSFragment newInstance() {
        BBSFragment fragment = new BBSFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        mThreadList = new ArrayList<>();
        mKeys = new ArrayList<>();


        firebaseRef = FirebaseAuth.getInstance();
        Uid = firebaseRef.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("threads").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                mKeys.add(key);

                ThreadPreview threadPreview;
                if(dataSnapshot.child("unixstamp").getValue() != null) {
                    threadPreview = ThreadPreview.createThreadPreview(dataSnapshot);
                }else{
                    threadPreview = new ThreadPreview();
                }
                mThreadList.add(threadPreview);
                mItemAdapter.notifyDataSetChanged();
                Log.d("onChildAdded", "" + dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                int i = mKeys.indexOf(key);
                ThreadPreview threadPreview = ThreadPreview.createThreadPreview(dataSnapshot);
                mThreadList.set(i, threadPreview);
                mItemAdapter.notifyDataSetChanged();

                Log.d("onChildChanged", "" + dataSnapshot.getValue());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int i = mKeys.indexOf(key);
                mThreadList.remove(i);
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
        View v = inflater.inflate(R.layout.fragment_bbs, container, false);
        ImageButton b = (ImageButton) v.findViewById(R.id.btn_new_thread);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CreateThread.class);
                startActivity(i);
            }
        });
        mListView = (ListView) v.findViewById(R.id.bbs_list);
        mItemAdapter = new ItemAdapter<>(getContext(), R.layout.item_list, mThreadList, inflater, ItemTypes.THREAD_PREVIEW);
        mListView.setAdapter(mItemAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ThreadPreview tp = (ThreadPreview) parent.getItemAtPosition(position);
                Intent i = new Intent(BBSFragment.this.getContext(), ShowThread.class);
                Bundle b = new Bundle();
                b.putString("threadid", tp.getThreadID());
                b.putString("threadtitle", tp.getThreadTitle());
                b.putStringArray("threadtags", tp.getThreadTags());
                b.putString("opid", tp.getOpID());
                b.putLong("unixstamp", tp.getUnixStamp());
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
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void onThreadFocus();
    }
}
