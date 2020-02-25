package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, Long>, UserRepositoryCustom{

    List<User> findAll();

    List<User> findById(int id);

    User findOneById(int id);

    User findOneByEmail(String email);

}