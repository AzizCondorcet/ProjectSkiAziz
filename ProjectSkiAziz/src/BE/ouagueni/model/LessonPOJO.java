package BE.ouagueni.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import dao.LessonDAO;
import dao.SkierDAO;
import singleton.EcoleConnection;

public class LessonPOJO implements Serializable {
	private int id;
    private int minBookings;
    private int maxBookings;
    
	public LessonPOJO(int id,int minBookings, int maxBookings) {
		super();
		this.id=id;
		this.minBookings = minBookings;
		this.maxBookings = maxBookings;
	}

	public int getid() {
		return id;
	}
	
	public int getMinBookings() {
		return minBookings;
	}

	public void setMinBookings(int minBookings) {
		this.minBookings = minBookings;
	}

	public int getMaxBookings() {
		return maxBookings;
	}

	public void setMaxBookings(int maxBookings) {
		this.maxBookings = maxBookings;
	}

	@Override
	public String toString() {
		return "LessonPOJO [minBookings=" + minBookings + ", maxBookings=" + maxBookings + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(maxBookings, minBookings);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LessonPOJO other = (LessonPOJO) obj;
		return maxBookings == other.maxBookings && minBookings == other.minBookings;
	}
	public static List<LessonPOJO> getAllLessons() {
        LessonDAO lessonDAO = new LessonDAO(EcoleConnection.getInstance().getConnect());
        return lessonDAO.getAllLessons();
    }
    
}
