package com.example.test1;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class MainActivity extends AppCompatActivity {

    private static int SIGN_IN_CODE = 1;
    private RelativeLayout activity_main;
    private FirebaseListAdapter<Message> adapter;
    private FloatingActionButton sendBtn;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_CODE){
            if(resultCode == RESULT_OK) {
                Snackbar.make(activity_main, "Ви авторизовані", Snackbar.LENGTH_SHORT).show();
                displayAllMassages();
            }
            else Snackbar.make(activity_main, "Ви не авторизовані", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity_main = findViewById(R.id.activity_main);
        sendBtn = findViewById(R.id.btnSend);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = findViewById(R.id.message_field);
                if (text.getText().toString() == "")
                    return;

                FirebaseDatabase.getInstance().getReference().push().setValue(new Message(FirebaseAuth.
                        getInstance().getCurrentUser().getEmail(), text.getText().toString()));
                text.setText("");
            }
        });

        // користувач не авторизований
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_CODE);
        else {
            Snackbar.make(activity_main, "Ви авторизовані", Snackbar.LENGTH_SHORT).show();
            displayAllMassages();
        }
    }

    private void displayAllMassages() {
        ListView listOfMessages = findViewById(R.id.listOfMessages);

        adapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.list_item,FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Message model, int position) {
                TextView mess_user, mess_time, mess_text;
                mess_user = v.findViewById(R.id.message_user);
                mess_time = v.findViewById(R.id.message_time);
                mess_text = v.findViewById(R.id.message_text);

                mess_user.setText(model.getUserName());
                mess_time.setText(DateFormat.format("dd-mm-yyyy HH:mm:ss", model.getMassageTime()));
                mess_text.setText(model.getTextMessage());
            }
        };
        listOfMessages.setAdapter(adapter);
    }
}
