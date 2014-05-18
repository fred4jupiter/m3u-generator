package de.fred4jupiter.m3u.generator.playlisting;

import java.io.File;

public class OneForAllPlaylistWriterStrategy implements PlaylistWriterStrategy {

    private final File baseDirFile;
    private final String playlistName;

    public OneForAllPlaylistWriterStrategy(File baseDirFile, String playlistName) {
        this.baseDirFile = baseDirFile;
        this.playlistName = playlistName;
    }

    @Override
    public void afterDirectory(File dir, String content) {
        // nothing
    }

    @Override
    public void afterScanning(File baseDir, String content) {
        ContentToFileWriter.writePlaylistToFile(content, baseDirFile, playlistName);
    }
}
