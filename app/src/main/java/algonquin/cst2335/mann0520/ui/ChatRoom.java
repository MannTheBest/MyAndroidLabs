package algonquin.cst2335.mann0520.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.mann0520.R;
import algonquin.cst2335.mann0520.data.ChatMessage;
import algonquin.cst2335.mann0520.data.ChatRoomViewModel;
import algonquin.cst2335.mann0520.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.mann0520.databinding.ReceivedMessageBinding;
import algonquin.cst2335.mann0520.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;

    ArrayList<ChatMessage> messages = new ArrayList<>();
    ChatRoomViewModel chatModel;

    RecyclerView.Adapter<MyRowHolder> myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        messages = chatModel.messages.getValue();
        if(messages == null)
        {
            chatModel.messages.postValue( messages = new ArrayList<ChatMessage>());
        }

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

               // if(viewType==0){
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());
              //  }else {
                  //  ReceivedMessageBinding binding = ReceivedMessageBinding.inflate(getLayoutInflater());
                   // return new MyRowHolder(binding.getRoot());

               // }

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
//        binding.button3.setOnClickListener(click -> {
//
//            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
//            String currentDateandTime = sdf.format(new Date());
//
//            messages.add(new ChatMessage(binding.editTextText.getText().toString(),currentDateandTime,false));
//
//            myAdapter.notifyItemInserted(messages.size()-1);
//            binding.editTextText.setText("");
//        });

        binding.test.setOnClickListener(e -> {
            Toast.makeText(this, "Testing", Toast.LENGTH_SHORT).show();
        });
    }

    class MyRowHolder extends RecyclerView.ViewHolder{

        TextView messagetext;
        TextView timeText;


        public MyRowHolder(@NonNull View itemView){
            super(itemView);

            messagetext = itemView.findViewById(R.id.message);
            timeText =  itemView.findViewById(R.id.time);


        }
    }


}