package com.weatherapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactusActivity extends AppCompatActivity {
    EditText nameEdit, mailEdit, subjectEdit, msgEdit;
    String name_, mail_, subject_, msg_;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        //find our views
        nameEdit = findViewById(R.id.name);
        mailEdit = findViewById(R.id.e_mail);
        subjectEdit = findViewById(R.id.subj);
        msgEdit = findViewById(R.id.mymsg);
        submit = findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_ = nameEdit.getText().toString();
                mail_ = mailEdit.getText().toString();
                subject_ = subjectEdit.getText().toString();
                msg_ = msgEdit.getText().toString();

                /*
                    Testing on our fields
                 */
                if (name_.isEmpty()) {
                    nameEdit.setError("Enter your name");
                    nameEdit.requestFocus();
                    return;
                }

                if (!isValidEmail(mail_)) {
                    mailEdit.setError("Invalid Email");
                    mailEdit.requestFocus();
                    return;
                }

                if (subject_.isEmpty()) {
                    subjectEdit.setError("Enter Your Subject");
                    subjectEdit.requestFocus();
                    return;
                }

                if (msg_.isEmpty()) {
                    msgEdit.setError("Enter Your Message");
                    msgEdit.requestFocus();
                    return;
                }

                //Send information to Gmail
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.setData(Uri.fromParts("mailto", "aalsayedtaha@fcih1.com", null));
                intent.putExtra(Intent.EXTRA_EMAIL, mail_);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject_);
                intent.putExtra(Intent.EXTRA_TEXT, "name: " + name_ + '\n' + msg_);
                startActivity(Intent.createChooser(intent, "Send Email"));

            }
        });
    }

    //Validation for entered e-mail
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
