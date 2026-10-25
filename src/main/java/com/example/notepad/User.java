package com.example.notepad;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// One User = one record (document) in MongoDB collection "users"
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String username;
    private String fullName;
    private String email;
    private String password;   // stored encrypted, never plain text
    private String notes = ""; // the user's notepad text

    public String getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
