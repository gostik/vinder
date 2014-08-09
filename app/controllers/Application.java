package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.*;
import play.utils.cache.CachedFinder;

public class Application extends Controller {

    public static Result index() {
        return ok(views.html.index.render());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getUser(Long id) {
        return play.mvc.Results.TODO;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getNextPhoto(Long id) {
        return play.mvc.Results.TODO;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result newUser() {

        StatusBuilder<User> userStatusBuilder = new StatusBuilder<>();

        JsonNode body = request().body().asJson();

        User user = Json.fromJson(body, User.class);

        int rowCount =  User.find.where().eq("uid",user.getUid()).findRowCount();

        if (rowCount > 0)
            return userStatusBuilder.getErrorStatus("User with uid="+user.getUid()+" already exists");

        if (user != null) {
            user.save();
            user.refresh();
        }

        Status responseStatus = userStatusBuilder.getResponseStatus(user);

        return responseStatus;
    }


    @BodyParser.Of(BodyParser.Json.class)
    public static Result updateUser(long id) {
        return play.mvc.Results.TODO;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getUserUid(long uid) {
        return play.mvc.Results.TODO;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result newPhoto() {
        String s = request().body().asText();
        return play.mvc.Results.TODO;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result deletePhoto(long id) {
        return play.mvc.Results.TODO;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getPhoto(long id) {
        return play.mvc.Results.TODO;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getPhotos(long user_id) {
        return play.mvc.Results.TODO;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result newMessage() {
        return play.mvc.Results.TODO;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getMessages(long who_id, long whom_id) {
        return play.mvc.Results.TODO;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getMessage(long id) {
        return play.mvc.Results.TODO;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getLike(long id) {
        return play.mvc.Results.TODO;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getLikes(long who_id, long whom_id) {
        return play.mvc.Results.TODO;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result newLike() {
        return play.mvc.Results.TODO;
    }


    /**
     * Handle the chat websocket.
     */
    public static WebSocket<JsonNode> chat(final String username) {
        return new WebSocket<JsonNode>() {

            // Called when the Websocket Handshake is done.
            public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) {

                // Join the chat room.
                try {
                    ChatRoom.join(username, in, out);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    private static class StatusBuilder<T> {

        private StatusBuilder() {
        }

        private Status getResponseStatus(T object) {

            ObjectNode result = Json.newObject();
            JsonNode jsonNode = Json.toJson(object);
            result.put("result", "OK");

            result.put("message", Json.stringify(jsonNode));

            return object != null ? ok(result) : badRequest(result);
        }

        private Status getErrorStatus(String reason) {

            ObjectNode result = Json.newObject();
            result.put("result", "BAD");

            result.put("reason", reason);

            return badRequest(result);
        }
    }
}