package com.example.newser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.multidex.MultiDex;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.newser.adapter.adapter_resturants;


import com.example.newser.model.resturants;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
//import com.google.firebase.analytics.FirebaseAnalytics;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


import java.util.ArrayList;
import java.util.Objects;

public class InboxActivity extends AppCompatActivity implements  FirebaseAuth.AuthStateListener{

    adapter_resturants adapter_resturants ;
    RecyclerView ListMainView;
    Toolbar toolbar;
    //CollapsingToolbarLayout collapsingToolbarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_main);
        // RecyclerView initialisation
        ListMainView = findViewById(R.id.ListMainView);
        //add_button = findViewById(R.id.fabhome);
        //RecyclerView initialised

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

//            case R.id.userprofile:
//                // User chose the "Favorite" action, mark the current item
//                // as a favorite...
//                Intent intent1 = new Intent(this , UserprofileActivity.class);
//                startActivity(intent1);
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




//    public void showAlertDialog() {
//        final EditText add_description = new EditText(this);
//        new AlertDialog.Builder(this)
//                .setTitle("Add resturants")
//                .setView(add_description)
//                .setPositiveButton("Publish", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                                    Add_Description(add_description.getText().toString().trim());
//                    }
//                })
//                .setNegativeButton("cancel",null)
//                .show();
//    }

//    public void Add_Description(String description){
//        resturants thought = new resturants(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName(), description);
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
        if(adapter_resturants != null){
        adapter_resturants.startListening();}
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
        if (!(adapter_resturants == null))
        {
            adapter_resturants.stopListening();
        }
    }



    public void  Initializerecyclerview(){

        Query query = FirebaseFirestore.getInstance()
                .collection("resturants");

        FirestoreRecyclerOptions<resturants> options = new FirestoreRecyclerOptions.Builder<resturants>()
                .setQuery(query, resturants.class)
                .build();

        adapter_resturants = new adapter_resturants( InboxActivity.this , options);
        ListMainView.setLayoutManager(new LinearLayoutManager(InboxActivity.this));
        ListMainView.setHasFixedSize(true);
     //    ListMainView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL ));
        ListMainView.setAdapter(adapter_resturants);
        adapter_resturants.startListening();

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



