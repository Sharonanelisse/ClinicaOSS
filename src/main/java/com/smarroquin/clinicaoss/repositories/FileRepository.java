package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.File;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FileRepository extends BaseRepository<File, Long> {
    @Override
    protected Class<File> entity() { return File.class; }
}

