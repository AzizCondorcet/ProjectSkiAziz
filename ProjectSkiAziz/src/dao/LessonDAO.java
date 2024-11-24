package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import BE.ouagueni.model.InstructorPOJO;
import BE.ouagueni.model.LessonPOJO;
import BE.ouagueni.model.LessonTypePOJO;
import BE.ouagueni.model.SkierPOJO;
import singleton.EcoleConnection;

public class LessonDAO extends DAO_Generique<InstructorPOJO> {

    public LessonDAO(Connection conn) {
		super(conn);
	}

    public List<LessonPOJO> getAllLessonsNotInBooking() {
        List<LessonPOJO> lessons = new ArrayList<>();
        try {
            Connection connection = EcoleConnection.getInstance().getConnect();

            // Vérifier si la connexion est fermée et la réouvrir si nécessaire
            if (connection.isClosed()) {
                connection = EcoleConnection.getInstance().getConnect(); // Réouvrir la connexion si fermée
            }

            PreparedStatement stmt = connection.prepareStatement("SELECT id, minBookings, maxBookings \r\n"
            		+ "        FROM Lesson l\r\n"
            		+ "        WHERE NOT EXISTS (\r\n"
            		+ "            SELECT 1\r\n"
            		+ "            FROM Booking b\r\n"
            		+ "            WHERE b.lesson_id = l.id\r\n"
            		+ "        )");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            	int id = rs.getInt("id");
                int minBookings = rs.getInt("minBookings");
                int maxBookings = rs.getInt("maxBookings");

                LessonPOJO lesson = new LessonPOJO(id,minBookings, maxBookings);
                lessons.add(lesson);
            }

            rs.close(); // Ne pas oublier de fermer les ResultSet
            stmt.close(); // Ne pas oublier de fermer les PreparedStatement

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessons;
    }
    public List<LessonPOJO> getAllLessons() {
    	List<LessonPOJO> lessons = new ArrayList<>();
        try {
            Connection connection = EcoleConnection.getInstance().getConnect();

            // Vérifier si la connexion est fermée et la réouvrir si nécessaire
            if (connection.isClosed()) {
                connection = EcoleConnection.getInstance().getConnect(); // Réouvrir la connexion si fermée
            }

            PreparedStatement stmt = connect.prepareStatement("SELECT id, nam, minBookings, maxBookings FROM Lesson l");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            	int id = rs.getInt("id");
            	String name = rs.getString("nam");
                int minBookings = rs.getInt("minBookings");
                int maxBookings = rs.getInt("maxBookings");

                LessonPOJO lesson = new LessonPOJO(name,id,minBookings, maxBookings);
                lessons.add(lesson);
            }

            rs.close(); // Ne pas oublier de fermer les ResultSet
            stmt.close(); // Ne pas oublier de fermer les PreparedStatement

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessons;
    }
    public LessonPOJO getLessonById(int idLesson) {
        LessonPOJO lesson = null;
        String query = 
            "SELECT l.id AS lesson_id, l.lessonType_id, l.instructor_id, l.minBookings, l.maxBookings, " +
            "       lt.lesson_level, lt.price, " +
            "       i.nom AS instructor_nom, i.prenom AS instructor_prenom, i.dateNaissance AS instructor_dateNaissance, i.experience AS instructor_experience " +
            "FROM Lesson l " +
            "JOIN LessonType lt ON l.lessonType_id = lt.id " +
            "JOIN Instructor i ON l.instructor_id = i.id " +
            "WHERE l.id = ?";

        try (PreparedStatement stmt = this.connect.prepareStatement(query)) {
            stmt.setInt(1, idLesson);   // Remplacer '?' par l'ID de la leçon
            
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    // Création de l'objet LessonPOJO
                    lesson = new LessonPOJO();
                    lesson.setId(result.getInt("lesson_id"));
                    lesson.setMinBookings(result.getInt("minBookings"));
                    lesson.setMaxBookings(result.getInt("maxBookings"));

                    // Informations sur LessonType
                    LessonTypePOJO lessonType = new LessonTypePOJO();
                    lessonType.setId(result.getInt("lessonType_id"));
                    lessonType.setLevel(result.getString("lesson_level"));
                    lessonType.setPrice(result.getBigDecimal("price"));

                    // Informations sur Instructor
                    InstructorPOJO instructor = new InstructorPOJO();
                    instructor.setId(result.getInt("instructor_id"));
                    instructor.setNom(result.getString("instructor_nom"));
                    instructor.setPrenom(result.getString("instructor_prenom"));
                    instructor.setDateNaissance(result.getDate("instructor_dateNaissance"));
                    instructor.setExperience(result.getInt("instructor_experience"));

                    // Associer les objets à la leçon
                    lesson.setLessontype(lessonType);  // Remplacer lessonTypeId par l'objet lessonType
                    lesson.setInstructor(instructor);  // Remplacer instructorId par l'objet instructor
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lesson;
    }


    public List<LessonPOJO> getAvailableLessons() {
        List<LessonPOJO> lessons = new ArrayList<>();
        String query = """
            SELECT l.id, l.nam, l.minBookings, l.maxBookings, 
                   NVL(COUNT(b.id), 0) AS currentBookings
            FROM Lesson l
            LEFT JOIN Booking b ON l.id = b.lesson_id
            GROUP BY l.id, l.nam, l.minBookings, l.maxBookings
            HAVING NVL(COUNT(b.id), 0) < l.maxBookings
        """;

        try (PreparedStatement stmt = connect.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lessons.add(new LessonPOJO(
	        		 rs.getString("nam"),         // Utiliser getString pour les colonnes VARCHAR
	                 rs.getInt("id"),             // Utiliser getInt pour les colonnes NUMBER/INT
	                 rs.getInt("minBookings"),   
	                 rs.getInt("maxBookings"),
	                 rs.getInt("currentBookings") // Utiliser getInt pour l'alias calculé
	                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessons;
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
}