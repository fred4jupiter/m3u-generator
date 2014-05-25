package de.fred4jupiter.m3u.generator;

import de.fred4jupiter.m3u.generator.scanning.DirectoryWalkerTest;
import de.fred4jupiter.m3u.generator.service.PlaylistGeneratorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({DirectoryWalkerTest.class, PlaylistGeneratorTest.class})
public class TestSuite {
    // nothing
}
