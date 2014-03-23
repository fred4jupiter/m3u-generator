package de.fred4jupiter.m3u.generator;

import org.junit.Test;

import java.io.File;

public class DirectoryWalkerTest {

    @Test
    public void scanDir() {
        //final File baseDir = new File("m:\\Musik\\30SecondsToMars\\");
        final File baseDir = new File("d:/temp3");

        DirectoryWalker directoryWalker = new DirectoryWalker(baseDir);
        directoryWalker.registerListener(new DirectoryListener() {
            @Override
            public void onEnterDirectory(File dir, String relativeDirectoryPrefix, int depth) {
                System.out.println("dir=" + dir);
                System.out.println("relativeDirectoryPrefix=" + relativeDirectoryPrefix);
                System.out.println("depth=" + depth);
            }
        });

        directoryWalker.scanDir(baseDir);
    }
}
