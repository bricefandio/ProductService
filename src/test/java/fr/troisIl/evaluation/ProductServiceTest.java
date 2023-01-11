package fr.troisIl.evaluation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProductServiceTest {

    private Database db = null;
    private ProductService productService;

    private int countBefore = 0;

    @Before
    public void setUp() throws SQLException {
        String testDatabaseFileName = "product.db";

        // reset la BDD avant le test
        File file = new File(testDatabaseFileName);
        file.delete();

        db = new Database(testDatabaseFileName);
        db.createBasicSqlTable();

        productService = new ProductService(db);

        countBefore = count();
    }

    /**
     * Compte les produits en BDD
     *
     * @return le nombre de produit en BDD
     */
    private int count() throws SQLException {
        ResultSet resultSet = db.executeSelect("Select count(*) from Product");
        assertNotNull(resultSet);
        return resultSet.getInt(1);
    }

    @Test
    public void testInsert() throws SQLException {
        String sql = "INSERT VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testUpdate() throws SQLException {
        Product product = new Product();
        try {
            Product product = session.product(Product.class);
            product.TestUpdate(product);
            session.commit();
            return true;
        }catch (Exception e){
            e.getMessage();
            return false;
        }
    }

    @Test
    public void testFindById() throws SQLException {
        Product product = new Product();
        product.testFindById(product);
        try {
            product.findproduct(id == 2);
            Assert.fail("L'id du produit est 2"); // s'assure que la méthode lève bien une exception
        } catch (RuntimeException e) {
            // s'assure que le message d'erreur est bien le bon
            Assert.assertEquals("le produit n'existe pas", e.getMessage());
        }
    }

    @Test
    public void testDelete() throws SQLException {
        Product product = new Product();
        try {
            product.exists(2d);
            Assert.fail("le produit n'existe pas");
        } catch (RuntimeException e) {

            Assert.assertEquals("le produit exixte", e.getMessage());
        }
    }

}
