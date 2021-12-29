package test.connection.config;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import connection.config.DatabaseConfig;
import connection.config.DatabaseConfigFactory;

class DatabaseConfigFactoryTest {
	private static final String EXPECTED_DRIVER = "org.postgresql.Driver";
    private static final String EXPECTED_URL = "jdbc:postgresql://localhost:5432/hotel";
    private static final String EXPECTED_USER = "postgres";
    private static final String EXPECTED_PASSWORD = "qwerty";

    private DatabaseConfigFactory configFactory = new DatabaseConfigFactory();

    @Test
    public void testCreateConfigShouldReturnConfigWhenMethodInvoked() {
        // given

        // when
        DatabaseConfig config = configFactory.createConfig();
        String driver = config.getDriver();
        String url = config.getUrl();
        String user = config.getUser();
        String password = config.getPassword();

        // then
        Assert.assertEquals(EXPECTED_DRIVER, driver);
        Assert.assertEquals(EXPECTED_URL, url);
        Assert.assertEquals(EXPECTED_USER, user);
        Assert.assertEquals(EXPECTED_PASSWORD, password);
    }
}
