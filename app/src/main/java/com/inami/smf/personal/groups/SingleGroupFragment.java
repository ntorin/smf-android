package com.inami.smf.personal.groups;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inami.smf.R;
import com.inami.smf.utils.DummyAdapter;
import com.inami.smf.utils.GroupMenu;
import com.inami.smf.utils.GroupMenuAdapter;
import com.inami.smf.utils.GroupPreview;

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
    private ListView mGroupFeed;
    private ListView mGroupMenu;

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseRef;
    private String Uid;
    private String groupID;

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

        groupID = getArguments().getString("groupid");
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
        AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.AlertDialog)
                .setMessage(R.string.confirm_leave_group)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabase.child("groupmembers").child(groupID).child(Uid).removeValue();
                        mDatabase.child("usergroups").child(Uid).child(groupID).removeValue();
                    }
                })
                .setNegativeButton(R.string.no, null).create();
        dialog.show();
    }

    private void requestGroupInvite() {
        AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.AlertDialog)
                .setMessage(R.string.confirm_join_group)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabase.child("groupmembers").child(groupID).child(Uid).setValue(true);
                        mDatabase.child("usergroups").child(Uid).child(groupID).setValue(true);
                    }
                })
                .setNegativeButton(R.string.no, null).create();
        dialog.show();
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
        mGroupFeed = (ListView) v.findViewById(R.id.group_activity_list);
        mGroupFeed.setAdapter(new DummyAdapter(getContext(), R.layout.item_list, new String[]{}, inflater));

        mGroupMenu = (ListView) v.findViewById(R.id.group_menu_list);
        mGroupMenu.setAdapter(new GroupMenuAdapter(getContext(), R.layout.item_list, inflater, new GroupPreview()));

        mGroupMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        mListener.onGroupBBS(groupID);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }
        });

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
        void onGroupBBS(String groupID);
    }
}
