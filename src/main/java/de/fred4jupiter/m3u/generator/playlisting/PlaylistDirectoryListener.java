package de.fred4jupiter.m3u.generator.playlisting;

import de.fred4jupiter.m3u.generator.FileConstants;
import de.fred4jupiter.m3u.generator.sorting.FileSorter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * Listener to produce the playlists based on scanned directories.
 */
public class PlaylistDirectoryListener implements DirectoryListener {

    private static final String LINE_FEED = "\n";

    private StringBuilder stringBuilder;

    private final FileSorter fileSorter;
    private final PlaylistWriterStrategy playlistWriterStrategy;

    public PlaylistDirectoryListener(FileSorter fileSorter, PlaylistWriterStrategy playlistWriterStrategy) {
        this.fileSorter = fileSorter;
        this.playlistWriterStrategy = playlistWriterStrategy;
        stringBuilder = new StringBuilder();
    }

    @Override
    public void onEnterDirectory(File dir, String relativeDirectoryPrefix, int depth) {
        File[] files = dir.listFiles(file -> file.isFile() && file.getName().endsWith(FileConstants.MP3_FILE_EXTENSION));

        fileSorter.sortFiles(files);

        for (File file : files) {
            if (StringUtils.isNotBlank(relativeDirectoryPrefix)) {
                stringBuilder.append(relativeDirectoryPrefix + File.separator + file.getName());
            } else {
                stringBuilder.append(dir.getName() + File.separator + file.getName());
            }

            stringBuilder.append(LINE_FEED);
        }

        playlistWriterStrategy.afterDirectory(dir, getContent());
    }

    @Override
    public void onScanFinished(File baseDir) {
        playlistWriterStrategy.afterScanning(baseDir, getContent());
    }

    private String getContent() {
        return stringBuilder.toString();
    }
}
