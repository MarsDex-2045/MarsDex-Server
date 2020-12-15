package be.howest.ti.mars.logic.classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

class TestData {
    protected final static Company company1 = new Company(1, "MarsDex", "B1gIr0n", "marsdex@mars.com", "+32472639356");
    protected final static Company company2 = new Company(2, "104th Discovery Battalion", "Expl0rer", "104thDB@mars.com", "+32472622356");
    protected final static LocalDateTime LDT1 = LocalDateTime.of(2052, 10, 12, 2, 2, 2);
    protected final static LocalDateTime LDT2 = LocalDateTime.of(2052, 11, 12, 10, 22, 22);
    protected final static LocalDate LD1 = LocalDate.of(2052, 10, 12);
    protected final static LocalDate LD2 = LocalDate.of(2052, 11, 12);
    protected final static Resource resource1 = new Resource(1, "Panite", 20.552, 2.55, LD1);
    protected final static Resource resource2 = new Resource(2, "Berylium", 20.552, 2.55, LD1);
    protected final static Resource resource3 = new Resource(3, "Void Opals", 20.552, 2.55, LD1);
    protected final static Resource resource4 = new Resource(4, "Low Temperature Diamonds", 20.552, 2.55, LD1);
    protected final static Resource resource5 = new Resource(5, "Cobalt", 20.552, 2.55, LD1);
    protected final static Location location1 = new Location(15.666, 144.444,2645.333);
    protected final static Location location2 = new Location(17.666, 144.444,2645.333);
    protected final static Location location2c = new Location(17.666, 144.444,2645.333);

    protected static Notification[] generateNotifications(){
        Notification[] res = new Notification[3];
        res[0] = new Notification(1, "Test Message", "Test Message");
        res[1] = new Notification(2, "Test Message", "Test Message");
        res[2] = new Notification(1, "Not a Test Message", "Not a Test Message");
        return res;
    }

    protected static Resource[] generateResources(){
        Resource[] res = new Resource[3];
        Calendar date1 = new Calendar.Builder().setDate(2052, 11, 13).build();
        Calendar date2 = new Calendar.Builder().setDate(2052, 11, 28).build();
        res[0] = new Resource(1, "Panite", 200.234, 2500, LD1);
        res[1] = new Resource(2, "Baurite", 145.666, 243, LD2);
        res[2] = new Resource(1, "Panite", 200.234, 2500, LD1);
        return res;
    }

    protected static Location[] generateLocations(){
        Location[] res = new Location[3];

        res[0] = location1;
        res[1] = location2;
        res[2] = location2c;

        return res;
    }

    protected static Company[] generateCompanies(){
        Company[] res = new Company[3];
        res[0] = new Company(1, "MarsDex", "BigIr0n", "marsdex@mars.com", "+324561621");
        res[1] = new Company(2, "104th Discovery Battalion", "Expl0rer", "104thdb@mars.com", "+32456162");
        res[2] = new Company(1, "MarsDex", "BigIr0n", "marsdex@mars.com", "+324561621");
        res[1].addResource(resource1);
        res[1].addResource(resource2);
        res[1].addResource(resource3);
        res[1].addResource(resource4);
        res[1].addResource(resource5);
        return res;
    }

    protected static Colony[] generateColonies(){
        Colony[] res = new Colony[3];
        res[0] = new Colony(1, "Jamerson's Lading", location2);
        res[1] = new Colony(1, "Jamerson's Lading", location2);
        res[2] = new Colony(2, "Bova Point", location1);
        company1.addResource(resource1);
        company1.addResource(resource2);
        company2.addResource(resource3);
        res[0].addCompany(company1);
        res[0].addCompany(company2);
        res[1].addCompany(company1);
        res[1].addCompany(company2);
        res[2].addCompany(company2);
        return res;
    }

    protected static Shipment[] generateShipments(){
        Shipment[] res = new Shipment[3];
        Set<Resource> resources = new HashSet<>();
        resources.add(resource1);
        resources.add(resource2);
        resources.add(resource3);
        res[0] = new Shipment(1, generateColonies()[0], LDT1, generateColonies()[2], LDT2, resources, Status.DELIVERED);
        res[1] = new Shipment(1, generateColonies()[0], LDT1, generateColonies()[2], LDT2, resources, Status.DELIVERED);
        res[2] = new Shipment(2, generateColonies()[0], generateColonies()[2], resources);
        return res;
    }
}
