package com.example.heathub_splash;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;

// Import conflicts se bachne ke liye baqi imports delete kar diye hain
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class chatbot extends AppCompatActivity {

    private TextView txtResponse;
    private EditText editMessage;
    private Button btnSend;
    private ScrollView chatScrollView;
    private GenerativeModelFutures model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        txtResponse = findViewById(R.id.txtResponse);
        editMessage = findViewById(R.id.editMessage);
        btnSend = findViewById(R.id.btnSend);
        chatScrollView = findViewById(R.id.chatScrollView);

        // Gemini Model Setup
        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", "AIzaSyB1tLm0n-ysnhEmzhKgVamRkZgkO8Ol8wc");
        model = GenerativeModelFutures.from(gm);

        btnSend.setOnClickListener(v -> {
            String userQuery = editMessage.getText().toString().trim();
            if (!userQuery.isEmpty()) {
                sendMessageToAI(userQuery);
            } else {
                Toast.makeText(chatbot.this, "Please enter a question", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessageToAI(String query) {
        txtResponse.append("\n\nMe: " + query);
        txtResponse.append("\n\nHeathub AI: Typing...");
        editMessage.setText("");
        scrollToBottom();

        String systemPrompt = "Role: Heathub Microwave Controller AI. " +
                "Task: Only help with microwave cooking, heating, and kitchen safety.";

        Content content = new Content.Builder()
                .addText(systemPrompt + "\nUser: " + query)
                .build();

        Executor executor = Executors.newSingleThreadExecutor();

        // --- ERROR FIX START ---
        // Yahan humne poora path (com.google.common.util.concurrent) likh diya hai taaki error na aaye
        com.google.common.util.concurrent.ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

        com.google.common.util.concurrent.Futures.addCallback(response, new com.google.common.util.concurrent.FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String aiText = result.getText();
                runOnUiThread(() -> {
                    String currentChat = txtResponse.getText().toString();
                    String updatedChat = currentChat.replace("Heathub AI: Typing...", "Heathub AI: " + aiText);
                    txtResponse.setText(updatedChat);
                    scrollToBottom();
                });
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                runOnUiThread(() -> {
                    Log.e("HEATHUB_ERROR", "Failed: " + t.getMessage());
                    txtResponse.append("\nError: " + t.getLocalizedMessage());
                });
            }
        }, executor);
        // --- ERROR FIX END ---
    }

    private void scrollToBottom() {
        chatScrollView.post(() -> chatScrollView.fullScroll(View.FOCUS_DOWN));
    }
}