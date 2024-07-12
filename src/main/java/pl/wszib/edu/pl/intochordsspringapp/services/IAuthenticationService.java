package pl.wszib.edu.pl.intochordsspringapp.services;

public interface IAuthenticationService {
    void login(String login, String password);
    void logout();
    String getLoginInfo();
}