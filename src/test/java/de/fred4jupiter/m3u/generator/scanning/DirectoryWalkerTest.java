package de.fred4jupiter.m3u.generator.scanning;

import de.fred4jupiter.m3u.generator.playlisting.DirectoryListener;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class DirectoryWalkerTest {

    private static final Logger LOG = LoggerFactory.getLogger(DirectoryWalkerTest.class);

    @Test
    public void scanDir() {
        final File baseDir = new File("src/test/resources/mp3DummyFolder");

        DirectoryWalker defaultDirectoryWalker = new DefaultDirectoryWalker(baseDir);
        AssertingDirectoryListener assertingDirectoryListener = new AssertingDirectoryListener();
        defaultDirectoryWalker.registerListener(assertingDirectoryListener);

        defaultDirectoryWalker.scanDir(baseDir);

        String content = assertingDirectoryListener.getContent();
        Assert.assertThat(content, Matchers.notNullValue());
        LOG.debug(content);
        Assert.assertThat(content, Matchers.containsString("genesis"));
        Assert.assertThat(content, Matchers.containsString("nirvana"));
    }

    private static final class AssertingDirectoryListener implements DirectoryListener {
        private StringBuilder stringBuilder = new StringBuilder();

        @Override
        public void onEnterDirectory(File dir, String relativeDirectoryPrefix, int depth) {
            stringBuilder.append(relativeDirectoryPrefix);
            stringBuilder.append("\n");
        }

        @Override
        public void onScanFinished(File baseDir) {

        }

        public String getContent() {
            return stringBuilder.toString();
        }
    }
}
