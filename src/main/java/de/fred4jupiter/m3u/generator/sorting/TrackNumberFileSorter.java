package de.fred4jupiter.m3u.generator.sorting;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import de.fred4jupiter.m3u.generator.PlaylistCreationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * Sorts the files based on the MP3 ID tag track information if available (fallback on filename).
 */
@Component
@Qualifier("trackNumberFileSorter")
public class TrackNumberFileSorter implements FileSorter {

    private static final Logger LOG = LoggerFactory.getLogger(TrackNumberFileSorter.class);

    @Override
    public void sortFiles(File[] files) {
        Collections.sort(Arrays.asList(files), (fileOne, fileTwo) -> compareFiles(fileOne, fileTwo));
    }

    private int compareFiles(File fileOne, File fileTwo) {
        Mp3File mp3FileOne = createMp3File(fileOne);
        Mp3File mp3FileTwo = createMp3File(fileTwo);

        Integer trackNumberOne = getTracknumber(mp3FileOne);
        Integer trackNumberTwo = getTracknumber(mp3FileTwo);

        if (trackNumberOne == null && trackNumberTwo == null) {
            return 0;
        }

        if (trackNumberOne == null && trackNumberTwo != null) {
            return 1;
        }

        if (trackNumberOne != null && trackNumberTwo == null) {
            return -1;
        }

        return trackNumberOne.compareTo(trackNumberTwo);
    }

    private Mp3File createMp3File(File file) {
        try {
            return new Mp3File(file.getAbsolutePath());
        } catch (UnsupportedTagException | InvalidDataException | IOException e) {
            LOG.error(e.getMessage(), e);
            throw new PlaylistCreationException("Error getting absolute file path. filename=" + file.getName() + ", cause: " + e.getMessage(), e);
        }
    }

    private Integer getTracknumber(Mp3File mp3File) {
        try {
            if (mp3File.hasId3v2Tag() && StringUtils.isNotBlank(mp3File.getId3v2Tag().getTrack())) {
                return convertToTrackNumber(mp3File.getId3v2Tag().getTrack());
            }

            if (mp3File.hasId3v1Tag() && StringUtils.isNotBlank(mp3File.getId3v1Tag().getTrack())) {
                return convertToTrackNumber(mp3File.getId3v1Tag().getTrack());
            }
        } catch (NumberFormatException e) {
            LOG.error("Could not convert track number to integer value. fileName: {}, cause: {}", mp3File.getFilename(), e.getMessage());
        }

        return null;
    }

    private Integer convertToTrackNumber(String trackNumberString) {
        if (trackNumberString.contains("/")) {
            String[] splitted = trackNumberString.split("/");
            return Integer.valueOf(splitted[0]);
        }
        return Integer.valueOf(trackNumberString);
    }
}
