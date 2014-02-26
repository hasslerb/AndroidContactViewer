package com.umnappclass.contactviewer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContactRepository {

    private Context context;
    private ContactViewerDbHelper dbHelper;

    private static final String SELECT_CONTACTS = "select Contact.id, Contact.name, Contact.title, Contact.phone, Contact.email, Contact.twitterId " +
                                                  "from Contact order by Contact.name asc";

    private static final String SELECT_CONTACT_BY_ID = "select Contact.id, Contact.name, Contact.title,  Contact.phone, Contact.email, Contact.twitterId " +
                                                  "from Contact where Contact.id = ? order by Contact.name asc";

    public ContactRepository(Context c) {
        context = c;

        dbHelper = new ContactViewerDbHelper(context);
    }

    public List<Contact> getContacts() {

        List<Contact> contacts = new ArrayList<Contact>();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.rawQuery(SELECT_CONTACTS, null);

        c.moveToFirst();

        while ( !c.isAfterLast() ) {
            Contact contact = new Contact(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5));

            contacts.add(contact);

            c.moveToNext();
        }

        c.close();

        return contacts;
    }

    public Contact getContactById(Integer id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] args = { id.toString() };

        Cursor c = db.rawQuery(SELECT_CONTACT_BY_ID, args);

        c.moveToFirst();

        Contact contact = new Contact(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5));

        c.close();

        return contact;

    }

    public void saveContact(Contact contact) {

        if ( contact.id != null ) {
            updateContact(contact);
        }
        else {
            insertContact( contact );
        }

    }

    private void insertContact(Contact contact) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put( "name", contact.name );
        values.put( "title", contact.title );
        values.put( "phone", contact.phone );
        values.put( "email", contact.email );
        values.put( "twitterId", contact.twitterId );

        db.insert( "Contact", null, values );

        db.close();

    }

    private void updateContact(Contact contact) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put( "name", contact.name );
        values.put( "title", contact.title );
        values.put( "phone", contact.phone );
        values.put( "email", contact.email );
        values.put( "twitterId", contact.twitterId );

        String where = "id = ?";

        String[] args = { contact.id.toString() };

        db.update( "Contact", values, where, args );

        db.close();

    }

     public void deleteContact(Contact contact) {
         SQLiteDatabase db = dbHelper.getWritableDatabase();

         String where = "id = ?";

         String[] args = { contact.id.toString() };

         db.delete("Contact", where, args);

         db.close();
    }

}
