package com.android.warehousemanager.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.warehousemanager.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class FeedBackActivity extends AppCompatActivity {
    private EditText etMessage, etSendEmail, etSubject;
    private Button btnSendEmail;
    private final String username = "thuanemail6420@gmail.com";
    private final String password = "gtdmyfigoeainxnu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Gửi email");
        setControl();
        setEvent();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setEvent() {
        etSendEmail.setText("n18dccn222@student.ptithcm.edu.vn");
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");

                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username,password);
                    }
                });


                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(username));
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(etSendEmail.getText().toString().trim()));
                    message.setSubject(etSubject.getText().toString().trim());
                    message.setText(etMessage.getText().toString());

                    new SendMail().execute(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setControl() {
        etMessage = findViewById(R.id.etMessage);
        etSendEmail = findViewById(R.id.etSendEmail);
        etSubject = findViewById(R.id.etSubject);
        btnSendEmail = findViewById(R.id.btnSendEmail);
    }

    private class SendMail extends AsyncTask<Message,String,String> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(FeedBackActivity.this,"Information","Sending ... ",true,false);
        }

        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
                return "success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "error";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if(s.equalsIgnoreCase("success")){
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(FeedBackActivity.this);
                builder.setCancelable(false);
                builder.setTitle(Html.fromHtml("<font color='#509324'>Success</font>"));
                builder.setMessage("Mail send  successfully.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                        etMessage.setText("");
                        etSendEmail.setText("");
                        etSubject.setText("");
                    }
                });
                builder.show();
            }else{
                Toast.makeText(getApplicationContext(),"Something went wrong ?",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
