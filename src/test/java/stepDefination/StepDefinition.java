package stepDefinition;

import io.cucumber.java.en.Given;
import manage.QueryManage;
import org.junit.Assert;
import utilities.JDBCReusableMethods;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StepDefinition {

    PreparedStatement preparedStatement;
    ResultSet resultSet;
    String query;
    int rowCount;

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
        while(resultSet.next()){
            String name = resultSet.getString("name");
            actualNames.add(name);
        }

        for (int i = 0; i< actualNames.size(); i++ ){

            assertEquals(expectedNames.get(i),actualNames.get(i));
            System.out.println(i +". index dogrulandi.");

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

        assertEquals(18,rowCount);
    }


// ------------------- INSERT QUERY 03 ----------------------------

    @Given("InsertQuery hazirlanir ve execute edilir")
    public void ınsert_query_hazirlanir_ve_execute_edilir() throws SQLException {

        query = queryManage.getInsertQuery03();
        preparedStatement = JDBCReusableMethods.getConnection().prepareStatement(query);

        // INSERT INTO u168183796_qaloantec.admin_password_resets
        // (id,email,token,status) VALUES (?,?,?,?);


        preparedStatement.setInt(1, 20);
        preparedStatement.setString(2,"tyuzotuz@gmail.com");
        preparedStatement.setString(3, "T13099");
        preparedStatement.setInt(4,1);

        rowCount = preparedStatement.executeUpdate();


    }
    @Given("InsertQuery sonuclari dogrulanir")
    public void ınsert_query_sonuclari_dogrulanir() {

        assertEquals(1,rowCount);

    }






}