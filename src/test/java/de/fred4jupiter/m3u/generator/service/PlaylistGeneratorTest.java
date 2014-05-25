package de.fred4jupiter.m3u.generator.service;

import de.fred4jupiter.m3u.generator.PlaylistFileType;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.support.util.FileUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/spring-shell-plugin.xml")
public class PlaylistGeneratorTest {

    private static final String BASE_DIR = "src/test/resources/mp3DummyFolder";

    @Autowired
//    @Qualifier("m3UPlaylistGenerator")
    @Qualifier("treeWalkingPlaylistGenerator")
    private PlaylistGenerator playlistGenerator;

    @Test
    public void generateOnePlaylistForAll() throws IOException {
        final String playlistName = "Playlist";

        playlistGenerator.createOnePlaylistForAll(BASE_DIR, playlistName, false);
        File generatedPlaylistFile = new File(BASE_DIR + File.separator + playlistName + PlaylistFileType.M3U.getFileExtension());
        Assert.assertThat(generatedPlaylistFile.exists(), Matchers.equalTo(true));
        String playlistContent = FileUtils.read(generatedPlaylistFile);
        Assert.assertNotNull(playlistContent);
        Assert.assertThat(playlistContent, Matchers.containsString("genesis" + File.separator + "song1.mp3"));
        Assert.assertThat(playlistContent, Matchers.containsString("nirvana" + File.separator + "song2.mp3"));
    }

    @Test
    public void generatePlaylistsForEachDirectory() throws IOException {
        playlistGenerator.createPlaylistsForEachDirectory(BASE_DIR, false);

        checkPlaylist("genesis.m3u", "genesis" + File.separator + "song1.mp3");
        checkPlaylist("nirvana.m3u", "nirvana" + File.separator + "song2.mp3");
    }

    private void checkPlaylist(String playlistName, String fileContent) {
        File playlistGenesis = new File(BASE_DIR + File.separator + playlistName);
        Assert.assertThat(playlistGenesis.exists(), Matchers.equalTo(true));
        String playlistContent = FileUtils.read(playlistGenesis);
        Assert.assertNotNull(playlistContent);
        Assert.assertThat(playlistContent, Matchers.containsString(fileContent));
    }
}
