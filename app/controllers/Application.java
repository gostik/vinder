package controllers;

import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import play.libs.Json;
import play.mvc.*;

import java.util.List;

public class Application extends Controller {

    public static Result index() {
        return ok(views.html.index.render());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getUser(Long id) {
        StatusBuilder<User> userStatusBuilder = new StatusBuilder<>();

        User user = User.find.byId(id);

        if (user == null)
            return userStatusBuilder.getErrorStatus("User with id=" + id + " not found");
        Status responseStatus = userStatusBuilder.getResponseStatus(user);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getNextPhoto(Long id) {

        StatusBuilder<Photo> photoStatusBuilder = new StatusBuilder<>();

        User user = User.find.byId(id);

        if (user == null)
            return photoStatusBuilder.getErrorStatus("User with id=" + id + " not found");

        Settings settings = user.getSettings();
        settings.refresh();

        ExpressionList<User> query = User.find.where().conjunction()
                .between("age", settings.getMin_age(), settings.getMax_age())
                .ne("id", id)
                        //sex == sex in settngs or sex ==0
                .disjunction().add(Expr.eq("sex", settings.getSex())).add(Expr.eq("sex", 0)).conjunction();

        if (settings.getFilter_by_pro()) {
            query = query.disjunction().add(Expr.eq("pro_status", user.getPro_status())).add(Expr.eq("pro_status", user.getVip_status()));
        }

        List<User> userList = query.findList();

        for (User userForCheck : userList) {

            //получаем фотки пользователя
            List<Photo> photos = Photo.find.where().eq("user", userForCheck).findList();

            for (Photo photo : photos) {
                //берем те , за которые не проголосовали
                int rowCount = Like.find.where().eq("photo", photo).eq("who", user).findRowCount();

                //если находим ту, за которую не проголосовали- выдаеем
                if (rowCount == 0)
                    return photoStatusBuilder.getResponseStatus(photo);
            }
        }

        return photoStatusBuilder.getErrorStatus("Photo not found");
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result newUser() {

        StatusBuilder<User> userStatusBuilder = new StatusBuilder<>();

        JsonNode body = request().body().asJson();

        User user = Json.fromJson(body, User.class);

        int rowCount = User.find.where().eq("uid", user.getUid()).findRowCount();

        if (rowCount > 0)
            return userStatusBuilder.getErrorStatus("User with uid=" + user.getUid() + " already exists");

        user.save();
        user.refresh();

        Status responseStatus = userStatusBuilder.getResponseStatus(user);

        return responseStatus;
    }


    @BodyParser.Of(BodyParser.Json.class)
    public static Result updateUser(long id) {
        StatusBuilder<User> userStatusBuilder = new StatusBuilder<>();

        JsonNode body = request().body().asJson();

        User newUser = Json.fromJson(body, User.class);

        User oldUser = User.find.byId(id);

        if (oldUser == null)
            return userStatusBuilder.getErrorStatus("User with uid=" + newUser.getUid() + " not exists");

        oldUser.update(newUser);

        oldUser.refresh();

        Status responseStatus = userStatusBuilder.getResponseStatus(oldUser);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getUserUid(long uid) {

        StatusBuilder<User> userStatusBuilder = new StatusBuilder<>();

        User user = User.find.where().eq("uid", uid).findUnique();

        if (user == null)
            return userStatusBuilder.getErrorStatus("User with uid=" + uid + " not exists");

        user.refresh();

        return userStatusBuilder.getResponseStatus(user);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result createPhoto(Long user_id) {
        StatusBuilder<Photo> photoStatusBuilder = new StatusBuilder<>();

        JsonNode body = request().body().asJson();

        User user = User.find.byId(user_id);

        List<Photo> all = Photo.find.all();

        if (user == null)
            return photoStatusBuilder.getErrorStatus("User with id=" + user_id + " not found");

        Photo photo = Json.fromJson(body, Photo.class);

        int rowCount = Photo.find.where().eq("url75", photo.getUrl75()).findRowCount();


        if (rowCount > 0)
            return photoStatusBuilder.getErrorStatus("Photo with url=" + photo.getUrl75() + " already exists");

        photo.setUser(user);
        photo.save();
        photo.refresh();

        Status responseStatus = photoStatusBuilder.getResponseStatus(photo);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result deletePhoto(long id) {
        StatusBuilder<Photo> photoStatusBuilder = new StatusBuilder<>();

        Photo photo = Photo.find.byId(id);

        if (photo == null)
            return photoStatusBuilder.getErrorStatus("Photo with id=" + id + " not exists");

        photo.delete();

        Status responseStatus = photoStatusBuilder.getResponseStatus(photo);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getPhoto(long id) {
        StatusBuilder<Photo> photoStatusBuilder = new StatusBuilder<>();

        Photo photo = Photo.find.byId(id);

        if (photo == null)
            return photoStatusBuilder.getErrorStatus("Photo with id=" + id + " not exists");

        Status responseStatus = photoStatusBuilder.getResponseStatus(photo);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getPhotos(long user_id) {
        StatusBuilder<List<Photo>> photoStatusBuilder = new StatusBuilder<>();

        User user = User.find.byId(user_id);

        if (user == null)
            return photoStatusBuilder.getErrorStatus("User with id=" + user_id + " was not found");
        user.refresh();
        List<Photo> photos = user.getPhotos();

        if (photos == null)
            return photoStatusBuilder.getErrorStatus("User with id=" + user_id + " has not photos");

        Status responseStatus = photoStatusBuilder.getResponseStatus(photos);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result newMessage() {
        StatusBuilder<Message> messageStatusBuilder = new StatusBuilder<>();

        JsonNode body = request().body().asJson();

        Message message = Json.fromJson(body, Message.class);

        if (message != null) {
            message.save();
            message.refresh();
        }

        Status responseStatus = messageStatusBuilder.getResponseStatus(message);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getMessages(long who_id, long whom_id) {
        StatusBuilder<List<Message>> userStatusBuilder = new StatusBuilder<>();

        List<Message> messages = Message.find
                .where()
                .eq("who", User.find.byId(who_id))
                .eq("whom", User.find.byId(whom_id))
                .findList();

        if (messages == null || messages.isEmpty())
            return userStatusBuilder.getErrorStatus("Messages with who_id=" + who_id + " whom_id=" + whom_id + " not exists");

        Status responseStatus = userStatusBuilder.getResponseStatus(messages);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getMessage(long id) {
        StatusBuilder<Message> photoStatusBuilder = new StatusBuilder<>();

        Message message = Message.find.byId(id);

        if (message == null)
            return photoStatusBuilder.getErrorStatus("Message with id=" + id + " not exists");

        Status responseStatus = photoStatusBuilder.getResponseStatus(message);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getLike(long id) {
        StatusBuilder<Like> photoStatusBuilder = new StatusBuilder<>();

        Like like = Like.find.byId(id);

        if (like == null)
            return photoStatusBuilder.getErrorStatus("Like with id=" + id + " not exists");

        Status responseStatus = photoStatusBuilder.getResponseStatus(like);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getLikes(long who_id, long whom_id) {
        StatusBuilder<List<Like>> userStatusBuilder = new StatusBuilder<>();

        List<Like> likes = Like.find.where()
                .eq("who", User.find.byId(who_id))
                .eq("whom", User.find.byId(who_id))
                .findList();

        if (likes == null || likes.isEmpty())
            return userStatusBuilder.getErrorStatus("Likes with who_id=" + who_id + " whom_id=" + whom_id + " not exists");

        Status responseStatus = userStatusBuilder.getResponseStatus(likes);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result newLike() {
        StatusBuilder<Like> messageStatusBuilder = new StatusBuilder<>();

        JsonNode body = request().body().asJson();

        Like like = Json.fromJson(body, Like.class);


        List<Like> list = Like.find.where()
                .eq("who", like.getWho())
                .eq("whom", like.getWhom())
                .eq("photo", like.getPhoto())
                .findList();

        if (list.size() > 1)
            return messageStatusBuilder.getErrorStatus("Same like already exists: who:" + like.getWho() + "\n whom:" + like.getWhom());

        like.save();
        like.refresh();

        Status responseStatus = messageStatusBuilder.getResponseStatus(like);

        return responseStatus;
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

    public static Result updateLike() {
        StatusBuilder<Like> likeStatusBuilder = new StatusBuilder<>();

        JsonNode body = request().body().asJson();

        Like like = Json.fromJson(body, Like.class);

        Like likeToUpdate = Like.find.byId(like.getID());
        likeToUpdate.setResult(like.getResult());
        likeToUpdate.update();
        if (likeToUpdate != null)
            return likeStatusBuilder.getResponseStatus(likeToUpdate);

        return likeStatusBuilder.getErrorStatus("Like is not found.");
    }


    public static Result getFriendshipsToMe(long user_id) {

        StatusBuilder<List<Friendship>> friendshipsStatusBuilder = new StatusBuilder<>();

        User user = User.find.byId(user_id);

        List<Friendship> friendships = Friendship.find.where().eq("whom", user).findList();

        return friendshipsStatusBuilder.getResponseStatus(friendships);

    }

    public static Result updateFriendship() {
        return Results.TODO;
    }

    public static Result getFromFriendship() {
        return Results.TODO;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result updateLocation(long user_id) {

        User user = User.find.byId(user_id);

        StatusBuilder<Location> locationStatusBuilder = new StatusBuilder<>();

        JsonNode body = request().body().asJson();

        Location location = Json.fromJson(body, Location.class);


        if (location != null) {
            user.setLocation(location);
            user.update();
            return locationStatusBuilder.getResponseStatus(location);
        }

        return locationStatusBuilder.getErrorStatus("Location is not found.");
    }


    @BodyParser.Of(BodyParser.Json.class)
    public static Result updateSettings(long user_id) {

        StatusBuilder<Settings> settingsBuilder = new StatusBuilder<>();

        JsonNode body = request().body().asJson();

        Settings settings = Json.fromJson(body, Settings.class);

        User user = User.find.byId(user_id);

        user.setSettings(settings);
//        Settings settings1 = user.getSettings();
//
//        user.setSettings(settings);
//
        user.update();

        if (settings != null)
            return settingsBuilder.getResponseStatus(settings);

        return settingsBuilder.getErrorStatus("Settings is not found..");

    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getDisliked(long user_id) {
        User user = User.find.byId(user_id);


        StatusBuilder<List<Like>> likeStatusBuilder = new StatusBuilder<>();

        if (user == null)
            likeStatusBuilder.getErrorStatus("User not found with id " + user_id);

        List<Like> list = Like.find.where()
                .eq("who", user)
                .eq("result", false)
                .setMaxRows(user!=null && user.getVip_status()?10:1)
                .findList();


        return likeStatusBuilder.getResponseStatus(list);


    }


    private static class StatusBuilder<T> {

        private StatusBuilder() {
        }

        private Status getResponseStatus(T object) {

            JsonNode jsonNode = Json.toJson(object);
            return object != null ? ok(Json.toJson(object)) : badRequest(Json.toJson(null));
        }

        private Status getErrorStatus(String reason) {

            ObjectNode result = Json.newObject();
            result.put("result", "BAD");

            result.put("reason", reason);

            return badRequest(result);
        }
    }
}