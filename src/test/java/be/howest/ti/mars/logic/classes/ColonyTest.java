package be.howest.ti.mars.logic.classes;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class ColonyTest {
    private final Company company1 = new Company(1, "MarsDex", "B1gIr0n", "marsdex@mars.com", "+32472639356");
    private final Company company2 = new Company(2, "104th Discovery Battalion", "Expl0rer", "104thDB@mars.com", "+32472622356");
    private final Calendar dateData = new Calendar.Builder().setDate(2052, 11, 12).build();
    private final Resource resource1 = new Resource(1, "Panite", 234.667, 22.000, dateData);
    private final Resource resource2 = new Resource(2, "Berylium", 22.356, 10.050, dateData);
    private final Resource resource3 = new Resource(2, "Cobalt", 25.356, 12.150, dateData);
    private static final Logger LOGGER = Logger.getLogger(ColonyTest.class.getName());


    @Test
    void addCompany() {
        Colony[] colonies = generateColonies();

        assertTrue(colonies[1].getCompanies().contains(company1));
    }

    @Test
    void toJSON() {
        Colony[] colonies = generateColonies();

        JsonObject json = colonies[0].toJSON();

        LOGGER.log(Level.INFO, json.toString());
        //for (int i = 0; i < this.company1.allResourcesToJSONArray().size(); i++){
            //assertTrue(json.getJsonArray("resources").contains(this.company1.allResourcesToJSONArray().getJsonObject(i)));
        //}
        //for (int i = 0; i < this.company2.allResourcesToJSONArray().size(); i++){
            //assertTrue(json.getJsonArray("resources").contains(this.company2.allResourcesToJSONArray().getJsonObject(i)));
        //}
    }

    @Test
    void testEquals() {
        Colony[] colonies = generateColonies();

        assertEquals(colonies[0], colonies[1]);
        assertNotEquals(colonies[1], colonies[2]);
    }

    private Colony[] generateColonies(){
        Colony[] res = new Colony[3];
        res[0] = new Colony(1, "Jamerson's Lading", new Location(124.000, 243.000, 42.000));
        res[1] = new Colony(1, "Jamerson's Lading", new Location(124.000, 243.000, 42.000));
        res[2] = new Colony(2, "Bova Point", new Location(28.000, 243.636, 42.000));
        company1.addResource(resource1, 22);
        company1.addResource(resource2, 33);
        company2.addResource(resource3, 43);
        res[0].addCompany(company1);
        res[0].addCompany(company2);
        res[1].addCompany(company1);
        res[1].addCompany(company2);
        res[2].addCompany(company2);
        return res;
    }
}