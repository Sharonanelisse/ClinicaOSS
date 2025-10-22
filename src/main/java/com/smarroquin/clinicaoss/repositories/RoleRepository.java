package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.Role;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoleRepository extends BaseRepository<Role, Long> {
    @Override
    protected Class<Role> entity(){return Role.class;}
}
