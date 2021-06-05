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

    // TODO ("uncomment to add menu ")
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.toolbar, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.settings:
//                // User chose the "Settings" item, show the app settings UI...
//                Intent intent = new Intent(this , SettingsActivity.class);
//                startActivity(intent);
//                return true;


//            case R.id.signout:
//                // User chose the "Favorite" action, mark the current item
//                // as a favorite...
//                AuthUI.getInstance().signOut(this)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                StartLoginRegisterActivtiy();
//                            }
//                        });
//                return true;

//            default:
//                // If we got here, the user's action was not recognized.
//                // Invoke the superclass to handle it.
//                return super.onOptionsItemSelected(item);
//
//        }

//    public void Add_Description(String description){
//        topics thought = new topics(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName(), description);
//
//        FirebaseFirestore.getInstance()
//                .collection("notes")
//                .add(thought)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                     Snackbar.make(findViewById(R.id.Main),"thought added successfully" , Snackbar.LENGTH_LONG);
//                    }
//                });
//    }

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



