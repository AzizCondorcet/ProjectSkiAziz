package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import BE.ouagueni.model.AccreditationPOJO;
import BE.ouagueni.model.InstructorPOJO;
import BE.ouagueni.model.LessonTypePOJO;
import singleton.EcoleConnection;

public class AccreditationDAO extends DAO_Generique<InstructorPOJO>{

	public AccreditationDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	public AccreditationDAO() {
        this.connect = EcoleConnection.getInstance().getConnect();
    }

	@Override
	public boolean create(InstructorPOJO obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(InstructorPOJO obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(InstructorPOJO obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public InstructorPOJO find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	public void createAccreditation(AccreditationPOJO accreditation, int lessonTypeId) {
	    String query = "INSERT INTO Accreditation (id, name, lessonType_id) VALUES (SEQ_ACCREDITATION.NEXTVAL, ?, ?)";

	    try (PreparedStatement statement = connect.prepareStatement(query)) {
	        statement.setString(1, accreditation.getName());
	        statement.setInt(2, lessonTypeId);

	        int rowsInserted = statement.executeUpdate();
	        if (rowsInserted > 0) {
	            System.out.println("Accreditation created successfully.");
	        }
	    } catch (SQLIntegrityConstraintViolationException e) {
	        System.err.println("Duplicate ID error: " + e.getMessage());
	        // Ajoutez une action ici, comme réinitialiser la séquence ou loguer un rapport
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public void deleteAccreditation(AccreditationPOJO accreditation) {
	    String query = "DELETE FROM Accreditation WHERE id = ?";
	    try (PreparedStatement statement = connect.prepareStatement(query)) {
	        statement.setInt(1, accreditation.getId()); // Suppression par ID
	        int rowsDeleted = statement.executeUpdate();
	        if (rowsDeleted > 0) {
	            System.out.println("Accreditation deleted successfully.");
	        } else {
	            System.out.println("No accreditation found with the given ID.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	public List<AccreditationPOJO> getAllAccreditations() {
        List<AccreditationPOJO> accreditations = new ArrayList<>();
        String query = "SELECT * FROM Accreditation";
        
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                AccreditationPOJO accreditation = new AccreditationPOJO(rs.getString("name"));
                accreditation.setId(rs.getInt("id"));
                accreditations.add(accreditation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return accreditations;
    }
	public List<AccreditationPOJO> getAccreditationByInstruId(int instructorId) {
	    List<AccreditationPOJO> accreditations = new ArrayList<>();
	    String query = """
	        SELECT a.id, a.name, a.lessonType_id
	        FROM Accreditation a
	        JOIN LessonType lt ON a.lessonType_id = lt.id
	        JOIN Lesson l ON lt.id = l.lessonType_id
	        WHERE l.instructor_id = ?
	    """;

	    try (PreparedStatement statement = connect.prepareStatement(query)) {
	        // Paramétrer l'ID de l'instructeur
	        statement.setInt(1, instructorId);

	        // Exécuter la requête
	        ResultSet resultSet = statement.executeQuery();

	        // Parcourir les résultats et ajouter les accréditations à la liste
	        while (resultSet.next()) {
	            int accreditationId = resultSet.getInt("id");
	            String name = resultSet.getString("name");
	            int lessonTypeId = resultSet.getInt("lessonType_id");

	            // Récupérer l'objet LessonTypePOJO associé
	            LessonTypePOJO lessonType = LessonTypePOJO.getLessonTypeById(lessonTypeId); 
	            // Assurez-vous que la méthode getLessonTypeById existe dans LessonTypePOJO

	            // Créer une nouvelle instance d'AccreditationPOJO
	            AccreditationPOJO accreditation = new AccreditationPOJO(accreditationId, name, lessonType);
	            accreditations.add(accreditation);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return accreditations;
	}


}
