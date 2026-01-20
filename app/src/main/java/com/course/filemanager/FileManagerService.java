package com.course.filemanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.nio.file.StandardCopyOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Сервис для работы с файловой системой.
 * Отвечает за получение списка файлов и копирование файлов между директориями.
 */
public class FileManagerService {
    private static final Logger logger =
        LogManager.getLogger(FileManagerService.class);


    /**
     * Возвращает список файлов в директории с их размерами.
     */
    public List<FileItem> listDirectory(Path directory) throws IOException {
        logger.info("Чтение директории: {}", directory);
        try (Stream<Path> paths = Files.list(directory)) {
            return paths
                    .map(this::toFileItem)
                    .collect(Collectors.toList());
        }
    }

    private FileItem toFileItem(Path path) {
        try {
            boolean isDir = Files.isDirectory(path);
            long size = isDir ? 0 : Files.size(path);

            return new FileItem(
                    path.getFileName().toString(),
                    path,
                    size,
                    isDir
            );
        } catch (IOException e) {
            logger.warn("Ошибка при чтении файла: {}", path, e);
            return new FileItem(
                    path.getFileName().toString(),
                    path,
                    0,
                    Files.isDirectory(path)
            );
        }
    }

    /**
     * Копирует файл в указанную директорию.
     *
     * @param sourceFile файл-источник
     * @param targetDirectory директория назначения
     * @return путь к скопированному файлу
     * @throws IOException если файл не существует, является директорией
     *                     или целевая директория некорректна
     */

    public Path copyFile(Path sourceFile, Path targetDirectory) throws IOException {
        logger.info("Копирование файла {} в {}", sourceFile, targetDirectory);

        if (!Files.exists(sourceFile)) {
            logger.error("Файл не существует: {}", sourceFile);
            throw new IOException("Файл не существует: " + sourceFile);
        }
        if (Files.isDirectory(sourceFile)) {
            logger.error("Источник является директорией, а не файлом: {}", sourceFile);
            throw new IOException("Это директория, а не файл: " + sourceFile);
        }
        if (!Files.exists(targetDirectory)) {
            logger.error("Целевая директория не существует: {}", targetDirectory);
            throw new IOException("Целевая директория не существует: " + targetDirectory);
        }
        if (!Files.isDirectory(targetDirectory)) {
            logger.error("Целевой путь не является директорией: {}", targetDirectory);
            throw new IOException("Целевой путь не является директорией: " + targetDirectory);
        }

        Path targetFile = targetDirectory.resolve(sourceFile.getFileName());
        Path copied = Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);

        logger.info("Файл успешно скопирован: {}", copied);
        return copied;
    }

}