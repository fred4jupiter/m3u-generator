package de.fred4jupiter.m3u.generator.playlisting;

import de.fred4jupiter.m3u.generator.FileConstants;

import java.io.File;

public class OneForEachDirectoryPlaylistWriterStrategy implements PlaylistWriterStrategy {

    private final File baseDirFile;

    public OneForEachDirectoryPlaylistWriterStrategy(File baseDirFile) {
        this.baseDirFile = baseDirFile;
    }

    @Override
    public void afterDirectory(File dir, String content) {
        String playlistName = dir.getName() + FileConstants.M3U_PLAYLIST_FILE_EXTENSION;
        ContentToFileWriter.writePlaylistToFile(content, baseDirFile, playlistName);
    }

    @Override
    public void afterScanning(File baseDir, String content) {
        // nothing
    }
}
