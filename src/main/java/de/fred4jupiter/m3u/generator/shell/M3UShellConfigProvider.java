package de.fred4jupiter.m3u.generator.shell;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.shell.plugin.BannerProvider;
import org.springframework.shell.plugin.PromptProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * Simple banner provider to customize the shell.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class M3UShellConfigProvider implements BannerProvider, PromptProvider {

    private static final Logger LOG = LoggerFactory.getLogger(M3UShellConfigProvider.class);

    @Override
    public String getBanner() {
        return readFileAsString("banner.txt");
    }

    private String readFileAsString(String fileName) {
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        try (InputStream in = classPathResource.getInputStream()) {
            return IOUtils.toString(in);
        } catch (IOException e) {
            LOG.error("getBanner: Could not load banner. {}", e.getMessage(), e);
            return "";
        }
    }

    @Override
    public String getVersion() {
        return readFileAsString("version.txt");
    }

    @Override
    public String getWelcomeMessage() {
        return "Welcome to M3U Generator. With this tool you can generate m3u playlist files.";
    }

    @Override
    public String getProviderName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getPrompt() {
        return "m3u-shell>";
    }
}
