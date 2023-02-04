package Controller;

import Model.Message;
import Model.Account;
import Service.MessageService;
import Service.AccountService;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        app.start(8080);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postRegisterHandler(Context ctx) {
        //used to create an account
        // contains representation of JSON account
        //does not contain account_id
        // username cannot be blank 
        // password is at least 4 characters long
        // account with that username doesn't already exist
        // response status should be 200 OK, which is default
        // if registration is not successful, response status should be 400 (client error)
        ObjectMapper mapper = new ObjectMapper();

    }
    private void postLoginHandler(Context ctx) {
        //verify login
        //request body should contain JSON of account without id
        //may generate a Session token in the future
        // successful if username and password provided in request body match real account
        //if success response body should contain JSON of account with id
        // Status should be 200
        // Status should be 401 (unauthorized) if unsucessful

    }
    private void postMessageHandler(Context ctx) {
        //submit new post 
        // request body should contain JSON representation of a message without ID
        // sucessful if message_text is not blank, under 255 characters
        // and posted_by refers to a real, existing user.
        // if successful response status should be 200
        // if unsuccessful response status should be 400 (Client error)
    }
    private void getMessageHandler(Context ctx) {
        //user submits get request 
        // response body should contain JSON list containing all messages retrieved from 
        // database. Should be empty if there are no messages.
        // status should always be 200
        ctx.json(MessageService.getAllMessages());
    }
    private void deleteMessageIDHandler(Context ctx) {
        // submit a delete request for message
        // deletion of an existing message should remove an existing message from 
        // the database
        // if the message existed response body should contain the now-deleted message
        // respsone should be 200 
        // if the message did not exist. response should be 200. but response body should
        // be empty. 
    }
    private void getMessageIDHandler(Context ctx) {
        // response body should contain JSON of the message identified by the message_id
        // expected for response body to simply be empty if there is no such message
        // resposne should always be 200
    }
    private void patchMessageIDHandler(Context ctx) {
        // update message text identified by a message id
        // request body should contain a new message_text values to replace the message
        // identified by the message_id
        // the update of a message should be successful if the message id already exist and the
        // new message_text is not blank and is not over 255 characters
        //  if successful the response body should contain the full updated message
        // including ( message_id, posted_by, message_text, time_posted_epoch)
        // response should be 200
        // if not successful response status should be 400 (client error)
    }
    private void getAccountMessagesHandler(Context ctx) {
        // retrieve all messages written by a user
        // respsonse body should contain a JSON of a list containing all messages posted by 
        // user. list should be empty if there are no messages.
        // status should always be 200
    }
   

}