package com.inami.smf.bbs;

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

public class ReplyThread extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseRef;
    private String Uid;
    String groupID;
    String messageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_thread);
        if(getIntent().getStringExtra("groupid") != null){
            groupID = getIntent().getStringExtra("groupid");
        }
        if(getIntent().getStringExtra("messageid") != null){
            messageID = getIntent().getStringExtra("messageID");
        }




        firebaseRef = FirebaseAuth.getInstance();
        Uid = firebaseRef.getCurrentUser().getUid();
        if(groupID != null){
            mDatabase = FirebaseDatabase.getInstance().getReference().child("groups").child(groupID);
        }else if(messageID != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("messages").child(messageID);
        }else{
                mDatabase = FirebaseDatabase.getInstance().getReference();
            }



        Button reply = (Button) findViewById(R.id.reply_create);
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(ReplyThread.this, R.style.AlertDialog)
                        .setMessage(R.string.confirm_create_post)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                createReply();
                                Toast.makeText(ReplyThread.this, R.string.reply_created, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null).create();
                dialog.show();
            }
        });
    }

    private void createReply() {
        EditText editPostContent = (EditText) findViewById(R.id.reply_content);

        String postContent = editPostContent.getText().toString();
        String threadID;
        if(getIntent().getStringExtra("messageid") != null){
             threadID = getIntent().getStringExtra("messageid");
        }else {
             threadID = getIntent().getStringExtra("threadid");
        }
        DatabaseReference post = mDatabase.child("posts").child(threadID).push();
        String postID = post.getKey();

        mDatabase.child("posts").child(threadID).child(postID).child("userid").setValue(Uid);
        mDatabase.child("posts").child(threadID).child(postID).child("content").setValue(postContent);
        int timestamp = (int) (System.currentTimeMillis()/1000);
        mDatabase.child("posts").child(threadID).child(postID).child("unixstamp").setValue(timestamp);

    }
}
