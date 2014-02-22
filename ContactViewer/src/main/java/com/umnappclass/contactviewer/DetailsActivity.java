package com.umnappclass.contactviewer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 *
 */
public class DetailsActivity extends Activity {

    private Contact _contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Intent i = getIntent();
        _contact = (Contact)i.getSerializableExtra("Contact");

        SetContactFields(_contact);

        // After pressing the button to go back to contacts,
        // destroy the activity in order to return to the contacts list
        Button goToContactsButton = (Button)findViewById(R.id.go_to_contacts);
        goToContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Go to the edit contact screen after the pressing Edit
        Button editContactButton = (Button)findViewById(R.id.edit_contact);
        editContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, EditDetailsActivity.class);
                intent.putExtra("Contact", _contact);
                startActivity(intent);
            }
        });

        // Bring up the phone client with the contact's phone number after pressing the call button
        Button callButton = (Button)findViewById(R.id.call_button);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView phoneTextView = (TextView)findViewById(R.id.details_phone);
                String phoneNumber = phoneTextView.getText().toString();
                phoneNumber = phoneNumber.replaceAll("[\\s\\-()]", "").trim();

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });

        // Bring up the sms client with the contact's phone number after pressing the send text button
        Button smsButton = (Button)findViewById(R.id.send_text_button);
        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView phoneTextView = (TextView)findViewById(R.id.details_phone);
                String phoneNumber = phoneTextView.getText().toString().trim();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:" + phoneNumber));
                startActivity(intent);
            }
        });

        // Bring up the email client with the contact's email after pressing the send email button
        Button emailButton = (Button)findViewById(R.id.send_email_button);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView emailTextView = (TextView)findViewById(R.id.details_email);
                String[] email = {emailTextView.getText().toString()};

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, email);
                startActivity(intent);
            }
        });
    }

    private void SetContactFields(Contact contact)
    {
        TextView titleView = (TextView)findViewById(R.id.details_title);
        titleView.setText(contact.title);

        TextView nameView = (TextView)findViewById(R.id.details_name);
        nameView.setText(contact.name);

        TextView phoneView = (TextView)findViewById(R.id.details_phone);
        phoneView.setText(contact.phone);

        TextView emailView = (TextView)findViewById(R.id.details_email);
        emailView.setText(contact.email);

        TextView twitterView = (TextView)findViewById(R.id.details_twitter);
        twitterView.setText(contact.twitterId);
    }


}
