package com.inami.smf.bbs;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inami.smf.R;
import com.inami.smf.utils.ResultCodes;

import java.util.Date;

public class CreateThread extends AppCompatActivity {

    RelativeLayout mBaseLayout;

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseRef;
    private String Uid;
    private String groupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_thread);
        if(getIntent().getStringExtra("groupid") != null){
            groupID = getIntent().getStringExtra("groupid");
        }

        firebaseRef = FirebaseAuth.getInstance();
        Uid = firebaseRef.getCurrentUser().getUid();
        if(groupID != null){
            mDatabase = FirebaseDatabase.getInstance().getReference().child("groups").child(groupID);
        }else {
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }

        mBaseLayout = (RelativeLayout) findViewById(R.id.activity_create_thread);

        Button create = (Button) findViewById(R.id.thread_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(CreateThread.this, R.style.AlertDialog)
                        .setMessage(R.string.confirm_create_thread)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Snackbar.make(mBaseLayout, R.string.invalid_login, Snackbar.LENGTH_LONG).show();
                                createThread();
                                Toast.makeText(CreateThread.this, R.string.thread_created,
                                        Toast.LENGTH_SHORT).show();
                                setResult(ResultCodes.THREAD_CREATED);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null).create();
                dialog.show();
            }
        });
    }

    private void createThread(){
        EditText editThreadTitle = (EditText) findViewById(R.id.thread_title);
        EditText editThreadContent = (EditText) findViewById(R.id.thread_content);
        EditText editThreadTags = (EditText) findViewById(R.id.thread_tags);

        String threadTitle = editThreadTitle.getText().toString();
        String threadContent = editThreadContent.getText().toString();
        String threadTags = editThreadTags.getText().toString();


        DatabaseReference thread = mDatabase.child("threads").push();
        String threadID = thread.getKey();

        String[] tags = threadTags.replace(" ", "").split(",");

        mDatabase.child("threads").child(threadID).child("userid").setValue(Uid);
        mDatabase.child("threads").child(threadID).child("threadtitle").setValue(threadTitle);


        for(String tag : tags) {
            mDatabase.child("threads").child(threadID).child("threadtags").child(tag).setValue(true);
        }

        DatabaseReference post = mDatabase.child("posts").child(threadID).push();
        String postID = post.getKey();
        mDatabase.child("posts").child(threadID).child(postID).child("userid").setValue(Uid);
        mDatabase.child("posts").child(threadID).child(postID).child("content").setValue(threadContent);
        int timestamp = (int) (System.currentTimeMillis()/1000);
        mDatabase.child("posts").child(threadID).child(postID).child("unixstamp").setValue(timestamp);
        mDatabase.child("threads").child(threadID).child("unixstamp").setValue(timestamp);
    }
}
