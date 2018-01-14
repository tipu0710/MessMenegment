package com.example.tsult.messmenegment.MemberDetailsPkg;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tsult.messmenegment.Home.Main2Activity;
import com.example.tsult.messmenegment.R;

public class SendMail extends AppCompatActivity {

    private EditText sendToEt, subjectEt, bodyEt;
    private Button sendBtn;

    private String email, name, phone;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);
        sendToEt = (EditText) findViewById(R.id.send_to);
        subjectEt = (EditText) findViewById(R.id.subject);
        bodyEt = (EditText) findViewById(R.id.body);
        sendBtn = (Button) findViewById(R.id.send_btn);

        subjectEt.setFocusable(true);
        final Intent intent = getIntent();
        email = intent.getStringExtra("mail");
        id = intent.getIntExtra("id", -1);
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        sendToEt.setText(email);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = sendToEt.getText().toString();
                String body = bodyEt.getText().toString();

                if (mail.isEmpty()){
                    sendToEt.setError(getString(R.string.error_msg));
                }else if (body.isEmpty()){
                    bodyEt.setError(getString(R.string.error_msg));
                }else {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setData(Uri.parse("mailto:"));
                    i.putExtra(Intent.EXTRA_EMAIL  , new  String[]{mail});
                    i.putExtra(Intent.EXTRA_SUBJECT, subjectEt.getText().toString());
                    i.putExtra(Intent.EXTRA_TEXT   , body);
                    i.setType("message/rfc822");
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(SendMail.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
