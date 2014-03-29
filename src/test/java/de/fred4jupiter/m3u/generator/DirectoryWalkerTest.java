package de.fred4jupiter.m3u.generator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class DirectoryWalkerTest {

    private static final Logger LOG = LoggerFactory.getLogger(DirectoryWalkerTest.class);

    @Test
    public void scanDir() {
        //final File baseDir = new File("m:\\Musik\\30SecondsToMars\\");
        final File baseDir = new File("d:/temp3");

        DirectoryWalker directoryWalker = new DirectoryWalker(baseDir);
        directoryWalker.registerListener((dir, relativeDirectoryPrefix, depth) -> {
            LOG.debug("dir={}, relativeDirectoryPrefix={}, depth={}", dir, relativeDirectoryPrefix, depth);
        });

        directoryWalker.scanDir(baseDir);
    }
}
