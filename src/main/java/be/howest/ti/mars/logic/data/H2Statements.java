package be.howest.ti.mars.logic.data;

class H2Statements {
    protected static final String H2_GET_COLONIES = "SELECT * FROM MARSDEX.COLONIES";
    protected static final String H2_GET_COMPANY_FULL = h2StatementCompanyFull();
    protected static final String H2_GET_COMPANY_SIMPLE = "SELECT * FROM MARSDEX.COMPANIES WHERE ID = ?";
    protected static final String H2_INSERT_COMPANY = "INSERT INTO MARSDEX.COMPANIES (name,email,phone,password) VALUES (?,?,?,?)";
    protected static final String H2_INSERT_COLONYLINK = "INSERT INTO MARSDEX.COLONIES_COMPANIES VALUES (?,?)";
    protected static final String H2_GET_COLONY_ID_BY_NAME = "SELECT Id FROM marsdex.colonies WHERE name= '?'";
    protected static final String H2_GET_COLONY = h2StatementColonyFull();

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
}
