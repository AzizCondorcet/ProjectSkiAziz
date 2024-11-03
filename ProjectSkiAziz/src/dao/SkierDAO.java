package dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import BE.ouagueni.model.SkierPOJO;

public class SkierDAO extends DAO_Generique<SkierPOJO> {
    public SkierDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean create(SkierPOJO skier) {
        // Implémentation SQL pour insérer un Skier
        return false;
    }

    @Override
    public boolean delete(SkierPOJO skier) {
        // Implémentation SQL pour supprimer un Skier
        return false;
    }

    @Override
    public boolean update(SkierPOJO skier) {
        // Implémentation SQL pour mettre à jour un Skier
        return false;
    }

    @Override
    public SkierPOJO find(int id) {
    	SkierPOJO skier = new SkierPOJO();
        try {
            ResultSet result = this.connect.createStatement().executeQuery(
                "SELECT * FROM Skier WHERE id = " + id
            );
            if (result.first()) {
               /* skier.setId(id);
                skier.setNom(result.getString("nom"));
                skier.setPrenom(result.getString("prenom"));
                etc */
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skier;
    }
}

