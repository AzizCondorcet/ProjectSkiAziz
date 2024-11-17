package dao;

import BE.ouagueni.model.PeriodPOJO;
import BE.ouagueni.model.SkierPOJO;
import singleton.EcoleConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeriodDAO extends DAO_Generique<SkierPOJO> {

    // Constructeur
    public PeriodDAO(Connection conn) {
        super(conn); // Appel du constructeur de la classe parente
    }

    // Méthode pour récupérer toutes les périodes
    public List<PeriodPOJO> getAllPeriod() {
        List<PeriodPOJO> periods = new ArrayList<>();
        if (connect.isClosed()) {
            connect= EcoleConnection.getInstance().getConnect(); // Réouvrir la connexion si fermée
        }
        String query = "SELECT * FROM Period"; // Requête SQL pour récupérer toutes les périodes

        try (PreparedStatement statement = connect.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Parcours des résultats
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");
                boolean isVacation = resultSet.getInt("isVacation") == 1;

                // Création de l'objet PeriodPOJO avec les données récupérées
                PeriodPOJO period = new PeriodPOJO(id, startDate, endDate, isVacation);
                periods.add(period);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return periods; // Retourner la liste des périodes
    }


	@Override
	public boolean create(SkierPOJO obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(SkierPOJO obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(SkierPOJO obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SkierPOJO find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
