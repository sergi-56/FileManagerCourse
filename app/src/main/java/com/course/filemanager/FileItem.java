package com.course.filemanager;

import java.nio.file.Path;

/**
 * Модель файла или директории.
 */
public class FileItem {

    private final String name;
    private final Path path;
    private final long size;
    private final boolean directory;

    public FileItem(String name, Path path, long size, boolean directory) {
        this.name = name;
        this.path = path;
        this.size = size;
        this.directory = directory;
    }

    public String getName() {
        return name;
    }

    public Path getPath() {
        return path;
    }

    public long getSize() {
        return size;
    }

    public boolean isDirectory() {
        return directory;
    }
}
