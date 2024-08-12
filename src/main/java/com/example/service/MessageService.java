package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class used to demonstrate ORM functionality for related entities.
 */
@Service
@Transactional
public class MessageService {
    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    /**
     * Insert a message into the message table.
     * The creation of the message will be successful if and only if the message_text 
     * is not blank and is under 255 characters.
     * @param message a Message object
     * @return the Message added into the database.
     */
    public Message addMessage(Message message){
        if (message == null) {
            return null;
        }
        else if (message.getMessageText() == null || message.getPostedBy() == null) {
            return null;
        }
        if (message.getMessageText().length() > 255 || message.getMessageText().length() == 0) {
            return null;
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    /**
     * Return a message from the message table that matches the given messageId
     * @param messageId an int that identifies an message
     * @return the Message identified by the given messageId.
     */
    public Message getMessageByMessageId(int messageId) {
        Optional<Message> optionalMessage = messageRepository.findByMessageId(messageId);
        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        }else{
            return null;
        }
    }

     /**
     * Delete a message from the message table that matches the given messageId
     * @param messageId an int that identifies an message
     * @return the number of rows updated
     */
    public Integer deleteMessageByMessageId(int messageId) {
        Optional<Message> optionalMessage = messageRepository.findByMessageId(messageId);
        if (optionalMessage.isPresent()) {
            messageRepository.delete(optionalMessage.get()); 
            return 1;
        } else {
            return null;
        }
    }

    /**
     * Patch a message from the message table that matches the given messageId
     * @param messageId an int that identifies an message
     * @param newMessage a Message with new message_text
     * @return the number of rows updated
     */
    public Integer patchMessageByMessageId(int messageId, Message newMessage) {
        if (newMessage == null) {
            return null;
        }
        else if (newMessage.getMessageText() == null) {
            return null;
        }
        if (newMessage.getMessageText().length() > 255 || newMessage.getMessageText().length() == 0) {
            return null;
        }
        Optional<Message> optionalMessage = messageRepository.findByMessageId(messageId);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setMessageText(newMessage.getMessageText());   

            messageRepository.save(message);
            return 1;
        } else {
            return null;
        }
    }

    /**
     * Get all messages from the message table that belong to the user of the given accountId
     * @param accountId an int that identifies an account
     * @return the number of rows updated
     */
    public List<Message> getAllMessagesByAccountId(int accountId) {
        List<Message> messages = messageRepository.findAllByPostedBy(accountId);
        return messages;
    }
}
