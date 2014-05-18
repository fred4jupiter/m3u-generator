package de.fred4jupiter.m3u.generator;

import de.fred4jupiter.m3u.generator.playlisting.OneForAllPlaylistWriterStrategy;
import de.fred4jupiter.m3u.generator.playlisting.OneForEachDirectoryPlaylistWriterStrategy;
import de.fred4jupiter.m3u.generator.playlisting.PlaylistDirectoryListener;
import de.fred4jupiter.m3u.generator.playlisting.PlaylistWriterStrategy;
import de.fred4jupiter.m3u.generator.scanning.DefaultDirectoryWalker;
import de.fred4jupiter.m3u.generator.scanning.DirectoryWalker;
import de.fred4jupiter.m3u.generator.sorting.FileSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * PlaylistGenerator for M3U playlists.
 */
@Component
public class M3UPlaylistGenerator implements PlaylistGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(M3UPlaylistGenerator.class);

    @Autowired
    @Qualifier("trackNumberFileSorter")
    private FileSorter trackNumberSorter;

    @Autowired
    @Qualifier("fileNameFileSorter")
    private FileSorter fileNameSorter;

    @Override
    public void createOnePlaylistForAll(String baseDir, String playlistName, boolean sortByTrackNumber) throws IOException {
        final File baseDirFile = new File(baseDir);
        checkIfDirectoryExists(baseDirFile);

        DirectoryWalker directoryWalker = createDirectoryWalker(baseDirFile);

        FileSorter fileSorter = selectFileSorter(sortByTrackNumber);
        PlaylistWriterStrategy playlistWriterStrategy = new OneForAllPlaylistWriterStrategy(baseDirFile, playlistName);
        PlaylistDirectoryListener listener = new PlaylistDirectoryListener(fileSorter, playlistWriterStrategy);
        directoryWalker.registerListener(listener);

        directoryWalker.scanDir(baseDirFile);
    }

    private DirectoryWalker createDirectoryWalker(File baseDirFile) {
        return new DefaultDirectoryWalker(baseDirFile);
    }

    @Override
    public void createPlaylistsForEachDirectory(String baseDir, boolean sortByTrackNumber) throws IOException {
        final File baseDirFile = new File(baseDir);
        checkIfDirectoryExists(baseDirFile);

        File[] dirs = baseDirFile.listFiles(file -> file.isDirectory());

        FileSorter fileSorter = selectFileSorter(sortByTrackNumber);
        for (File dir : dirs) {
            DirectoryWalker directoryWalker = createDirectoryWalker(baseDirFile);
            PlaylistWriterStrategy playlistWriterStrategy = new OneForEachDirectoryPlaylistWriterStrategy(baseDirFile);
            PlaylistDirectoryListener listener = new PlaylistDirectoryListener(fileSorter, playlistWriterStrategy);
            directoryWalker.registerListener(listener);

            directoryWalker.scanDir(dir);
        }
    }

    private FileSorter selectFileSorter(boolean sortByTrackNumber) {
        FileSorter fileSorter = sortByTrackNumber ? trackNumberSorter : fileNameSorter;
        LOG.info("createPlaylist: using sorter: " + fileSorter.getClass().getSimpleName());
        return fileSorter;
    }

    private void checkIfDirectoryExists(File baseDirFile) throws IOException {
        if (!baseDirFile.exists()) {
            throw new PlaylistCreationException("Could not create playlist, because base directory=" + baseDirFile.getCanonicalFile() + " does not exists");
        }
    }
}
