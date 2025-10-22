package com.smarroquin.clinicaoss.repositories;

import com.smarroquin.clinicaoss.models.User;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepostory extends BaseRepository<User, Long> {
    @Override
    protected Class<User> entity(){return User.class;}
}
