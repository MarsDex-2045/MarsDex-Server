package be.howest.ti.mars.logic.classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    @Test
    void testEquals() {
        Company[] companies = initCompanies();

        assertEquals(companies[0], companies[2]);
        assertNotEquals(companies[1], companies[0]);
    }

    @Test
    void testHashCode() {
        Company[] companies = initCompanies();

        assertEquals(companies[0].hashCode(), companies[2].hashCode());
        assertNotEquals(companies[0].hashCode(), companies[1].hashCode());
    }

    @Test
    void checkPassword() {
        Company[] companies = initCompanies();

        assertTrue(companies[0].checkPassword("BigIr0n"));
        assertFalse(companies[1].checkPassword("Shut-1n"));
    }

    @Test
    void allResourcesToJson() {
    }

    @Test
    void toJSON() {
    }

    private Company[] initCompanies(){
        Company[] res = new Company[3];
        res[0] = new Company(1, "MarsDex", "BigIr0n", "marsdex@mars.com", "+324561621");
        res[1] = new Company(2, "104th Discovery Battalion", "Expl0rer", "104thdb@mars.com", "+32456162");
        res[2] = new Company(1, "MarsDex", "BigIr0n", "marsdex@mars.com", "+324561621");
        return res;
    }
}