package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class UsuarioRepository extends BaseRepository<Usuario, Long> {
    @Override
    protected Class<Usuario> entity(){return Usuario.class;}

    @PersistenceContext
    private EntityManager em;

    public List<Usuario> findAll() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class)
                .getResultList();
    }

    public Usuario guardar(Usuario usuario) {
        if (usuario.getId() == null) {
            em.persist(usuario);
            return usuario;
        } else {
            return em.merge(usuario);
        }
    }

    public void eliminar(Usuario usuario) {
        Usuario managed = em.find(Usuario.class, usuario.getId());
        if (managed != null) {
            em.remove(managed);
        }
    }

    public Usuario find(Long id) {
        return em.find(Usuario.class, id);
    }
}
