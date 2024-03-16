package algonquin.cst2335.mann0520.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.mann0520.R;
import algonquin.cst2335.mann0520.data.ChatMassageDAO;
import algonquin.cst2335.mann0520.data.ChatMessage;
import algonquin.cst2335.mann0520.data.ChatRoomViewModel;
import algonquin.cst2335.mann0520.data.MessageDatabase;
import algonquin.cst2335.mann0520.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.mann0520.databinding.ReceivedMessageBinding;
import algonquin.cst2335.mann0520.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;

    ArrayList<ChatMessage> messages = new ArrayList<>();
    ChatRoomViewModel chatModel;

    ChatMassageDAO mDAO;
    RecyclerView.Adapter<MyRowHolder> myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);


        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "MessageDatabase").build();
        mDAO = db.cmDAO();
        messages = chatModel.messages.getValue();
        if(messages == null)
        {
            chatModel.messages.postValue( messages = new ArrayList<ChatMessage>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(()->{
                messages.addAll(mDAO.getAllMessages());
                runOnUiThread(() -> binding.recycleView.setAdapter(myAdapter));
            });
        }

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                if(viewType==0){
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());
                }else {
                    ReceivedMessageBinding binding = ReceivedMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());

                }

            }


            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.messagetext.setText("");
                holder.timeText.setText("");

                ChatMessage obj = messages.get(position);
                holder.messagetext.setText(obj.getMessage());
                holder.timeText.setText(obj.getTimeSent());

            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position){

                ChatMessage obj = messages.get(position);
                if(obj.isSentButton()){
                    return 0;
                }else {
                    return 1;
                }
            }
        });

        binding.button2.setOnClickListener(click -> {

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());


            messages.add(new ChatMessage(binding.editTextText.getText().toString(),currentDateandTime,true));

            myAdapter.notifyItemInserted(messages.size()-1);
            binding.editTextText.setText("");
            Log.d("Hi","Test");
        });
        binding.button3.setOnClickListener(click -> {

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());

            messages.add(new ChatMessage(binding.editTextText.getText().toString(),currentDateandTime,false));

            myAdapter.notifyItemInserted(messages.size()-1);
            binding.editTextText.setText("");
        });


    }

    class MyRowHolder extends RecyclerView.ViewHolder{

        TextView messagetext;
        TextView timeText;


        public MyRowHolder(@NonNull View itemView){
            super(itemView);
            AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );

            messagetext = itemView.findViewById(R.id.message);
            timeText =  itemView.findViewById(R.id.time);
            itemView.setOnClickListener( v -> {
                int position = getAbsoluteAdapterPosition();
                ChatMessage m = messages.get(position);

                builder.setMessage("Do you want to delete the message:" + messagetext.getText());
                builder.setTitle("Question:");
                builder.setNegativeButton("NO", (dialog, cl)->{});
                builder.setPositiveButton( "Yes", (dialog, cl)->{
                    Executor thread = Executors.newSingleThreadExecutor();
                    thread.execute(() ->
                    {
                        mDAO.deleteMessage(m);});

                    messages. remove(position);
                    myAdapter.notifyItemRemoved(position);
                });
                builder.create().show();
            });


        }
    }


}