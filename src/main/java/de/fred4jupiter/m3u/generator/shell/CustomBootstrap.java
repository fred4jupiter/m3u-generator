package de.fred4jupiter.m3u.generator.shell;

import org.springframework.shell.Bootstrap;

import java.io.IOException;

/**
 * Created by michael on 26.08.2014.
 */
public class CustomBootstrap {

    // --disableInternalCommands

    public static void main(String[] args) throws IOException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.main(new String[]{"--disableInternalCommands"});
    }
}
