package de.fred4jupiter.m3u.generator;

import de.fred4jupiter.m3u.generator.sorting.FileSorter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;

/**
 * Listener to produce the playlists based on scanned directories.
 */
public class PlaylistDirectoryListener implements DirectoryListener {

    private static final Logger LOG = LoggerFactory.getLogger(PlaylistDirectoryListener.class);

    private static final String LINE_FEED = "\n";

    private StringBuilder stringBuilder;

    private final FileSorter fileSorter;

    public PlaylistDirectoryListener(FileSorter fileSorter) {
        this.fileSorter = fileSorter;
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
    }

    public String getContent() throws IOException {
        return stringBuilder.toString();
    }

    public File writePlaylistToFile(File baseDirFile, String playlistName) throws IOException {
        Assert.notNull(playlistName, "playlistName must not be null");
        final String content = getContent();
        if (StringUtils.isBlank(content)) {
            LOG.debug("writePlaylistToFile: no content to write to playlist.");
            return null;
        }

        File file = new File(baseDirFile.getAbsolutePath() + File.separator + playlistName);
        LOG.info("writing playlist file: {}", file.getCanonicalFile());
        FileUtils.writeStringToFile(file, content);
        return file;
    }
}
