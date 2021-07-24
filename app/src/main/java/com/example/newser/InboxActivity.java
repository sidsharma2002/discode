package com.example.newser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.multidex.MultiDex;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.newser.adapter.adapter_topics;


import com.example.newser.model.topics;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
//import com.google.firebase.analytics.FirebaseAnalytics;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class InboxActivity extends AppCompatActivity implements  FirebaseAuth.AuthStateListener{

    adapter_topics adapter_topics;
    RecyclerView ListMainView;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_main);

        ListMainView = findViewById(R.id.ListMainView);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            StartLoginRegisterActivtiy();
            finish();
        }
    }

    public void StartLoginRegisterActivtiy() {
        Intent intent = new Intent(this, LoginRegisterActivity.class);
        startActivity(intent);
        finish();
    }



@Override
protected void attachBaseContext(Context context) {
    super.attachBaseContext(context);
    MultiDex.install(this);
}

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
        if(adapter_topics != null){
        adapter_topics.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
        if (!(adapter_topics == null))
        {
            adapter_topics.stopListening();
        }
    }



    public void  Initializerecyclerview(){

        Query query = FirebaseFirestore.getInstance()
                .collection("topics");

        FirestoreRecyclerOptions<topics> options = new FirestoreRecyclerOptions.Builder<topics>()
                .setQuery(query, topics.class)
                .build();

        adapter_topics = new adapter_topics( InboxActivity.this , options);
        ListMainView.setLayoutManager(new LinearLayoutManager(InboxActivity.this));
        ListMainView.setHasFixedSize(true);
     //    ListMainView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL ));
        ListMainView.setAdapter(adapter_topics);
        adapter_topics.startListening();

    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            StartLoginRegisterActivtiy();
            return;
        } else {
          toolbar.setTitle(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        Initializerecyclerview();
        }
    }
}



