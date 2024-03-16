package algonquin.cst2335.mann0520.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatMassageDAO {
    @Insert
    public void insertMessage(ChatMessage message);

    @Query("SELECT * FROM ChatMessage")
    public List<ChatMessage> getAllMessages();

    @Delete
    void deleteMessage(ChatMessage message);




}
