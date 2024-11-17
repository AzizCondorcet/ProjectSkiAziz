package dao;

import java.sql.Connection;

import singleton.EcoleConnection;

public abstract class DAO_Generique<T> {
    protected Connection connect = null;
    
    public DAO_Generique(Connection conn)
    {
        this.connect = conn;
    }
    
    public DAO_Generique() {
        this.connect= EcoleConnection.getInstance().getConnect();
    }
    
    public abstract boolean create(T obj);
    public abstract boolean delete(T obj);
    public abstract boolean update(T obj);
    public abstract T find(int id);
}
