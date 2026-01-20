package com.course.filemanager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerServiceTest {

    @TempDir
    Path tempDir;

    @Test
    void copyFile_successfullyCopiesFile() throws IOException {
        // arrange
        FileManagerService service = new FileManagerService();

        Path sourceFile = tempDir.resolve("source.txt");
        Files.writeString(sourceFile, "hello");

        Path targetDir = tempDir.resolve("target");
        Files.createDirectory(targetDir);

        // act
        Path copiedFile = service.copyFile(sourceFile, targetDir);

        // assert
        assertTrue(Files.exists(copiedFile), "Файл должен быть скопирован");
        assertEquals(
                Files.readString(sourceFile),
                Files.readString(copiedFile),
                "Содержимое файла должно совпадать"
        );
    }
}
