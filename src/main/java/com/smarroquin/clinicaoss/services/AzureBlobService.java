package com.smarroquin.clinicaoss.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.InputStream;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@ApplicationScoped
public class AzureBlobService {

    private final BlobContainerClient containerClient;

    public AzureBlobService() {
        // Intentar leer configuración desde variables de entorno (o .env si está disponible)
        Dotenv dotenv = null;
        try {
            dotenv = Dotenv.load();
        } catch (Exception ignored) {}

        String connectionString = firstNonNull(
                System.getenv("AZURE_STORAGE_CONNECTION_STRING"),
                dotenv != null ? dotenv.get("AZURE_STORAGE_CONNECTION_STRING") : null
        );

        String container = firstNonNull(
                System.getenv("AZURE_BLOB_CONTAINER"),
                dotenv != null ? dotenv.get("AZURE_BLOB_CONTAINER") : "uploads"
        );

        if (connectionString == null || connectionString.isBlank()) {
            // Dejar que la aplicación lance una excepción al intentar usar el servicio si no está configurado
            this.containerClient = null;
            return;
        }

        BlobServiceClient client = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();

        BlobContainerClient containerClient = client.getBlobContainerClient(container);
        if (!containerClient.exists()) {
            containerClient.create();
        }
        this.containerClient = containerClient;
    }
package com.smarroquin.clinicaoss.services;
    private static String firstNonNull(String... vals) {
        for (String v : vals) if (v != null && !v.isBlank()) return v;
        return null;
    }

    /**
     * Sube el contenido al blob con el nombre indicado y devuelve la URL pública (si el contenedor es público)
     */
    public String upload(String blobName, InputStream data, long length, String contentType) {
        if (containerClient == null) throw new IllegalStateException("Azure Blob Storage no configurado (AZURE_STORAGE_CONNECTION_STRING)");
        if (blobName == null || blobName.isBlank()) throw new IllegalArgumentException("blobName requerido");
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        blobClient.upload(data, length, true);
        if (contentType != null && !contentType.isBlank()) {
            BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(contentType);
            blobClient.setHttpHeaders(headers);
        }
        return blobClient.getBlobUrl();
    }

    /**
     * Genera un prefijo con timestamp para evitar colisiones
     */
    public static String prefixWithTimestamp(String name) {
        String ts = OffsetDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.ROOT));
        return ts + "_" + name;
    }
}

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.InputStream;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@ApplicationScoped
public class AzureBlobService {

    private final BlobContainerClient containerClient;

    public AzureBlobService() {
        // Intentar leer configuración desde variables de entorno (o .env si está disponible)
        Dotenv dotenv = null;
        try {
            dotenv = Dotenv.load();
        } catch (Exception ignored) {}

        String connectionString = firstNonNull(
                System.getenv("AZURE_STORAGE_CONNECTION_STRING"),
                dotenv != null ? dotenv.get("AZURE_STORAGE_CONNECTION_STRING") : null
        );

        String container = firstNonNull(
                System.getenv("AZURE_BLOB_CONTAINER"),
                dotenv != null ? dotenv.get("AZURE_BLOB_CONTAINER") : "uploads"
        );

        if (connectionString == null || connectionString.isBlank()) {
            // Dejar que la aplicación lance una excepción al intentar usar el servicio si no está configurado
            this.containerClient = null;
            return;
        }

        BlobServiceClient client = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();

        BlobContainerClient containerClient = client.getBlobContainerClient(container);
        if (!containerClient.exists()) {
            containerClient.create();
        }
        this.containerClient = containerClient;
    }

    private static String firstNonNull(String... vals) {
        for (String v : vals) if (v != null && !v.isBlank()) return v;
        return null;
    }

    /**
     * Sube el contenido al blob con el nombre indicado y devuelve la URL pública (si el contenedor es público)
     */
    public String upload(String blobName, InputStream data, long length, String contentType) {
        if (containerClient == null) throw new IllegalStateException("Azure Blob Storage no configurado (AZURE_STORAGE_CONNECTION_STRING)");
        if (blobName == null || blobName.isBlank()) throw new IllegalArgumentException("blobName requerido");
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        blobClient.upload(data, length, true);
        if (contentType != null && !contentType.isBlank()) {
            BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(contentType);
            blobClient.setHttpHeaders(headers);
        }
        return blobClient.getBlobUrl();
    }

    /**
     * Genera un prefijo con timestamp para evitar colisiones
     */
    public static String prefixWithTimestamp(String name) {
        String ts = OffsetDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.ROOT));
        return ts + "_" + name;
    }
}
