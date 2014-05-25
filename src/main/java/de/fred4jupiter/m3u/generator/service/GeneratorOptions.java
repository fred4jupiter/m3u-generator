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

    private final PlaylistLevel playlistLevel;

    private String playlistName = DEFAULT_PLAYLIST_NAME;

    private boolean sortByTrackNumber;

    private String targetDir;

    private PlaylistFileType playlistFileType = PlaylistFileType.M3U;

    public GeneratorOptions(String baseDir, PlaylistLevel playlistLevel) {
        this.baseDir = baseDir;
        this.playlistLevel = playlistLevel;
        this.targetDir = baseDir;
        this.sortByTrackNumber = false;
    }

    public PlaylistFileType getPlaylistFileType() {
        return playlistFileType;
    }

    public void setPlaylistFileType(PlaylistFileType playlistFileType) {
        this.playlistFileType = playlistFileType;
    }

    public PlaylistLevel getPlaylistLevel() {
        return playlistLevel;
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

    public enum PlaylistLevel {
        ONE_FOR_ALL(0),

        EVERY_ARTIST(1),

        EVERY_ARTIST_ALBUM(2);

        private int level;

        private PlaylistLevel(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
    }
}
