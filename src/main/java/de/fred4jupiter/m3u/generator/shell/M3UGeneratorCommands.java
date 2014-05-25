package de.fred4jupiter.m3u.generator.shell;

import de.fred4jupiter.m3u.generator.Constants;
import de.fred4jupiter.m3u.generator.service.GeneratorOptions;
import de.fred4jupiter.m3u.generator.service.PlaylistGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Commands for the spring-shell.
 */
@Component
public class M3UGeneratorCommands implements CommandMarker {

    private static final Logger LOG = LoggerFactory.getLogger(M3UGeneratorCommands.class);

    @Autowired
    private PlaylistGenerator playlistGenerator;

    @CliAvailabilityIndicator({"m3u oneForAll", "m3u forEachDirLevelOne"})
    public boolean isPlaylistCommandsAvailable() {
        //always available
        return true;
    }

    @CliCommand(value = "m3u oneForAll", help = "Generates one playlist for a given base directory")
    public void createOnePlaylistForAll(
            @CliOption(key = {"basedir"}, mandatory = true, help = "The directory where to scan files for the M3U playlist.") final String baseDir,
            @CliOption(key = {"playlistName"}, help = "The name of the playlist.", specifiedDefaultValue = Constants.DEFAULT_PLAYLIST_NAME,
                    unspecifiedDefaultValue = Constants.DEFAULT_PLAYLIST_NAME) final String playlistName,
            @CliOption(key = {"sortByTrackNumber"}, help = "Sorts by ID-Tag track number if available.", specifiedDefaultValue = "true",
                    unspecifiedDefaultValue = "false") final Boolean sortByTrackNumber) {

        LOG.debug("createOnePlaylistForAll: baseDir={}, playlistName={}", baseDir, playlistName);
        GeneratorOptions generatorOptions = new GeneratorOptions(baseDir, GeneratorOptions.PlaylistLevel.ONE_FOR_ALL);
        generatorOptions.setPlaylistName(playlistName);
        generatorOptions.setSortByTrackNumber(sortByTrackNumber);

        try {
            this.playlistGenerator.createPlaylist(generatorOptions);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @CliCommand(value = "m3u forEachDirLevelOne", help = "Generates playlists for each subdirectory one first level hierarchy")
    public void createPlaylistsForEachDirectory(
            @CliOption(key = {"basedir"}, mandatory = true, help = "The directory where to scan files for the M3U playlist.") final String baseDir,
            @CliOption(key = {"sortByTrackNumber"}, help = "Sorts by ID-Tag track number if available.",
                    specifiedDefaultValue = "true", unspecifiedDefaultValue = "false") final Boolean sortByTrackNumber) {
        LOG.debug("createPlaylistsForEachDirectory: baseDir={}", baseDir);

        GeneratorOptions generatorOptions = new GeneratorOptions(baseDir, GeneratorOptions.PlaylistLevel.EVERY_ARTIST_ALBUM);
        generatorOptions.setSortByTrackNumber(sortByTrackNumber);

        try {
            this.playlistGenerator.createPlaylist(generatorOptions);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
