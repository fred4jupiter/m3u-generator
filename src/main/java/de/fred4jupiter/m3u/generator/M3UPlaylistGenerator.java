package de.fred4jupiter.m3u.generator;

import de.fred4jupiter.m3u.PlaylistCreationException;
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

        createPlaylist(playlistName, sortByTrackNumber, baseDirFile);
    }

    private void createPlaylist(String playlistName, boolean sortByTrackNumber, File baseDirFile) throws IOException {
        DirectoryWalker directoryWalker = new DirectoryWalker(baseDirFile);
        FileSorter fileSorter = sortByTrackNumber ? trackNumberSorter : fileNameSorter;
        LOG.info("createPlaylist: using sorter: " + fileSorter.getClass().getName());
        PlaylistDirectoryListener listener = new PlaylistDirectoryListener(fileSorter);
        directoryWalker.registerListener(listener);
        directoryWalker.scanDir(baseDirFile);
        listener.writePlaylistToFile(baseDirFile, playlistName);
    }

    @Override
    public void createPlaylistsForEachDirectory(String baseDir, boolean sortByTrackNumber) throws IOException {
        final File baseDirFile = new File(baseDir);
        checkIfDirectoryExists(baseDirFile);

        File[] dirs = baseDirFile.listFiles(file -> file.isDirectory());

        for (File dir : dirs) {
            String playlistName = dir.getName() + FileConstants.M3U_PLAYLIST_FILE_EXTENSION;
            createPlaylist(playlistName, sortByTrackNumber, baseDirFile);
        }
    }

    private void checkIfDirectoryExists(File baseDirFile) throws IOException {
        if (!baseDirFile.exists()) {
            throw new PlaylistCreationException("Could not create playlist, because base directory=" + baseDirFile.getCanonicalFile() + " does not exists");
        }
    }
}
