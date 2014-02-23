package com.umnappclass.contactviewer;

import java.io.Serializable;

/**
 *
 */
public class Contact implements Serializable {
    Integer id;
    String name;
    String title;
    String phone;
    String twitterId;
    String email;

    public Contact(Integer i, String n, String t, String p, String e, String tw) {
        id = i;
        name = n;
        title = t;
        phone = p;
        twitterId = tw;
        email = e;
    }
}
