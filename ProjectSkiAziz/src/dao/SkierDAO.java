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
        String query = "SELECT * FROM Skier WHERE nom = ? AND prenom = ?";
        
        try (PreparedStatement stmt = this.connect.prepareStatement(query)) {
            stmt.setString(1, name);   // Remplace le premier '?' par 'name'
            stmt.setString(2, surname); // Remplace le second '?' par 'surname'
            
            ResultSet result = stmt.executeQuery();
            
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
    public SkierPOJO getSkierById(int idSkier) {
        SkierPOJO skier = null;
        String query = "SELECT * FROM Skier WHERE id = ?";
        
        try (PreparedStatement stmt = this.connect.prepareStatement(query)) {
            stmt.setInt(1, idSkier);   // Remplace le premier '?' par 'idSkier'
            
            ResultSet result = stmt.executeQuery();
            
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
    public boolean NewSkier(String Nom, String Prenom, java.util.Date date, String Niveau, boolean Assurance) {
        boolean success = false; // Initialiser le succès à faux
        try {
            // Vérifier si le skieur existe déjà dans la base de données
            String checkQuery = "SELECT COUNT(*) FROM Skier WHERE nom = ? AND prenom = ?";
            PreparedStatement checkStmt = this.connect.prepareStatement(checkQuery);
            checkStmt.setString(1, Nom);  // Utilisation des paramètres passés à la méthode
            checkStmt.setString(2, Prenom);

            ResultSet resultSet = checkStmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    // Si le skieur existe déjà, renvoyer false
                    System.out.println("Ce skieur existe déjà dans la base de données.");
                    return false;
                }
            }

            // Requête d'insertion
            String insertQuery = "INSERT INTO Skier (nom, prenom, dateNaissance, niveau, assurance) " +
                                 "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = this.connect.prepareStatement(insertQuery);
            insertStmt.setString(1, Nom);  // Utilisation des paramètres passés à la méthode
            insertStmt.setString(2, Prenom);
            insertStmt.setDate(3, new java.sql.Date(date.getTime()));  // Convertir la Date Java en SQL Date
            insertStmt.setString(4, Niveau);  // Le niveau du skieur
            insertStmt.setBoolean(5, Assurance);  // Assurance en tant que booléen (true/false)

            // Exécution de la requête d'insertion et vérification des lignes affectées
            int affectedRows = insertStmt.executeUpdate();
            if (affectedRows > 0) {
                success = true; // Si au moins une ligne est affectée, succès
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Affiche l'erreur dans la console
        }
        return success; // Retourner le succès ou l'échec
    }
    
    public List<SkierPOJO> getAllSkiersNotInBooking() {
        List<SkierPOJO> skiers = new ArrayList<>();
        String query = """
                SELECT id, nom, prenom, dateNaissance, niveau, assurance 
                FROM Skier s
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM Booking b
                    WHERE b.skier_id = s.id
                )
            """;

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
    public List<SkierPOJO> getAllSkiers() {
        List<SkierPOJO> skiers = new ArrayList<>();
        String query = """
        	    SELECT id, nom, prenom, dateNaissance, niveau, assurance
        	    FROM Skier s
        	""";

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
