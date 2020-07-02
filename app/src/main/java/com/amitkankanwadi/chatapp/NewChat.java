package com.amitkankanwadi.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.cert.PolicyNode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NewChat extends AppCompatActivity {

    private EditText editText;
    private Button sendMessageButton;
    private TextView textView;

    private String userName, roomName;
    private String temp_key;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        editText = findViewById(R.id.editText);
        sendMessageButton = findViewById(R.id.sendMessagebutton);
        textView = findViewById(R.id.textView);

        userName = getIntent().getExtras().get("user_name").toString(); //gets user_name from MainActivity
        roomName = getIntent().getExtras().get("room_name").toString(); //gets room_name from MainActivity

        databaseReference = FirebaseDatabase.getInstance().getReference().child(roomName);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = databaseReference.push().getKey();   //stores the key of the message in a variable
                databaseReference.updateChildren(map);

                DatabaseReference messageRoot = databaseReference.child(temp_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("name", userName);
                map2.put("msg", editText.getText().toString());

                messageRoot.updateChildren(map2);
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addChat(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addChat(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addChat(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            String chatMsg = (String) ((DataSnapshot) i.next()).getValue();
            String chatUserName = (String) ((DataSnapshot) i.next()).getValue();
            textView.append(chatUserName + " : " +chatMsg+ " \n");
        }
    }
}
