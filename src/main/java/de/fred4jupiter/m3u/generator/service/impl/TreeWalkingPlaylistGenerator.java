package de.fred4jupiter.m3u.generator.service.impl;

import de.fred4jupiter.m3u.generator.service.GeneratorOptions;
import de.fred4jupiter.m3u.generator.service.PlaylistCreationException;
import de.fred4jupiter.m3u.generator.service.PlaylistGenerator;
import de.fred4jupiter.m3u.generator.sorting.FileSorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;

@Component
public class TreeWalkingPlaylistGenerator implements PlaylistGenerator {

    @Autowired
    @Qualifier("trackNumberFileSorter")
    private FileSorter trackNumberSorter;

    @Autowired
    @Qualifier("fileNameFileSorter")
    private FileSorter fileNameSorter;

    @Override
    public void createOnePlaylistForAll(String baseDir, String playlistName, boolean sortByTrackNumber) throws IOException {
        GeneratorOptions generatorOptions = new GeneratorOptions(baseDir, GeneratorOptions.PlaylistLevel.ONE_FOR_ALL);
        generatorOptions.setSortByTrackNumber(sortByTrackNumber);
        generatorOptions.setPlaylistName(playlistName);
        createPlaylist(generatorOptions);
    }

    @Override
    public void createOnePlaylistForAll(String baseDir, boolean sortByTrackNumber) throws IOException {
        GeneratorOptions generatorOptions = new GeneratorOptions(baseDir, GeneratorOptions.PlaylistLevel.ONE_FOR_ALL);
        generatorOptions.setSortByTrackNumber(sortByTrackNumber);
        createPlaylist(generatorOptions);
    }

    @Override
    public void createPlaylistsForEachDirectory(String baseDir, boolean sortByTrackNumber) throws IOException {
        GeneratorOptions generatorOptions = new GeneratorOptions(baseDir, GeneratorOptions.PlaylistLevel.EVERY_ARTIST_ALBUM);
        generatorOptions.setSortByTrackNumber(sortByTrackNumber);
        createPlaylist(generatorOptions);
    }

    public void createPlaylist(GeneratorOptions generatorOptions) {
        FileSorter fileSorter = selectFileSorter(generatorOptions);
        FileContentCollector fileContentCollector = new FileContentCollector(generatorOptions);
        try {
            Files.walkFileTree(generatorOptions.getBaseDirPath(), new DirectoryVisitor(fileContentCollector, generatorOptions, fileSorter));
        } catch (IOException e) {
            throw new PlaylistCreationException(e.getMessage(), e);
        }
    }

    private FileSorter selectFileSorter(GeneratorOptions generatorOptions) {
        return generatorOptions.isSortByTrackNumber() ? trackNumberSorter : fileNameSorter;
    }
}
