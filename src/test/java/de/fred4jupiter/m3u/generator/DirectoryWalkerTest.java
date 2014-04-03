package de.fred4jupiter.m3u.generator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class DirectoryWalkerTest {

    private static final Logger LOG = LoggerFactory.getLogger(DirectoryWalkerTest.class);

    @Test
    public void scanDir() {
        final File baseDir = new File("src/test/resources/mp3DummyFolder");

        DirectoryWalker directoryWalker = new DirectoryWalker(baseDir);
        AssertingDirectoryListener assertingDirectoryListener = new AssertingDirectoryListener();
        directoryWalker.registerListener(assertingDirectoryListener);

        directoryWalker.scanDir(baseDir);

        String content = assertingDirectoryListener.getContent();
        assertThat(content, notNullValue());
        LOG.debug(content);
        assertThat(content, containsString("genesis"));
        assertThat(content, containsString("nirvana"));
    }

    private static final class AssertingDirectoryListener implements DirectoryListener {
        private StringBuilder stringBuilder = new StringBuilder();

        @Override
        public void onEnterDirectory(File dir, String relativeDirectoryPrefix, int depth) {
            stringBuilder.append(relativeDirectoryPrefix);
            stringBuilder.append("\n");
        }

        public String getContent() {
            return stringBuilder.toString();
        }
    }
}
