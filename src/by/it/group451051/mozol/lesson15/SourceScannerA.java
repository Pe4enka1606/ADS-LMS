package by.it.group451051.mozol.lesson15;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

public class SourceScannerA {

    // Класс для хранения информации об обработанном файле
    private static class JavaFileItem {
        int sizeInBytes;
        String relativePath;

        JavaFileItem(int sizeInBytes, String relativePath) {
            this.sizeInBytes = sizeInBytes;
            this.relativePath = relativePath;
        }
    }

    // Динамический массив для хранения объектов JavaFileItem (замена ArrayList)
    private static JavaFileItem[] fileItems = new JavaFileItem[10];
    private static int itemCount = 0;

    private static void addFileItem(JavaFileItem item) {
        if (itemCount == fileItems.length) {
            JavaFileItem[] newArray = new JavaFileItem[fileItems.length * 3 / 2 + 1];
            System.arraycopy(fileItems, 0, newArray, 0, itemCount);
            fileItems = newArray;
        }
        fileItems[itemCount++] = item;
    }

    public static void main(String[] args) {
        // Получаем путь к каталогу src
        String srcPath = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        File srcDir = new File(srcPath);

        if (srcDir.exists() && srcDir.isDirectory()) {
            processDirectory(srcDir, srcPath);
        }

        // Сортировка полученных результатов (сортировка вставками)
        // Сортируем сначала по размеру (по возрастанию), а при совпадении — лексикографически по пути
        for (int i = 1; i < itemCount; i++) {
            JavaFileItem key = fileItems[i];
            int j = i - 1;
            while (j >= 0 && (fileItems[j].sizeInBytes > key.sizeInBytes ||
                    (fileItems[j].sizeInBytes == key.sizeInBytes && fileItems[j].relativePath.compareTo(key.relativePath) > 0))) {
                fileItems[j + 1] = fileItems[j];
                j--;
            }
            fileItems[j + 1] = key;
        }

        // Вывод результатов в консоль
        for (int i = 0; i < itemCount; i++) {
            System.out.println(fileItems[i].sizeInBytes + " Б " + fileItems[i].relativePath);
        }
    }

    // Рекурсивный обход каталогов
    private static void processDirectory(File directory, String srcPath) {
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                processDirectory(file, srcPath);
            } else if (file.isFile() && file.getName().endsWith(".java")) {
                processJavaFile(file, srcPath);
            }
        }
    }

    // Обработка отдельного файла .java
    private static void processJavaFile(File file, String srcPath) {
        String content = readFileToString(file);
        if (content == null) return;

        // Фильтрация: если файл содержит тесты, пропускаем его
        if (content.contains("@Test") || content.contains("org.junit.Test")) {
            return;
        }

        // 1. Удаление строки package и всех импортов за O(n)
        String processedContent = removePackageAndImports(content);

        // 2. Удаление управляющих символов (код < 33) в начале и конце за O(n)
        processedContent = trimControlCharacters(processedContent);

        // Рассчитываем размер полученного текста в байтах (в кодировке UTF-8)
        int sizeInBytes = processedContent.getBytes(StandardCharsets.UTF_8).length;

        // Вычисляем относительный путь от каталога src
        String relativePath = file.getAbsolutePath().substring(srcPath.length());

        // Добавляем результат в массив
        addFileItem(new JavaFileItem(sizeInBytes, relativePath));
    }

    // Чтение файла в строку с защитой от MalformedInputException
    private static String readFileToString(File file) {
        StringBuilder sb = new StringBuilder();
        // Настраиваем декодер для игнорирования / замены ошибочных байтов
        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
        decoder.onMalformedInput(CodingErrorAction.REPLACE);
        decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);

        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, decoder)) {

            char[] buffer = new char[1024];
            int length;
            while ((length = isr.read(buffer)) != -1) {
                sb.append(buffer, 0, length);
            }
            return sb.toString();
        } catch (IOException e) {
            return null; // Пропускаем файл при ошибках ввода-вывода
        }
    }

    // Удаление строк, начинающихся с package и import за один проход O(n)
    private static String removePackageAndImports(String text) {
        StringBuilder sb = new StringBuilder();
        int len = text.length();
        int i = 0;

        while (i < len) {
            // Находим границы текущей строки
            int start = i;
            while (i < len && text.charAt(i) != '\n' && text.charAt(i) != '\r') {
                i++;
            }
            int end = i;

            // Пропускаем символы перевода строки
            if (i < len && text.charAt(i) == '\r') i++;
            if (i < len && text.charAt(i) == '\n') i++;
            int nextLineStart = i;

            // Выделяем строку для анализа (убираем начальные пробелы/табуляцию)
            int subStart = start;
            while (subStart < end && text.charAt(subStart) <= 32) {
                subStart++;
            }

            // Проверяем, начинается ли строка с "package " или "import "
            boolean isPackageOrImport = false;
            if (subStart < end) {
                String linePart = text.substring(subStart, Math.min(subStart + 8, end));
                if (linePart.startsWith("package ") || linePart.startsWith("import ")) {
                    isPackageOrImport = true;
                }
            }

            // Если это не package и не import, сохраняем строку целиком (вместе с её \r\n)
            if (!isPackageOrImport) {
                sb.append(text, start, nextLineStart);
            }
        }
        return sb.toString();
    }

    // Обрезание символов с кодом < 33 с начала и конца текста за O(n)
    private static String trimControlCharacters(String text) {
        int start = 0;
        int end = text.length() - 1;

        while (start <= end && text.charAt(start) < 33) {
            start++;
        }
        while (end >= start && text.charAt(end) < 33) {
            end--;
        }

        if (start > end) {
            return "";
        }
        return text.substring(start, end + 1);
    }
}
