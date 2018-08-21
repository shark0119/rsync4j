package com.shark.unary.mock;

import com.shark.unary.FileProvider;

import java.io.File;
import java.io.FileFilter;

public class SimpleFileProvider implements FileProvider {
    public String getIdentification() {
        return null;
    }

    public File next() {
        return null;
    }

    public File get(String identification) {
        return null;
    }

    public void reset() {

    }

    public void setFileFilter(FileFilter fileFilter) {

    }

}
