package com.example.affix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.affix.model.ChatMessageModel;
import com.example.affix.model.ChatRoomModel;
import com.example.affix.model.UserModel;
import com.example.affix.utils.AndroidUtil;
import com.example.affix.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import org.w3c.dom.Document;

import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {

    UserModel otherUser;
    String chatroomId;
    ChatRoomModel chatroommodel;
    EditText messageInput;
    ImageButton sendMessageBtn;
    ImageButton backBtn;
    TextView otherUsername;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //get UserModel
        otherUser= AndroidUtil.getUserModelFromIntent(getIntent());
        chatroomId= FirebaseUtil.getChatroomId(FirebaseUtil.currentUserId(),otherUser.getUserId());

        messageInput=findViewById(R.id.chat_message_input);
        sendMessageBtn=findViewById(R.id.message_send_btn);
        backBtn=findViewById(R.id.back_btn);
        otherUsername.findViewById(R.id.other_username);
        recyclerView=findViewById(R.id.chat_recycler_view);

        backBtn.setOnClickListener((v)->{
             onBackPressed();
        });
        otherUsername.setText(otherUser.getEmail());

        sendMessageBtn.setOnClickListener((v->{
            String message= messageInput.getText().toString().trim();
            if(message.isEmpty())
                return;
            //sendMessageToUser(message);
        }));

        getOrCreateChatRoomModel();
    }

    /*void sendMessageToUser(String message){
        chatroommodel.setLastMessageTimestamp(com.google.firebase.Timestamp.now());
        chatroommodel.setLastMessageSenderId(FirebaseUtil.currentUserId());
        FirebaseUtil.getChatroomReference(chatroomId).set(chatroommodel);

        ChatMessageModel chatMessageModel = new ChatMessageModel(message,FirebaseUtil.currentUserId(), com.google.firebase.Timestamp.now());
        FirebaseUtil.getChatroomMessageReference(chatroomId).add(chatMessageModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>(){
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            messageInput.setText("");
                        }
                    }
                });

    }*/
    void getOrCreateChatRoomModel(){
        FirebaseUtil.getChatroomReference(chatroomId).get().addOnCompleteListener(task->{
            if(task.isSuccessful()){
                chatroommodel = task.getResult().toObject(ChatRoomModel.class);
                if(chatroommodel==null){ //new chat/first time chatting
                    chatroommodel = new ChatRoomModel(
                            chatroomId,
                            Arrays.asList(FirebaseUtil.currentUserId(), otherUser.getUserId()),
                            Timestamp.now(),
                    "");
                    FirebaseUtil.getChatroomReference(chatroomId).set(chatroommodel);
                }
            }
        });
    }
}
