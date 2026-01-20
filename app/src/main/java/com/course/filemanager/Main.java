package com.course.filemanager;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/**
 * Точка входа в приложение файлового менеджера.
 * Обрабатывает пользовательские команды из консоли.
 */

public class Main {

    /**
     * Запуск консольного файлового менеджера.
     *
     * @param args аргументы командной строки (не используются)
     */

    public static void main(String[] args) {
        FileManagerService service = new FileManagerService();
        Path currentDir = Path.of(".").toAbsolutePath().normalize();

        System.out.println("File Manager");
        System.out.println("Команды:");
        System.out.println("  ls                     - показать файлы в текущей папке");
        System.out.println("  copy <file> <dir>      - копировать файл в папку");
        System.out.println("  exit                   - выход");
        System.out.println();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print(currentDir + " > ");

                // Если ввода нет (stdin закрыт), не падаем — выходим корректно
                if (!scanner.hasNextLine()) {
                    System.out.println();
                    System.out.println("Нет ввода (stdin закрыт). Завершение работы.");
                    break;
                }

                String line = scanner.nextLine().trim();

                if (line.isEmpty()) continue;
                if (line.equalsIgnoreCase("exit")) break;

                String[] parts = line.split("\\s+");
                String command = parts[0].toLowerCase();

                try {
                    switch (command) {
                        case "ls" -> {
                            List<FileItem> items = service.listDirectory(currentDir);
                            items.forEach(item -> System.out.printf(
                                    "%s | %s | %d bytes%n",
                                    item.isDirectory() ? "DIR " : "FILE",
                                    item.getName(),
                                    item.getSize()
                            ));
                        }
                        case "copy" -> {
                            if (parts.length < 3) {
                                System.out.println("Использование: copy <file> <dir>");
                                break;
                            }
                            Path source = currentDir.resolve(parts[1]).normalize();
                            Path targetDir = Path.of(parts[2]).toAbsolutePath().normalize();

                            Path copied = service.copyFile(source, targetDir);
                            System.out.println("Скопировано в: " + copied);
                        }
                        default -> System.out.println("Неизвестная команда: " + command);
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }
        }
    }
}
