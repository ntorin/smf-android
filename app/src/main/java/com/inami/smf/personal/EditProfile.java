package com.inami.smf.personal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inami.smf.R;

public class EditProfile extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseRef;
    private String Uid;
    //private DatabaseReference mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseRef = FirebaseAuth.getInstance();
        Uid = firebaseRef.getCurrentUser().getUid();
        //mDatabase.child("users").child(Uid);


        Log.d("EditProfile:onCreate", Uid);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        ImageButton editAvatar = (ImageButton) findViewById(R.id.edit_profile_picture);
        final GridView profilePictureSelection = (GridView) findViewById(R.id.profile_picture_selection);

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
        mDatabase.child("accountids").child("test").child("userid").setValue(Uid);
        mDatabase.child("accountids").child("test").child("accid").setValue("test");
    }
}
