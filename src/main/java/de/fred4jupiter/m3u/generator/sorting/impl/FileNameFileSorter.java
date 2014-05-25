package de.fred4jupiter.m3u.generator.sorting.impl;

import de.fred4jupiter.m3u.generator.sorting.FileSorter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

/**
 * Sorts files based on their fileName.
 */
@Component
@Qualifier("fileNameFileSorter")
public class FileNameFileSorter implements FileSorter {

    @Override
    public void sortFiles(File[] directoryFiles) {
        Collections.sort(Arrays.asList(directoryFiles), (fileOne, fileTwo) -> fileOne.getName().compareTo(fileTwo.getName()));
    }
}
