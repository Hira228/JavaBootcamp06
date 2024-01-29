package edu.school21.services;

import edu.school21.exceptions.AlwaysAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UsersServiceImplTest {

    @Mock
    UsersRepository usersRepository;

    @Test
    public void findByLoginTestAlwaysAuthenticatedException() {
        UsersServiceImpl users = new UsersServiceImpl(usersRepository);
        User user = new User(1L, "mama", "1234");
        user.setStatusAuthentication(true);

        Mockito.when(usersRepository.findByLogin("mama")).thenThrow(AlwaysAuthenticatedException.class);

        Assertions.assertThrows(AlwaysAuthenticatedException.class, () -> users.authenticate("mama", "1234"));
    }

    @Test
    public void findByLoginTestFalse() {
        UsersServiceImpl users = new UsersServiceImpl(usersRepository);
        User user = new User(1L, "mama", "1234");
        user.setStatusAuthentication(false);

        Mockito.when(usersRepository.findByLogin("mama")).thenReturn(user);

        Assertions.assertFalse(() -> users.authenticate("mama", "12"));
    }

    @Test
    public void findByLoginTestTrue() {
        UsersServiceImpl users = new UsersServiceImpl(usersRepository);
        User user = new User(1L, "mama", "1234");
        user.setStatusAuthentication(false);

        Mockito.when(usersRepository.findByLogin("mama")).thenReturn(user);

        Assertions.assertTrue(() -> users.authenticate("mama", "1234"));
    }

    @Test
    public void findByLoginTestEntityNotFoundException() {
        UsersServiceImpl users = new UsersServiceImpl(usersRepository);
        User user = new User(1L, "mama", "1234");
        user.setStatusAuthentication(false);

        Mockito.when(usersRepository.findByLogin("mam")).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> users.authenticate("mam", "1234"));
    }

    @Test
    public void checkUpdateVerify() {
        UsersServiceImpl users = new UsersServiceImpl(usersRepository);
        User user = new User(1L, "mama", "1234");
        user.setStatusAuthentication(false);

        Mockito.when(usersRepository.findByLogin("mama")).thenReturn(user);
        Assertions.assertTrue(users.authenticate("mama", "1234"));

        Mockito.verify(usersRepository,Mockito.times(2)).findByLogin("mama");
        Mockito.verify(usersRepository,Mockito.times(1)).update(user);
    }
}
