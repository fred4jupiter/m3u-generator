package de.fred4jupiter.m3u.generator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String BASE_DIR = "src/test/resources/mp3DummyFolder";

    @Autowired
    private PlaylistGenerator playlistGenerator;

    @Test
    public void generateOnePlaylistForAll() throws IOException {
        final String playlistName = "Playlist";

        playlistGenerator.createOnePlaylistForAll(BASE_DIR, playlistName, false);
        File generatedPlaylistFile = new File(BASE_DIR + File.separator + playlistName + PlaylistFile.M3U.getFileExtension());
        assertThat(generatedPlaylistFile.exists(), equalTo(true));
        String playlistContent = FileUtils.read(generatedPlaylistFile);
        assertNotNull(playlistContent);
        assertThat(playlistContent, containsString("genesis" + File.separator + "song1.mp3"));
        assertThat(playlistContent, containsString("nirvana" + File.separator + "song2.mp3"));
    }

    @Test
    public void generatePlaylistsForEachDirectory() throws IOException {
        playlistGenerator.createPlaylistsForEachDirectory(BASE_DIR, false);

        checkPlaylist("genesis.m3u", "genesis" + File.separator + "song1.mp3");
        checkPlaylist("nirvana.m3u", "nirvana" + File.separator + "song2.mp3");
    }

    private void checkPlaylist(String playlistName, String fileContent) {
        File playlistGenesis = new File(BASE_DIR + File.separator + playlistName);
        assertThat(playlistGenesis.exists(), equalTo(true));
        String playlistContent = FileUtils.read(playlistGenesis);
        assertNotNull(playlistContent);
        assertThat(playlistContent, containsString(fileContent));
    }
}
