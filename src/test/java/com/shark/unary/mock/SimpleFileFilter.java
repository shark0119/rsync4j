package com.shark.unary.mock;

import java.io.File;
import java.io.FileFilter;

public class SimpleFileFilter implements FileFilter {
    public boolean accept(File pathname) {
        return false;
    }
}
