package algonquin.cst2335.mann0520;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import algonquin.cst2335.mann0520.data.ChatRoomViewModel;
import algonquin.cst2335.mann0520.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.mann0520.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;

    ArrayList<String> messages = new ArrayList<>();
    ChatRoomViewModel chatModel;

    private RecyclerView.Adapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_chat_room);

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        if(messages == null){
            chatModel.messages.postValue(messages = new ArrayList<String>());
        }

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                return new MyRowHolder(binding.getRoot());
            }


            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.messagetext.setText("");
                holder.timeText.setText("");

                String obj = messages.get(position);
                holder.messagetext.setText(obj);

            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position){
                return 0;
            }
        });

        binding.button2.setOnClickListener(click -> {

            messages.add(binding.editTextText.getText().toString());

            myAdapter.notifyItemInserted(messages.size()-1);
            binding.editTextText.setText("");
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