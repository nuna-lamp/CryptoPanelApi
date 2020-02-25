package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.model.AuthData;
import de.lamp.cryptopanel.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UsersRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UsersRepository userRepository;
    AuthData authData;

    @Override
    public List<User> findById(int id) {
        return userRepository.findById(id);
    }

    /*@Override
    public User findOneByEmail(String email) {
        return userRepository.findOneByEmail(email);
        //User found = userRepository.findOneByEmail("nuna@bopp.de");
        User found = userRepository.findOneByEmail(authData.getEmail());
        if(found.getPassword().equals(authData.getPassword())){
            return userRepository.findOneByEmail(email);
        }
        //throw new GraphQLException("Invalid credentials");
    }*/

}
