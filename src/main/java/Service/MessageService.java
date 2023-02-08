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

    public Message addMessage(Message message){
        if( !message.getMessage_text().isBlank() && message.getMessage_text().length() <= 255){
           // Message messages = messageDAO.findMessageByUser(message);
            return messageDAO.insertMessage(message);
            /*if(messages != null){
                Message insertedMessage = messageDAO.insertMessage(messages);
                if(insertedMessage != null){
                    return insertedMessage; 
                } else{
                    return null;
                }
                
            } else {
                return null;
       }*/
    } else {
        return null;
    }
    }

    public List<Message> getAllMessagesbyAccountID(Message message){ 
        return messageDAO.getMessageByAccountID(message);
    
}
 
    public Message getAllMessagesbyMessageID(int id){
        return messageDAO.getMessageByMessageID(id);
    }

    //update message if new message is not blank and not too long
    public Message updateMessage(Message message){
         return messageDAO.updateMessage(message); }
/* 
        if(!message.getMessage_text().isBlank() && message.getMessage_text().length() <= 255){
          
        }else{
        return null;
        
    }*/

    public Message deleteMessage(int message_id){
        return messageDAO.deleteMessage(message_id);
    }
 }