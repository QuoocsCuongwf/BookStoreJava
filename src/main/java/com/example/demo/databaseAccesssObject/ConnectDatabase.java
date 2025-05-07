package com.example.demo.databaseAccesssObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.*;

public class ConnectDatabase {
    private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String url = "jdbc:sqlserver://localhost:1433;databaseName=bookstore;encrypt=true;trustServerCertificate=true;";
    private String user = "sa";
    private String password = "Admin123@";
    private Connection conn = null;
    private ResultSet rs = null;
    public ConnectDatabase() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public void insert(String query){
        if(conn == null){new ConnectDatabase();}
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("++++++++++++++++++++++++++++++++");
            System.out.println("BUGGGGGGGGGGGGGGGGGGGGGGGGGGG CONNECTDATABASE ERROR");
            throw new RuntimeException(e);
        }
    }


    public String query(String query) {
        if(conn == null){new ConnectDatabase();}
        String jsonString;
        try {
            rs = conn.createStatement().executeQuery(query);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Initialize JSON structures
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode jsonArray = objectMapper.createArrayNode();

            while (rs.next()) {
                ObjectNode jsonObject = objectMapper.createObjectNode();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String columnValue = rs.getString(i);
                    jsonObject.put(columnName, columnValue);
                }
                jsonArray.add(jsonObject);
            }

            // Convert JSON array to string
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonArray);
            System.out.println(jsonString);

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } catch (JsonProcessingException e) {

            throw new RuntimeException(e);
        }
        return jsonString;
    }

    public void update(String query) {
        if (conn == null) {
            new ConnectDatabase(); // tuy nhiên đoạn này không hiệu quả, giải thích bên dưới
        }
        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Updated " + rowsAffected + " rows.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // duyen : sanPhamDAO không dùng phương thức update và insert được nên viết thêm hàm
    public void insertSP(String query, Object[] params) {
        if (conn == null) {
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to database");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Gán các giá trị vào PreparedStatement từ mảng params
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing insert query: " + e.getMessage(), e);
        }
    }

    public void updateSP(String query, Object[] params) {
        if (conn == null) {
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to database");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Gán các giá trị vào PreparedStatement từ mảng params
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Updated " + rowsAffected + " rows.");
        } catch (SQLException e) {
            throw new RuntimeException("Error executing update query: " + e.getMessage(), e);
        }
    }


}
//spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=bookstore;encrypt=true;trustServerCertificate=true;
//spring.datasource.username=SA
//spring.datasource.password=Admin123@
//        spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
//
//spring.jpa.hibernate.ddl-auto=update
//spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect