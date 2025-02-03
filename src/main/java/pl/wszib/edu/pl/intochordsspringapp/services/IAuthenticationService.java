package pl.wszib.edu.pl.intochordsspringapp.services;

import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;

import java.util.Optional;

public interface IAuthenticationService {
    void login(String login, String password);
    void logout();
    String getLoginInfo();
}