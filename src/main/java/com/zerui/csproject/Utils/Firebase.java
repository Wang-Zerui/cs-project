package com.zerui.csproject.Utils;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.database.GenericTypeIndicator;
import com.zerui.csproject.Model.*;
import com.zerui.csproject.Model.Personal.AccountModel;
import com.zerui.csproject.Model.FirebasePostModel;
import com.zerui.csproject.Model.Personal.User;
import com.zerui.csproject.SplashScreen;
import javafx.scene.layout.Pane;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Firebase {
    public static Bucket bucket;
    public static Firestore db;
    public static FirebaseAuth auth;
    public static void initFirebase() {
        try {
            InputStream inputStream = SplashScreen.class.getResourceAsStream("csproj.json");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .setStorageBucket("cs-project-60a27.appspot.com")
                    .build();
            FirebaseApp.initializeApp(options);
            bucket = StorageClient.getInstance().bucket();
            db = FirestoreClient.getFirestore();
            auth = FirebaseAuth.getInstance(FirebaseApp.getInstance());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static String login(String email, String password) {
        try {
            HashMap<String, Object> hashMap = WebManager.sendPOST(email, password);
            assert hashMap != null;
            return hashMap.get("localId").toString();
        } catch (Exception ignored) {
            return null;
        }
    }
    public static boolean userExists(String username) {
        try {
            ArrayList<String> arrayList = (ArrayList<String>) db.collection("registered").document("username").get().get().get("nameList");
            assert arrayList != null;
            return arrayList.contains(username);
        } catch (Exception ignored) {}
        return false;
    }
    public static void createAccount(String name, String username, String password, String email, String UUID, URL profileImageURL) throws FirebaseAuthException {
        UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setEmailVerified(false)
                .setUid(UUID)
                .setDisplayName(username)
                .setPhotoUrl(profileImageURL.toString());
        auth.createUser(createRequest);
        db.document("users/"+UUID).set(new AccountModel(name, username, "", UUID, profileImageURL.toString()));
        Map<String, Object> regArray = new HashMap<>();
        regArray.put("nameList", new ArrayList<>(List.of(username)));
        db.collection("registered").document("username").set(regArray, SetOptions.merge());
        sendVerificationEmail(email);
    }
    public static String getUsername(String uid) {
        try {
            return db.collection("users").document(uid).get().get().getString("username");
        } catch (Exception e) { return "Fatal Error"; }
    }
    public static URL uploadFile(File file, String path) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        Blob b = bucket.create(path, inputStream, Bucket.BlobWriteOption.userProject("cs-project-60a27"));
        return b.signUrl(3650, TimeUnit.DAYS);
    }
    public static String genUUID() {
        return UUID.randomUUID().toString();
    }
    public static boolean isVerified(String email) {
        try {
            return auth.getUserByEmail(email).isEmailVerified();
        } catch (FirebaseAuthException e) { return false; }
    }
    public static void sendVerificationEmail(String email) {
        try {
            Mail.sendMessage("Verify your email signup using link!", auth.generateEmailVerificationLink(email), email);
        } catch (FirebaseAuthException ignore) {}
    }
    public static boolean resetPassword(String email) {
        try {
            Mail.sendMessage("Reset your email with this link!", auth.generatePasswordResetLink(email), email);
            return true;
        } catch (FirebaseAuthException ignore) { return false; }
    }
    public static AccountModel getAccount(String uid) {
        try {
            ApiFuture<DocumentSnapshot> future = db.collection("users").document(uid).get();
            DocumentSnapshot document = future.get();
            return document.toObject(AccountModel.class);
        } catch (Exception e) {System.out.println(e.getMessage()); return null;}
    }
    public static PostModel getPost(String uid) {
        try {
            ApiFuture<DocumentSnapshot> future = db.collection("posts").document(uid).get();
            DocumentSnapshot document = future.get();
            return new PostModel(document.toObject(FirebasePostModel.class));
        } catch (Exception e) {System.out.println(e.getMessage()); return null;}
    }
    public static ArrayList<CommentModel> getComments(String postId) {
        try {
            ApiFuture<QuerySnapshot> future = db.collection("posts").document(postId).collection("comments").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            ArrayList<CommentModel> comments = new ArrayList<>();
            for (DocumentSnapshot document : documents) {
                comments.add(new CommentModel(document.toObject(FirebaseCommentModel.class)));
            }
            return comments;
        } catch (Exception e) {System.out.println(e.getMessage()); return new ArrayList<>();}
    }
    public static void createComment(String authorID, String message, String commentID, long timestamp, String postId) {
        FirebaseCommentModel commentModel = new FirebaseCommentModel(authorID, message, commentID, timestamp);
        db.collection("posts").document(postId).collection("comments").document(commentID).set(commentModel);
    }
    public static void createPost(ArrayList<File> selFile, String caption) throws IOException {
        AccountModel currUser = User.getAccount();
        String postID = Firebase.genUUID();
        ArrayList<String> imageLinks = new ArrayList<>();
        for (File file:selFile) {
            imageLinks.add(Firebase.uploadFile(file, String.format("posts/%s/%s", postID , file.getName())).toString());
        }
        FirebasePostModel model = new FirebasePostModel(postID, currUser.uuid, caption, imageLinks, Instant.now().getEpochSecond());
        Map<String, Object> docData = new HashMap<>();
        docData.put("postsArrayUid", Collections.singletonList(postID));
        db.collection("posts").document(postID).set(model);
        db.collection("users").document(User.getAccount().uuid).set(docData, SetOptions.merge());
    }
    public static String getProfileURL(String userUid) throws ExecutionException, InterruptedException {
        return db.collection("users").document(userUid).get().get().getString("profileLink");
    }
    public static ArrayList<String> loadPosts() throws ExecutionException, InterruptedException {
        CollectionReference posts = Firebase.db.collection("posts");
        Query firstPage = posts.orderBy("time");
        ArrayList<String> postID = new ArrayList<>();
        for (QueryDocumentSnapshot i:firstPage.get().get().getDocuments()) {
            postID.add(i.getId());
        }
        return postID;
    }
    public static void changeLikeStatus(Post p) throws ExecutionException, InterruptedException { // TODO Implement Like
        if (likedPost(p)) {
            db.collection("posts").document(p.id).update("likeUid", FieldValue.arrayRemove(p.authorID));
        } else {
            db.collection("posts").document(p.id).update("likeUid", FieldValue.arrayUnion(p.authorID));
        }
    }
    public static boolean likedPost(Post p) throws ExecutionException, InterruptedException {
        ArrayList<String> liked = (ArrayList<String>) db.collection("posts").document(p.id).get().get().get("likeUid");
        assert liked != null;
        return CollectionUtils.emptyIfNull(liked).contains(p.authorID);
    }
    public static int getNoLikes(Post p) {
        return p.likeUid.size();
    }
}