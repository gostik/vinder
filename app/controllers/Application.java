package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;

public class Application extends Controller {

    public static Result index() {
        return ok(views.html.index.render());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getUser(Long id) {
        return play.mvc.Results.TODO;
    }

    public static Result getNextPhoto(Long id) {
        return play.mvc.Results.TODO;
    }

    public static Result newUser() {
        return play.mvc.Results.TODO;
    }

    public static Result updateUser(long id) {
        return play.mvc.Results.TODO;
    }

    public static Result getUserUid(long uid) {
        return play.mvc.Results.TODO;
    }

    public static Result newPhoto() {
        return play.mvc.Results.TODO;
    }

    public static Result deletePhoto(long id) {
        return play.mvc.Results.TODO;
    }

    public static Result getPhoto(long id) {
        return play.mvc.Results.TODO;
    }

    public static Result getPhotos(long user_id) {
        return play.mvc.Results.TODO;
    }

    public static Result newMessage() {
        return play.mvc.Results.TODO;
    }

    public static Result getMessages(long who_id, long whom_id) {
        return play.mvc.Results.TODO;
    }

    public static Result getMessage(long id) {
        return play.mvc.Results.TODO;
    }

    public static Result getLike(long id) {
        return play.mvc.Results.TODO;
    }

    public static Result getLikes(long who_id, long whom_id) {
        return play.mvc.Results.TODO;
    }

    public static Result newLike() {
        return play.mvc.Results.TODO;
    }

    /**
     * Handle the chat websocket.
     */
    public static WebSocket<JsonNode> chat(final String username) {
        return new WebSocket<JsonNode>() {

            // Called when the Websocket Handshake is done.
            public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out){

                // Join the chat room.
                try {
                    ChatRoom.join(username, in, out);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
    }
}