package de.fred4jupiter.m3u.generator.service;

import de.fred4jupiter.m3u.generator.AbstractShellIntegrationTest;
import de.fred4jupiter.m3u.generator.Constants;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.shell.core.CommandResult;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

public class PlaylistGeneratorTest extends AbstractShellIntegrationTest {

    private static final String GENESIS_ANOTHER_RECORD_FILE_NAME = "Another Record - Genesis.mp3";
    private static final String GENESIS_KEEP_IT_DARKER_FILE_NAME = "Keep it darker - Genesis.mp3";

    private static final String NIRVANA_COME_AS_YOU_ARE_FILE_NAME = "Come as you are - Nirvana.mp3";
    private static final String NIRVANA_ON_A_PLAIN_FILE_NAME = "On a plain - Nirvana.mp3";

    private static final String BASE_DIR = "src/test/resources/mp3DummyFolder";

    @Before
    public void cleanup() {
        File[] files = Paths.get(BASE_DIR).toFile().listFiles(file -> file.isFile() && file.getName().endsWith(PlaylistFileType.M3U.getFileExtension()));
        assertThat(files).isNotNull();
        for (File file : files) {
            file.delete();
        }
    }

    @Test
    public void oneForAll() throws IOException {
        CommandResult commandResult = getShell().executeCommand("m3u oneForAll --basedir " + BASE_DIR);
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess()).isTrue();

        String playlistName = Constants.DEFAULT_PLAYLIST_NAME + PlaylistFileType.M3U.getFileExtension();
        checkPlaylist(playlistName, 4, GENESIS_ANOTHER_RECORD_FILE_NAME, GENESIS_KEEP_IT_DARKER_FILE_NAME);
        checkPlaylist(playlistName, 4, NIRVANA_COME_AS_YOU_ARE_FILE_NAME, NIRVANA_ON_A_PLAIN_FILE_NAME);
    }

    @Test
    public void oneForAllWithOtherPlaylistName() throws IOException {
        CommandResult commandResult = getShell().executeCommand("m3u oneForAll --basedir " + BASE_DIR + " --playlistName MyList");
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess()).isTrue();

        String playlistName = "MyList" + PlaylistFileType.M3U.getFileExtension();
        checkPlaylist(playlistName, 4, GENESIS_ANOTHER_RECORD_FILE_NAME, GENESIS_KEEP_IT_DARKER_FILE_NAME);
        checkPlaylist(playlistName, 4, NIRVANA_COME_AS_YOU_ARE_FILE_NAME, NIRVANA_ON_A_PLAIN_FILE_NAME);
    }

    @Test
    public void oneForAllSortByTracknumber() throws IOException {
        CommandResult commandResult = getShell().executeCommand("m3u oneForAll --basedir " + BASE_DIR + " --sortByTrackNumber");
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess()).isTrue();

        String playlistName = Constants.DEFAULT_PLAYLIST_NAME + PlaylistFileType.M3U.getFileExtension();
        checkPlaylist(playlistName, 4, GENESIS_KEEP_IT_DARKER_FILE_NAME, GENESIS_ANOTHER_RECORD_FILE_NAME);
        checkPlaylist(playlistName, 4, NIRVANA_ON_A_PLAIN_FILE_NAME, NIRVANA_COME_AS_YOU_ARE_FILE_NAME);
    }

    @Test
    public void forEachArtist() throws IOException {
        CommandResult commandResult = getShell().executeCommand("m3u forEachArtist --basedir " + BASE_DIR);
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess()).isTrue();

        checkPlaylist("genesis.m3u", GENESIS_ANOTHER_RECORD_FILE_NAME, GENESIS_KEEP_IT_DARKER_FILE_NAME);
        checkPlaylist("nirvana.m3u", NIRVANA_COME_AS_YOU_ARE_FILE_NAME, NIRVANA_ON_A_PLAIN_FILE_NAME);
    }

    @Test
    public void createForEachArtistAndAlbum() throws IOException {
        CommandResult commandResult = getShell().executeCommand("m3u create --basedir " + BASE_DIR);
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess()).isTrue();

        checkPlaylist("genesis - Abacab.m3u", GENESIS_ANOTHER_RECORD_FILE_NAME, GENESIS_KEEP_IT_DARKER_FILE_NAME);
        checkPlaylist("nirvana - unplugged.m3u", NIRVANA_COME_AS_YOU_ARE_FILE_NAME, NIRVANA_ON_A_PLAIN_FILE_NAME);
    }

    @Test
    public void createForEachArtistAndAlbumWithType() throws IOException {
        CommandResult commandResult = getShell().executeCommand("m3u create --type EVERY_ARTIST_ALBUM --basedir " + BASE_DIR);
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess()).isTrue();

        checkPlaylist("genesis - Abacab.m3u", GENESIS_ANOTHER_RECORD_FILE_NAME, GENESIS_KEEP_IT_DARKER_FILE_NAME);
        checkPlaylist("nirvana - unplugged.m3u", NIRVANA_COME_AS_YOU_ARE_FILE_NAME, NIRVANA_ON_A_PLAIN_FILE_NAME);
    }

    @Test
    public void createForEachArtist() throws IOException {
        CommandResult commandResult = getShell().executeCommand("m3u create --type EVERY_ARTIST --basedir " + BASE_DIR);
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess()).isTrue();

        checkPlaylist("genesis.m3u", GENESIS_ANOTHER_RECORD_FILE_NAME, GENESIS_KEEP_IT_DARKER_FILE_NAME);
        checkPlaylist("nirvana.m3u", NIRVANA_COME_AS_YOU_ARE_FILE_NAME, NIRVANA_ON_A_PLAIN_FILE_NAME);
    }

    @Test
    public void createOneForAllWithOtherTargetDir() throws IOException {
        final String targetDir = "target" + File.separator + "other";
        CommandResult commandResult = getShell().executeCommand("m3u create --type ONE_FOR_ALL --basedir " + BASE_DIR + " --targetDir " + targetDir);
        assertNotNull(commandResult);
        assertThat(commandResult.isSuccess()).isTrue();

        String playlistName = Constants.DEFAULT_PLAYLIST_NAME + PlaylistFileType.M3U.getFileExtension();
        checkPlaylist(playlistName, targetDir, Arrays.asList(GENESIS_ANOTHER_RECORD_FILE_NAME, GENESIS_KEEP_IT_DARKER_FILE_NAME), 4);
        checkPlaylist(playlistName, targetDir, Arrays.asList(NIRVANA_COME_AS_YOU_ARE_FILE_NAME, NIRVANA_ON_A_PLAIN_FILE_NAME), 4);
    }

    private void checkPlaylist(String playlistName, String... fileNames) throws IOException {
        checkPlaylist(playlistName, BASE_DIR, Arrays.asList(fileNames), fileNames.length);
    }

    private void checkPlaylist(String playlistName, int numberOfEntries, String... fileNames) throws IOException {
        checkPlaylist(playlistName, BASE_DIR, Arrays.asList(fileNames), numberOfEntries);
    }

    private void checkPlaylist(String playlistName, String targetDir, List<String> fileNames, int numberOfEntries) throws IOException {
        File playlistFile = new File(targetDir + File.separator + playlistName);
        assertThat(playlistFile.getAbsoluteFile().exists()).isTrue();

        List<String> fileContent = FileUtils.readLines(playlistFile, StandardCharsets.UTF_8);
        assertNotNull(fileContent);
        assertThat(fileContent.size()).isEqualTo(numberOfEntries);

        int lastIndex = 0;
        for (int i = 0; i < fileNames.size(); i++) {
            String fileNameToCheck = fileNames.get(i);
            int index = indexFor(fileContent, fileNameToCheck);
            assertThat(fileContent).anyMatch(line -> line.contains(getPathFor(fileNameToCheck)));
            if (index > 0) {
                assertThat(lastIndex < index).isTrue();
            }
            lastIndex = index;
        }
    }

    private int indexFor(List<String> fileContent, String fileName) {
        return fileContent.indexOf(fileName);
    }

    private String getPathFor(String fileName) {
        if (fileName.contains("Genesis")) {
            return "genesis" + File.separator + "Abacab" + File.separator + fileName;
        } else {
            return "nirvana" + File.separator + "unplugged" + File.separator + fileName;
        }
    }
}
