package com.inami.smf.personal.groups;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inami.smf.R;
import com.inami.smf.bbs.CreateThread;
import com.inami.smf.utils.ResultCodes;

public class CreateGroup extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseRef;
    private String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);


        firebaseRef = FirebaseAuth.getInstance();
        Uid = firebaseRef.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button create = (Button) findViewById(R.id.group_create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(CreateGroup.this, R.style.AlertDialog)
                        .setMessage(R.string.confirm_create_group)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                createGroup();
                                Toast.makeText(CreateGroup.this, R.string.group_created,
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null).create();
                dialog.show();
            }
        });
    }

    private void createGroup() {
        EditText editGroupName = (EditText) findViewById(R.id.group_name);
        EditText editGroupScreenID = (EditText) findViewById(R.id.group_screen_id);
        EditText editGroupTags = (EditText) findViewById(R.id.group_tags);

        String groupName = editGroupName.getText().toString();
        String groupScreenID = editGroupScreenID.getText().toString();
        String groupTags = editGroupTags.getText().toString();


        DatabaseReference group = mDatabase.child("groups").push();
        String groupID = group.getKey();

        String[] tags = groupTags.replace(" ", "").split(",");

        mDatabase.child("groups").child(groupID).child("groupname").setValue(groupName);
        mDatabase.child("groups").child(groupID).child("groupscreenid").setValue(groupScreenID);
        mDatabase.child("groups").child(groupID).child("creatorid").setValue(Uid);

        for(String tag : tags){
            mDatabase.child("groups").child(groupID).child("grouptags").child(tag).setValue(tag);
        }

        int timestamp = (int) (System.currentTimeMillis()/1000);

        mDatabase.child("groups").child(groupID).child("unixstamp").setValue(timestamp);


    }
}
