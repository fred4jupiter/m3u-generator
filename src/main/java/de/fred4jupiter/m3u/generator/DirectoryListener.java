package de.fred4jupiter.m3u.generator;

import java.io.File;

public interface DirectoryListener {

    void onEnterDirectory(File dir, String relativeDirectoryPrefix, int depth);
}
