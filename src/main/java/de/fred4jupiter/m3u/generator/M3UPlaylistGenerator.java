package de.fred4jupiter.m3u.generator;

import de.fred4jupiter.m3u.PlaylistCreationException;
import de.fred4jupiter.m3u.generator.sorting.FileSorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * PlaylistGenerator for M3U playlists.
 */
@Component
public class M3UPlaylistGenerator implements PlaylistGenerator {

    @Autowired
    @Qualifier("trackNumberFileSorter")
    private FileSorter fileSorter;

    @Override
    public void createOnePlaylistForAll(String baseDir, String playlistName) throws IOException {
        final File baseDirFile = new File(baseDir);
        checkIfDirectoryExists(baseDirFile);

        DirectoryWalker directoryWalker = new DirectoryWalker(baseDirFile);
        PlaylistDirectoryListener listener = new PlaylistDirectoryListener(fileSorter);
        directoryWalker.registerListener(listener);
        directoryWalker.scanDir(baseDirFile);
        listener.writePlaylistToFile(baseDirFile, playlistName);
    }

    @Override
    public void createPlaylistsForEachDirectory(String baseDir) throws IOException {
        final File baseDirFile = new File(baseDir);
        checkIfDirectoryExists(baseDirFile);

        File[] dirs = baseDirFile.listFiles(file -> file.isDirectory());

        for (File dir : dirs) {
            DirectoryWalker directoryWalker = new DirectoryWalker(baseDirFile);
            PlaylistDirectoryListener listener = new PlaylistDirectoryListener(fileSorter);
            directoryWalker.registerListener(listener);
            directoryWalker.scanDir(dir);
            listener.writePlaylistToFile(baseDirFile, dir.getName() + FileConstants.M3U_PLAYLIST_FILE_EXTENSION);
        }
    }

    private void checkIfDirectoryExists(File baseDirFile) throws IOException {
        if (!baseDirFile.exists()) {
            throw new PlaylistCreationException("Could not create playlist, because base directory=" + baseDirFile.getCanonicalFile() + " does not exists");
        }
    }
}
