package com.example.notepad;

import org.springframework.data.mongodb.repository.MongoRepository;

// Spring creates all the database code for us. We only declare what we need.
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
