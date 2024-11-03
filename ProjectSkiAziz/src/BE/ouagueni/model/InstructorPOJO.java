package BE.ouagueni.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class InstructorPOJO extends PersonnePOJO {
    private int experience;
    private List<String> certifications = new ArrayList<>();
    
	public InstructorPOJO(int id, String nom, String prenom, Date dateNaissance, int experience,
			List<String> certifications) {
		super(id, nom, prenom, dateNaissance);
		this.experience = experience;
		this.certifications = certifications;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public List<String> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<String> certifications) {
		this.certifications = certifications;
	}

	@Override
	public String toString() {
		return "InstructorPOJO [experience=" + experience + ", certifications=" + certifications + ", getId()="
				+ getId() + ", getNom()=" + getNom() + ", getPrenom()=" + getPrenom() + ", getDateNaissance()="
				+ getDateNaissance() + ", toString()=" + super.toString() + ", hashCode()=" + hashCode()
				+ ", getClass()=" + getClass() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(certifications, experience);
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
		return Objects.equals(certifications, other.certifications) && experience == other.experience;
	}
    
}

