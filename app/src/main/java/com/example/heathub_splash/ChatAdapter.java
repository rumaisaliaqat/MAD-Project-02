package com.example.heathub_splash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<MessageModel> messageList;
    int ITEM_SENT = 1;
    int ITEM_RECEIVED = 2;

    public ChatAdapter(ArrayList<MessageModel> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sent, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_received, parent, false);
            return new ReceivedViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        MessageModel message = messageList.get(position);
        if (FirebaseAuth.getInstance().getUid().equals(message.senderId)) {
            return ITEM_SENT;
        } else {
            return ITEM_RECEIVED;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel message = messageList.get(position);
        if (holder.getClass() == SentViewHolder.class) {
            ((SentViewHolder) holder).sentMsg.setText(message.message);
        } else {
            ((ReceivedViewHolder) holder).receivedMsg.setText(message.message);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    // Do alag ViewHolders: Aik bhejne wale ke liye, aik lene wale ke liye
    public static class SentViewHolder extends RecyclerView.ViewHolder {
        TextView sentMsg;
        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            sentMsg = itemView.findViewById(R.id.messageTxt);
        }
    }

    public static class ReceivedViewHolder extends RecyclerView.ViewHolder {
        TextView receivedMsg;
        public ReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            receivedMsg = itemView.findViewById(R.id.messageTxt);
        }
    }
}