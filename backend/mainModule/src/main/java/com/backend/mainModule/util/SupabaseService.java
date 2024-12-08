package com.backend.mainModule.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.json.JSONObject;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class SupabaseService {
    Dotenv dotenv = Dotenv.configure().directory("backend/mainModule/src/main/java/com/backend/mainModule/.env").load();

    private static final String BASE_URL = "https://jbjebgnyyscabvtkvuzf.supabase.co/storage/v1/object";
    private  final String AUTH_TOKEN = dotenv.get("AUTH_TOKEN");
    private static final String BUCKET_NAME = "resumes";

    public String getUrl(String fileName) throws IOException, InterruptedException {
        String link = "https://jbjebgnyyscabvtkvuzf.supabase.co/storage/v1/object/sign/resumes/" + fileName;
        
        String jsonPayload = "{\"expiresIn\":1000000000}";
    
        HttpClient client = HttpClient.newHttpClient();
    
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(link))
                .header("Authorization", "Bearer " + AUTH_TOKEN)
                .header("Content-Type", "application/json")  // Content-Type must be application/json
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))  // Correct body publisher for JSON
                .build();
    
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
        // Handle the response
        if (response.statusCode() == 200 || response.statusCode() == 201) {
            // Parse the response to get the signed URL
            String responseBody = response.body();
            
            JSONObject jsonResponse = new JSONObject(responseBody);
            String signedUrlPath = jsonResponse.getString("signedURL");
    
            return signedUrlPath;  // Return only the signed URL path
        } else {
            System.out.println("Error Response: " + response.body());
            System.out.println("Status Code: " + response.statusCode());
            throw new IOException("Failed to get signed URL. Status Code: " + response.statusCode());
        }
    }
    

    public String uploadFile(MultipartFile file, String fileName) throws IOException, InterruptedException {
        String fullPath = BUCKET_NAME + "/" + fileName;
    
        HttpClient client = HttpClient.newHttpClient();
        System.out.println(file.getContentType());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + fullPath))
                .header("Authorization", "Bearer " + AUTH_TOKEN)
                .header("Content-Type", file.getContentType())
                .header("x-upsert", "true")  // This header is for upsert
                .POST(HttpRequest.BodyPublishers.ofByteArray(file.getBytes()))  // Use PUT for uploading the file
                .build();
    
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
        // Handle the response
        if (response.statusCode() == 200 || response.statusCode() == 201) {
            // After uploading, get the signed URL
            String signedUrlPath = getUrl(fileName);  // Get the signed URL path
            
            // Concatenate the base URL and the signed URL path
            String baseUrl = "https://jbjebgnyyscabvtkvuzf.supabase.co/storage/v1";
            return baseUrl + signedUrlPath;  // Return the full URL
        } else {
            System.out.println("Error Response: " + response.body());
            System.out.println("Status Code: " + response.statusCode());
            throw new IOException("Failed to upload file. Status Code: " + response.statusCode());
        }
    }
    
}
