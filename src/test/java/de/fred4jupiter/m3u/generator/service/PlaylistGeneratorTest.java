package de.fred4jupiter.m3u.generator.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.support.util.FileUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/spring-shell-plugin.xml")
public class PlaylistGeneratorTest {

    private static final String BASE_DIR = "src/test/resources/mp3DummyFolder";

    @Autowired
    private PlaylistGenerator playlistGenerator;

    @Before
    public void cleanup() {
        File[] files = Paths.get(BASE_DIR).toFile().listFiles(file -> file.isFile() && file.getName().endsWith(PlaylistFileType.M3U.getFileExtension()));
        for (File file : files) {
            file.delete();
        }
    }

    @Test
    public void generateOnePlaylistForAll() {
        GeneratorOptions generatorOptions = new GeneratorOptions(BASE_DIR, GeneratorOptions.PlaylistType.ONE_FOR_ALL);

        playlistGenerator.createPlaylist(generatorOptions);

        File generatedPlaylistFile = new File(generatorOptions.getTargetDir() + File.separator + generatorOptions.getPlaylistName() + PlaylistFileType.M3U.getFileExtension());
        assertThat(generatedPlaylistFile.exists(), equalTo(true));
        String playlistContent = FileUtils.read(generatedPlaylistFile);
        assertNotNull(playlistContent);
        assertThat(playlistContent, containsString("genesis" + File.separator + "Abacab" + File.separator + "song1.mp3"));
        assertThat(playlistContent, containsString("nirvana" + File.separator + "unplugged" + File.separator + "song2.mp3"));
    }

    @Test
    public void generatePlaylistsForEveryArtist() {
        GeneratorOptions generatorOptions = new GeneratorOptions(BASE_DIR, GeneratorOptions.PlaylistType.EVERY_ARTIST);

        playlistGenerator.createPlaylist(generatorOptions);

        checkPlaylist("genesis.m3u", "genesis" + File.separator + "Abacab" + File.separator + "song1.mp3");
        checkPlaylist("nirvana.m3u", "nirvana" + File.separator + "unplugged" + File.separator + "song2.mp3");
    }

    @Test
    public void generatePlaylistsForEveryArtistAndAlbum() {
        GeneratorOptions generatorOptions = new GeneratorOptions(BASE_DIR, GeneratorOptions.PlaylistType.EVERY_ARTIST_ALBUM);

        playlistGenerator.createPlaylist(generatorOptions);

        checkPlaylist("genesis - Abacab.m3u", "genesis" + File.separator + "Abacab" + File.separator + "song1.mp3");
        checkPlaylist("nirvana - unplugged.m3u", "nirvana" + File.separator + "unplugged" + File.separator + "song2.mp3");
    }

    private void checkPlaylist(String playlistName, String fileContent) {
        File playlistGenesis = new File(BASE_DIR + File.separator + playlistName);
        assertThat(playlistGenesis.exists(), equalTo(true));
        String playlistContent = FileUtils.read(playlistGenesis);
        assertNotNull(playlistContent);
        assertThat(playlistContent, containsString(fileContent));
    }
}
