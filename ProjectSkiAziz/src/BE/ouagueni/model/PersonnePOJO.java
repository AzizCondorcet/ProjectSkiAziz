package BE.ouagueni.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public abstract class PersonnePOJO implements Serializable {
    private int id;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    
	public PersonnePOJO(int id, String nom, String prenom, Date dateNaissance) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
	}

	public PersonnePOJO() {
		super();
		this.id = 0;
		this.nom = "";
		this.prenom = "";
		this.dateNaissance = new Date();
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	@Override
	public String toString() {
	    return String.format(
	        "Personne: [ID: %d, Nom: %s, Prénom: %s, Date de Naissance: %s]",
	        id,
	        nom,
	        prenom,
	        dateNaissance
	    );
	}


	@Override
	public int hashCode() {
		return Objects.hash(dateNaissance, id, nom, prenom);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonnePOJO other = (PersonnePOJO) obj;
		return Objects.equals(dateNaissance, other.dateNaissance) && id == other.id && Objects.equals(nom, other.nom)
				&& Objects.equals(prenom, other.prenom);
	}
    
}