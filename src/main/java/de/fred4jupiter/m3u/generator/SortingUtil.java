package de.fred4jupiter.m3u.generator;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

/**
 * Sorting utility class.
 */
public final class SortingUtil {

    private SortingUtil() {
        // avoid instance creation
    }

    /**
     * Sorting files by name ASC.
     *
     * @param directoryFiles files to sort.
     */
    public static void sortFilesByName(File[] directoryFiles) {
        Collections.sort(Arrays.asList(directoryFiles), (fileOne, fileTwo) -> fileOne.getName().compareTo(fileTwo.getName()));
    }
}
