package com.inami.smf.personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inami.smf.R;

public class EditProfile extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseRef;
    private String Uid;
    //private DatabaseReference mUser;


     EditText mEditAccountID;
     EditText mEditScreenName;
     EditText mEditShortDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseRef = FirebaseAuth.getInstance();
        Uid = firebaseRef.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //mDatabase.child("users").child(Uid);


        Log.d("EditProfile:onCreate", Uid);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ImageButton editAvatar = (ImageButton) findViewById(R.id.edit_profile_picture);
        final GridView profilePictureSelection = (GridView) findViewById(R.id.profile_picture_selection);

        initEditProfileValues();

        editAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profilePictureSelection.getVisibility() == View.VISIBLE){
                    profilePictureSelection.setVisibility(View.GONE);
                }else{
                    profilePictureSelection.setVisibility(View.VISIBLE);
                }
            }
        });



    }

    private void initEditProfileValues() {
         mEditAccountID = (EditText) findViewById(R.id.edit_account_id);
         mEditScreenName = (EditText) findViewById(R.id.edit_screen_name);
         mEditShortDescription = (EditText) findViewById(R.id.edit_short_description);

        mDatabase.child("users").child(Uid).child("screenname").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("ProfileFragment", "" + dataSnapshot.getValue());
                mEditScreenName.setText((CharSequence) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("users").child(Uid).child("accountid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("ProfileFragment", "" + dataSnapshot.getValue());
                mEditAccountID.setText((CharSequence) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("users").child(Uid).child("shortdescription").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("ProfileFragment", "" + dataSnapshot.getValue());
                mEditShortDescription.setText((CharSequence) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_edit_profile_complete:
                updateUserInfo();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void updateUserInfo(){
        String screenName = mEditScreenName.getText().toString();
        String accountID = mEditAccountID.getText().toString();
        String shortDescription = mEditShortDescription.getText().toString();

        mDatabase.child("users").child(Uid).child("screenname").setValue(screenName);
        mDatabase.child("users").child(Uid).child("accountid").setValue(accountID);
        mDatabase.child("users").child(Uid).child("shortdescription").setValue(shortDescription);


        //mDatabase.child("accountids").child("test").child("userid").setValue(Uid);
        //mDatabase.child("accountids").child("test").child("accid").setValue("test");
    }
}
