package be.howest.ti.mars.logic.data;

class H2Statements {
    protected static final String H2_GET_COLONIES = "SELECT * FROM MARSDEX.COLONIES";
    protected static final String H2_GET_COLONY= "SELECT * FROM MARSDEX.COLONIES WHERE ID = ?";

    private H2Statements() {
    }
}
