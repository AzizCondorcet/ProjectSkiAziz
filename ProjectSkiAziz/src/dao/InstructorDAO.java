package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import BE.ouagueni.model.InstructorPOJO;
import BE.ouagueni.model.SkierPOJO;

public class InstructorDAO extends DAO_Generique<InstructorPOJO> {
	
  public InstructorDAO(Connection conn) {
        super(conn);
    }
  	@Override
    public boolean create(InstructorPOJO Instructor) {
  	// a faire
        return false;
    }

    @Override
    public boolean delete(InstructorPOJO Instructor) {
    	// a faire
        return false;
    }

    @Override
    public boolean update(InstructorPOJO Instructor) {
    	// a faire
        return false;
    }

    public InstructorPOJO findByNameAndSurname(String name, String surname) {
        InstructorPOJO instructor = null;
        try {
            Statement stmt = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = 
                "SELECT i.id, i.nom, i.prenom, i.dateNaissance, i.experience, c.name AS certification_name " +
                "FROM Instructor i " +
                "LEFT JOIN Instructor_Certification ic ON i.id = ic.instructor_id " +
                "LEFT JOIN Certification c ON ic.certification_id = c.id " +
                "WHERE i.nom = '" + name + "' AND i.prenom = '" + surname + "'";

            ResultSet result = stmt.executeQuery(query);
            
            List<String> certifications = new ArrayList<>();
            
            while (result.next()) {
                if (instructor == null) { 
                    instructor = new InstructorPOJO();
                    instructor.setId(result.getInt("id"));
                    instructor.setNom(result.getString("nom"));
                    instructor.setPrenom(result.getString("prenom"));
                    instructor.setDateNaissance(result.getDate("dateNaissance"));
                    instructor.setExperience(result.getInt("experience"));
                }

                // Ajout des certifications à la liste
                String certificationName = result.getString("certification_name");
                if (certificationName != null) {
                    certifications.add(certificationName);
                }
            }

            // Assigner la liste de certifications à l'instructeur
            if (instructor != null) {
                instructor.setCertifications(certifications);
            } else {
                System.out.println("No instructor found with name: " + name + " and surname: " + surname);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instructor;
    }
    
	@Override
	public InstructorPOJO find(int id) {
		// TODO Auto-generated method stub
		return null;
	}


}
