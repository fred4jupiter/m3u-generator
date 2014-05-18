package de.fred4jupiter.m3u.generator;

import java.io.IOException;

/**
 * Playlist generator.
 */
public interface PlaylistGenerator {

    /**
     * Creates one playlist for all MP3 files found in given directory.
     *
     * @param baseDir      The base directoy path. All subdirectories will be scanned for MP3 files.
     * @param playlistName name of the playlist that will be generated.
     * @throws IOException in case the path does not exists.
     */
    void createOnePlaylistForAll(String baseDir, String playlistName, boolean sortByTrackNumber) throws IOException;

    /**
     * Creates one playlist for all MP3 files found in given directory.
     *
     * @param baseDir The base directoy path. All subdirectories will be scanned for MP3 files.
     * @throws IOException in case the path does not exists.
     */
    void createOnePlaylistForAll(String baseDir, boolean sortByTrackNumber) throws IOException;

    /**
     * Generates playlists of each direct descendant of given baseDir.
     *
     * @param baseDir The base directoy path. All subdirectories will be scanned for MP3 files.
     * @throws IOException in case the path does not exists.
     */
    void createPlaylistsForEachDirectory(String baseDir, boolean sortByTrackNumber) throws IOException;
}
