package UI;

import java.sql.SQLException;

public interface LoginComplete {
    void login(String var1, String var2) throws SQLException;

    void register(String var1, String var2)throws SQLException;
}
