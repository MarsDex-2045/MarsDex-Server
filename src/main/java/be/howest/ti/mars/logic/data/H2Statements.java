package be.howest.ti.mars.logic.data;

class H2Statements {
    protected static final String H2_GET_COLONIES = "SELECT * FROM MARSDEX.COLONIES";
    protected static final String H2_GET_COLONY= constructColonySQLStatement();

    private static String constructColonySQLStatement() {
        StringBuilder res = new StringBuilder();
        res.append("SELECT CN.NAME, CN.NAME, CN.LATITUDE, CN.LONGITUDE,  CN.ALTITUDE, R.NAME, CR.WEIGHT, CR.ADDED_TIMESTAMP, R.PRICE ")
                .append("FROM MARSDEX.COLONIES CN ")
                .append("JOIN MARSDEX.COLONIES_COMPANIES CC ON CN.ID = CC.COLONY_ID ")
                .append("JOIN MARSDEX.COMPANIES CM ON CC.COMPANY_ID = CM.ID ")
                .append("JOIN MARSDEX.COMPANIES_RESOURCES CR ON CM.ID = CR.COMPANY_ID ")
                .append("JOIN MARSDEX.RESOURCES R ON CR.RESOURCE_ID = R.ID ")
                .append("WHERE CN.ID = ?");
        return res.toString();
    }

    private H2Statements() {
    }
}
