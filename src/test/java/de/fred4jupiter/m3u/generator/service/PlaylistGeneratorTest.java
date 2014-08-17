package de.fred4jupiter.m3u.generator.service;

import de.fred4jupiter.m3u.generator.AbstractShellIntegrationTest;
import de.fred4jupiter.m3u.generator.Constants;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.shell.core.CommandResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

public class PlaylistGeneratorTest extends AbstractShellIntegrationTest {

    private static final String GENESIS_ANOTHER_RECORD_FILE_NAME = "Another Record - Genesis.mp3";
    private static final String GENESIS_KEEP_IT_DARKER_FILE_NAME = "Keep it darker - Genesis.mp3";

    private static final String NIRVANA_COME_AS_YOU_ARE_FILE_NAME = "Come as you are - Nirvana.mp3";
    private static final String NIRVANA_ON_A_PLAIN_FILE_NAME = "On a plain - Nirvana.mp3";

    private static final String BASE_DIR = "src/test/resources/mp3DummyFolder";

    @Before
    public void cleanup() {
        File[] files = Paths.get(BASE_DIR).toFile().listFiles(file -> file.isFile() && file.getName().endsWith(PlaylistFileType.M3U.getFileExtension()));
        for (File file : files) {
            file.delete();
        }
    }

    @Test
    public void oneForAll() throws IOException {
        CommandResult commandResult = getShell().executeCommand("m3u oneForAll --basedir " + BASE_DIR);
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess(), equalTo(true));

        File targetDir = new File(BASE_DIR);

        File generatedPlaylistFile = new File(targetDir + File.separator + Constants.DEFAULT_PLAYLIST_NAME + PlaylistFileType.M3U.getFileExtension());
        assertThat(generatedPlaylistFile.exists(), equalTo(true));
        List<String> fileContent = IOUtils.readLines(new FileInputStream(generatedPlaylistFile));
        assertNotNull(fileContent);
        assertThat(fileContent.size(), equalTo(4));
        // sorting by filename
        assertThat(fileContent.get(0), containsString(getPathFor(GENESIS_ANOTHER_RECORD_FILE_NAME)));
        assertThat(fileContent.get(1), containsString(getPathFor(GENESIS_KEEP_IT_DARKER_FILE_NAME)));
        assertThat(fileContent.get(2), containsString(getPathFor(NIRVANA_COME_AS_YOU_ARE_FILE_NAME)));
        assertThat(fileContent.get(3), containsString(getPathFor(NIRVANA_ON_A_PLAIN_FILE_NAME)));
    }

    private String getPathFor(String fileName) {
        if (fileName.contains("Genesis")) {
            return "genesis" + File.separator + "Abacab" + File.separator + fileName;
        } else {
            return "nirvana" + File.separator + "unplugged" + File.separator + fileName;
        }
    }

    @Test
    public void oneForAllWithOtherPlaylistName() throws IOException {
        CommandResult commandResult = getShell().executeCommand("m3u oneForAll --basedir " + BASE_DIR + " --playlistName MyList");
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess(), equalTo(true));

        File targetDir = new File(BASE_DIR);

        File generatedPlaylistFile = new File(targetDir + File.separator + "MyList.m3u");
        assertThat(generatedPlaylistFile.exists(), equalTo(true));
        List<String> fileContent = IOUtils.readLines(new FileInputStream(generatedPlaylistFile));
        assertNotNull(fileContent);
        assertThat(fileContent.size(), equalTo(4));
        // sorting by filename
        assertThat(fileContent.get(0), containsString(getPathFor(GENESIS_ANOTHER_RECORD_FILE_NAME)));
        assertThat(fileContent.get(1), containsString(getPathFor(GENESIS_KEEP_IT_DARKER_FILE_NAME)));
        assertThat(fileContent.get(2), containsString(getPathFor(NIRVANA_COME_AS_YOU_ARE_FILE_NAME)));
        assertThat(fileContent.get(3), containsString(getPathFor(NIRVANA_ON_A_PLAIN_FILE_NAME)));
    }

    @Test
    public void oneForAllSortByTracknumber() throws IOException {
        CommandResult commandResult = getShell().executeCommand("m3u oneForAll --basedir " + BASE_DIR + " --sortByTrackNumber");
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess(), equalTo(true));

        File targetDir = new File(BASE_DIR);

        File generatedPlaylistFile = new File(targetDir + File.separator + Constants.DEFAULT_PLAYLIST_NAME + PlaylistFileType.M3U.getFileExtension());
        assertThat(generatedPlaylistFile.exists(), equalTo(true));
        List<String> fileContent = IOUtils.readLines(new FileInputStream(generatedPlaylistFile));
        assertNotNull(fileContent);
        assertThat(fileContent.size(), equalTo(4));
        // sorting by filename
        assertThat(fileContent.get(0), containsString(getPathFor(GENESIS_KEEP_IT_DARKER_FILE_NAME)));
        assertThat(fileContent.get(1), containsString(getPathFor(GENESIS_ANOTHER_RECORD_FILE_NAME)));
        assertThat(fileContent.get(2), containsString(getPathFor(NIRVANA_ON_A_PLAIN_FILE_NAME)));
        assertThat(fileContent.get(3), containsString(getPathFor(NIRVANA_COME_AS_YOU_ARE_FILE_NAME)));
    }

    @Test
    public void forEachArtist() throws IOException {
        CommandResult commandResult = getShell().executeCommand("m3u forEachArtist --basedir " + BASE_DIR);
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess(), equalTo(true));

        checkPlaylist("genesis.m3u", GENESIS_ANOTHER_RECORD_FILE_NAME, GENESIS_KEEP_IT_DARKER_FILE_NAME);
        checkPlaylist("nirvana.m3u", NIRVANA_COME_AS_YOU_ARE_FILE_NAME, NIRVANA_ON_A_PLAIN_FILE_NAME);
    }

    @Test
    public void createForEachArtistAndAlbum() throws IOException {
        CommandResult commandResult = getShell().executeCommand("m3u create --basedir " + BASE_DIR);
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess(), equalTo(true));

        checkPlaylist("genesis.m3u", GENESIS_ANOTHER_RECORD_FILE_NAME, GENESIS_KEEP_IT_DARKER_FILE_NAME);
        checkPlaylist("nirvana.m3u", NIRVANA_COME_AS_YOU_ARE_FILE_NAME, NIRVANA_ON_A_PLAIN_FILE_NAME);
    }

    @Test
    public void createForEachArtistAndAlbumWithType() throws IOException {
        CommandResult commandResult = getShell().executeCommand("m3u create --type EVERY_ARTIST_ALBUM --basedir " + BASE_DIR);
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess(), equalTo(true));

        checkPlaylist("genesis.m3u", GENESIS_ANOTHER_RECORD_FILE_NAME, GENESIS_KEEP_IT_DARKER_FILE_NAME);
        checkPlaylist("nirvana.m3u", NIRVANA_COME_AS_YOU_ARE_FILE_NAME, NIRVANA_ON_A_PLAIN_FILE_NAME);
    }

    @Test
    public void createForEachArtist() throws IOException {
        CommandResult commandResult = getShell().executeCommand("m3u create --type EVERY_ARTIST --basedir " + BASE_DIR);
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess(), equalTo(true));

        checkPlaylist("genesis.m3u", GENESIS_ANOTHER_RECORD_FILE_NAME, GENESIS_KEEP_IT_DARKER_FILE_NAME);
        checkPlaylist("nirvana.m3u", NIRVANA_COME_AS_YOU_ARE_FILE_NAME, NIRVANA_ON_A_PLAIN_FILE_NAME);
    }

    private void checkPlaylist(String playlistName, String... fileNames) throws IOException {
        File playlistFile = new File(BASE_DIR + File.separator + playlistName);
        assertThat("playlist does not exists: " + playlistFile, playlistFile.getAbsoluteFile().exists(), equalTo(true));

        List<String> fileContent = IOUtils.readLines(new FileInputStream(playlistFile));
        assertNotNull(fileContent);
        assertThat(fileContent.size(), equalTo(fileNames.length));

        for (int i = 0; i < fileContent.size(); i++) {
            assertThat(fileContent.get(i), containsString(getPathFor(fileNames[i])));
        }
    }
}
