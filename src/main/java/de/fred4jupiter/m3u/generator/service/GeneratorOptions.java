package de.fred4jupiter.m3u.generator.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Container for all available options in playlist creation.
 */
public class GeneratorOptions {

    private static final String DEFAULT_PLAYLIST_NAME = "Playlist";

    private final String baseDir;

    private final PlaylistType playlistType;

    private String playlistName = DEFAULT_PLAYLIST_NAME;

    private boolean sortByTrackNumber;

    private String targetDir;

    private PlaylistFileType playlistFileType = PlaylistFileType.M3U;

    public GeneratorOptions(String baseDir, PlaylistType playlistType) {
        this.baseDir = baseDir;
        this.playlistType = playlistType;
        this.targetDir = baseDir;
        this.sortByTrackNumber = false;
    }

    public PlaylistFileType getPlaylistFileType() {
        return playlistFileType;
    }

    public void setPlaylistFileType(PlaylistFileType playlistFileType) {
        this.playlistFileType = playlistFileType;
    }

    public PlaylistType getPlaylistType() {
        return playlistType;
    }

    public String getBaseDir() {
        return baseDir;
    }

    public Path getBaseDirPath() {
        final File baseDirFile = new File(getBaseDir());
        Path baseDirPath = Paths.get(baseDirFile.getAbsolutePath());
        return baseDirPath;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public boolean isSortByTrackNumber() {
        return sortByTrackNumber;
    }

    public void setSortByTrackNumber(boolean sortByTrackNumber) {
        this.sortByTrackNumber = sortByTrackNumber;
    }

    public String getTargetDir() {
        return targetDir;
    }

    public File getTargetDirAsFile() {
        return new File(targetDir);
    }

    public Path getTargetDirPath() {
        return Paths.get(getTargetDirAsFile().getAbsolutePath());
    }

    public void setTargetDir(String targetDir) {
        this.targetDir = targetDir;
    }

    public enum PlaylistType {
        ONE_FOR_ALL(0),

        EVERY_ARTIST(1),

        EVERY_ARTIST_ALBUM(2);

        private int type;

        private PlaylistType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }
}
