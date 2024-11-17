package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
    public SkierPOJO findByNameAndSurname(String name, String surname) {
        SkierPOJO skier = null;
        try {
            Statement stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM Skier WHERE nom = '" + name + "' AND prenom = '" + surname + "'";
            ResultSet result = stmt.executeQuery(query);

            if (result.next()) {
                skier = new SkierPOJO();
                skier.setId(result.getInt("id"));
                skier.setNom(result.getString("nom"));
                skier.setPrenom(result.getString("prenom"));
                skier.setDateNaissance(result.getDate("dateNaissance"));
                skier.setNiveau(result.getString("niveau"));
                skier.setAssurance(result.getBoolean("assurance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skier;
    }
    public List<SkierPOJO> getAllSkiers() {
        List<SkierPOJO> skiers = new ArrayList<>();
        String query = "SELECT id, nom, prenom, dateNaissance, niveau, assurance FROM Skier";

        try (PreparedStatement statement = connect.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // Récupérer les données de chaque skieur depuis le ResultSet
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                Date dateNaissance = resultSet.getDate("dateNaissance");
                String niveau = resultSet.getString("niveau");
                boolean assurance = resultSet.getBoolean("assurance");

                // Créer un objet SkierPOJO et l'ajouter à la liste
                SkierPOJO skier = new SkierPOJO(id, nom, prenom, dateNaissance, niveau, assurance);
                skiers.add(skier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skiers;
    }

	@Override
	public SkierPOJO find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
