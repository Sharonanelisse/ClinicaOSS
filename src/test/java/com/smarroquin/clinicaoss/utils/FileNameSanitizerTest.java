package com.smarroquin.clinicaoss.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileNameSanitizerTest {
    @Test
    public void testSanitizeAndValidate_validNames() {
        assertEquals("archivo_pdf.pdf", FileNameSanitizer.sanitize("archivo pdf.pdf"));
        assertTrue(FileNameSanitizer.isValid("documento.pdf"));
        assertTrue(FileNameSanitizer.isValid("imagen_2025-11-05.png"));
        assertEquals("miarchivo_txt.txt", FileNameSanitizer.sanitize("mi-archivo txt.txt"));
    }

    @Test
    public void testSanitizeAndValidate_invalidNames() {
        assertFalse(FileNameSanitizer.isValid("malicious.php.pdf"));
        assertFalse(FileNameSanitizer.isValid("pdf.php"));
        assertFalse(FileNameSanitizer.isValid("../secreto.pdf"));
        assertFalse(FileNameSanitizer.isValid("/etc/passwd.txt"));
        assertFalse(FileNameSanitizer.isValid("script.sh"));
    }
}


