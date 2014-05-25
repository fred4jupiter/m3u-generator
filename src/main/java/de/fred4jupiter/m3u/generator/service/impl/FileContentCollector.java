package de.fred4jupiter.m3u.generator.service.impl;

import de.fred4jupiter.m3u.generator.PlaylistFileType;
import de.fred4jupiter.m3u.generator.service.GeneratorOptions;
import de.fred4jupiter.m3u.generator.service.PlaylistCreationException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileContentCollector {

    private static final Logger LOG = LoggerFactory.getLogger(FileContentCollector.class);

    private List<String> fileContent;
    private final GeneratorOptions generatorOptions;

    public FileContentCollector(GeneratorOptions generatorOptions) {
        this.generatorOptions = generatorOptions;
        init();
    }

    public void appendFile(File file) {
        Path targetDirPath = generatorOptions.getTargetDirPath();
        Path currentDirPath = Paths.get(file.getAbsolutePath());
        Path relativizedPathFileName = targetDirPath.relativize(currentDirPath);

        String relativePathFileName = relativizedPathFileName.toString();
        LOG.debug("appending file: {}", relativePathFileName);
        fileContent.add(relativePathFileName);
    }

    private void init() {
        fileContent = new ArrayList<>();
    }

    public void writeContentToPlaylistFile(String playlistName) {
        Assert.notNull(playlistName, "playlistName must not be null");
        if (CollectionUtils.isEmpty(fileContent)) {
            return;
        }

        try {
            final PlaylistFileType playlistFileType = generatorOptions.getPlaylistFileType();
            final String fileName = playlistName + playlistFileType.getFileExtension();
            final File completePlayListFileName = new File(generatorOptions.getTargetDirAsFile() + File.separator + fileName);

            LOG.info("writing playlist: {}", completePlayListFileName.getCanonicalFile());
            FileUtils.writeLines(completePlayListFileName, playlistFileType.getEncoding(), fileContent);
            init();
        } catch (IOException e) {
            throw new PlaylistCreationException(e.getMessage(), e);
        }
    }
}
