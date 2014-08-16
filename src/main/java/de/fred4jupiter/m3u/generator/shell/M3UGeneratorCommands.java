package de.fred4jupiter.m3u.generator.shell;

import de.fred4jupiter.m3u.generator.Constants;
import de.fred4jupiter.m3u.generator.service.GeneratorOptions;
import de.fred4jupiter.m3u.generator.service.PlaylistGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * Commands for the spring-shell.
 */
@Component
public class M3UGeneratorCommands implements CommandMarker {

    private static final Logger LOG = LoggerFactory.getLogger(M3UGeneratorCommands.class);

    @Autowired
    private PlaylistGenerator playlistGenerator;

    @CliCommand(value = "m3u oneForAll", help = "Generates one playlist for a given base directory")
    public void createOnePlaylistForAll(
            @CliOption(key = {"basedir"}, mandatory = true, help = "The directory where to scan files for the M3U playlist.") final String baseDir,
            @CliOption(key = {"playlistName"}, help = "The name of the playlist without extension.", specifiedDefaultValue = Constants.DEFAULT_PLAYLIST_NAME,
                    unspecifiedDefaultValue = Constants.DEFAULT_PLAYLIST_NAME) final String playlistName,
            @CliOption(key = {"sortByTrackNumber"}, help = "Sorts by ID-Tag track number if available.", specifiedDefaultValue = "true",
                    unspecifiedDefaultValue = "false") final Boolean sortByTrackNumber) {

        LOG.debug("baseDir={}, playlistName={}", baseDir, playlistName);
        GeneratorOptions generatorOptions = new GeneratorOptions(baseDir, GeneratorOptions.PlaylistType.ONE_FOR_ALL);
        generatorOptions.setPlaylistName(playlistName);
        generatorOptions.setSortByTrackNumber(sortByTrackNumber);
        createByOptions(generatorOptions);
    }

    @CliCommand(value = "m3u forEachArtist", help = "Generates playlists for each artist (first directory level).")
    public void createPlaylistsForArtist(
            @CliOption(key = {"basedir"}, mandatory = true, help = "The directory where to scan files for the M3U playlist.") final String baseDir,
            @CliOption(key = {"sortByTrackNumber"}, help = "Sorts by ID-Tag track number if available.",
                    specifiedDefaultValue = "true", unspecifiedDefaultValue = "false") final Boolean sortByTrackNumber) {
        LOG.debug("baseDir={}", baseDir);

        GeneratorOptions generatorOptions = new GeneratorOptions(baseDir, GeneratorOptions.PlaylistType.EVERY_ARTIST);
        generatorOptions.setSortByTrackNumber(sortByTrackNumber);
        createByOptions(generatorOptions);
    }

    @CliCommand(value = "m3u create", help = "Generates playlists for given type.")
    public void createPlaylist(
            @CliOption(key = {"basedir"}, mandatory = true, help = "The directory where to scan files for the M3U playlist.") final String baseDir,
            @CliOption(key = {"sortByTrackNumber"}, help = "Sorts by ID-Tag track number if available.",
                    specifiedDefaultValue = "true", unspecifiedDefaultValue = "false") final Boolean sortByTrackNumber,
            @CliOption(key = {"type"}, mandatory = true, help = "Select which kind of playlist you want to create") GeneratorOptions.PlaylistType type,
            @CliOption(key = {"targetDir"}, help = "Select the directory where to write the playlists into.") final String targetDir) {
        LOG.debug("baseDir={}", baseDir);
        LOG.debug("targetDir={}", targetDir);

        GeneratorOptions generatorOptions = new GeneratorOptions(baseDir, type);
        generatorOptions.setSortByTrackNumber(sortByTrackNumber);
        if (StringUtils.isNotBlank(targetDir)) {
            generatorOptions.setTargetDir(targetDir);
        }
        createByOptions(generatorOptions);
    }

    private void createByOptions(GeneratorOptions generatorOptions) {
        try {
            this.playlistGenerator.createPlaylist(generatorOptions);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
