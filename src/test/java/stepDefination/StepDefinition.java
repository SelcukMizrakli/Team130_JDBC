package stepDefinition;

import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import manage.QueryManage;
import org.junit.Assert;
import utilities.ConfigReader;
import utilities.JDBCReusableMethods;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StepDefinition {

    PreparedStatement preparedStatement;
    ResultSet resultSet;
    String query;
    int rowCount;
    int id;

    Faker faker = new Faker();

    QueryManage queryManage = new QueryManage();

    @Given("Database baglantisi kurulur.")
    public void database_baglantisi_kurulur() {
        JDBCReusableMethods.getConnection();
    }

    @Given("Query01 hazirlanir ve execute edilir.")
    public void query01_hazirlanir_ve_execute_edilir() throws SQLException {
        query = "SELECT user_id FROM u168183796_qaloantec.deposits WHERE amount BETWEEN 100 AND 500";
        resultSet = JDBCReusableMethods.getStatement().executeQuery(query);
    }

    @Given("ResultSet01 sonuclari islenir.")
    public void result_set01_sonuclari_islenir() throws SQLException {
        resultSet.next();
        int actualResult = resultSet.getInt("user_id");
        System.out.println(actualResult);
        int expectedResult = 1;

        assertEquals(expectedResult, actualResult);
    }

    @Given("Database baglantisi kapatilir.")
    public void database_baglantisi_kapatilir() {
        JDBCReusableMethods.closeConnection();
    }


    //  QUERY02

    @Given("Query02 hazirlanir ve execute edilir.")
    public void query02_hazirlanir_ve_execute_edilir() throws SQLException {

        query = queryManage.getQuery02();
        resultSet = JDBCReusableMethods.getStatement().executeQuery(query);
    }

    @Given("ResultSet02 sonuclari islenir.")
    public void result_set02_sonuclari_islenir() throws SQLException {

        /* test datalarimiz bize su sekilde verildi
          - 5 Minutes
          - 10 Minutes
        */

        List<String> expectedNames = new ArrayList<>();
        expectedNames.add("5 Minutes");
        expectedNames.add("10 Minutes");


        List<String> actualNames = new ArrayList<>();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            actualNames.add(name);
        }

        for (int i = 0; i < actualNames.size(); i++) {

            assertEquals(expectedNames.get(i), actualNames.get(i));
            System.out.println(i + ". index dogrulandi.");

        }

    }

    // ------------------UPDATE QUERY 01 (Normal Statement ile) ------------------------

    @Given("UpdateQuery01 olusturulur. ve execute edilir.")
    public void update_query01_olusturulur_ve_execute_edilir() throws SQLException {

        query = queryManage.getUpdateQuery01();
        rowCount = JDBCReusableMethods.updateQuery(query);

    }

    @Given("UpdateQuery01 sonuclari islenir.")
    public void update_query01_sonuclari_islenir() {

        assertEquals(18, rowCount);
    }

    // ------------------UPDATE QUERY 02 (Prepared Statement ile) ------------------------


    @Given("Prepared UpdateQuery01 olusturulur. ve execute edilir.")
    public void prepared_update_query01_olusturulur_ve_execute_edilir() throws SQLException {

        query = queryManage.getUpdateQuery02();

        preparedStatement = JDBCReusableMethods.getConnection().prepareStatement(query);

        preparedStatement.setInt(1, 147852369);
        preparedStatement.setString(2, "%e_");

        rowCount = preparedStatement.executeUpdate();


    }

    @Given("Prepared UpdateQuery01 sonuclari islenir.")
    public void prepared_update_query01_sonuclari_islenir() {

        assertEquals(18, rowCount);
    }


// ------------------- INSERT QUERY 03 ----------------------------

    @Given("InsertQuery hazirlanir ve execute edilir")
    public void ınsert_query_hazirlanir_ve_execute_edilir() throws SQLException {

        query = queryManage.getInsertQuery03();
        preparedStatement = JDBCReusableMethods.getConnection().prepareStatement(query);

        // INSERT INTO u168183796_qaloantec.admin_password_resets
        // (id,email,token,status) VALUES (?,?,?,?);


        preparedStatement.setInt(1, 11);
        preparedStatement.setString(2, "tyuzotuz@gmail.com");
        preparedStatement.setString(3, "T13099");
        preparedStatement.setInt(4, 1);

        rowCount = preparedStatement.executeUpdate();


    }

    @Given("InsertQuery sonuclari dogrulanir")
    public void ınsert_query_sonuclari_dogrulanir() {

        assertEquals(1, rowCount);

    }
    // ************************* DELETE QUERY 01 **********************************

    @Given("Delete Query hazirlanir ve execute edilir")
    public void delete_query_hazirlanir_ve_execute_edilir() throws SQLException {

        query = queryManage.getDeleteQuery01();
        preparedStatement = JDBCReusableMethods.getConnection().prepareStatement(query);

        String silinecekID = ConfigReader.getProperty("DeleteQuery01SilinecekID");

        preparedStatement.setString(1, silinecekID);
        rowCount = preparedStatement.executeUpdate();

        System.out.println(rowCount);
        assertEquals(1, rowCount);


        String controlQuery = queryManage.getDeleteControlQuery();
        preparedStatement = JDBCReusableMethods.getConnection().prepareStatement(controlQuery);

        preparedStatement.setString(1, silinecekID);
        resultSet = preparedStatement.executeQuery();


        assertFalse(resultSet.next());


    }

    @Given("Datanin silindigi dogrulanir.")
    public void datanin_silindigi_dogrulanir() {

        // Bu methodda olmasi greken kodlari bir ustteki methoda tasidik.

    }


    // **************************** DELETE QUERY 02 **********************


    @Given("support_attachments tablosuna veri girilir.")
    public void support_attachments_tablosuna_veri_girilir() throws SQLException {

        query = queryManage.getDeleteQuery02Insert();
        preparedStatement = JDBCReusableMethods.getConnection().prepareStatement(query);

        id = faker.number().numberBetween(700, 900);

        // INSERT INTO u168183796_qaloantec.support_attachments
        // (id, support_message_id, attachment, created_at) VALUES (?,?,?,?)
        preparedStatement.setInt(1, id);
        preparedStatement.setInt(2, 987456321);
        preparedStatement.setString(3, "658401a61409c1703149990.png");
        preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));

        rowCount = preparedStatement.executeUpdate();

        System.out.println("Kayit edilen data id'si :  " + id);

    }

    @Given("support_attachments tablosuna eklenen veri silinir")
    public void support_attachments_tablosuna_eklenen_veri_silinir() throws SQLException {

        query = queryManage.getDeleteQuery02();
        preparedStatement = JDBCReusableMethods.getConnection().prepareStatement(query);

        preparedStatement.setInt(1, id);
        rowCount = preparedStatement.executeUpdate();
        System.out.println(id + " id numarali veri silindi");

        assertEquals(1, rowCount);
    }

    @Given("Verinin silindigi dogrulanir.")
    public void verinin_silindigi_dogrulanir() throws SQLException {

        query = queryManage.getDeleteQuery02Control();
        preparedStatement = JDBCReusableMethods.getConnection().prepareStatement(query);

        preparedStatement.setInt(1, id);

        resultSet = preparedStatement.executeQuery();
        assertFalse(resultSet.next());

        if (!resultSet.next()) {
            System.out.println(id + " id numarali veri bulunamadi");
        }

    }

}