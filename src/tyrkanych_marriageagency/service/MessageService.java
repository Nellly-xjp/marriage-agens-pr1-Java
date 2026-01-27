package tyrkanych_marriageagency.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import tyrkanych_marriageagency.model.Message;

public class MessageService {

    private final List<Message> messages = new ArrayList<>();

    public void send(String fromId, String toId, String text) {
        messages.add(new Message(fromId, toId, text));
    }

    public List<Message> inbox(String userId) {
        return messages.stream()
              .filter(m -> m.toUserId().equals(userId))
              .collect(Collectors.toList());
    }
}
