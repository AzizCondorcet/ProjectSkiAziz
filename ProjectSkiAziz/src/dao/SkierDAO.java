package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import BE.ouagueni.model.SkierPOJO;

public class SkierDAO extends DAO_Generique<SkierPOJO> {
    
    public SkierDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean create(SkierPOJO skier) {
        return false;
    }

    @Override
    public boolean delete(SkierPOJO skier) {
        return false;
    }

    @Override
    public boolean update(SkierPOJO skier) {

        return false;
    }
    @Override
    public SkierPOJO find(int id) {
        SkierPOJO skier = null;  
        try {
            Statement stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            String query = "SELECT * FROM Skier WHERE id = " + id;
            System.out.println("Executing query: " + query);
            
            ResultSet result = stmt.executeQuery(query);
            
            if (result.next()) { 
                skier = new SkierPOJO();
                skier.setId(result.getInt("id"));
                skier.setNom(result.getString("nom"));
                skier.setPrenom(result.getString("prenom"));
            } else {
                System.out.println("No skier found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skier;
    }
}
