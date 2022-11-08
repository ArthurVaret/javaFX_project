package service;

import java.util.ArrayList;
import java.util.List;

import models.Product;
import models.Cloth;
import models.Shoe;
import models.Accessory;

import static service.DBCredentials.DBMS;
import static service.DBCredentials.HOST;
import static service.DBCredentials.PORT;
import static service.DBCredentials.DATABASE;
import static service.DBCredentials.USERNAME;
import static service.DBCredentials.PASSWORD;

import java.sql.*;

public class DBManager {
    public List<Product> loadProducts() {
        List<Product> productList = new ArrayList<>();
        Connection connection = this.Connector();
        try {
            Statement statement = connection.createStatement();
            String query = "select * from products";
            ResultSet res = statement.executeQuery(query);
            while (res.next()) {
                String type =           res.getString("type");
                int productId =         res.getInt("id");
                String productName =    res.getString("name");
                double productPrice =   res.getDouble("price");
                int productStock =      res.getInt("stock");
                int productSize =       res.getInt("size");

                Product product = null;
                switch (type) {
                    case "cloth" -> {
                        product = new Cloth(productId,productName,productPrice,productStock, productSize);
                    }
                    case "shoe" -> {
                        product = new Shoe(productId,productName,productPrice,productStock, productSize);
                    }
                    case "accessory" -> {
                        product = new Accessory(productId,productName,productPrice,productStock);
                    }
                }
                productList.add(product);
            }
            this.close(connection, statement, res);
            return productList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Connection Connector(){
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:" + DBMS + "://" + HOST + ":" + PORT + "/" + DATABASE +"",
                    USERNAME,
                    PASSWORD
            );
            return connection;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try{
            if (myStmt != null)     myStmt.close();
            if (myRs != null)       myRs.close();
            if (myConn != null)     myConn.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void addStudent(Product product){
        Connection myConn=null;
        PreparedStatement myStmt = null;
        ResultSet myRs= null;
        try {
            myConn = this.Connector();
            String sql = "insert into products(name,type,price,stock) values (\"?\", \"?\", ?, ?);";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, product.getName());
            myStmt.setString(2, product.getType());
            myStmt.setString(3, String.valueOf(product.getPrice()));
            myStmt.setString(4, String.valueOf(product.getNbItems()));
            myStmt.execute();
            System.out.println("Product added");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn,myStmt,myRs);
        }
    }

}