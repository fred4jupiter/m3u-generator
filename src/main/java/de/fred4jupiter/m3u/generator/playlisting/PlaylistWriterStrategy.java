package de.fred4jupiter.m3u.generator.playlisting;

import java.io.File;

/**
 * Created by michael on 18.05.2014.
 */
public interface PlaylistWriterStrategy {

    void afterDirectory(File dir, String content);

    void afterScanning(File baseDir, String content);
}
