package controllers;

import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Date;
import java.util.List;

public class Application extends Controller {


    public static Result index() {


        return ok();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getUser(Long id) {
        StatusBuilder<User> userStatusBuilder = new StatusBuilder<User>();

        User user = User.find.byId(id);

        if (user == null)
            return userStatusBuilder.getErrorStatus("Пользователь не найден");
        Status responseStatus = userStatusBuilder.getResponseStatus(user);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getNextPhoto(Long id) {

        StatusBuilder<Photo> photoStatusBuilder = new StatusBuilder<Photo>();

        User user = User.find.byId(id);

        user.setUpdatedDate(new Date());
        user.save();

        if (user == null)
            return photoStatusBuilder.getErrorStatus("Пользователь не найден");

        Settings settings = user.getSettings();


        ExpressionList<User> query = User.find.where().conjunction()
                .between("age", settings.getMin_age(), settings.getMax_age())
                .ne("id", id)
                        //sex == sex in settngs or sex ==0
                .disjunction().add(Expr.eq("sex", settings.getSex_for_search())).add(Expr.eq("sex", 0));
        query.findList();

        if (settings.getFilter_by_pro()) {
            query = query.where().disjunction()
                    .add(Expr.eq("pro_status", user.getPro_status()))
                    .add(Expr.eq("pro_status", user.getVip_status()));
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

        return photoStatusBuilder.getErrorStatus("Больше фотографий не найдено");
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result newUser() {

        StatusBuilder<User> userStatusBuilder = new StatusBuilder<User>();

        JsonNode body = request().body().asJson();

        User user = Json.fromJson(body, User.class);

        int rowCount = User.find.where().eq("uid", user.getUid()).findRowCount();

        if (rowCount > 0)
            return userStatusBuilder.getErrorStatus("Пользователь уже существует");

        user.save();
        user.refresh();

        Status responseStatus = userStatusBuilder.getResponseStatus(user);

        return responseStatus;
    }


    @BodyParser.Of(BodyParser.Json.class)
    public static Result updateUser(long id) {
        StatusBuilder<User> userStatusBuilder = new StatusBuilder<User>();

        JsonNode body = request().body().asJson();

        User newUser = Json.fromJson(body, User.class);

        User oldUser = User.find.byId(id);

        oldUser.setAge(newUser.getAge());
        oldUser.setVip_status(newUser.getVip_status());
        //oldUser.setLocation(newUser.getLocation());
        oldUser.setSex(newUser.getSex());
        oldUser.setReg_id(newUser.getReg_id());

//        if (oldUser == null)
//            return userStatusBuilder.getErrorStatus("Пользователь не существует");

        oldUser.save();


        Status responseStatus = userStatusBuilder.getResponseStatus(newUser);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getUserUid(long uid) {

        StatusBuilder<User> userStatusBuilder = new StatusBuilder<User>();

        User user = User.find.where().eq("uid", uid).findUnique();

        if (user == null)
            return userStatusBuilder.getErrorStatus("Пользователь не существует");

        user.refresh();

        return userStatusBuilder.getResponseStatus(user);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result createPhoto(Long user_id) {
        StatusBuilder<Photo> photoStatusBuilder = new StatusBuilder<Photo>();

        JsonNode body = request().body().asJson();

        User user = User.find.byId(user_id);

        List<Photo> all = Photo.find.all();

        if (user == null)
            return photoStatusBuilder.getErrorStatus("Пользователь не существует");

        Photo photo = Json.fromJson(body, Photo.class);

        int rowCount = Photo.find.where().eq("url75", photo.getUrl75()).findRowCount();


        if (rowCount > 0)
            return photoStatusBuilder.getErrorStatus("Фото уже существует");

        photo.setUser(user);
        photo.save();
        photo.refresh();

        Status responseStatus = photoStatusBuilder.getResponseStatus(photo);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result deletePhoto(long id) {
        StatusBuilder<Photo> photoStatusBuilder = new StatusBuilder<Photo>();

        Photo photo = Photo.find.byId(id);

        if (photo == null)
            return photoStatusBuilder.getErrorStatus("Фото не существует");

        photo.delete();

        Status responseStatus = photoStatusBuilder.getResponseStatus(photo);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getPhoto(long id) {
        StatusBuilder<Photo> photoStatusBuilder = new StatusBuilder<Photo>();

        Photo photo = Photo.find.byId(id);

        if (photo == null)
            return photoStatusBuilder.getErrorStatus("Фото не существует");

        Status responseStatus = photoStatusBuilder.getResponseStatus(photo);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getPhotos(long user_id) {
        StatusBuilder<List<Photo>> photoStatusBuilder = new StatusBuilder<List<Photo>>();

        User user = User.find.byId(user_id);

        if (user == null)
            return photoStatusBuilder.getErrorStatus("Пользователя не существует");
        user.refresh();
        List<Photo> photos = user.getPhotos();

        if (photos == null)
            return photoStatusBuilder.getErrorStatus("У пользователя нет фото");

        Status responseStatus = photoStatusBuilder.getResponseStatus(photos);

        return responseStatus;
    }

    static GCMSender gcmSender = new GCMSender();

    @BodyParser.Of(BodyParser.Json.class)
    public static Result newMessage() {
        StatusBuilder<Message> messageStatusBuilder = new StatusBuilder<Message>();

        //Message as = request().body().as(Message.class);
        JsonNode body = request().body().asJson();

        Message message = Json.fromJson(body, Message.class);

        if (message != null) {
            message.save();
            message.refresh();

            sendMessageToGcm(message);
        }


        Status responseStatus = messageStatusBuilder.getResponseStatus(message);

        return responseStatus;
    }

    private static void sendMessageToGcm(Message message) {

        JsonNode jsonNode = Json.toJson(message);
        gcmSender.sendMessageViaGCM(message.getWhom().getReg_id(),"message",Json.stringify(jsonNode));
    }

    private static void sendFrendshipToGcm(Friendship friendship) {

        JsonNode jsonNode = Json.toJson(friendship);
        if (!friendship.getUser1Delivered())
            gcmSender.sendMessageViaGCM(friendship.getUser1().getReg_id(),"friendship",Json.stringify(jsonNode));

        //jsonNode = Json.toJson(notification);
        if (!friendship.getUser2Delivered())
            gcmSender.sendMessageViaGCM(friendship.getUser2().getReg_id(),"friendship",Json.stringify(jsonNode));

        friendship.setUser1Delivered(true);
        friendship.setUser2Delivered(true);
        friendship.update();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getMessages(long who_id, long whom_id) {
        StatusBuilder<List<Message>> userStatusBuilder = new StatusBuilder<List<Message>>();

        List<Message> messages = Message.find
                .where()
                .eq("who", User.find.byId(who_id))
                .eq("whom", User.find.byId(whom_id))
                .findList();

        if (messages == null || messages.isEmpty())
            return userStatusBuilder.getErrorStatus("Сообщения не существует");

        Status responseStatus = userStatusBuilder.getResponseStatus(messages);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getMessage(long id) {
        StatusBuilder<Message> photoStatusBuilder = new StatusBuilder<Message>();

        Message message = Message.find.byId(id);

        if (message == null)
            return photoStatusBuilder.getErrorStatus("Сообщения не существует");

        Status responseStatus = photoStatusBuilder.getResponseStatus(message);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getLike(long id) {
        StatusBuilder<Like> photoStatusBuilder = new StatusBuilder<Like>();

        Like like = Like.find.byId(id);

        if (like == null)
            return photoStatusBuilder.getErrorStatus("Лайка не существует");

        Status responseStatus = photoStatusBuilder.getResponseStatus(like);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getLikes(long who_id, long whom_id) {
        StatusBuilder<List<Like>> userStatusBuilder = new StatusBuilder<List<Like>>();

        List<Like> likes = Like.find.where()
                .eq("who", User.find.byId(who_id))
                .eq("whom", User.find.byId(who_id))
                .findList();

        if (likes == null || likes.isEmpty())
            return userStatusBuilder.getErrorStatus("Лайка не существует");

        Status responseStatus = userStatusBuilder.getResponseStatus(likes);

        return responseStatus;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result newLike() {
        StatusBuilder<Like> messageStatusBuilder = new StatusBuilder<Like>();

        JsonNode body = request().body().asJson();

        Like like = Json.fromJson(body, Like.class);


        List<Like> list = Like.find.where()
                .eq("who", like.getWho())
                .eq("whom", like.getWhom())
                .eq("photo", like.getPhoto())
                .findList();

        if (list.size() > 0)
            return messageStatusBuilder.getErrorStatus("Вы уже голосовали за эту фотографию");

        like.save();
        like.refresh();

        checkLikesAndCreateFriendshipIfNeed(like);

        Status responseStatus = messageStatusBuilder.getResponseStatus(like);

        return responseStatus;
    }

    private static void checkLikesAndCreateFriendshipIfNeed(Like like) {

        List<Like> likes = Like.find.where().eq("result", Boolean.TRUE)
                .and(Expr.and(Expr.eq("who", like.getWho()), Expr.eq("whom", like.getWhom())),
                        Expr.and(Expr.eq("whom", like.getWho()), Expr.eq("who", like.getWhom())))
                .findList();

        boolean mutualDetected = likes.isEmpty();

        boolean alreadyFriends = Friendship.find.where()
                .or(Expr.and(Expr.eq("user1", like.getWho()), Expr.eq("user2", like.getWhom())),
                        Expr.and(Expr.eq("user2", like.getWho()), Expr.eq("user1", like.getWhom())))
                .findRowCount() > 0;


        ////ITS NEW FRIENDSHIP!!!!//


        if (!mutualDetected || alreadyFriends) return;

        Like likeToHim = like;
        List<Like> likesToMe = Like.find.where()
                .eq("result", Boolean.TRUE)
                .eq("whom", like.getWho())
                .eq("who", like.getWhom())
                .findList();

        if (likesToMe.isEmpty()) return;

        Like likeToMe = likesToMe.get(0);

        Friendship.find.where().or(Expr.eq("likeToUser2", like), Expr.eq("likeToUser1", like));

        Friendship friendship = new Friendship();
        friendship.setUser1(like.getWho());
        friendship.setUser2(like.getWhom());
        friendship.setLikeToUser2(likeToHim);
        friendship.setLikeToUser1(likeToMe);
        friendship.setUser1Delivered(false);
        friendship.setUser2Delivered(false);
        friendship.save();
        friendship.refresh();

        sendFrendshipToGcm(friendship);
    }

    public static Result updateLike() {
        StatusBuilder<Like> likeStatusBuilder = new StatusBuilder<Like>();

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

        StatusBuilder<List<Friendship>> friendshipsStatusBuilder = new StatusBuilder<List<Friendship>>();

        User user = User.find.byId(user_id);

        List<Friendship> friendships = Friendship.find.where()
                .or(Expr.eq("user1", user), Expr.eq("user2", user)).findList();

        return friendshipsStatusBuilder.getResponseStatus(friendships);
    }

    public static Result updateFriendship() {
        StatusBuilder<Friendship> settingsBuilder = new StatusBuilder<Friendship>();

        JsonNode body = request().body().asJson();

        Friendship friendshipJson = Json.fromJson(body, Friendship.class);

        Friendship friendship = Friendship.find.byId(friendshipJson.getID());

        friendship.update();

        if (friendship != null)
            return settingsBuilder.getResponseStatus(friendship);

        return settingsBuilder.getErrorStatus("Settings is not found..");
    }

    public static Result getFromFriendship(Long friendship_id) {

        Friendship friendship = Friendship.find.byId(friendship_id);

        List<Message> messageList = Message.find.where()
                .or(
                        Expr.and(Expr.eq("who", friendship.getUser1()),
                                Expr.eq("whom", friendship.getUser2())),
                        Expr.and(Expr.eq("whom", friendship.getUser1()),
                                Expr.eq("who", friendship.getUser2())))
                .findList();


        StatusBuilder messagesStatusBuilder = new StatusBuilder<List<Message>>();

        if (messageList != null)
            return messagesStatusBuilder.getResponseStatus(messageList);

        return messagesStatusBuilder.getErrorStatus("Location is not found.");
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result updateLocation(long user_id) {

        User user = User.find.byId(user_id);

        StatusBuilder<Location> locationStatusBuilder = new StatusBuilder<Location>();

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

        StatusBuilder<Settings> settingsBuilder = new StatusBuilder<Settings>();

        JsonNode body = request().body().asJson();

        Settings settings = Json.fromJson(body, Settings.class);

        //  settings.save();
//
        User user = User.find.byId(user_id);

        user.setSettings(settings);

        user.update();


        if (settings != null)
            return settingsBuilder.getResponseStatus(settings);

        return settingsBuilder.getErrorStatus("Settings is not found..");

    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getDisliked(long user_id) {
        User user = User.find.byId(user_id);


        StatusBuilder<List<Like>> likeStatusBuilder = new StatusBuilder<List<Like>>();

        if (user == null)
            likeStatusBuilder.getErrorStatus("User not found with id " + user_id);

        List<Like> list = Like.find.where()
                .eq("who", user)
                .eq("result", false)
                .setMaxRows(user != null && user.getVip_status() ? 10 : 1)
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

            return badRequest();
        }
    }
}