package com.lifeiscoding.spring.core;


import com.lifeiscoding.spring.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class FileSystemResource implements Resource {

    private final String path;
    private final File file;


    public FileSystemResource(String path) {
        Assert.notNull(path, "Path must not be null");
        this.file = new File(path);
        this.path = path;
    }

    public FileSystemResource(File file) {
        Assert.notNull(file, "file must not be null");
        this.file = file;
        this.path = file.getPath();
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    public String getDescription() {
        return "file [" + this.file.getAbsolutePath() + "]";
    }

}
