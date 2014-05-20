package de.fred4jupiter.m3u.generator.playlisting;

import de.fred4jupiter.m3u.generator.Constants;

import java.io.File;

public class OneForEachDirectoryPlaylistWriterStrategy implements PlaylistWriterStrategy {

    private final File baseDirFile;

    public OneForEachDirectoryPlaylistWriterStrategy(File baseDirFile) {
        this.baseDirFile = baseDirFile;
    }

    @Override
    public void afterDirectory(File dir, String content) {
        ContentToFileWriter.writePlaylistToFile(content, baseDirFile, dir.getName());
    }

    @Override
    public void afterScanning(File baseDir, String content) {
        // nothing
    }
}
