package com.smarroquin.clinicaoss.utils;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;



public final class FileNameSanitizer {

    private static final Pattern WHITESPACE = Pattern.compile("\\s+");
    private static final Pattern INVALID_CHARS = Pattern.compile("[^A-Za-z0-9._]");
    private static final Pattern MULTI_DOTS = Pattern.compile("\\.{2,}");

    private static final Set<String> ALLOWED_EXTENSIONS = new HashSet<>(Arrays.asList(
            "png", "jpg", "jpeg", "gif", "svg",
            "pdf", "doc", "docx", "txt", "rtf", "xls", "xlsx",
            "odt", "ppt", "pptx"
    ));

    private static final Set<String> DANGEROUS_EXTENSIONS = new HashSet<>(Arrays.asList(
            "php", "php3", "phtml", "pht", "asp", "aspx", "jsp", "jspx", "exe", "sh", "pl", "cgi"
    ));

    private FileNameSanitizer() {}


    public static String sanitize(String original) {
        if (original == null) return null;
        String name = original.trim();
        name = Normalizer.normalize(name, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        name = WHITESPACE.matcher(name).replaceAll("_");
        name = name.replaceAll("[—–\\-]+", "_");
        name = INVALID_CHARS.matcher(name).replaceAll("");
        name = MULTI_DOTS.matcher(name).replaceAll(".");
        while (name.startsWith(".")) {
            name = name.substring(1);
        }
        if (name.length() > 128) {
            name = name.substring(0, 128);
        }
        if (name.isEmpty()) {
            return "file";
        }
        return name;
    }


    public static boolean isValid(String filename) {
        if (filename == null || filename.trim().isEmpty()) return false;
        String lower = filename.toLowerCase(Locale.ROOT).trim();
        if (lower.contains("/") || lower.contains("\\\\") || lower.contains("..")) return false;
        String[] parts = lower.split("\\.");
        if (parts.length < 2) return false;
        String extension = parts[parts.length - 1];
        if (!ALLOWED_EXTENSIONS.contains(extension)) return false;
        for (int i = 0; i < parts.length - 1; i++) {
            String seg = parts[i];
            if (DANGEROUS_EXTENSIONS.contains(seg)) return false;
        }
        return true;
    }

    public static String sanitizedIfValid(String original) {
        String s = sanitize(original);
        return isValid(s) ? s : null;
    }
}