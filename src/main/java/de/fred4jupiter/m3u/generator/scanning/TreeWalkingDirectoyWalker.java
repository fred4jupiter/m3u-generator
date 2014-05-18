package de.fred4jupiter.m3u.generator.scanning;

import de.fred4jupiter.m3u.generator.playlisting.DirectoryListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by michael on 16.05.2014.
 */
public class TreeWalkingDirectoyWalker implements DirectoryWalker {

    @Override
    public void scanDir(File baseDir) {
        Path baseDirPath = Paths.get(baseDir.getAbsolutePath());
        try {
            Files.walkFileTree(baseDirPath, new PrintingFileVisitor());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerListener(DirectoryListener directoryListener) {

    }
}
