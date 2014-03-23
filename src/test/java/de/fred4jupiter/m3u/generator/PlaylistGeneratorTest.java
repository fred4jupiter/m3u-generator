package de.fred4jupiter.m3u.generator;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class PlaylistGeneratorTest {

    private PlaylistGenerator playlistGenerator;

    @Before
    public void setup() {
        playlistGenerator = new M3UPlaylistGenerator();
    }

    @Test
    public void generateOnePlaylistForAll() throws IOException {
        // final String baseDir = "m:\\Musik\\30SecondsToMars\\";
        final String baseDir = "d:/temp3";
        playlistGenerator.generateOnePlaylistForAll(baseDir);
    }

    @Test
    public void generatePlaylistsForEachDirectory() throws IOException {
        final String baseDir = "m:\\Musik\\30SecondsToMars\\";
        //final String baseDir = "d:/temp3";
        playlistGenerator.generatePlaylistsForEachDirectory(baseDir);
    }
}
