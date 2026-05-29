package by.it.group451051.mozol.lesson15;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

public class SourceScannerC {

    // Класс для хранения информации о нормализованном файле
    private static class JavaFileItem {
        String relativePath;
        String normalizedContent;

        JavaFileItem(String relativePath, String normalizedContent) {
            this.relativePath = relativePath;
            this.normalizedContent = normalizedContent;
        }
    }

    // Динамический массив для хранения объектов (аналог ArrayList)
    private static JavaFileItem[] fileItems = new JavaFileItem[16];
    private static int itemCount = 0;

    private static void addFileItem(JavaFileItem item) {
        if (itemCount == fileItems.length) {
            JavaFileItem[] newArray = new JavaFileItem[fileItems.length * 2];
            System.arraycopy(fileItems, 0, newArray, 0, itemCount);
            fileItems = newArray;
        }
        fileItems[itemCount++] = item;
    }

    public static void main(String[] args) {
        String srcPath = System.getProperty("user.dir") + File.separator + "src" + File.separator;
        File srcDir = new File(srcPath);

        if (srcDir.exists() && srcDir.isDirectory()) {
            processDirectory(srcDir, srcPath);
        }

        if (itemCount == 0) return;

        // Предварительно сортируем файлы лексикографически по пути для упорядоченного анализа и вывода
        sortFilesByPath();

        // Массив флагов для отслеживания того, какие файлы являются дубликатами (копиями) для текущего файла
        // Чтобы не выводить одну и ту же группу многократно
        boolean[] hasCopies = new boolean[itemCount];
        int[][] copiesMatrix = new int[itemCount][itemCount];
        int[] copiesCount = new int[itemCount];

        // Поиск копий на основе оптимизированного расстояния Левенштейна
        for (int i = 0; i < itemCount; i++) {
            for (int j = 0; j < itemCount; j++) {
                if (i == j) continue;

                String s1 = fileItems[i].normalizedContent;
                String s2 = fileItems[j].normalizedContent;

                // Оптимизация 1: Если разница длин >= 10, расстояние Левенштейна точно >= 10
                if (Math.abs(s1.length() - s2.length()) >= 10) {
                    continue;
                }

                // Вычисляем расстояние с ограничением (не более 10)
                int distance = getLevenshteinDistanceWithLimit(s1, s2, 10);
                if (distance < 10) {
                    copiesMatrix[i][copiesCount[i]++] = j;
                    hasCopies[i] = true;
                }
            }
        }

        // Вывод результатов: сначала оригинальный файл, затем пути к его копиям
        for (int i = 0; i < itemCount; i++) {
            if (hasCopies[i]) {
                System.out.println(fileItems[i].relativePath);
                for (int c = 0; c < copiesCount[i]; c++) {
                    int copyIdx = copiesMatrix[i][c];
                    System.out.println("    " + fileItems[copyIdx].relativePath);
                }
            }
        }
    }

    // Рекурсивный обход папок
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

    private static void processJavaFile(File file, String srcPath) {
        String content = readFileToString(file);
        if (content == null) return;

        // Игнорируем файлы тестов JUnit
        if (content.contains("@Test") || content.contains("org.junit.Test")) {
            return;
        }

        // 1. Удаление package и всех импортов
        String processed = removePackageAndImports(content);

        // 2. Удаление всех комментариев за O(n)
        processed = removeComments(processed);

        // 3. Замена последовательностей символов < 33 на один пробел (код 32)
        // 4. Выполнение trim() для полученной строки
        processed = flattenAndTrim(processed);

        if (processed.isEmpty()) return;

        String relativePath = file.getAbsolutePath().substring(srcPath.length());
        addFileItem(new JavaFileItem(relativePath, processed));
    }

    // Чтение файлов с безопасной обработкой MalformedInputException
    private static String readFileToString(File file) {
        StringBuilder sb = new StringBuilder();
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
            return null;
        }
    }

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
                    sb.append(c);
                }
            } else if (inBlockComment) {
                if (c == '*' && next == '/') {
                    inBlockComment = false;
                    i++;
                }
            } else if (inString) {
                sb.append(c);
                if (c == '\\') {
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

    // Приведение текста к одной строке: замена символов < 33 на пробел, сжатие пробелов и trim()
    private static String flattenAndTrim(String text) {
        StringBuilder sb = new StringBuilder();
        int len = text.length();
        boolean lastWasSpace = false;

        for (int i = 0; i < len; i++) {
            char c = text.charAt(i);
            if (c < 33) {
                if (!lastWasSpace) {
                    sb.append(' ');
                    lastWasSpace = true;
                }
            } else {
                sb.append(c);
                lastWasSpace = false;
            }
        }

        // Выполняем ручной аналог trim()
        String res = sb.toString();
        int start = 0;
        int end = res.length() - 1;

        while (start <= end && res.charAt(start) == ' ') {
            start++;
        }
        while (end >= start && res.charAt(end) == ' ') {
            end--;
        }

        if (start > end) return "";
        return res.substring(start, end + 1);
    }

    // Оптимизированный расчет расстояния Левенштейна с отсечением по лимиту (Ограничение < 10)
    private static int getLevenshteinDistanceWithLimit(String s1, String s2, int limit) {
        int n = s1.length();
        int m = s2.length();

        // Память O(min(N,M)): работаем только с двумя строками матрицы
        int[] prevRow = new int[m + 1];
        int[] currRow = new int[m + 1];

        for (int j = 0; j <= m; j++) {
            prevRow[j] = j;
        }

        for (int i = 1; i <= n; i++) {
            currRow[0] = i;
            int minInRow = currRow[0];

            for (int j = 1; j <= m; j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                currRow[j] = Math.min(Math.min(currRow[j - 1] + 1, prevRow[j] + 1), prevRow[j - 1] + cost);
                if (currRow[j] < minInRow) {
                    minInRow = currRow[j];
                }
            }

            // Оптимизация 2: Раннее завершение, если минимальное количество правок в текущей строке матрицы превысило лимит
            if (minInRow >= limit) {
                return limit;
            }

            // Перестановка ссылок на строки матрицы
            int[] temp = prevRow;
            prevRow = currRow;
            currRow = temp;
        }

        return prevRow[m];
    }

    // Лексикографическая сортировка файлов по их относительному пути (Сортировка вставками)
    private static void sortFilesByPath() {
        for (int i = 1; i < itemCount; i++) {
            JavaFileItem key = fileItems[i];
            int j = i - 1;
            while (j >= 0 && fileItems[j].relativePath.compareTo(key.relativePath) > 0) {
                fileItems[j + 1] = fileItems[j];
                j--;
            }
            fileItems[j + 1] = key;
        }
    }
}