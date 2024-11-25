package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BE.ouagueni.model.InstructorPOJO;
import BE.ouagueni.model.LessonTypePOJO;
import singleton.EcoleConnection;

public class LessonTypeDAO extends DAO_Generique<InstructorPOJO> {

	public LessonTypeDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	public LessonTypeDAO() {
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
	 public List<LessonTypePOJO> getAllLessonTypes() {
	        List<LessonTypePOJO> lessonTypes = new ArrayList<>();
	        String query = "SELECT id, lesson_level, price FROM LessonType";

	        try (PreparedStatement statement = connect.prepareStatement(query)) {
	            ResultSet resultSet = statement.executeQuery();
	            while (resultSet.next()) {
	                int id = resultSet.getInt("id");
	                String level = resultSet.getString("lesson_level");
	                double price = resultSet.getDouble("price");

	                BigDecimal priceBigDecimal = BigDecimal.valueOf(price);  // Conversion du double en BigDecimal
	                LessonTypePOJO lessonType = new LessonTypePOJO(level, priceBigDecimal);
	                lessonType.setId(id); // Assurez-vous d'avoir un champ `id` dans LessonTypePOJO.
	                lessonTypes.add(lessonType);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return lessonTypes;
	    }
	 public LessonTypePOJO getLessonTypeById(int idLessonType) {
		    LessonTypePOJO lessonType = null;
		    String query = "SELECT id, lesson_level, price FROM LessonType WHERE id = ?";

		    try (PreparedStatement statement = connect.prepareStatement(query)) {
		        // Paramétrer l'ID du type de leçon
		        statement.setInt(1, idLessonType);

		        // Exécuter la requête
		        ResultSet resultSet = statement.executeQuery();

		        // Si une correspondance est trouvée, créer un objet LessonTypePOJO
		        if (resultSet.next()) {
		            int id = resultSet.getInt("id");
		            String level = resultSet.getString("lesson_level");
		            BigDecimal price = resultSet.getBigDecimal("price");

		            lessonType = new LessonTypePOJO();
		            lessonType.setId(id);
		            lessonType.setLevel(level);
		            lessonType.setPrice(price);
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return lessonType;
		}
	 
}
