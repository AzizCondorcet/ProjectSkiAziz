package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import BE.ouagueni.model.PersonnePOJO;

public class PersonneDAO {

    private Connection connect;

    public PersonneDAO(Connection conn) {
        this.connect = conn;
    }
}
