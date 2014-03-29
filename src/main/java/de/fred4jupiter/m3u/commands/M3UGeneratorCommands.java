package de.fred4jupiter.m3u.commands;

import de.fred4jupiter.m3u.generator.PlaylistGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class M3UGeneratorCommands implements CommandMarker {

    @Autowired
    private PlaylistGenerator playlistGenerator;

    @CliCommand(value = "m3u oneForAll", help = "Generates one playlist for a given base directory")
    public void createOnePlaylistForAll(
            @CliOption(key = {"basedir"}, mandatory = true, help = "The directory where to scan files for the M3U playlist.") final String baseDir,
            @CliOption(key = {"playlistName"}, mandatory = false, help = "The name of the playlist", specifiedDefaultValue = "Playlist.m3u") final String playlistName) {
        try {
            this.playlistGenerator.createOnePlaylistForAll(baseDir, playlistName);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
