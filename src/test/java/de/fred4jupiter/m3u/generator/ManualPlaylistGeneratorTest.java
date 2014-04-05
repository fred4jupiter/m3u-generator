package de.fred4jupiter.m3u.generator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/spring-shell-plugin.xml")
public class ManualPlaylistGeneratorTest {

    @Autowired
    private PlaylistGenerator playlistGenerator;

    @Test
    public void generateOnePlaylistForAll() throws IOException {
        final String baseDir = "m:\\Musik\\30SecondsToMars\\";
        final String playlistName = "Playlist.m3u";

        playlistGenerator.createOnePlaylistForAll(baseDir, playlistName);
    }
}
