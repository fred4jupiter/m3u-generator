package de.fred4jupiter.m3u.generator.playlisting;

import de.fred4jupiter.m3u.generator.Constants;
import de.fred4jupiter.m3u.generator.PlaylistCreationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;

public final class ContentToFileWriter {

    private static final Logger LOG = LoggerFactory.getLogger(ContentToFileWriter.class);

    private ContentToFileWriter() {
        // avoid instance creation
    }

    public static File writePlaylistToFile(String content, File baseDirFile, String playlistName) {
        Assert.notNull(playlistName, "playlistName must not be null");
        try {
            if (StringUtils.isBlank(content)) {
                LOG.debug("writePlaylistToFile: no content to write to playlist.");
                return null;
            }

            File file = new File(baseDirFile.getAbsolutePath() + File.separator + playlistName);
            LOG.info("writing playlist file: {}", file.getCanonicalFile());
            FileUtils.writeStringToFile(file, content, Constants.FILE_ENCODING);
            return file;
        } catch (IOException e) {
            throw new PlaylistCreationException(e.getMessage(), e);
        }
    }
}
