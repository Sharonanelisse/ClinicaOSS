package com.smarroquin.clinicaoss.controllers.files;

import com.smarroquin.clinicaoss.models.File;
import com.smarroquin.clinicaoss.service.CatalogService;
import com.smarroquin.clinicaoss.services.AzureBlobService;
import com.smarroquin.clinicaoss.utils.FileNameSanitizer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import java.io.IOException;
import java.io.InputStream;

@Named("fileUploadBean")
@RequestScoped
public class FileUploadBean {

    @Inject
    AzureBlobService azureBlobService;

    @Inject
    CatalogService catalogService;

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile uploadedFile = event.getFile();
        if (uploadedFile == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No file", "No se ha seleccionado archivo"));
            return;
        }
        String original = uploadedFile.getFileName();
        String sanitized = FileNameSanitizer.sanitizedIfValid(original);
        if (sanitized == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid file name", "Nombre de archivo inválido o extensión no permitida"));
            return;
        }

        String blobName = AzureBlobService.prefixWithTimestamp(sanitized);

        try (InputStream is = uploadedFile.getInputStream()) {
            String url = azureBlobService.upload(blobName, is, uploadedFile.getSize(), uploadedFile.getContentType());

            String lower = sanitized.toLowerCase();
            String ext = "";
            String baseName = sanitized;
            int idx = lower.lastIndexOf('.');
            if (idx > -1 && idx < sanitized.length() - 1) {
                ext = lower.substring(idx + 1);
                baseName = sanitized.substring(0, idx);
            }

            File fileEntity = new File();
            fileEntity.setFileName(baseName);
            fileEntity.setFileExt(ext);
            fileEntity.setFileSize(uploadedFile.getSize());
            fileEntity.setFileUrl(url);

            try {
                catalogService.saveFile(fileEntity);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Upload successful", url));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Upload saved to blob but metadata not persisted", e.getMessage()));
            }

        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Upload failed", e.getMessage()));
        } catch (IllegalStateException | IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Configuration error", e.getMessage()));
        }
    }
}
