package singleton;

import BE.ouagueni.model.SkierPOJO;
import dao.DAOFactory;
import dao.DAO_Generique;

public class TestDAO {
    public static void main(String[] args) {
        DAOFactory daoFactory = new DAOFactory();
        
        DAO_Generique<SkierPOJO> skierDAO = daoFactory.getSkierDAO();
        if (skierDAO != null) {
            SkierPOJO skier = skierDAO.find(1);
            if (skier != null) {
                System.out.println("Skier N°" + skier.getId() + " - " + skier.getNom() + " " + skier.getPrenom());
            } else {
                System.out.println("Skier not found!");
            }
        } else {
            System.out.println("SkierDAO is null!");
        }
    }
}
