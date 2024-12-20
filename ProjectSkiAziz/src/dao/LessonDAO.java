package dao;

import java.sql.Connection;
import java.sql.Date;
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

            PreparedStatement stmt = connection.prepareStatement("SELECT lesson_date,id, minBookings, maxBookings \r\n"
            		+ "        FROM Lesson l\r\n"
            		+ "        WHERE NOT EXISTS (\r\n"
            		+ "            SELECT 1\r\n"
            		+ "            FROM Booking b\r\n"
            		+ "            WHERE b.lesson_id = l.id\r\n"
            		+ "        )");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            	int id = rs.getInt("id");
                Date LessonDate = rs.getDate("lesson_date");
                int minBookings = rs.getInt("minBookings");
                int maxBookings = rs.getInt("maxBookings");

                LessonPOJO lesson = new LessonPOJO(LessonDate,id,minBookings, maxBookings);
                lessons.add(lesson);
            }

            rs.close(); // Ne pas oublier de fermer les ResultSet
            stmt.close(); // Ne pas oublier de fermer les PreparedStatement

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessons;
    }
    public static List<LessonPOJO> getLessonsByAgeCategory(boolean isChild) {
        List<LessonPOJO> filteredLessons = new ArrayList<>();
        try (Connection connection = EcoleConnection.getInstance().getConnect()) {
        	String sql = "SELECT l.*, lt.id AS lt_id, lt.lesson_level AS lt_level, "
        	           + "lt.price AS lt_price, i.id AS i_id, i.nom AS i_nom, i.prenom AS i_prenom, "
        	           + "i.dateNaissance AS i_dateNaissance, i.experience AS i_experience, " // Ajout de la virgule ici
        	           + "l.lesson_date AS lesson_date " 
        	           + "FROM Lesson l "
        	           + "JOIN LessonType lt ON l.lessonType_id = lt.id "
        	           + "JOIN Instructor i ON l.instructor_id = i.id "
        	           + "WHERE lt.id BETWEEN ? AND ?";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            System.out.println(isChild);
            if (isChild) {
                ps.setInt(1, 1);  // IDs pour niveaux enfants
                ps.setInt(2, 11);  // Fin des niveaux enfants
            } else {
                ps.setInt(1, 12); // IDs pour niveaux adultes
                ps.setInt(2, 18); // Fin des niveaux adultes
            }
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Créer l'objet LessonTypePOJO
                LessonTypePOJO lessonType = new LessonTypePOJO();
                lessonType.setId(rs.getInt("lt_id"));
                lessonType.setLevel(rs.getString("lt_level")); // Champ corrigé
                lessonType.setPrice(rs.getBigDecimal("lt_price"));

                // Créer l'objet InstructorPOJO
                InstructorPOJO instructor = new InstructorPOJO(
                    rs.getInt("i_id"),
                    rs.getString("i_nom"),
                    rs.getString("i_prenom"),
                    rs.getDate("i_dateNaissance"),
                    rs.getInt("i_experience")
                );

                // Créer l'objet LessonPOJO
                LessonPOJO lesson = new LessonPOJO(
                	rs.getDate("lesson_date"),
                    rs.getInt("id"),        // ID de la leçon
                    lessonType,             // Objet LessonTypePOJO
                    instructor,             // Objet InstructorPOJO
                    rs.getInt("minBookings"),
                    rs.getInt("maxBookings")
                );

                // Ajouter la leçon à la liste filtrée
                filteredLessons.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredLessons;
    }
    public List<LessonPOJO> getAllLessons() {
    	List<LessonPOJO> lessons = new ArrayList<>();
        try {
            Connection connection = EcoleConnection.getInstance().getConnect();

            // Vérifier si la connexion est fermée et la réouvrir si nécessaire
            if (connection.isClosed()) {
                connection = EcoleConnection.getInstance().getConnect(); // Réouvrir la connexion si fermée
            }

            PreparedStatement stmt = connect.prepareStatement("SELECT lesson_date,id, nam, minBookings, maxBookings FROM Lesson l");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            	Date LessonDate = rs.getDate("lesson_date");
            	int id = rs.getInt("id");
            	String name = rs.getString("nam");
                int minBookings = rs.getInt("minBookings");
                int maxBookings = rs.getInt("maxBookings");

                LessonPOJO lesson = new LessonPOJO(LessonDate,name,id,minBookings, maxBookings);
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
            "SELECT l.id AS lesson_id,l.nam, l.lessonType_id, l.instructor_id, l.minBookings, l.maxBookings, " +
            "       lt.lesson_level, lt.price, " +
            "       i.nom AS instructor_nom, i.prenom AS instructor_prenom, i.dateNaissance AS instructor_dateNaissance, i.experience AS instructor_experience " +
            "FROM Lesson l " +
            "JOIN LessonType lt ON l.lessonType_id = lt.id " +
            "JOIN Instructor i ON l.instructor_id = i.id " +
            "WHERE l.id = ?";

        System.out.println("Executing query with ID: " + idLesson);
        try (PreparedStatement stmt = this.connect.prepareStatement(query)) {
            stmt.setInt(1, idLesson); 
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    System.out.println("Query returned a result!");
                    // Création de l'objet LessonPOJO
                    lesson = new LessonPOJO();
                    System.out.println("lesson_id "+result.getInt("lesson_id"));
                    System.out.println("minBookings "+result.getInt("minBookings"));
                    System.out.println("maxBookings "+result.getInt("maxBookings"));
                    lesson.setId(result.getInt("lesson_id"));
                    lesson.setMinBookings(result.getInt("minBookings"));
                    lesson.setMaxBookings(result.getInt("maxBookings"));
                    lesson.setName(result.getString("nam"));

                    // Informations sur LessonType
                    LessonTypePOJO lessonType = new LessonTypePOJO();
                    System.out.println("lessonType_id "+result.getInt("lessonType_id"));
                    System.out.println("lesson_level "+result.getString("lesson_level"));
                    System.out.println("price "+result.getBigDecimal("price"));
                    lessonType.setId(result.getInt("lessonType_id"));
                    lessonType.setLevel(result.getString("lesson_level"));
                    lessonType.setPrice(result.getBigDecimal("price"));

                    // Informations sur Instructor
                    InstructorPOJO instructor = new InstructorPOJO();
                    System.out.println("instructor_id "+result.getInt("instructor_id"));
                    System.out.println("instructor_nom "+result.getString("instructor_nom"));
                    System.out.println("instructor_prenom "+result.getString("instructor_prenom"));
                    System.out.println("instructor_dateNaissance "+result.getDate("instructor_dateNaissance"));
                    System.out.println("instructor_experience "+result.getInt("instructor_experience"));
                    instructor.setId(result.getInt("instructor_id"));
                    instructor.setNom(result.getString("instructor_nom"));
                    instructor.setPrenom(result.getString("instructor_prenom"));
                    instructor.setDateNaissance(result.getDate("instructor_dateNaissance"));
                    instructor.setExperience(result.getInt("instructor_experience"));

                    // Associer les objets à la leçon
                    lesson.setLessontype(lessonType);  // Remplacer lessonTypeId par l'objet lessonType
                    lesson.setInstructor(instructor);  // Remplacer instructorId par l'objet instructor
                } else {
                    System.out.println("No lesson found with ID: " + idLesson);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("La lesson dans Lesson DAO : ");
        System.out.println("Lesson ToString :");
        System.out.println(lesson.toString());
        System.out.println("----------------------------");
        return lesson;
    }


    public List<LessonPOJO> getAvailableLessons() {
        List<LessonPOJO> lessons = new ArrayList<>();
        String query = """
            SELECT l.id, l.lesson_date,l.nam, l.minBookings, l.maxBookings, 
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
                	 rs.getDate("lesson_date"),
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