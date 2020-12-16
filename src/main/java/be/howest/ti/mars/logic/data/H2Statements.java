package be.howest.ti.mars.logic.data;

class H2Statements {
    protected static final String H2_GET_COLONIES = "SELECT * FROM MARSDEX.COLONIES";
    protected static final String H2_GET_COMPANY_FULL = h2StatementCompanyFull();
    protected static final String H2_GET_COMPANY_SIMPLE = "SELECT * FROM MARSDEX.COMPANIES WHERE ID = ?";
    protected static final String H2_INSERT_COMPANY = "INSERT INTO MARSDEX.COMPANIES (name,email,phone,password) VALUES (?,?,?,?)";
    protected static final String H2_INSERT_COLONYLINK = "INSERT INTO MARSDEX.COLONIES_COMPANIES VALUES (?,?)";
    protected static final String H2_GET_COLONY = h2StatementColonyFull();
    protected static final String H2_GET_TRANSPORT_DETAILS = h2StatementCompanyTransportDetails();
    protected static final String H2_GET_TRANSPORT_RESOURCES = h2StatementCompanyTransportResources();
    protected static final String H2_INSERT_RESOURCE = "INSERT INTO MARSDEX.RESOURCES(price, name) VALUES (?, ?);";
    protected static final String H2_INSERT_COMPANIES_RESOURCES = "INSERT INTO MARSDEX.COMPANIES_RESOURCES(company_id, resource_id, weight, added_timestamp) VALUES (?, ?, ?, ?);";
    protected static final String H2_GET_RESOURCE_COMPANY = h2StatementResourceFromCompany();
    protected static final String H2_GET_COLONY_OF_COMPANY = h2StatementColonyFromCompany();
    protected static final String H2_DELETE_COMPANY = "DELETE FROM MARSDEX.COMPANIES WHERE ID = ?";
    protected static final String H2_GET_RESOURCE_BY_NAME = h2StatementCompanyResourceByName();
    protected static final String H2_UPDATE_RESOURCE = "UPDATE MARSDEX.COMPANIES_RESOURCES SET WEIGHT = ? WHERE COMPANY_ID = ? AND RESOURCE_ID = ?;";
    protected static final String H2_OCCURRENCE_OF_RESOURCE = h2StatementOccurrenceResource();
    protected static final String H2_DELETE_RESOURCE_ENTRY = "DELETE FROM MARSDEX.COMPANIES_RESOURCES CR WHERE CR.COMPANY_ID = ? AND CR.RESOURCE_ID = ?;";
    protected static final String H2_DELETE_RESOURCE = "DELETE FROM MARSDEX.RESOURCES R WHERE R.ID = ?";

    private static final String MARSDEX_RESOURCES = "FROM MARSDEX.RESOURCES R ";
    private static final String JOIN_CR_ON_R = "JOIN MARSDEX.COMPANIES_RESOURCES CR ON R.ID = CR.RESOURCE_ID ";

    private H2Statements() {
    }

    private static String h2StatementColonyFull() {
        return "SELECT CN.ID AS COLONY_ID, CN.NAME AS COLONY_NAME, CN.LATITUDE, CN.LONGITUDE,  CN.ALTITUDE," +
                " CC.COMPANY_ID AS COMPANY_ID " +
                "FROM MARSDEX.COLONIES CN " +
                "JOIN MARSDEX.COLONIES_COMPANIES CC ON CN.ID = CC.COLONY_ID " +
                "JOIN MARSDEX.COMPANIES CM ON CC.COMPANY_ID = CM.ID " +
                "WHERE CN.ID = ?";
    }

    private static String h2StatementCompanyFull(){
        return "SELECT C.ID AS COMPANY_ID, C.NAME AS COMPANY_NAME, C.PASSWORD, C.EMAIL, C.PHONE, " +
                "R.ID AS RESOURCE_ID, R.NAME AS RESOURCE_NAME, R.PRICE, CR.WEIGHT, CR.ADDED_TIMESTAMP " +
                "FROM MARSDEX.COMPANIES C " +
                "JOIN MARSDEX.COMPANIES_RESOURCES CR ON C.ID = CR.COMPANY_ID " +
                "JOIN MARSDEX.RESOURCES R ON CR.RESOURCE_ID = R.ID " +
                "WHERE C.ID = ?";
    }

    private static String h2StatementCompanyTransportDetails() {
        return "SELECT * " +
                "FROM MARSDEX.SHIPMENTS S " +
                "WHERE SENDER_ID = ? OR RECEIVER_ID = ? " +
                "ORDER BY ID";
    }

    private static String h2StatementCompanyTransportResources() {
        return "SELECT R.ID AS RESOURCE_ID, R.NAME AS RESOURCE_NAME, SR.WEIGHT, SR.ADDED_TIMESTAMP, R.PRICE " +
                "FROM MARSDEX.SHIPMENTS_RESOURCES SR " +
                "JOIN MARSDEX.RESOURCES R ON SR.RESOURCE_ID = R.ID " +
                "WHERE SR.SHIPMENT_ID = ?";
    }

    private static String h2StatementResourceFromCompany() {
        return "SELECT R.*, CR.WEIGHT " +
                MARSDEX_RESOURCES +
                JOIN_CR_ON_R +
                "WHERE R.NAME=? AND CR.COMPANY_ID = ?";
    }

    private static String h2StatementColonyFromCompany() {
        return "SELECT C.ID AS COLONY_ID, C.NAME AS COLONY_NAME, C.*" +
                "FROM MARSDEX.COLONIES C " +
                "JOIN MARSDEX.COLONIES_COMPANIES CC ON C.ID = CC.COLONY_ID " +
                "WHERE CC.COMPANY_ID = ?";
    }

    private static String h2StatementCompanyResourceByName() {
        return "SELECT * " +
                MARSDEX_RESOURCES +
                JOIN_CR_ON_R +
                "WHERE LOWER(R.NAME) = ? AND CR.COMPANY_ID = ? ";
    }

    private static String h2StatementOccurrenceResource() {
        return "SELECT COUNT(*) AS OCCURRENCE " +
                MARSDEX_RESOURCES +
                JOIN_CR_ON_R +
                "WHERE CR.RESOURCE_ID = ? ";
    }
}
