package de.fred4jupiter.m3u.generator.playlisting;

import java.io.File;

/**
 * Listener callback on entering directories one by one.
 */
public interface DirectoryListener {

    /**
     * Will be called on entering a scanned directory.
     * @param dir directory scanned currently.
     * @param relativeDirectoryPrefix The part of the path relativ to the files located in this directory.
     * @param depth directoy depth beginning at index 0 on base dir.
     */
    void onEnterDirectory(File dir, String relativeDirectoryPrefix, int depth);
}
