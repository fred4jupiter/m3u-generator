package de.fred4jupiter.m3u.generator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/spring-shell-plugin.xml")
public class PlaylistGeneratorTest {

    private PlaylistGenerator playlistGenerator;

    @Before
    public void setup() {
        playlistGenerator = new M3UPlaylistGenerator();
    }

    @Test
    public void generateOnePlaylistForAll() throws IOException {
        playlistGenerator.createOnePlaylistForAll("e:/", "Playlist.m3u");
    }

    @Test
    public void generatePlaylistsForEachDirectory() throws IOException {
        //final String baseDir = "m:\\Musik\\30SecondsToMars\\";
        final String baseDir = "e:/";
        playlistGenerator.createPlaylistsForEachDirectory(baseDir);
    }
}
