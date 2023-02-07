package Controller;

import Model.Message;
import Model.Account;
import Service.MessageService;
import Service.AccountService;

import io.javalin.Javalin;
import io.javalin.http.Context;
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
        app.post("/register/", this::postRegisterHandler);
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
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
    
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
 */
/**
     * Handler to post a new account.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.post method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void postRegisterHandler(Context ctx) throws JsonProcessingException {
        //used to create an account
        // contains representation of JSON account
        //does not contain account_id
        // username cannot be blank 
        // password is at least 4 characters long
        // account with that username doesn't already exist
        // response status should be 200 OK, which is default
        // if registration is not successful, response status should be 400 (client error)
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }

    }
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        //verify login
        //request body should contain JSON of account without id
        //may generate a Session token in the future
        // successful if username and password provided in request body match real account
        //if success response body should contain JSON of account with id
        // Status should be 200
        // Status should be 401 (unauthorized) if unsucessful
        
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account findAccount = accountService.addAccount(account);
        if(findAccount!=null){
            ctx.json(mapper.writeValueAsString(findAccount));
        }else{
            ctx.status(401);
        }
    }
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        // add new message
        // request body should contain JSON representation of a message without ID
        // sucessful if message_text is not blank, under 255 characters
        // and posted_by refers to a real, existing user.
        // if successful response status should be 200
        // if unsuccessful response status should be 400 (Client error)
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
    private void getMessageHandler(Context ctx) {
        //user submits get request 
        // response body should contain JSON list containing all messages retrieved from 
        // database. Should be empty if there are no messages.
        // status should always be 200
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }
    private void deleteMessageIDHandler(Context ctx) {
        // submit a delete request for message
        // deletion of an existing message should remove an existing message from 
        // the database
        // if the message existed response body should contain the now-deleted message
        // respsone should be 200 
        // if the message did not exist. response should be 200. but response body should
        // be empty. 
    
        ctx.json(messageService.deleteMessage(Integer.parseInt(ctx.pathParam("id"))));
    }
    private void getMessageIDHandler(Context ctx) {
        // response body should contain JSON of the message identified by the message_id
        // expected for response body to simply be empty if there is no such message
        // resposne should always be 200
        
        ctx.json(messageService.getAllMessagesbyMessageID(Integer.parseInt(ctx.pathParam("id"))));
    }
    private void patchMessageIDHandler(Context ctx)  throws JsonProcessingException{
        // update message text identified by a message id
        // request body should contain a new message_text values to replace the message
        // identified by the message_id
        // the update of a message should be successful if the message id already exist and the
        // new message_text is not blank and is not over 255 characters
        //  if successful the response body should contain the full updated message
        // including ( message_id, posted_by, message_text, time_posted_epoch)
        // response should be 200
        // if not successful response status should be 400 (client error)
        ObjectMapper mapper = new ObjectMapper();
        //Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message_id);
        System.out.println(updatedMessage);
        if(updatedMessage == null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }

    }
    private void getAccountMessagesHandler(Context ctx) {
        // retrieve all messages written by a user
        // respsonse body should contain a JSON of a list containing all messages posted by 
        // user. list should be empty if there are no messages.
        // status should always be 200
        ctx.json(messageService.getAllMessagesbyAccountID(Integer.parseInt(ctx.pathParam("id"))));
    }
   

}