package com.smarroquin.clinicaoss.repositories;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoleRepository extends BaseRepository<Role, Long> {
    @Override
    protected Class<Role> entity(){return Role.class;}
}
