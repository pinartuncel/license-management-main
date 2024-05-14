package de.hse.gruppe8.exception;

public class NoUserFoundException extends RuntimeException {
    public NoUserFoundException(String username) {
        super(String.format("Login incorrect. No user with username \"%s\" found or password incorrect.", username));
    }
}