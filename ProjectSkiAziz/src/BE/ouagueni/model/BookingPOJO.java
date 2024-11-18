package BE.ouagueni.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import dao.BookingDAO;
import singleton.EcoleConnection;

public class BookingPOJO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
    private Date dateReservation;
    private int nombreParticipants;
    private SkierPOJO skier;  // Référence à SkierPOJO
    private InstructorPOJO instructor; // Référence à InstructorPOJO
    private LessonPOJO lesson; // Référence à LessonPOJO
    private PeriodPOJO period;  // Référence à PeriodPOJO
    
	public BookingPOJO(int id, Date dateReservation, int nombreParticipants) {
		super();
		this.id = id;
		this.dateReservation = dateReservation;
		this.nombreParticipants = nombreParticipants;
	}
	public BookingPOJO(SkierPOJO skier, InstructorPOJO instructor, LessonPOJO lesson, PeriodPOJO period) {
        this.skier = skier;
        this.instructor = instructor;
        this.lesson = lesson;
        this.period = period;
        this.dateReservation = new Date();  // La date actuelle de la réservation
        this.nombreParticipants = 1;  // Par défaut, une réservation pour 1 participant
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

	@Override
	public String toString() {
		return "BookingPOJO [id=" + id + ", dateReservation=" + dateReservation + ", nombreParticipants="
				+ nombreParticipants + "]";
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
        // Création d'une instance de BookingDAO
        BookingDAO bookingDAO = new BookingDAO(EcoleConnection.getInstance().getConnect());

        // Appel de la méthode getAllBookings() du BookingDAO pour récupérer les réservations
        return bookingDAO.getAllBookings();
    }
	public void createBooking() {
		System.out.println("555555555555555");
        BookingDAO bookingDAO = new BookingDAO(EcoleConnection.getInstance().getConnect());
        bookingDAO.createBooking(this);
    }
    
}

