package de.fred4jupiter.m3u.generator;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class M3UPlaylistGenerator implements PlaylistGenerator {

    @Override
    public void createOnePlaylistForAll(String baseDir, String playlistName) throws IOException {
        final File baseDirFile = new File(baseDir);
        DirectoryWalker directoryWalker = new DirectoryWalker(baseDirFile);
        PlaylistDirectoryListener listener = new PlaylistDirectoryListener();
        directoryWalker.registerListener(listener);
        directoryWalker.scanDir(baseDirFile);
        listener.writePlaylistToFile(baseDirFile, playlistName);
    }

    @Override
    public void createPlaylistsForEachDirectory(String baseDir) throws IOException {
        final File baseDirFile = new File(baseDir);

        File[] dirs = baseDirFile.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });

        for (File dir : dirs) {
            DirectoryWalker directoryWalker = new DirectoryWalker(baseDirFile);
            PlaylistDirectoryListener listener = new PlaylistDirectoryListener();
            directoryWalker.registerListener(listener);
            directoryWalker.scanDir(dir);
            listener.writePlaylistToFile(baseDirFile, dir.getName() + ".m3u");
        }
    }
}
