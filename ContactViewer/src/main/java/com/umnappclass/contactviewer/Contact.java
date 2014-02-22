package com.umnappclass.contactviewer;

import java.io.Serializable;

/**
 *
 */
public class Contact implements Serializable {
    String name;
    String title;
    String phone;
    String twitterId;
    String email;

    public Contact(String n, String t, String p, String e, String tw) {
        name = n;
        title = t;
        phone = p;
        twitterId = tw;
        email = e;
    }

    public static Contact[] dummies() {
        return new Contact[] {
                new Contact ("Nobody1", "Mr", "555-5551", "no@gmail.com", "@yo"),
                new Contact ("Nobody2", "Mrs", "555-5552", "yes@gmail.com", "@hi")
        };
    }
}
