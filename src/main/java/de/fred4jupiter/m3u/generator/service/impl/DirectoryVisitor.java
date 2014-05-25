package de.fred4jupiter.m3u.generator.service.impl;

import de.fred4jupiter.m3u.generator.Constants;
import de.fred4jupiter.m3u.generator.service.GeneratorOptions;
import de.fred4jupiter.m3u.generator.sorting.FileSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;

public class DirectoryVisitor extends SimpleFileVisitor<Path> {

    private static final Logger LOG = LoggerFactory.getLogger(DirectoryVisitor.class);

    private final FileContentCollector fileContentCollector;
    private final GeneratorOptions generatorOptions;
    private final FileSorter fileSorter;

    public DirectoryVisitor(FileContentCollector fileContentCollector, GeneratorOptions generatorOptions, FileSorter fileSorter) {
        this.fileContentCollector = fileContentCollector;
        this.generatorOptions = generatorOptions;
        this.fileSorter = fileSorter;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        processFilesInDirectory(dir);
        return FileVisitResult.CONTINUE;
    }

    private void processFilesInDirectory(Path dir) {
        int currentLevel = calculateLevel(dir);
        LOG.debug("entering dir={}, level={}", dir, currentLevel);

        File[] files = dir.toFile().listFiles(file -> file.isFile() && file.getName().endsWith(Constants.MP3_FILE_EXTENSION));
        fileSorter.sortFiles(files);

        for (File file : files) {
            fileContentCollector.appendFile(file);
        }

        String playlistName = determinePlaylistName(dir, currentLevel);

        if (generatorOptions.getPlaylistLevel().getLevel() >= currentLevel) {
            fileContentCollector.writeContentToPlaylistFile(playlistName);
        }
    }

    private String determinePlaylistName(Path dir, int currentLevel) {
        String playlistName = null;

        if (currentLevel == GeneratorOptions.PlaylistLevel.EVERY_ARTIST_ALBUM.getLevel()) {
            String album = dir.getFileName().toString();
            String artist = dir.getParent().getFileName().toString();
            playlistName = artist + " - " + album;
        }

        if (currentLevel == GeneratorOptions.PlaylistLevel.EVERY_ARTIST.getLevel()) {
            playlistName = dir.getFileName().toString();
        }

        if (currentLevel == GeneratorOptions.PlaylistLevel.ONE_FOR_ALL.getLevel()) {
            playlistName = generatorOptions.getPlaylistName();
        }

        return playlistName;
    }

    private int calculateLevel(Path dir) {
        return dir.getNameCount() - generatorOptions.getBaseDirPath().getNameCount();
    }
}
