package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import connection.config.DatabaseConfig;
import connection.config.DatabaseConfigFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class DBUtil {
    private static Connection conn = null;
    private static final Logger LOGGER = LogManager.getLogger(DBUtil.class);
    
    
    public static Connection getConnection() {
        if(conn != null)
            return conn;
        else
        {
            try
            {
            	DatabaseConfigFactory configFactory = new DatabaseConfigFactory();
                DatabaseConfig config = configFactory.createConfig();

                String driver = config.getDriver();
                String url = config.getUrl();
                String user = config.getUser();
                String password = config.getPassword();
                Class.forName(driver);
                conn = DriverManager.getConnection(url, user, password);
                LOGGER.info(conn);
            } catch (ClassNotFoundException | SQLException e) {
            	LOGGER.error(e.getMessage());
            }
            return conn;
        }

    }
}