package de.fred4jupiter.m3u.generator;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class PathUtil {

    private PathUtil() {

    }

    public static String removeDriveLetter(String path) {
        Path newPath = Paths.get(path);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < newPath.getNameCount(); i++) {
            stringBuilder.append(newPath.getName(i));
            stringBuilder.append(File.separator);
        }

        return removeLastSeparatorIfExistend(stringBuilder);
    }

    private static String removeLastSeparatorIfExistend(StringBuilder stringBuilder) {
        String relativePath = stringBuilder.toString();
        String constructedPath = relativePath;
        if (relativePath.endsWith(File.separator)) {
            constructedPath = relativePath.substring(0, relativePath.length() - 1);
        }
        return constructedPath;
    }

    public static String getRelativeWithoutFirst(String path) {
        Path newPath = Paths.get(path);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < newPath.getNameCount(); i++) {
            stringBuilder.append(newPath.getName(i));
            stringBuilder.append(File.separator);
        }

        return removeLastSeparatorIfExistend(stringBuilder);
    }

    public static int calculatePathDepth(String baseDir, String dir) {
        Path baseDirPath = Paths.get(baseDir);

        Path dirPath = Paths.get(dir);
        return dirPath.getNameCount() - baseDirPath.getNameCount();
    }
}
