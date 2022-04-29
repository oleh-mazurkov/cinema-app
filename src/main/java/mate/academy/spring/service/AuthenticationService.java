package mate.academy.spring.service;

import mate.academy.spring.model.User;

public interface AuthenticationService {
    User registerUser(String email, String password);

    User registerAdmin(String email, String password);
}
