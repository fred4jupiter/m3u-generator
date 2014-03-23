package de.fred4jupiter.m3u.generator;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class PathUtilTest {

    @Test
    public void removeDriveLetter1() {
        assertThat(PathUtil.removeDriveLetter("e:\\Temp1"), equalTo("Temp1"));
    }

    @Test
    public void removeDriveLetter2() {
        assertThat(PathUtil.removeDriveLetter("e:\\Temp1\\A\\B\\"), equalTo("Temp1\\A\\B"));
    }

    @Test
    public void removeDriveLetter3() {
        assertThat(PathUtil.removeDriveLetter("c:\\Temp2\\A\\B\\C"), equalTo("Temp2\\A\\B\\C"));
    }

    @Test
    public void getRelativeWithoutFirst1() {
        assertThat(PathUtil.getRelativeWithoutFirst("c:\\Temp2\\A\\B\\C"), equalTo("A\\B\\C"));
    }

    @Test
    public void calculatePathDepth() {
        String baseDir = "d:/Temp3";
        String dir = "d:\\Temp3\\Asdfs\\Bdfgdgf\\";
        assertThat(PathUtil.calculatePathDepth(baseDir, dir), equalTo(2));
    }

    @Test
    public void calculatePathDepth2() {
        String baseDir = "d:/Temp3/Asdfs";
        String dir = "d:\\Temp3\\Asdfs\\Bdfgdgf\\";
        assertThat(PathUtil.calculatePathDepth(baseDir, dir), equalTo(1));
    }

    @Test
    public void calculatePathDepth3() {
        String baseDir = "d:/";
        String dir = "d:\\Temp3\\Asdfs\\Bdfgdgf\\";
        assertThat(PathUtil.calculatePathDepth(baseDir, dir), equalTo(3));
    }
}
