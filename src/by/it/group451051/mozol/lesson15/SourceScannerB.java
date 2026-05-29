package by.it.group451051.mozol.lesson15;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

public class SourceScannerB {

    // Класс для хранения информации об обработанном файле
    private static class JavaFileItem {
        int sizeInBytes;
        String relativePath;

        JavaFileItem(int sizeInBytes, String relativePath) {
            this.sizeInBytes = sizeInBytes;
            this.relativePath = relativePath;
        }
    }

    // Динамический массив объектов (замена ArrayList)
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
        // Определяем путь к каталогу src
        String srcPath = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        File srcDir = new File(srcPath);

        if (srcDir.exists() && srcDir.isDirectory()) {
            processDirectory(srcDir, srcPath);
        }

        // Двухкритериальная сортировка результатов (сортировка вставками)
        // Приоритет 1: Размер в байтах по возрастанию. Приоритет 2: Путь лексикографически.
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

        // Вывод информации в консоль
        for (int i = 0; i < itemCount; i++) {
            System.out.println(fileItems[i].sizeInBytes + " Б " + fileItems[i].relativePath);
        }
    }

    // Рекурсивный обход дерева каталогов
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

    // Логика обработки отдельного файла .java
    private static void processJavaFile(File file, String srcPath) {
        String content = readFileToString(file);
        if (content == null) return;

        // Фильтр: пропускаем файлы тестов
        if (content.contains("@Test") || content.contains("org.junit.Test")) {
            return;
        }

        // 1. Удаляем package и импорты за O(n)
        String processed = removePackageAndImports(content);

        // 2. Удаляем все типы комментариев за O(n) с учетом строковых литералов
        processed = removeComments(processed);

        // 3. Удаляем пустые строки и управляющие символы по краям
        processed = removeEmptyLinesAndTrim(processed);

        // Если после всех очисток файл стал абсолютно пустым, не выводим его
        if (processed.isEmpty()) {
            return;
        }

        // Вычисляем вес текста в байтах (UTF-8) и относительный путь
        int sizeInBytes = processed.getBytes(StandardCharsets.UTF_8).length;
        String relativePath = file.getAbsolutePath().substring(srcPath.length());

        addFileItem(new JavaFileItem(sizeInBytes, relativePath));
    }

    // Чтение файла без падения по MalformedInputException
    private static String readFileToString(File file) {
        StringBuilder sb = new StringBuilder();
        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
        // Заменяем некорректные байты на маркеры, предотвращая исключение
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
            return null;
        }
    }

    // Удаление строк package и import за один проход O(n)
    private static String removePackageAndImports(String text) {
        StringBuilder sb = new StringBuilder();
        int len = text.length();
        int i = 0;

        while (i < len) {
            int start = i;
            while (i < len && text.charAt(i) != '\n' && text.charAt(i) != '\r') {
                i++;
            }
            int end = i;

            if (i < len && text.charAt(i) == '\r') i++;
            if (i < len && text.charAt(i) == '\n') i++;
            int nextLineStart = i;

            int subStart = start;
            while (subStart < end && text.charAt(subStart) <= 32) {
                subStart++;
            }

            boolean isPackageOrImport = false;
            if (subStart < end) {
                String linePart = text.substring(subStart, Math.min(subStart + 8, end));
                if (linePart.startsWith("package ") || linePart.startsWith("import ")) {
                    isPackageOrImport = true;
                }
            }

            if (!isPackageOrImport) {
                sb.append(text, start, nextLineStart);
            }
        }
        return sb.toString();
    }

    // Конечный автомат для удаления однострочных и многострочных комментариев за O(n)
    private static String removeComments(String text) {
        StringBuilder sb = new StringBuilder();
        int len = text.length();

        boolean inString = false;
        boolean inChar = false;
        boolean inLineComment = false;
        boolean inBlockComment = false;

        for (int i = 0; i < len; i++) {
            char c = text.charAt(i);
            char next = (i + 1 < len) ? text.charAt(i + 1) : '\0';

            if (inLineComment) {
                if (c == '\n' || c == '\r') {
                    inLineComment = false;
                    sb.append(c); // сохраняем перенос строки
                }
            } else if (inBlockComment) {
                if (c == '*' && next == '/') {
                    inBlockComment = false;
                    i++; // перешагиваем через '/'
                }
            } else if (inString) {
                sb.append(c);
                if (c == '\\') { // экранирование символов внутри строки
                    if (next != '\0') {
                        sb.append(next);
                        i++;
                    }
                } else if (c == '"') {
                    inString = false;
                }
            } else if (inChar) {
                sb.append(c);
                if (c == '\\') {
                    if (next != '\0') {
                        sb.append(next);
                        i++;
                    }
                } else if (c == '\'') {
                    inChar = false;
                }
            } else {
                // Если мы находимся в обычном коде, проверяем начало комментариев или строк
                if (c == '/' && next == '/') {
                    inLineComment = true;
                    i++;
                } else if (c == '/' && next == '*') {
                    inBlockComment = true;
                    i++;
                } else if (c == '"') {
                    inString = true;
                    sb.append(c);
                } else if (c == '\'') {
                    inChar = true;
                    sb.append(c);
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    // Очистка пустых строк и обрезка краев текста (символы < 33) за O(n)
    private static String removeEmptyLinesAndTrim(String text) {
        StringBuilder sb = new StringBuilder();
        int len = text.length();
        int i = 0;

        while (i < len) {
            int start = i;
            while (i < len && text.charAt(i) != '\n' && text.charAt(i) != '\r') {
                i++;
            }
            int end = i;

            if (i < len && text.charAt(i) == '\r') i++;
            if (i < len && text.charAt(i) == '\n') i++;
            int nextLineStart = i;

            // Проверяем, пустая ли строка (состоит ли только из пробельных символов)
            boolean isEmptyLine = true;
            for (int k = start; k < end; k++) {
                if (text.charAt(k) > 32) {
                    isEmptyLine = false;
                    break;
                }
            }

            // Если в строке есть значимый код, сохраняем её
            if (!isEmptyLine) {
                sb.append(text, start, nextLineStart);
            }
        }

        // Обрезаем начальные и конечные символы с кодом < 33
        String res = sb.toString();
        int startIdx = 0;
        int endIdx = res.length() - 1;

        while (startIdx <= endIdx && res.charAt(startIdx) < 33) {
            startIdx++;
        }
        while (endIdx >= startIdx && res.charAt(endIdx) < 33) {
            endIdx--;
        }

        if (startIdx > endIdx) {
            return "";
        }
        return res.substring(startIdx, endIdx + 1);
    }
}
