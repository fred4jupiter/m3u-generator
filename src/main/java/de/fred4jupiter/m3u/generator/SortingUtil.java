package de.fred4jupiter.m3u.generator;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

public final class SortingUtil {

    private SortingUtil() {

    }

    public static void sortFilesByName(File[] directoryFiles) {
        Collections.sort(Arrays.asList(directoryFiles), (fileOne, fileTwo) -> fileOne.getName().compareTo(fileTwo.getName()));
    }
}
