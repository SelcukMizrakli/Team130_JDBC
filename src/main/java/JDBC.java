import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

    // Sizden bir JDBC testi yapilmasi istendiginde yapacaginiz ilk is:

    // Ilgili DATABASE YONETICISI ile iletisime gecerek
    // DATABASE ACCESS INFORMATION'larini edinmek.


    /*
            type    jdbc:mysql
            host/ip 45.87.83.5
            port    3306
            database_name   u168183796_qaloantec
            username    u168183796_qaloantecuser
            password    0&vG1A/MuWN
     */


    /*
    URL: "jdbc:mysql://45.87.83.5/u168183796_qaloantec";
    USERNAME= "u168183796_qaloantecuser";
    PASSWORD="0&vG1A/MuWN";

     */


    // Verilen access informationlardan URL USERNAME ve PASSWORD cikartmaniz gerekir.

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        // 1.ADIM: Dogru Sunucuyu Ekle

        Class.forName("com.mysql.cj.jdbc.Driver");


        // 2.ADIM: DataBase ile Baglanti kur

        Connection connection = DriverManager.getConnection("jdbc:mysql://45.87.83.5/u168183796_qaloantec",
                "u168183796_qaloantecuser",
                "0&vG1A/MuWN");

        // 3.ADIM: SQL Query'si Olustur

        String query = "SELECT * FROM u168183796_qaloantec.users";



    }

}
