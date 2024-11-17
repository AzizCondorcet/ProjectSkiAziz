package BE.ouagueni.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import dao.AccreditationDAO;

public class AccreditationPOJO implements Serializable {
    private String name;
    private int id;
    
	public AccreditationPOJO(String name) {
		super();
		this.name = name;
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


	@Override
	public String toString() {
		return "AccreditationPOJO [name=" + name + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(name);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccreditationPOJO other = (AccreditationPOJO) obj;
		return Objects.equals(name, other.name);
	}
	public void createAccreditation(int lessonTypeId) {
        AccreditationDAO accreditationDAO = new AccreditationDAO();
        accreditationDAO.createAccreditation(this, lessonTypeId); 
    }
	public void deleteAccreditation() {
	    AccreditationDAO accreditationDAO = new AccreditationDAO();
	    accreditationDAO.deleteAccreditation(this);
	}
	
	// Appeler le DAO pour récupérer les accréditations
    public static List<AccreditationPOJO> getAllAccreditations() {
        AccreditationDAO accreditationDAO = new AccreditationDAO();
        return accreditationDAO.getAllAccreditations();
    }

    
}

