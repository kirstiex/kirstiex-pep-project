package Controller;

import Model.Message;
import Model.Account;
import Service.MessageService;
import Service.AccountService;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    MessageService messageService;
    AccountService accountService;

    public SocialMediaController(){
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register/", this::newUserHandler);
        app.post("/login/", this::postLoginHandler);
        app.post("/messages/", this::postMessageHandler);
        app.get("/messages/", this::getMessageHandler);
        app.get("/messages/{message_id}", this::getMessageIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageIDHandler);
        app.patch("/messages/{message_id}", this::patchMessageIDHandler);
        app.get("/accounts/{account_id}/messages/", this::getAccountMessagesHandler);
        return app;
    }

/**
     * Handler to post a new account.
     *  Creates New account with account username and password. Username cannot be blank and password must be at least 4 characters
     *  contains JSON representation of account. response status 200 OK. 
     *  If registration not successful, response status should be 400 (client error)
     */
    private void newUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
            ctx.status(200);
        }else{
            ctx.status(400);
        }

    }
   /**
     * Handler to login to an account.
     * login to account with account username and password.
        * verify login - request body should contain JSON of account without id
        * successful if username and password provided in request body match real account
        * if successful response body should contain JSON of account with id
        * Status 200 if successful 
        * Status 401 (unauthorized) if unsucessful
     */ 
    private void postLoginHandler(Context ctx) throws JsonProcessingException { 
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account findAccount = accountService.findAccount(account);
        if(findAccount!=null){
            ctx.json(mapper.writeValueAsString(findAccount));
            ctx.status(200);
        }else{
            ctx.status(401);
        }
    }
    /**
     * Handler to post a new message.
     *  Creates New message. Successful if message_text is not blank, under 255 characters
     *  posted_by must refer to a real, existing user.
     *  if successful status should be 200. If unsuccessful status should be 400.
     */
    private void postMessageHandler(Context ctx) throws JsonProcessingException, JsonMappingException  {   
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage!=null){
            ctx.json(mapper.writeValueAsString(addedMessage));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    } 
        
    /**
     * Handler to get all messages
     *  response body should contain JSON list containing all messages retrieved from the database.
     *  It should be empty if no messages are found. 
     *  Status should stay 200.
     */
    private void getMessageHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
        ctx.status(200);
    }
    /**
     * Handler to get a message by its message_id
       Response body should contain a JSON of the message identified by the message_id 
     * if there is no message it should just send an empty response.
     * Status should always be 200.
     */
    private void getMessageIDHandler(Context ctx) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            int message_id = Integer.parseInt(ctx.pathParam("message_id"));
            Message messagebyID = messageService.getAllMessagesbyMessageID(message_id); 
            if(messagebyID != null){
                ctx.json(mapper.writeValueAsString(messagebyID));
                ctx.status(200);     
            } else{
                ctx.status(200);     
            }

        }      
    /**
     * Handler to get all messages written by an user
     * Response body should contain a JSON of a list containing all messages posted by a user.
     * List should be empty if there are no messages. 
     * Status should always be 200.
     */
    private void getAccountMessagesHandler(Context ctx) {
         ctx.json(messageService.getAllMessagesbyAccountID(Integer.parseInt(ctx.pathParam("account_id"))));
         ctx.status(200);
    }
    /**
     * Handler to update a message
     *  Updates existing message identified by a message_id 
     *  request body should contain a new message_text value. The update of a message should
     *  be successful successful if the message_id already exist and the new message_text is not blank and is not over 255 characters.
     *  if successful the response body will contain the full updated message.
     *  response should be 200. if not successful response should be 400.
     */
    private void patchMessageIDHandler(Context ctx)  throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message_id, message);
        if(updatedMessage==null){
            ctx.status(400); 
        }else{
            ctx.json(mapper.writeValueAsString(updatedMessage));
            ctx.status(200);
        }
    }
        // submit a delete request for message
        // deletion of an existing message should remove an existing message from the database
        // if the message existed response body should contain the now-deleted message response should be 200 
        // if the message did not exist. response should be 200. but response body should
        // be empty.  
        private void deleteMessageIDHandler(Context ctx) throws JsonProcessingException {
            int message_id = Integer.parseInt(ctx.pathParam("message_id"));
            Message checkForMessage = messageService.getAllMessagesbyMessageID(message_id);
            messageService.deleteMessage(Integer.parseInt(ctx.pathParam("message_id")));
            if(checkForMessage != null){
            ctx.json(checkForMessage);
            ctx.status(200);     
            } else{
                ctx.status(200);     
            }
        }
}



 
         
    


    

