package com.zerui.csproject.Model.Utils;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.*;

public class Firebase {
    public static Bucket bucket;
    public static Firestore db;
    public static FirebaseAuth auth;
    public static void initFirebase() throws IOException {
        FileInputStream refreshToken = new FileInputStream("csproj.json");
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
            return hashMap.get("displayName").toString();
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
    public static void createAccount(String name, String username, String password, String email, String profileUUID) throws FirebaseAuthException {
        Map<String, Object> docData = new HashMap<>();
        docData.put("name", name);
        docData.put("email", email);
        db.document("users/"+genHash(username, password)).set(docData);
        Map<String, Object> regArray = new HashMap<>();
        regArray.put("nameList", new ArrayList<>(List.of(username)));
        db.collection("registered").document("username").set(regArray, SetOptions.merge());
        UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setEmailVerified(false)
                .setUid(profileUUID)
                .setDisplayName(username);
        auth.createUser(createRequest);
        String link = auth.generateEmailVerificationLink(email);
        Mail.sendMessage("Verify your email signup using link!", link, email);
    }
    public static String getName(String userHash) {
        try {
            return db.collection("users").document(userHash).get().get().getString("name");
        } catch (Exception e) { return "Fatal Error"; }
    }
    public static String genHash(String username, String password)  {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update((username+password).getBytes());
            return new String(Base64.getEncoder().encode(messageDigest.digest()));
        } catch (Exception ignored) {}
        return "";
    }
    public static void uploadFile(File file, String path) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        Blob b = bucket.create(path, inputStream, Bucket.BlobWriteOption.userProject("cs-project-60a27"));
    }
    public static String genUUID() {
        return UUID.randomUUID().toString();
    }
    public static void loginAPI(String email, String password) {
        try {
            WebManager.sendPOST(email, password);
        } catch (IOException ignored) {}
    }
}
