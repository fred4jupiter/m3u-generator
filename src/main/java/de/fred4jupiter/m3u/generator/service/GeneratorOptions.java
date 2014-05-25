package de.fred4jupiter.m3u.generator.service;

import de.fred4jupiter.m3u.generator.PlaylistFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by michael on 25.05.2014.
 */
public class GeneratorOptions {

    private static final String DEFAULT_PLAYLIST_NAME = "Playlist";

    private String baseDir;

    private int level;

    private String playlistName = DEFAULT_PLAYLIST_NAME;

    private boolean sortByTrackNumber;

    private String targetDir;

    private PlaylistFile playlistFile = PlaylistFile.M3U;

    public GeneratorOptions(String baseDir, int level) {
        this.baseDir = baseDir;
        this.level = level;
        this.targetDir = baseDir;
        this.sortByTrackNumber = false;
    }

    public PlaylistFile getPlaylistFile() {
        return playlistFile;
    }

    public void setPlaylistFile(PlaylistFile playlistFile) {
        this.playlistFile = playlistFile;
    }

    public int getLevel() {
        return level;
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
}
