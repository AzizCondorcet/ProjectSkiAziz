package BE.ouagueni.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import dao.BookingDAO;
import dao.InstructorDAO;
import singleton.EcoleConnection;

public class BookingPOJO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
    private Date dateReservation;
    private int nombreParticipants;
    private String NomBooking;
    private SkierPOJO skier;  // Référence à SkierPOJO
    private InstructorPOJO instructor; // Référence à InstructorPOJO
    private LessonPOJO lesson; // Référence à LessonPOJO
    private PeriodPOJO period;  // Référence à PeriodPOJO
    
    
	public BookingPOJO(int id, Date dateReservation, int nombreParticipants,String NomBooking) {
		super();
		this.id = id;
		this.dateReservation = dateReservation;
		this.nombreParticipants = nombreParticipants;
		this.NomBooking = NomBooking;
	}
	public BookingPOJO(SkierPOJO skier, InstructorPOJO instructor, LessonPOJO lesson, PeriodPOJO period,String nomBooking) {
        this.skier = skier;
        this.instructor = instructor;
        this.lesson = lesson;
        this.period = period;
        this.dateReservation = new Date();  // La date actuelle de la réservation
        this.nombreParticipants = 1;  // Par défaut, une réservation pour 1 participant
        this.NomBooking = nomBooking;
    }

	public BookingPOJO(int id,java.util.Date Date,int nombreParticipants,String nomBooking, LessonPOJO lesson,InstructorPOJO instructor,SkierPOJO skier, PeriodPOJO period) {
        this.id=id;
		this.dateReservation=Date;
        this.nombreParticipants = nombreParticipants;
        this.NomBooking = nomBooking;
        this.lesson = lesson;
        this.instructor = instructor;
		this.skier = skier;
        this.period = period;
    }
	public void setNomBooking(String nomBooking) {
		NomBooking = nomBooking;
	}
	// Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public int getNombreParticipants() {
        return nombreParticipants;
    }

    public void setNombreParticipants(int nombreParticipants) {
        this.nombreParticipants = nombreParticipants;
    }

    public SkierPOJO getSkier() {
        return skier;
    }

    public void setSkier(SkierPOJO skier) {
        this.skier = skier;
    }

    public InstructorPOJO getInstructor() {
        return instructor;
    }

    public void setInstructor(InstructorPOJO instructor) {
        this.instructor = instructor;
    }

    public LessonPOJO getLesson() {
        return lesson;
    }

    public void setLesson(LessonPOJO lesson) {
        this.lesson = lesson;
    }

    public PeriodPOJO getPeriod() {
        return period;
    }

    public void setPeriod(PeriodPOJO period) {
        this.period = period;
    }
    public String getNomBooking()
    {
    	return NomBooking;
    }

    @Override
    public String toString() {
        return String.format("Booking Details:\n" +
                             "  - ID: %d\n" +
                             "  - Nom de la réservation: %s\n" +
                             "  - Date de Réservation: %s\n" +
                             "  - Nombre de Participants: %d\n" +
                             "  - Skieur: %s\n" +
                             "  - Instructeur: %s\n" +
                             "  - Leçon: %s\n" +
                             "  - Période: %s",
                             id, 
                             NomBooking, 
                             dateReservation, 
                             nombreParticipants,
                             skier != null ? skier.toString() : "Aucun skieur",
                             instructor != null ? instructor.toString() : "Aucun instructeur",
                             lesson != null ? lesson.toString() : "Aucune leçon",
                             period != null ? period.toString() : "Aucune période");
    }

	@Override
	public int hashCode() {
		return Objects.hash(dateReservation, id, nombreParticipants);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookingPOJO other = (BookingPOJO) obj;
		return Objects.equals(dateReservation, other.dateReservation) && id == other.id
				&& nombreParticipants == other.nombreParticipants;
	}
	public static List<BookingPOJO> getAllBookings() {
        BookingDAO bookingDAO = new BookingDAO(EcoleConnection.getInstance().getConnect());
        return bookingDAO.getAllBookings();
    }
	public void createBooking() {
		System.out.println("555555555555555");
        BookingDAO bookingDAO = new BookingDAO(EcoleConnection.getInstance().getConnect());
        bookingDAO.createBooking(this);
    }
	public static boolean AddBookingWithId(int idSkier,int idLesson,int idInstructeur,int idPeriod,String NomBooking) throws SQLException 
	{
		System.out.println("555555555555555");
		BookingDAO bookingDAO = new BookingDAO(EcoleConnection.getInstance().getConnect());
        return bookingDAO.AddBookingWithId(idSkier,idLesson,idInstructeur,idPeriod,NomBooking);
    }
    public static List<BookingPOJO> getBookingsByInstructorId(int instructorId) {
    	BookingDAO BookingDAO = new BookingDAO(EcoleConnection.getInstance().getConnect());
    	return BookingDAO.getBookingsByInstructorId(instructorId);
    }
   
    
}

