package de.fred4jupiter.m3u.generator.shell;

import org.springframework.shell.Bootstrap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Custom bootstrap class to disable the default build-in commands.
 */
public class CustomBootstrap {

    private static final String DISABLE_INTERNAL_COMMANDS = "--disableInternalCommands";

    public static void main(String[] args) throws IOException {
        Bootstrap bootstrap = new Bootstrap();

        List<String> argumentList = new ArrayList<String>(Arrays.asList(args));
        if (!argumentList.contains(DISABLE_INTERNAL_COMMANDS)) {
            argumentList.add(DISABLE_INTERNAL_COMMANDS);
        }

        bootstrap.main(argumentList.toArray(new String[0]));
    }
}
