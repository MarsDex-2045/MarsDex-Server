package be.howest.ti.mars.logic.classes;

import java.util.Calendar;

class TestData {
    protected final static Company company1 = new Company(1, "MarsDex", "B1gIr0n", "marsdex@mars.com", "+32472639356");
    protected final static Company company2 = new Company(2, "104th Discovery Battalion", "Expl0rer", "104thDB@mars.com", "+32472622356");
    protected final static Calendar dateData = new Calendar.Builder().setDate(2052, 11, 12).build();
    protected final static Resource resource1 = new Resource(1, "Panite", 20.552, 2.55, Calendar.getInstance());
    protected final static Resource resource2 = new Resource(2, "Berylium", 20.552, 2.55, Calendar.getInstance());
    protected final static Resource resource3 = new Resource(3, "Void Opals", 20.552, 2.55, Calendar.getInstance());
    protected final static Resource resource4 = new Resource(4, "Low Temperature Diamonds", 20.552, 2.55, Calendar.getInstance());
    protected final static Resource resource5 = new Resource(5, "Cobalt", 20.552, 2.55, Calendar.getInstance());

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
        res[0] = new Resource(1, "Panite", 200.234, 2500, date1);
        res[1] = new Resource(2, "Baurite", 145.666, 243, date2);
        res[2] = new Resource(1, "Panite", 200.234, 2500, date1);
        return res;
    }

    protected static Location[] generateLocations(){
        Location[] res = new Location[3];

        res[0] = new Location(15.666, 144.444,2645.333);
        res[1] = new Location(17.666, 144.444,2645.333);
        res[2] = new Location(17.666, 144.444,2645.333);

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
        res[0] = new Colony(1, "Jamerson's Lading", new Location(124.000, 243.000, 42.000));
        res[1] = new Colony(1, "Jamerson's Lading", new Location(124.000, 243.000, 42.000));
        res[2] = new Colony(2, "Bova Point", new Location(28.000, 243.636, 42.000));
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
}
