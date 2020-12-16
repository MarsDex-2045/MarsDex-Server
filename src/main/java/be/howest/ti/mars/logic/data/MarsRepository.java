package be.howest.ti.mars.logic.data;


import be.howest.ti.mars.logic.classes.*;
import be.howest.ti.mars.logic.exceptions.*;
import org.h2.tools.Server;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
MBL: this is only a starter class to use a H2 database.
To make this class useful, please complete it with the topics seen in the module OOA & SD
- Make sure the conf/config.json properties are correct.
- The h2 web console is available at http://localhost:9000
- The h2 database file is located at ~/mars-db
- Hint:
  - Mocking this repository is not needed. Create database creating and population script in plain SQL.
    Use the @Before or @Before each (depending on the type of test) to quickly setup a fully populated db.
 */
public class MarsRepository {
    private static final MarsRepository INSTANCE = new MarsRepository();
    private static final Logger LOGGER = Logger.getLogger(MarsRepository.class.getName());
    protected static final String GENERIC_SQL_ERROR = "Something went wrong with executing the query";
    private Server dbWebConsole;
    private String username;
    private String password;
    private String url;

    private MarsRepository() {
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.url, this.username, this.password);
    }

    public static MarsRepository getInstance() {
        return INSTANCE;
    }

    public void cleanUp() {
        dbWebConsole.stop();
    }

    public static void configure(String url, String username, String password, int console)
            throws SQLException {
        INSTANCE.username = username;
        INSTANCE.password = password;
        INSTANCE.url = url;
        INSTANCE.dbWebConsole = Server.createWebServer(
                "-ifNotExists",
                "-webPort", String.valueOf(console)).start();
    }

    protected static Resource convertToResource(ResultSet rs) throws SQLException {
        return new Resource(rs.getInt("RESOURCE_ID"),
                rs.getString("RESOURCE_NAME"),
                rs.getDouble("PRICE"),
                rs.getDouble("WEIGHT"),
                rs.getDate("ADDED_TIMESTAMP").toLocalDate());

    }

    protected static Colony transferToColony(ResultSet rs) {
        try {
            rs.next();
            int cId = rs.getInt("COLONY_ID");
            String cName = rs.getString("COLONY_NAME");
            Location location = new Location(rs.getDouble("LATITUDE"), rs.getDouble("LONGITUDE"), rs.getDouble("ALTITUDE"));
            return new Colony(cId, cName, location);
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, "The result set was empty, aborting...");
            throw new IdentifierException("Empty result set; Possible faulty colony ID.");
        }
    }
}

