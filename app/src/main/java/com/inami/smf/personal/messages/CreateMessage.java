package com.inami.smf.personal.messages;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inami.smf.R;
import com.inami.smf.utils.ResultCodes;
import com.inami.smf.utils.Search;

import java.util.Date;

public class CreateMessage extends AppCompatActivity {

    RelativeLayout mBaseLayout;

    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseRef;
    private String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);

        firebaseRef = FirebaseAuth.getInstance();
        Uid = firebaseRef.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mBaseLayout = (RelativeLayout) findViewById(R.id.activity_create_message);

        Button create = (Button) findViewById(R.id.message_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(CreateMessage.this, R.style.AlertDialog)
                        .setMessage(R.string.confirm_create_thread)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Snackbar.make(mBaseLayout, R.string.invalid_login, Snackbar.LENGTH_LONG).show();
                                createThread();
                                Toast.makeText(CreateMessage.this, R.string.thread_created,
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
        EditText editMessageRecipient = (EditText) findViewById(R.id.message_recipient);
        EditText editMessageTitle = (EditText) findViewById(R.id.message_title);
        EditText editMessageContent = (EditText) findViewById(R.id.message_content);
        EditText editMessageTags = (EditText) findViewById(R.id.message_tags);

        String messageRecipient = editMessageRecipient.getText().toString();
        String messageTitle = editMessageTitle.getText().toString();
        String messageContent = editMessageContent.getText().toString();
        String messageTags = editMessageTags.getText().toString();




        DatabaseReference message = mDatabase.child("messages").push();
        final String messageID = message.getKey();

        String[] tags = messageTags.replace(" ", "").split(",");
        String[] recipients = messageRecipient.replace(" ", "").split(",");

        mDatabase.child("messages").child(messageID).child("userid").setValue(Uid);
        mDatabase.child("messages").child(messageID).child("messagetitle").setValue(messageTitle);



        mDatabase.child("messages").child(messageID).child("recipients").child(Uid).setValue(true);
        mDatabase.child("usermessages").child(Uid).child(messageID).setValue(true);

        for(String recipient : recipients) {
            mDatabase.child("users").orderByChild("accountid").equalTo(recipient).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String uid;
                    for(DataSnapshot d : dataSnapshot.getChildren()){
                        uid = d.getKey();
                        mDatabase.child("messages").child(messageID).child("recipients").child(uid).setValue(true);
                        mDatabase.child("usermessages").child(uid).child(messageID).setValue(true);
                        break;

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


        for(String tag : tags) {
            mDatabase.child("messages").child(messageID).child("messagetags").child(tag).setValue(true);
        }

        DatabaseReference post = mDatabase.child("posts").child(messageID).push();
        String postID = post.getKey();
        mDatabase.child("posts").child(messageID).child(postID).child("userid").setValue(Uid);
        mDatabase.child("posts").child(messageID).child(postID).child("content").setValue(messageContent);
        int timestamp = (int) (System.currentTimeMillis()/1000);
        mDatabase.child("posts").child(messageID).child(postID).child("unixstamp").setValue(timestamp);
        mDatabase.child("messages").child(messageID).child("unixstamp").setValue(timestamp);
    }
}
