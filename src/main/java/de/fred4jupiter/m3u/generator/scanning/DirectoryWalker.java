package de.fred4jupiter.m3u.generator.scanning;

import de.fred4jupiter.m3u.generator.playlisting.DirectoryListener;

import java.io.File;


public interface DirectoryWalker {

    void scanDir(final File baseDir);

    void registerListener(DirectoryListener directoryListener);
}
