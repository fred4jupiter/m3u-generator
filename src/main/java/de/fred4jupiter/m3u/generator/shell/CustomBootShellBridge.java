package de.fred4jupiter.m3u.generator.shell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.shell.Bootstrap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Custom spring boot command line running class as bridge for spring shell with custom commands.
 */
public class CustomBootShellBridge implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(CustomBootShellBridge.class);

    private static final String DISABLE_INTERNAL_COMMANDS = "--disableInternalCommands";

    public static void main(String[] args) {
        try {
            new CustomBootShellBridge().run(args);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        // by default spring shell loads the application context from classpath*:/META-INF/spring/spring-shell-plugin.xml
        Bootstrap bootstrap = new Bootstrap();

        List<String> argumentList = new ArrayList<String>(Arrays.asList(args));
        if (!argumentList.contains(DISABLE_INTERNAL_COMMANDS)) {
            argumentList.add(DISABLE_INTERNAL_COMMANDS);
        }

        bootstrap.main(argumentList.toArray(new String[0]));
    }
}
