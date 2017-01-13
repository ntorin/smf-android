package com.inami.smf.bbs;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.inami.smf.R;
import com.inami.smf.utils.ResultCodes;

public class CreateThread extends AppCompatActivity {

    RelativeLayout mBaseLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_thread);
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
}
