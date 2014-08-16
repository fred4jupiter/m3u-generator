package de.fred4jupiter.m3u.generator.service;

import de.fred4jupiter.m3u.generator.AbstractShellIntegrationTest;
import de.fred4jupiter.m3u.generator.Constants;
import org.junit.Before;
import org.junit.Test;
import org.springframework.shell.core.CommandResult;
import org.springframework.shell.support.util.FileUtils;

import java.io.File;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

public class PlaylistGeneratorTest extends AbstractShellIntegrationTest {

    private static final String BASE_DIR = "src/test/resources/mp3DummyFolder";

    @Before
    public void cleanup() {
        File[] files = Paths.get(BASE_DIR).toFile().listFiles(file -> file.isFile() && file.getName().endsWith(PlaylistFileType.M3U.getFileExtension()));
        for (File file : files) {
            file.delete();
        }
    }

    @Test
    public void oneForAll() {
        CommandResult commandResult = getShell().executeCommand("m3u oneForAll --basedir " + BASE_DIR);
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess(), equalTo(true));

        File targetDir = new File(BASE_DIR);

        File generatedPlaylistFile = new File(targetDir + File.separator + Constants.DEFAULT_PLAYLIST_NAME + PlaylistFileType.M3U.getFileExtension());
        assertThat(generatedPlaylistFile.exists(), equalTo(true));
        String playlistContent = FileUtils.read(generatedPlaylistFile);
        assertNotNull(playlistContent);
        assertThat(playlistContent, containsString("genesis" + File.separator + "Abacab" + File.separator + "song1.mp3"));
        assertThat(playlistContent, containsString("nirvana" + File.separator + "unplugged" + File.separator + "song2.mp3"));
    }

    @Test
    public void oneForAllWithOtherPlaylistName() {
        CommandResult commandResult = getShell().executeCommand("m3u oneForAll --basedir " + BASE_DIR + " --playlistName MyList");
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess(), equalTo(true));

        File targetDir = new File(BASE_DIR);

        File generatedPlaylistFile = new File(targetDir + File.separator + "MyList.m3u");
        assertThat(generatedPlaylistFile.exists(), equalTo(true));
        String playlistContent = FileUtils.read(generatedPlaylistFile);
        assertNotNull(playlistContent);
        assertThat(playlistContent, containsString("genesis" + File.separator + "Abacab" + File.separator + "song1.mp3"));
        assertThat(playlistContent, containsString("nirvana" + File.separator + "unplugged" + File.separator + "song2.mp3"));
    }

    @Test
    public void oneForAllSortByTracknumber() {
        // tbd
    }

    @Test
    public void forEachArtist() {
        CommandResult commandResult = getShell().executeCommand("m3u forEachArtist --basedir " + BASE_DIR);
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess(), equalTo(true));

        checkPlaylist("genesis.m3u", "genesis" + File.separator + "Abacab" + File.separator + "song1.mp3");
        checkPlaylist("nirvana.m3u", "nirvana" + File.separator + "unplugged" + File.separator + "song2.mp3");
    }

    @Test
    public void createForEachArtistAndAlbum() {
        CommandResult commandResult = getShell().executeCommand("m3u create --basedir " + BASE_DIR);
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess(), equalTo(true));

        checkPlaylist("genesis - Abacab.m3u", "genesis" + File.separator + "Abacab" + File.separator + "song1.mp3");
        checkPlaylist("nirvana - unplugged.m3u", "nirvana" + File.separator + "unplugged" + File.separator + "song2.mp3");
    }

    @Test
    public void createForEachArtistAndAlbumWithType() {
        CommandResult commandResult = getShell().executeCommand("m3u create --type EVERY_ARTIST_ALBUM --basedir " + BASE_DIR);
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess(), equalTo(true));

        checkPlaylist("genesis - Abacab.m3u", "genesis" + File.separator + "Abacab" + File.separator + "song1.mp3");
        checkPlaylist("nirvana - unplugged.m3u", "nirvana" + File.separator + "unplugged" + File.separator + "song2.mp3");
    }

    @Test
    public void createForEachArtist() {
        CommandResult commandResult = getShell().executeCommand("m3u create --type EVERY_ARTIST --basedir " + BASE_DIR);
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess(), equalTo(true));

        checkPlaylist("genesis.m3u", "genesis" + File.separator + "Abacab" + File.separator + "song1.mp3");
        checkPlaylist("nirvana.m3u", "nirvana" + File.separator + "unplugged" + File.separator + "song2.mp3");
    }

    private void checkPlaylist(String playlistName, String fileContent) {
        File playlistGenesis = new File(BASE_DIR + File.separator + playlistName);
        assertThat(playlistGenesis.exists(), equalTo(true));
        String playlistContent = FileUtils.read(playlistGenesis);
        assertNotNull(playlistContent);
        assertThat(playlistContent, containsString(fileContent));
    }
}
