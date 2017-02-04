package com.inami.smf.personal.groups;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inami.smf.R;
import com.inami.smf.utils.DummyAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SingleGroupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SingleGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleGroupFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private ListView mListView;

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseRef;
    private String Uid;

    public SingleGroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SingleGroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingleGroupFragment newInstance() {
        SingleGroupFragment fragment = new SingleGroupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseRef = FirebaseAuth.getInstance();
        Uid = firebaseRef.getCurrentUser().getUid();

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_single_group, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.action_join_group:
                requestGroupInvite();
                return true;
            case R.id.action_leave_group:
                leaveGroup();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void leaveGroup() {

    }

    private void requestGroupInvite() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_single_group, container, false);

        setupSingleGroup(v, inflater);
        return v;
    }

    private void setupSingleGroup(View v, LayoutInflater inflater) {
        mListView = (ListView) v.findViewById(R.id.group_activity_list);
        mListView.setAdapter(new DummyAdapter(getContext(), R.layout.item_list, new String[]{}, inflater));

        TextView groupScreenName = (TextView) v.findViewById(R.id.group_screen_name);
        groupScreenName.setText(getArguments().getString("groupname"));

        TextView groupScreenID = (TextView) v.findViewById(R.id.group_screen_id);
        groupScreenID.setText(getArguments().getString("groupscreenid"));

        TextView groupShortDescription = (TextView) v.findViewById(R.id.group_short_description);
        groupShortDescription.setText(getArguments().getString("groupshortdescription"));
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
    }
}
