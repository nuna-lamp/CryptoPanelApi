package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.model.User;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> findById(int id);

}

