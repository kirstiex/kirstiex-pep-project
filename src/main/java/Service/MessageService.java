package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

/**
 * The purpose of a Service class is to contain "business logic" that sits between the web layer (controller) and
 * persistence layer (DAO). That means that the Service class performs tasks that aren't done through the web or
 * SQL: programming tasks like checking that the input is valid, conducting additional security checks, or saving the
 * actions undertaken by the API to a logging file.
 *
 * It's perfectly normal to have Service methods that only contain a single line that calls a DAO method. An
 * application that follows best practices will often have unnecessary code, but this makes the code more
 * readable and maintainable in the long run!
 */

 public class MessageService{
    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    //add a new message to database if the message is not blank and under 255 characters
    //also user must not already exist

    public Message addMessage(Message message){
        
        if( !message.getMessage_text().isBlank() && message.getMessage_text().length() <= 255 || this.messageDAO.getAccountByAccountId(message) != null){
            return messageDAO.insertMessage(message);
        
        }else {
        return null;
        }
    } 
    //get all messages by message_id
    public Message getAllMessagesbyMessageID(int id){
        Message messageFromDb = this.messageDAO.getMessageByMessageID(id);
        if(messageFromDb == null) return null;

        return messageDAO.getMessageByMessageID(id);
    }

    //get all messages by the account_id / posted_by 
    public List<Message> getAllMessagesbyAccountID(int account_id){ 
        return messageDAO.getMessagePostedByUser(account_id);
    
}
     //update message 
    public Message updateMessage(int message_id, Message message){
        Message messageFromDb = this.messageDAO.getMessageByMessageID(message_id);

        // if there is no message in database return null
        if(messageFromDb == null){
            return null;
        } 
        //return updated message if new message isn't blank and has length less than 255
         else if(!message.getMessage_text().isBlank() && message.getMessage_text().length() <= 255) {
            messageDAO.updateMessage(message_id, message); 
            return this.messageDAO.getMessageByMessageID(message_id);
        }
        else {
            return null;
       }
    }
    
  // public void deleteMessage(int message_id) {
   //     messageDAO.deleteMessage(message_id);
 //}

    //delete a message from the database with message_id
    
    
     
     public Message deleteMessage(int message_id){
        Message messageFromDb = this.messageDAO.getMessageByMessageID(message_id);

        // if there is no message in database return null
        if(messageFromDb == null ){
            return null;
        }        
        else {
           
            messageDAO.deleteMessage(message_id); 
            return messageFromDb;
            
       }
    }
 }