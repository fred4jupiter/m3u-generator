package de.fred4jupiter.m3u.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DirectoryWalker {

    private static final Logger LOG = LoggerFactory.getLogger(DirectoryWalker.class);

    private List<DirectoryListener> listeners = new ArrayList<>();
    private final File directoryWritingPlaylistTo;

    public DirectoryWalker(File directoryWritingPlaylistTo) {
        this.directoryWritingPlaylistTo = directoryWritingPlaylistTo;
    }

    public void scanDir(final File baseDir) {
        scanDir(baseDir, baseDir);
    }

    private void scanDir(final File baseDir, final File dir) {
        Path baseDirPath = Paths.get(baseDir.getAbsolutePath());
        Path dirPath = Paths.get(dir.getAbsolutePath());
        int depth = dirPath.getNameCount() - baseDirPath.getNameCount();

        LOG.debug("current dir: '" + dir + "', depth: " + depth);

        String relativePath = calculateRelativePath(dirPath, depth);
        notifyOnEnteringDirectory(dir, relativePath, depth);

        File[] directories = dir.listFiles(file -> file.isDirectory());

        SortingUtil.sortFilesByName(directories);
        for (File directory : directories) {
            if (directory.isDirectory()) {
                scanDir(baseDir, directory);
            }
        }
    }

    private String calculateRelativePath(Path dirPath, int depth) {
        if (depth == 0) {
            return "";
        }
        Path playlistLocationPath = Paths.get(directoryWritingPlaylistTo.getAbsolutePath());

        Path relativePath = dirPath.subpath(playlistLocationPath.getNameCount(), dirPath.getNameCount());
        return relativePath.toString();
    }

    private void notifyOnEnteringDirectory(File dir, String relativeDirectoryPrefix, int depth) {
        for (DirectoryListener listener : listeners) {
            listener.onEnterDirectory(dir, relativeDirectoryPrefix, depth);
        }
    }

    public void registerListener(DirectoryListener directoryListener) {
        this.listeners.add(directoryListener);
    }
}
