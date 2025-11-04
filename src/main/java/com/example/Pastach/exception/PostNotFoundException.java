package com.example.Pastach.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(int id) {
        super("Post with id " + id + " not found" + id);
    }
}