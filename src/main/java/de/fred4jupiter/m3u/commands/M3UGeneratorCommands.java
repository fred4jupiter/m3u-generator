package de.fred4jupiter.m3u.commands;

import de.fred4jupiter.m3u.generator.PlaylistGenerator;
import org.apache.commons.lang3.StringUtils;
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

    public static final String DEFAULT_PLAYLIST_NAME = "PlayAllPlaylist.m3u";

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
            @CliOption(key = {"playlistName"}, mandatory = false, help = "The name of the playlist",
                    specifiedDefaultValue = DEFAULT_PLAYLIST_NAME) final String playlistName) {

        final String usedPlaylistName = StringUtils.isBlank(playlistName) ? DEFAULT_PLAYLIST_NAME : playlistName;

        LOG.debug("createOnePlaylistForAll: baseDir={}, playlistName={}", baseDir, usedPlaylistName);
        try {
            this.playlistGenerator.createOnePlaylistForAll(baseDir, usedPlaylistName);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @CliCommand(value = "m3u forEachDirLevelOne", help = "Generates playlists for each subdirectory one first level hierarchy")
    public void createPlaylistsForEachDirectory(@CliOption(key = {"basedir"},
            mandatory = true, help = "The directory where to scan files for the M3U playlist.") final String baseDir) {
        LOG.debug("createPlaylistsForEachDirectory: baseDir={}", baseDir);
        try {
            this.playlistGenerator.createPlaylistsForEachDirectory(baseDir);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
