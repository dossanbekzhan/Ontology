package demo;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created by beka on 6/21/18.
 */
public class ProxyAuthenticator extends Authenticator {

    private String user;
    private String password;


    public ProxyAuthenticator(String user, String password, String host, String port) {
        this.user = user;
        this.password = password;
        System.setProperty("http.proxyHost", host);
        System.setProperty("http.proxyPort", port);
    }




    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password.toCharArray());
    }
}