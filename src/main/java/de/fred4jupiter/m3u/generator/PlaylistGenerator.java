package de.fred4jupiter.m3u.generator;

import java.io.IOException;

public interface PlaylistGenerator {

    void generateOnePlaylistForAll(String baseDir) throws IOException;

    void generatePlaylistsForEachDirectory(String baseDir) throws IOException;
}
