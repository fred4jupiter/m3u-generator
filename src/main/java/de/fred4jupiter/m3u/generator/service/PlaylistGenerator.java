package de.fred4jupiter.m3u.generator.service;

/**
 * Playlist generator.
 */
public interface PlaylistGenerator {

    /**
     * Creates a playlist for given options.
     *
     * @param generatorOptions playlist options
     */
    void createPlaylist(GeneratorOptions generatorOptions);
}
