package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    // 3. Create a new message
    public ResponseEntity<?> createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().isBlank()
                || message.getMessageText().length() > 255
                || message.getPostedBy() == null
                || !accountRepository.existsById(message.getPostedBy())) {
            return ResponseEntity.badRequest().build(); // 400
        }

        Message saved = messageRepository.save(message);
        return ResponseEntity.ok(saved); // 200 OK
    }

    // 4. Get all messages
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // 5. Get message by ID
    public ResponseEntity<?> getMessageById(int messageId) {
        Optional<Message> found = messageRepository.findById(messageId);
        return ResponseEntity.ok(found.orElse(null)); // Always 200 with empty body if not found
    }

    // 6. Delete a message
    public ResponseEntity<?> deleteMessage(int messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return ResponseEntity.ok(1); // One row affected
        }
        return ResponseEntity.ok().build(); // Empty response if not found
    }

    // 7. Update message text
    public ResponseEntity<?> updateMessageText(int messageId, String newText) {
        if (newText == null || newText.isBlank() || newText.length() > 255) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setMessageText(newText);
            messageRepository.save(message);
            return ResponseEntity.ok(1); // One row updated
        } else {
            return ResponseEntity.badRequest().build(); // No such message
        }
    }

    // 8. Get messages by accountId
    public List<Message> getMessagesByAccountId(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
