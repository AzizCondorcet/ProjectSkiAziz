package BE.ouagueni.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import dao.InstructorDAO;
import dao.LessonDAO;
import dao.SkierDAO;
import singleton.EcoleConnection;

public class SkierPOJO extends PersonnePOJO {
    private String niveau;
    private boolean assurance;

    public SkierPOJO(int id, String nom, String prenom, Date dateNaissance, String niveau, boolean assurance) {
        super(id, nom, prenom, dateNaissance);
        this.niveau = niveau;
        this.assurance = assurance;
    }

    public SkierPOJO() {
        super(0, "", "", new Date()); // Initialisation par défaut
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public boolean isAssurance() {
        return assurance;
    }

    public void setAssurance(boolean assurance) {
        this.assurance = assurance;
    }

    @Override
    public String toString() {
        return super.toString() + ", Skier [niveau=" + niveau + ", assurance=" + assurance + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), assurance, niveau);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        SkierPOJO other = (SkierPOJO) obj;
        return assurance == other.assurance && Objects.equals(niveau, other.niveau);
    }
    public static boolean isUnder18(Date dateNaissance) {
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(dateNaissance);

        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        // Si l'anniversaire n'est pas encore passé cette année, soustraire 1 à l'âge
        if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age < 18;
    }
    
    public static boolean checkIfPersonExists(String name, String surname)
    {
        SkierDAO skierDAO = new SkierDAO(EcoleConnection.getInstance().getConnect());
        return skierDAO.findByNameAndSurname(name, surname) != null;
    }
    public static List<SkierPOJO> getAllSkierNotInBooking() {
        SkierDAO SkierDAO = new SkierDAO(EcoleConnection.getInstance().getConnect());
        return SkierDAO.getAllSkiersNotInBooking();
    }
    public static List<SkierPOJO> getAllSkiersInBooking() {
        SkierDAO SkierDAO = new SkierDAO(EcoleConnection.getInstance().getConnect());
        return SkierDAO.getAllSkiersInBooking();
    }
    public static List<SkierPOJO> getAllSkiers() {
        SkierDAO SkierDAO = new SkierDAO(EcoleConnection.getInstance().getConnect());
        return SkierDAO.getAllSkiers();
    }
    public static boolean NewSkier(String Nom,String Prenom, Date Date,String Niveau,boolean assurance) {
        SkierDAO skierDAO = new SkierDAO(EcoleConnection.getInstance().getConnect());
        return skierDAO.NewSkier(Nom,Prenom,Date,Niveau,assurance); 
    }
    public static SkierPOJO findByNameAndSurname(String Nom,String Prenom) {
        SkierDAO skierDAO = new SkierDAO(EcoleConnection.getInstance().getConnect());
        return skierDAO.findByNameAndSurname(Nom,Prenom); 
    }
    public static int HaveToPay(int selectedSkierIndex) {
    	System.out.println("papa");
        SkierDAO skierDAO = new SkierDAO(EcoleConnection.getInstance().getConnect());
        return skierDAO.HaveToPay(selectedSkierIndex); 
    }

    public static SkierPOJO getSkierById(int idSkier) {
    	SkierDAO skierDAO = new SkierDAO(EcoleConnection.getInstance().getConnect());
        return skierDAO.getSkierById(idSkier); 
    }
}