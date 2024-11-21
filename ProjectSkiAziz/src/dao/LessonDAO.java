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