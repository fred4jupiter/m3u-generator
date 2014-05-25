package de.fred4jupiter.m3u.generator.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/spring-shell-plugin.xml")
public class ManualPlaylistGeneratorTest {

    @Autowired
    private PlaylistGenerator playlistGenerator;

    @Test
    public void createPlaylistLeveOneForAll() {
        final String baseDir = "d:/Temp3";

        GeneratorOptions generatorOptions = new GeneratorOptions(baseDir, GeneratorOptions.PlaylistType.ONE_FOR_ALL);
        playlistGenerator.createPlaylist(generatorOptions);
    }

    @Test
    public void createPlaylistLevelForArtists() {
        final String baseDir = "d:/Temp3";

        GeneratorOptions generatorOptions = new GeneratorOptions(baseDir, GeneratorOptions.PlaylistType.EVERY_ARTIST);
        playlistGenerator.createPlaylist(generatorOptions);
    }

    @Test
    public void createPlaylistLevelForEachArtistAndAlbum() {
        final String baseDir = "d:/Temp3";

        GeneratorOptions generatorOptions = new GeneratorOptions(baseDir, GeneratorOptions.PlaylistType.EVERY_ARTIST_ALBUM);
        playlistGenerator.createPlaylist(generatorOptions);
    }
}
