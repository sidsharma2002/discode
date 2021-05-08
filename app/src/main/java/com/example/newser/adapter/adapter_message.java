package com.example.newser.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.newser.R;
import com.example.newser.model.message;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class adapter_message extends FirestoreRecyclerAdapter<message, adapter_message.messageViewHolder> {

    public adapter_message(@NonNull FirestoreRecyclerOptions<message> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull adapter_message.messageViewHolder holder, int position, @NonNull message model) {

//        YoYo.with(Techniques.FadeIn)
//                .duration(1600)
//                .playOn(holder.sender_text);
//        YoYo.with(Techniques.FadeIn)
//                .duration(1000)
//                .playOn(holder.message_text);
//        YoYo.with(Techniques.FadeIn)
//                .duration(2000)
//                .playOn(holder.time);

        holder.message_text.setText(model.getMessage());
        holder.time.setText(model.getCreatedon());
        if(FirebaseAuth.getInstance().getCurrentUser().getDisplayName().equals(model.getSender())){
            holder.sender_text.setText("you");
        } else {
            holder.sender_text.setText(model.getSender());
        }

    }

    @NonNull
    @Override
    public adapter_message.messageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new messageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false));
    }

    public static class messageViewHolder extends RecyclerView.ViewHolder {
        TextView message_text, sender_text, time;
        public messageViewHolder(@NonNull View itemView) {
            super(itemView);
            message_text = itemView.findViewById(R.id.message_text);
            sender_text = itemView.findViewById(R.id.sender_text);
            time = itemView.findViewById(R.id.time);
        }
    }
}
