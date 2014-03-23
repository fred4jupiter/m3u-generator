package de.fred4jupiter.m3u.generator;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public final class SortingUtil {

    private SortingUtil() {

    }

    public static void sortFilesByName(File[] directoryFiles) {
        Collections.sort(Arrays.asList(directoryFiles), new Comparator<File>() {
            @Override
            public int compare(File fileOne, File fileTwo) {
                return fileOne.getName().compareTo(fileTwo.getName());
            }
        });
    }
}
