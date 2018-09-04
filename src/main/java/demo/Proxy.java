package demo;

/**
 * Created by beka on 8/30/18.
 */
public class Proxy {
    private String username;
    private String pass;
    private String host;
    private String port;

    public Proxy(String username, String pass, String host, String port) {
        this.username = username;
        this.pass = pass;
        this.host = host;
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }
}
