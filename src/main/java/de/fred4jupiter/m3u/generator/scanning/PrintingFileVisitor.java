package de.fred4jupiter.m3u.generator.scanning;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;

/**
 * Created by michael on 16.05.2014.
 */
public class PrintingFileVisitor extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.format("Directory: %s%n", dir);
        return FileVisitResult.CONTINUE;
    }
}
