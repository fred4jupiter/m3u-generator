package de.fred4jupiter.m3u.generator;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class PlaylistDirectoryListener implements DirectoryListener {

    private StringBuilder stringBuilder;

    public PlaylistDirectoryListener() {
        stringBuilder = new StringBuilder();
    }

    @Override
    public void onEnterDirectory(File dir, String relativeDirectoryPrefix, int depth) {
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile() && file.getName().endsWith(".mp3");
            }
        });

        SortingUtil.sortFilesByName(files);

        for (File file : files) {
            if (!"".equals(relativeDirectoryPrefix)) {
                stringBuilder.append(relativeDirectoryPrefix + File.separator + file.getName());
            } else {
                stringBuilder.append(dir.getName() + File.separator + file.getName());
            }

            stringBuilder.append("\n");
        }
    }

    public String getContent() throws IOException {
        return stringBuilder.toString();
    }

    public File writePlaylistToFile(File baseDirFile) throws IOException {
        return writePlaylistToFile(baseDirFile, null);
    }

    public File writePlaylistToFile(File baseDirFile, String playlistName) throws IOException {
        String content = getContent();
        if (content == null || "".equals(content)) {
            return null;
        }

        String tmpPlaylistName = playlistName;
        if (tmpPlaylistName == null) {
            tmpPlaylistName = baseDirFile.getName() + ".m3u";
        }

        File file = new File(baseDirFile.getAbsolutePath() + File.separator + tmpPlaylistName);
        FileUtils.writeStringToFile(file, getContent());
        return file;
    }
}
