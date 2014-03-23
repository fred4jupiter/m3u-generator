package de.fred4jupiter.m3u.generator;

import java.io.IOException;

public interface PlaylistGenerator {

    void createOnePlaylistForAll(String baseDir, String playlistName) throws IOException;

    void createPlaylistsForEachDirectory(String baseDir) throws IOException;
}
