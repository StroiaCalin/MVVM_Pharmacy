package project;
import View.UserLogin;

import java.sql.SQLException;
import java.util.logging.Logger;

public class Main {
    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws SQLException {
        new UserLogin();
    }
}
