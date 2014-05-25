package de.fred4jupiter.m3u.generator.service;

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
    public void createPlaylistLeveOneForAll() {
        final String baseDir = "d:/Temp3";

        GeneratorOptions generatorOptions = new GeneratorOptions(baseDir, GeneratorOptions.PlaylistLevel.ONE_FOR_ALL);
        playlistGenerator.createPlaylist(generatorOptions);
    }

    @Test
    public void createPlaylistLevelForArtists() {
        final String baseDir = "d:/Temp3";

        GeneratorOptions generatorOptions = new GeneratorOptions(baseDir, GeneratorOptions.PlaylistLevel.EVERY_ARTIST);
        playlistGenerator.createPlaylist(generatorOptions);
    }

    @Test
    public void createPlaylistLevelForEachArtistAndAlbum() {
        final String baseDir = "d:/Temp3";

        GeneratorOptions generatorOptions = new GeneratorOptions(baseDir, GeneratorOptions.PlaylistLevel.EVERY_ARTIST_ALBUM);
        playlistGenerator.createPlaylist(generatorOptions);
    }
}
