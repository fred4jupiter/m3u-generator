package de.fred4jupiter.m3u.generator.scanning;

import de.fred4jupiter.m3u.generator.playlisting.DirectoryListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Helper class traversing the directory tree.
 */
public class DefaultDirectoryWalker implements DirectoryWalker {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDirectoryWalker.class);

    private List<DirectoryListener> listeners = new ArrayList<>();
    private final File directoryWritingPlaylistTo;

    public DefaultDirectoryWalker(File directoryWritingPlaylistTo) {
        this.directoryWritingPlaylistTo = directoryWritingPlaylistTo;
    }

    @Override
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

        Collections.sort(Arrays.asList(directories), (fileOne, fileTwo) -> fileOne.getName().compareTo(fileTwo.getName()));

        for (File directory : directories) {
            if (directory.isDirectory()) {
                scanDir(baseDir, directory);
            }
        }

        notifiyOnScanFinished(baseDir);
    }

    private void notifiyOnScanFinished(File baseDir) {
        for (DirectoryListener listener : listeners) {
            listener.onScanFinished(baseDir);
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
