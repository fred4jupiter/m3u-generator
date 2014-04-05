package de.fred4jupiter.m3u.generator;

import de.fred4jupiter.m3u.PlaylistCreationException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * PlaylistGenerator for M3U playlists.
 */
@Service
public class M3UPlaylistGenerator implements PlaylistGenerator {

    @Override
    public void createOnePlaylistForAll(String baseDir, String playlistName) throws IOException {
        final File baseDirFile = new File(baseDir);
        checkIfDirectoryExists(baseDirFile);

        DirectoryWalker directoryWalker = new DirectoryWalker(baseDirFile);
        PlaylistDirectoryListener listener = new PlaylistDirectoryListener();
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
            PlaylistDirectoryListener listener = new PlaylistDirectoryListener();
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
