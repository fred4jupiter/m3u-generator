package de.fred4jupiter.m3u.generator;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class M3UPlaylistGenerator implements PlaylistGenerator {

    @Override
    public void generateOnePlaylistForAll(String baseDir) throws IOException {
        final File baseDirFile = new File(baseDir);
        writePlaylistFor(baseDirFile);
    }

    private void writePlaylistFor(File baseDirFile) throws IOException {
        DirectoryWalker directoryWalker = new DirectoryWalker(baseDirFile);
        OneFilePlaylistListener listener = new OneFilePlaylistListener();
        directoryWalker.registerListener(listener);
        directoryWalker.scanDir(baseDirFile);
        listener.writePlaylistToFile(baseDirFile);
    }

    @Override
    public void generatePlaylistsForEachDirectory(String baseDir) throws IOException {
        final File baseDirFile = new File(baseDir);

        File[] dirs = baseDirFile.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });

        for (File dir : dirs) {
            DirectoryWalker directoryWalker = new DirectoryWalker(baseDirFile);
            OneFilePlaylistListener listener = new OneFilePlaylistListener();
            directoryWalker.registerListener(listener);
            directoryWalker.scanDir(dir);
            listener.writePlaylistToFile(baseDirFile, dir.getName() + ".m3u");
        }
    }
}
