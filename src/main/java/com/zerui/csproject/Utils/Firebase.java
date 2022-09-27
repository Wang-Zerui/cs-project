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
import com.zerui.csproject.Model.Comment;
import com.zerui.csproject.Model.Personal.AccountModel;
import com.zerui.csproject.Model.PostModel;
import com.zerui.csproject.SplashScreen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Firebase {
    public static Bucket bucket;
    public static Firestore db;
    public static FirebaseAuth auth;
    public static void initFirebase() throws IOException, URISyntaxException {
        File imageSel = new File(SplashScreen.class.getResource("csproj.json").toURI());
        FileInputStream refreshToken = new FileInputStream(imageSel);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .setStorageBucket("cs-project-60a27.appspot.com")
                .build();
        FirebaseApp.initializeApp(options);
        bucket = StorageClient.getInstance().bucket();
        db = FirestoreClient.getFirestore();
        auth = FirebaseAuth.getInstance(FirebaseApp.getInstance());
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
    public static String getName(String userHash) {
        try {
            return db.collection("users").document(userHash).get().get().getString("name");
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
    public static ArrayList<String> getPostImages(String uuid) {
        return null;
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
            return document.toObject(PostModel.class);
        } catch (Exception e) {System.out.println(e.getMessage()); return null;}
    }
    public static ArrayList<Comment> getComments(String postId) {
        try {
            ApiFuture<QuerySnapshot> future = db.collection("posts").document(postId).collection("comments").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            ArrayList<Comment> comments = new ArrayList<>();
            for (DocumentSnapshot document : documents) {
                comments.add(document.toObject(Comment.class));
            }
            return comments;
        } catch (Exception e) {System.out.println(e.getMessage()); return new ArrayList<>();}
    }
}
