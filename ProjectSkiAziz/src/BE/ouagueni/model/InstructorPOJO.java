package BE.ouagueni.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import dao.InstructorDAO;
import dao.SkierDAO;
import singleton.EcoleConnection;


// WINDOWS BUILDER ==> CLASSE POJO ==> CLASSE DAO
// PUIS
// CLASSE DAO ==> CLASSE POJO ==> WINDOWS BUILDER

public class InstructorPOJO extends PersonnePOJO {
    private int experience;
    
	public InstructorPOJO(int id, String nom, String prenom, Date dateNaissance, int experience) {
		super(id, nom, prenom, dateNaissance);
		this.experience = experience;
	}
	public InstructorPOJO()
	{
        super(0, "", "", new Date()); 
    }

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	@Override
	public String toString() {
		return "InstructorPOJO [experience=" + experience  + ", getId()="
				+ getId() + ", getNom()=" + getNom() + ", getPrenom()=" + getPrenom() + ", getDateNaissance()="
				+ getDateNaissance() + ", toString()=" + super.toString() + ", hashCode()=" + hashCode()
				+ ", getClass()=" + getClass() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(experience);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstructorPOJO other = (InstructorPOJO) obj;
		return experience == other.experience;
	}
	// CRUD Methods
    public static InstructorPOJO getInstructor(int id, InstructorDAO dao) {
        return dao.find(id);
    }

    public void addInstructor(InstructorDAO dao) {
        dao.create(this);
    }

    public void modifyInstructor(InstructorDAO dao) {
        dao.update(this);
    }

    public void deleteInstructor(InstructorDAO dao) {
        dao.delete(this);
    }
    
    public static boolean checkIfPersonExists(String name, String surname) {
        InstructorDAO instructorDAO = new InstructorDAO(EcoleConnection.getInstance().getConnect());
        return instructorDAO.findByNameAndSurname(name, surname) != null;
    }
    public static List<InstructorPOJO> getAllInstructorNotInBooking() {
    	InstructorDAO instructorDAO = new InstructorDAO(EcoleConnection.getInstance().getConnect());
        return instructorDAO.getAllInstructorsNotInBooking();
    }
    public static List<InstructorPOJO> getAllInstructor() {
    	InstructorDAO instructorDAO = new InstructorDAO(EcoleConnection.getInstance().getConnect());
        return instructorDAO.getAllInstructor();
    }
    
}

