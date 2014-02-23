package com.umnappclass.contactviewer;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ContactRepository repo = new ContactRepository(this);

        setListAdapter(new ContactAdapter(this, R.layout.contact_item, repo.getContacts()));

        // Go to the edit contact screen after the pressing Edit
        Button newContactButton = (Button)findViewById(R.id.new_contact_button);
        newContactButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditDetailsActivity.class);
                intent.putExtra("Contact", new Contact(null,"","","","",""));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Contact contact = ((ContactAdapter)l.getAdapter()).getItem(position);

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("contactId", contact.id);
        startActivity(intent);
    }

    class ContactAdapter extends ArrayAdapter<Contact> {

        public ContactAdapter(Context context, int resource, List<Contact> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rootView = convertView;
            if(rootView == null) {
                rootView = getLayoutInflater().inflate(R.layout.contact_item, parent, false);
            }

            Contact contact = getItem(position);
            TextView nameView = (TextView)rootView.findViewById(R.id.contactItemName);
            nameView.setText(contact.name);

            TextView titleView = (TextView)rootView.findViewById(R.id.contactItemTitle);
            titleView.setText(contact.title);

            TextView phoneView = (TextView)rootView.findViewById(R.id.contactItemPhone);
            phoneView.setText(contact.phone);

            return rootView;
        }
    }
}
