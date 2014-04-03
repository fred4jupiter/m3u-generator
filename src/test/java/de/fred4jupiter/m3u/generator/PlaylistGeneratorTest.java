package de.fred4jupiter.m3u.generator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.shell.support.util.FileUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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
        String baseDir = "src/test/resources/mp3DummyFolder";
        String playlistName = "Playlist.m3u";

        playlistGenerator.createOnePlaylistForAll(baseDir, playlistName);
        File generatedPlaylistFile = new File(baseDir + File.separator + playlistName);
        assertThat(generatedPlaylistFile.exists(), equalTo(true));
        String playlistContent = FileUtils.read(generatedPlaylistFile);
        assertNotNull(playlistContent);
        assertThat(playlistContent, containsString("genesis" + File.separator + "song1.mp3"));
        assertThat(playlistContent, containsString("nirvana" + File.separator + "song2.mp3"));
    }

    @Test
    public void generatePlaylistsForEachDirectory() throws IOException {
        //final String baseDir = "m:\\Musik\\30SecondsToMars\\";
        final String baseDir = "e:/";
        playlistGenerator.createPlaylistsForEachDirectory(baseDir);
    }
}
