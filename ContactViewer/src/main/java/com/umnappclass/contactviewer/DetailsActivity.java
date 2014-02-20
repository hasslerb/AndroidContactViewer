package com.umnappclass.contactviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 *
 */
public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Button goToContactsButton = (Button)findViewById(R.id.go_to_contacts);

        goToContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        


        Button editContactButton = (Button)findViewById(R.id.edit_contact);

        editContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, EditDetailsActivity.class);
                startActivity(intent);
            }
        });
    }


}
