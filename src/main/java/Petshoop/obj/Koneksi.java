/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Petshoop.obj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    public static Connection Go() throws SQLException {
        Connection con = null;
        try {
            // Pastikan database dan user MySQL sesuai dengan Laragon kamu
            String url = "jdbc:mysql://localhost:3306/petshopdb"; // nama database = petshopdb
            String user = "root"; // default Laragon user
            String password = ""; // default Laragon password kosong

            con = DriverManager.getConnection(url, user, password);
            System.out.println("Koneksi berhasil!");
        } catch (SQLException e) {
            System.err.println("Koneksi gagal: " + e.getMessage());
            throw e;
        }
        return con;
    }

}
