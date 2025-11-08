package com.smarroquin.clinicaoss.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del archivo es obligatorio")
    @Size(max = 128, message = "El nombre del archivo no puede exceder 128 caracteres")
    @Column(name = "file_name", nullable = false, length = 128)
    private String fileName;

    @NotBlank(message = "La extensión del archivo es obligatoria")
    @Size(max = 16, message = "La extensión es demasiado larga")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "La extensión solo puede contener caracteres alfanuméricos")
    @Column(name = "file_ext", nullable = false, length = 16)
    private String fileExt;

    @NotNull(message = "El tamaño del archivo es obligatorio")
    @Positive(message = "El tamaño del archivo debe ser un número positivo")
    @Column(name = "file_size", nullable = false)
    private Long fileSize; // en bytes

    @NotBlank(message = "La URL del archivo es obligatoria")
    @Size(max = 2048, message = "La URL es demasiado larga")
    @Pattern(regexp = "^(https?)://.+", message = "La URL debe comenzar con http:// o https://")
    @Column(name = "file_url", nullable = false, length = 2048)
    private String fileUrl;

    public File() {
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (fileName != null) fileName = fileName.trim();
        if (fileExt != null) fileExt = fileExt.trim().toLowerCase();
        if (fileUrl != null) fileUrl = fileUrl.trim();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileExt='" + fileExt + '\'' +
                ", fileSize=" + fileSize +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}
