package edu.school21.services;

import edu.school21.exceptions.AlwaysAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {
    UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    boolean authenticate(String login, String password) {
        if (usersRepository.findByLogin(login).isStatusAuthentication()) {
            throw new AlwaysAuthenticatedException("он заходил");
        }
        User user = usersRepository.findByLogin(login);
        if (user.getPassword().equals(password)) {
            user.setStatusAuthentication(true);
            usersRepository.update(user);
            return true;
        }
        return false;
    }
}