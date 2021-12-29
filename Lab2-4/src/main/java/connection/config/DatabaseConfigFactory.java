package connection.config;

public class DatabaseConfigFactory {

    public DatabaseConfig createConfig() {
    	String url = "jdbc:postgresql://localhost:5432/hotel";
        String driverName = "org.postgresql.Driver";
        String user = "postgres";
        String password = "qwerty";
        return new DatabaseConfig(driverName, url, user, password);
    }
}
