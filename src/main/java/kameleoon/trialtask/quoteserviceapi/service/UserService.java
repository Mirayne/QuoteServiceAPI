package kameleoon.trialtask.quoteserviceapi.service;

import kameleoon.trialtask.quoteserviceapi.database.UsersTable;
import kameleoon.trialtask.quoteserviceapi.exceptions.RegisterDataAlreadyUsed;
import kameleoon.trialtask.quoteserviceapi.exceptions.ResourceNotFoundException;
import kameleoon.trialtask.quoteserviceapi.database.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UsersRepository usersRepository;

    public UsersTable findById(Long id) {
        return usersRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + id + " not found.")
        );
    }

    public UsersTable createUser(String username, String email, String password){
        UsersTable newUser = new UsersTable(username, email, password);
        try {
            usersRepository.save(newUser);
            return newUser;
        } catch (RuntimeException e) {
            throw new RegisterDataAlreadyUsed("Login or email already used.");
        }
    }
}
