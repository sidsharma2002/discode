package com.example.newser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.newser.Backend.message_limiter;
import com.example.newser.adapter.adapter_message;
import com.example.newser.adapter.adapter_resturants;
import com.example.newser.model.message;
import com.example.newser.model.resturants;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar_chat;
    String Resturant_name;
    ImageButton send_button;
    EditText send_edittext;
   // CardView send_button_cardview, limit_cardview;
    RecyclerView message_recyclerview;
    TextView limitsLeft;
    com.example.newser.adapter.adapter_message adapter_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

      //  send_button_cardview = findViewById(R.id.send_button_cardview);
        //send_button = findViewById(R.id.send_button);
        //send_button.setVisibility(View.GONE);
        //send_button_cardview.setVisibility(View.GONE);
        send_edittext = findViewById(R.id.send_edittext);
        //limitsLeft = findViewById(R.id.limitsleft);
        //limit_cardview = findViewById(R.id.limit_cardview);
        send_button = findViewById(R.id.send_button);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            Resturant_name = bundle.getString("RESTURANT_NAME");
        }

        toolbar_chat = findViewById(R.id.toolbar_chat);
        setSupportActionBar(toolbar_chat);
        toolbar_chat.setTitle(Resturant_name);
        toolbar_chat.setTitleTextColor(getResources().getColor(R.color.white));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        message_recyclerview  =findViewById(R.id.message_recyclerview);
       // limitsLeft.setText("Limits left: " + new message_limiter(this, limitsLeft).getmessagelimit());

        InitializeMessageRecyclerview();
        Inputmessage();

    }

    void Inputmessage(){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 String messageinput = send_edittext.getText().toString().trim();

                 if(  !messageinput.isEmpty()){
                     send_button.setVisibility(View.VISIBLE);
                 }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        send_edittext.addTextChangedListener(textWatcher);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            String message = send_edittext.getText().toString().trim();

                            if(! (message.equals(""))){
                            message message1 = new message(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), message, Resturant_name, new SimpleDateFormat("HH.mm.ss").format(new Date()));
                            addmessage(message1);

                            } else {
                                send_edittext.setError("enter something");
                            }

                            send_edittext.setText("");
            }
        });

    }

            void addmessage(message message){

                message_limiter  limiter = new message_limiter(this, limitsLeft);
                //if(limiter.getmessagelimit() >0) {

               //     limiter.updatemessagelimit(limiter.getmessagelimit() - 1);

                    FirebaseFirestore.getInstance()
                            .collection("messages")
                            .add(message)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                }
                            });

             //   }
            }

            void InitializeMessageRecyclerview(){
                Query query = FirebaseFirestore.getInstance()
                        .collection("messages")
                        .whereEqualTo("resturant", Resturant_name);

                FirestoreRecyclerOptions<message> options = new FirestoreRecyclerOptions.Builder<message>()
                        .setQuery(query, message.class)
                        .build();
;
                adapter_message = new adapter_message( options);
                message_recyclerview.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                message_recyclerview.setHasFixedSize(true);
//                message_recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL ));
                message_recyclerview.setAdapter(adapter_message);
                adapter_message.startListening();
            }

    @Override
    protected void onStart() {
        super.onStart();
        adapter_message.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter_message.stopListening();
    }
}