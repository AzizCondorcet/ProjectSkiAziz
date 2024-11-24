package BE.ouagueni.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import dao.LessonDAO;
import dao.SkierDAO;
import singleton.EcoleConnection;

public class LessonPOJO implements Serializable {
	private int id;
	private String name;
    private int minBookings;
    private int maxBookings;
    private int CurrentBooking;
    private LessonTypePOJO Lessontype;
    private InstructorPOJO Instructor;
    
	public LessonPOJO(String name,int id,int minBookings, int maxBookings) {
		super();
		this.name=name;
		this.id=id;
		this.minBookings = minBookings;
		this.maxBookings = maxBookings;
	}
	public LessonPOJO() {
		super();
	}
	public LessonPOJO(String name,int id,int minBookings, int maxBookings,int CurrentBooking) {
		super();
		this.name=name;
		this.id=id;
		this.minBookings = minBookings;
		this.maxBookings = maxBookings;
		this.CurrentBooking= CurrentBooking;
	}

	public LessonPOJO(int id, int minBookings, int maxBookings)
	{
		super();
		this.id=id;
		this.minBookings = minBookings;
		this.maxBookings = maxBookings;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCurrentBooking(int currentBooking) {
		CurrentBooking = currentBooking;
	}
	public LessonTypePOJO getLessontype() {
		return Lessontype;
	}
	public void setLessontype(LessonTypePOJO lessontype) {
		Lessontype = lessontype;
	}
	public InstructorPOJO getInstructor() {
		return Instructor;
	}
	public void setInstructor(InstructorPOJO instructor) {
		Instructor = instructor;
	}
	public int getCurrentBooking() {
		return CurrentBooking;
	}
	public String getname() {
		return name;
	}
	public void SetCurrentBooking(int CurrentBooking) {
		this.CurrentBooking= CurrentBooking;
	}
	public void Setname(String name) {
		this.name=name;
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
	    return "Leçon : " + name + "\n" +
	           "ID : " + id + "\n" +
	           "Réservations actuelles : " + CurrentBooking + "\n" +
	           "Nombre minimum de réservations : " + minBookings + "\n" +
	           "Nombre maximum de réservations : " + maxBookings + "\n";
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
	public static List<LessonPOJO> getAllLessonsNotInBooking() {
        LessonDAO lessonDAO = new LessonDAO(EcoleConnection.getInstance().getConnect());
        return lessonDAO.getAllLessonsNotInBooking();
    }
	public static List<LessonPOJO> getAllLessons() {
        LessonDAO lessonDAO = new LessonDAO(EcoleConnection.getInstance().getConnect());
        return lessonDAO.getAllLessons();
    }
	public static List<LessonPOJO> getAvailableLessons(){
		LessonDAO lessonDAO = new LessonDAO(EcoleConnection.getInstance().getConnect());
		return lessonDAO.getAvailableLessons();
	}
    public static LessonPOJO getLessonById(int idLesson) {
    	LessonDAO lessonDAO = new LessonDAO(EcoleConnection.getInstance().getConnect());
        return lessonDAO.getLessonById(idLesson); 
    }
    
}
