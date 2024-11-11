package BE.ouagueni.model;

import java.util.Date;
import java.util.Objects;

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
    
    public static boolean checkIfPersonExists(String name, String surname)
    {
        SkierDAO skierDAO = new SkierDAO(EcoleConnection.getInstance().getConnect());
        return skierDAO.findByNameAndSurname(name, surname) != null;
    }

}