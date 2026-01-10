package com.example.heathub_splash;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class chat extends AppCompatActivity {

    RecyclerView chatRv;
    EditText msgEdit;
    Button sendBtn;
    ChatAdapter adapter;
    ArrayList<MessageModel> messageList;
    DatabaseReference db;

    // Unique rooms for sender and receiver
    String senderRoom, receiverRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Intent se dusre user ki ID aur Name lena
        String receiverUid = getIntent().getStringExtra("uid");
        String senderUid = FirebaseAuth.getInstance().getUid();

        // Room IDs create karna
        senderRoom = senderUid + receiverUid;
        receiverRoom = receiverUid + senderUid;

        chatRv = findViewById(R.id.chatRecyclerView);
        msgEdit = findViewById(R.id.messageEdit);
        sendBtn = findViewById(R.id.sendBtn);

        messageList = new ArrayList<>();
        adapter = new ChatAdapter(messageList);

        chatRv.setLayoutManager(new LinearLayoutManager(this));
        chatRv.setAdapter(adapter);

        db = FirebaseDatabase.getInstance().getReference();

        // 1. Firebase se purane messages load karna
        db.child("Chats").child(senderRoom).child("messages")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            MessageModel model = ds.getValue(MessageModel.class);
                            messageList.add(model);
                        }
                        adapter.notifyDataSetChanged();
                        // Scroll to bottom when new message arrives
                        if (messageList.size() > 0) {
                            chatRv.smoothScrollToPosition(messageList.size() - 1);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });

        // 2. Naya message send karne ka logic
        sendBtn.setOnClickListener(v -> {
            String msg = msgEdit.getText().toString().trim();
            if (msg.isEmpty()) return;

            MessageModel model = new MessageModel(msg, senderUid);

            // Donon rooms mein message save karna taake dono ko nazar aaye
            db.child("Chats").child(senderRoom).child("messages").push().setValue(model)
                    .addOnSuccessListener(unused -> {
                        db.child("Chats").child(receiverRoom).child("messages").push().setValue(model);
                    });

            msgEdit.setText(""); // Input khali kar dein
        });
    }
}