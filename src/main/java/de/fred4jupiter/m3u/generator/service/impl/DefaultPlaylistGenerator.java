package de.fred4jupiter.m3u.generator.service.impl;

import de.fred4jupiter.m3u.generator.service.GeneratorOptions;
import de.fred4jupiter.m3u.generator.PlaylistCreationException;
import de.fred4jupiter.m3u.generator.service.PlaylistGenerator;
import de.fred4jupiter.m3u.generator.sorting.FileSorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;

@Component
public class DefaultPlaylistGenerator implements PlaylistGenerator {

    @Autowired
    @Qualifier("trackNumberFileSorter")
    private FileSorter trackNumberSorter;

    @Autowired
    @Qualifier("fileNameFileSorter")
    private FileSorter fileNameSorter;

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
