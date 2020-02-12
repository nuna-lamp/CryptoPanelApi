package de.lamp.cryptopanel.repositories;

import de.lamp.cryptopanel.model.AuthData;
import de.lamp.cryptopanel.model.SigninPayload;
import de.lamp.cryptopanel.model.User;

public class Mutation {

    private UsersRepository usersRepository;

   public Mutation(UsersRepository usersRepository){
       this.usersRepository = usersRepository;
   }

   public User createUser(String name, AuthData auth){
       User newUser = new User(name, auth.getEmail(), auth.getPassword());
       return usersRepository.save(newUser);
   }

   public SigninPayload signinUser(AuthData auth){
       User user = usersRepository.findOneByEmail(auth.getEmail());
       if (user.getPassword().equals(auth.getPassword())){
           return new SigninPayload(user.getId(2), user);
       }

       return null;
   }



}
