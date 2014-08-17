package de.fred4jupiter.m3u.generator;

/**
 * Exception throwing on playlist creation.
 */
public class PlaylistCreationException extends RuntimeException {

    public PlaylistCreationException(String message) {
        super(message);
    }

    public PlaylistCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
