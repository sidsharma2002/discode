package com.example.newser.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newser.ChatActivity;
import com.example.newser.R;
import com.example.newser.model.topics;
//import com.firebase.ui.database.FirestoreRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public  class adapter_topics extends FirestoreRecyclerAdapter<topics, adapter_topics.ViewHolder1> {

    Context context;
    public adapter_topics(Context context, @NonNull FirestoreRecyclerOptions options) {
        super(options);
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder1 holder, int position, @NonNull final topics model) {
        holder.displayname.setText(model.getTopicname());

        holder.displayname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , ChatActivity.class);
                intent.putExtra("TOPIC_NAME", model.getTopicname());
                context.startActivity(intent);
            }
        });
    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {
        TextView displayname;
        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            displayname = itemView.findViewById(R.id.Resturant_name);
        }
    }
}
