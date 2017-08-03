package com.example.tsult.messmenegment.MemberDetailsPkg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tsult.messmenegment.R;

public class SendMail extends AppCompatActivity {

    private EditText sendToEt, subjectEt, bodyEt;
    private Button sendBtn;

    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);
        sendToEt = (EditText) findViewById(R.id.send_to);
        subjectEt = (EditText) findViewById(R.id.subject);
        bodyEt = (EditText) findViewById(R.id.body);
        sendBtn = (Button) findViewById(R.id.send_btn);

        subjectEt.setFocusable(true);
        Intent intent = getIntent();
        email = intent.getStringExtra("mail");
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
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL  , mail);
                    i.putExtra(Intent.EXTRA_SUBJECT, subjectEt.getText().toString());
                    i.putExtra(Intent.EXTRA_TEXT   , body);
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(SendMail.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
