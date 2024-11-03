package dao;

import BE.ouagueni.model.SkierPOJO;
import singleton.EcoleConnection;

public class DAOFactory {
    public DAO_Generique<SkierPOJO> getSkierDAO() {
        return new SkierDAO(EcoleConnection.getInstance().getConnect());
    }
}
