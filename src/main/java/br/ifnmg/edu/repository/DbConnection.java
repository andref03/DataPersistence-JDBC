package br.ifnmg.edu.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta classe é fundamental para a conexão com o Banco de Dados, sendo esse o 
 * seu principal objetivo.
 * 
 * @author andref03
 */
public class DbConnection {

    // Retains the established connection to the database during system operation
    private static Connection connection;

    /**
     * URL database connection (protocol/sgbd/ip/port/database/parameters)
     */
    public static final String URL;

    // Database user
    private static final String USER;

    // Database password
    private static final String PASSWORD;

    // Static initialization
    static {
        // Local server
        URL = "jdbc:mysql://127.0.0.1:3306/" + Dao.DB
                + "?useUnicode=true"
                + "&useJDBCCompliantTimezoneShift=true"
                + "&serverTimezone=UTC"
                + "&autoReconnect=true";
        USER = "root";
        PASSWORD = "";
    }

    /**
     * Establishes and generates database connection retention
     *
     * @return Dabatase connection
     */
    public static Connection getConnection() {

        // If there is no connection established...
        if (connection == null) {
            // ... try ...
            try {
                System.out.println(">> New database connection");
                // ... establish and retain the connection 
                //     from the provided URL, username and password
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException ex) {
                // Log failure
                Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, "System stopped because there is no RDBMS connection", ex);
                System.exit(-1);
            }
        }

        // Returns the established connection
        return connection;
    }

    //<editor-fold defaultstate="collapsed" desc="Private constructor">
    /*
    * Private constructor to force access to the connection by the
    * static member getConnection() without requiring the generation of
    * new objects ConexaoBd ConexaoBd
     */
    private DbConnection() {
    }
    //</editor-fold>

}
