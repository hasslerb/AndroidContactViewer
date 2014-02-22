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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setListAdapter(new ContactAdapter(this, R.layout.contact_item, Contact.dummies()));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Contact contact = ((ContactAdapter)l.getAdapter()).getItem(position);

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("Contact", contact);
        startActivity(intent);
    }

    class ContactAdapter extends ArrayAdapter<Contact> {

        public ContactAdapter(Context context, int resource, Contact[] objects) {
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
