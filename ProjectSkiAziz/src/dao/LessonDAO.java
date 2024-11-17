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
		// TODO Auto-generated constructor stub
	}

    public List<LessonPOJO> getAllLessons() {
        List<LessonPOJO> lessons = new ArrayList<>();
        try {
            Connection connection = EcoleConnection.getInstance().getConnect();

            // Vérifier si la connexion est fermée et la réouvrir si nécessaire
            if (connection.isClosed()) {
                connection = EcoleConnection.getInstance().getConnect(); // Réouvrir la connexion si fermée
            }

            PreparedStatement stmt = connection.prepareStatement("SELECT minBookings, maxBookings FROM Lesson");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int minBookings = rs.getInt("minBookings");
                int maxBookings = rs.getInt("maxBookings");

                LessonPOJO lesson = new LessonPOJO(minBookings, maxBookings);
                lessons.add(lesson);
            }

            rs.close(); // Ne pas oublier de fermer les ResultSet
            stmt.close(); // Ne pas oublier de fermer les PreparedStatement

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