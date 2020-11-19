package be.howest.ti.mars.logic.data;

class H2Statements {
    protected static final String H2_GET_COLONIES = "SELECT * FROM MARSDEX.COLONIES";
    protected static final String H2_GET_COMPANY_FULL = h2StatementCompanyFull();
    protected static final String H2_GET_COMPANY_SIMPLE = "SELECT * FROM MARSDEX.COMPANIES WHERE ID = ?";
    private H2Statements() {
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
