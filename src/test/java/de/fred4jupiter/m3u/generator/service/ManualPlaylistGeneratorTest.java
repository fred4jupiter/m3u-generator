package de.fred4jupiter.m3u.generator.service;

import de.fred4jupiter.m3u.generator.service.PlaylistGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/spring-shell-plugin.xml")
public class ManualPlaylistGeneratorTest {

    @Autowired
//    @Qualifier("m3uPlaylistGenerator")
    @Qualifier("treeWalkingPlaylistGenerator")
    private PlaylistGenerator playlistGenerator;

    @Test
    public void generateOnePlaylistForAll() throws IOException {
        final boolean sortByTrackNumber = false;
        //final String baseDir = "e:/Mike Oldfield";
//        final String baseDir = "m:/Musik/30SecondsToMars";
        final String baseDir = "d:/Temp3";

        playlistGenerator.createOnePlaylistForAll(baseDir, sortByTrackNumber);
    }

    @Test
    public void createPlaylistLevel0() throws IOException {
        final boolean sortByTrackNumber = false;
        //final String baseDir = "e:/Mike Oldfield";
//        final String baseDir = "m:/Musik/30SecondsToMars";
        final String baseDir = "d:/Temp3";

        GeneratorOptions generatorOptions = new GeneratorOptions(baseDir, 0);
        generatorOptions.setSortByTrackNumber(sortByTrackNumber);

        playlistGenerator.createPlaylist(generatorOptions);
    }

    @Test
    public void createPlaylistLeveOneForAll() throws IOException {
        final String baseDir = "d:/Temp3";

        GeneratorOptions generatorOptions = new GeneratorOptions(baseDir, 0);
        playlistGenerator.createPlaylist(generatorOptions);
    }

    @Test
    public void createPlaylistLevelForArtists() throws IOException {
        final String baseDir = "d:/Temp3";

        GeneratorOptions generatorOptions = new GeneratorOptions(baseDir, 1);
        playlistGenerator.createPlaylist(generatorOptions);
    }

    @Test
    public void createPlaylistLevelForEachArtistAndAlbum() throws IOException {
        final String baseDir = "d:/Temp3";

        GeneratorOptions generatorOptions = new GeneratorOptions(baseDir, 2);
        playlistGenerator.createPlaylist(generatorOptions);
    }

    @Test
    public void generatePlaylistForEachDir() throws IOException {
        final boolean sortByTrackNumber = false;

        final String baseDir = "m:/Musik/30SecondsToMars";
        //final String baseDir = "d:/Temp2";

        playlistGenerator.createPlaylistsForEachDirectory(baseDir, sortByTrackNumber);
    }
}
