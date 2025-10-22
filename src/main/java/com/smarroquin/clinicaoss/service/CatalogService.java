package com.smarroquin.clinicaoss.service;


import com.smarroquin.clinicaoss.models.Role;
import com.smarroquin.clinicaoss.models.User;
import com.smarroquin.clinicaoss.repositories.RoleRepository;
import com.smarroquin.clinicaoss.repositories.UserRepostory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ApplicationScoped
public class CatalogService implements Serializable {
    private static final long serialVersionUID = 1L;

    public CatalogService() { }

    @Inject
    UserRepostory userRepository;
    @Inject
    RoleRepository roleRepository;


    public List<User> users(){ return userRepository.findAll(); }
    public User guardar(User user){ return userRepository.guardar(user); }
    public void eliminar(User user){ userRepository.eliminar(user); }
    public User findUserById(Long id) { return userRepository.find(id); }

    public List<Role> roles() { return roleRepository.findAll(); }
    public Role guardar(Role role) { return roleRepository.guardar(role); }
    public void eliminar(Role role) { roleRepository.eliminar(role); }
    
}