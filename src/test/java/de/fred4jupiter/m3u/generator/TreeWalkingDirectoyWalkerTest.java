package de.fred4jupiter.m3u.generator;

import de.fred4jupiter.m3u.generator.scanning.TreeWalkingDirectoyWalker;
import org.junit.Test;

import java.io.File;

public class TreeWalkingDirectoyWalkerTest {

    @Test
    public void scanDir() {
        final String baseDir = "d:/Temp2";
        TreeWalkingDirectoyWalker walker = new TreeWalkingDirectoyWalker();
        walker.scanDir(new File(baseDir));
    }
}
