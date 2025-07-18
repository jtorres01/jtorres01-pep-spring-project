package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
 @RequestMapping("/")
public class SocialMediaController {

    

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    // ACCOUNT ENDPOINTS

    //Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account) {
        return accountService.registerAccount(account);
    }

    //Login an existing user
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        return accountService.login(account);
    }

    //MESSAGE ENDPOINTS

    //Create a new message
    @PostMapping("/messages")
    public ResponseEntity<?> postMessage(@RequestBody Message message) {
        return messageService.createMessage(message);
    }

    //Retrieve all messages
    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    //Retrieve a message by ID
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable int messageId) {
        return messageService.getMessageById(messageId);
    }

    //Delete a message by ID
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable int messageId) {
        return messageService.deleteMessage(messageId);
    }

    //Update a message's text by ID
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessage(
            @PathVariable int messageId,
            @RequestBody Message message) {
        return messageService.updateMessageText(messageId, message.getMessageText());
    }

    //Get all messages for a  account
    @GetMapping("/accounts/{accountId}/messages")
    public List<Message> getMessagesByAccount(@PathVariable int accountId) {
        return messageService.getMessagesByAccountId(accountId);
    }
}
