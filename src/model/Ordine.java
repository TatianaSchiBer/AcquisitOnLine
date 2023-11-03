package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DAO.ConnectionFactory;

public class Ordine {
    private int idOrdine;
    private int idCliente;
    private String tipoPagamento;
    
    public Ordine(int idCliente, String tipoPagamento) {
        
        this.idCliente=idCliente;
        this.tipoPagamento=tipoPagamento;
    }
    
    public int getidOrdine() {
        return idOrdine;
    }
    
    public void setidOrdine(int idOrdine) {
        this.idOrdine=idOrdine;
        
    }
    
    public int getidCliente() {
        return idCliente;
    }
    
    public void setidCliente(int idCliente){
        this.idCliente=idCliente;
        
    }
    
    public String gettipoPagamento() {
        return tipoPagamento;
    }
    
    public void settipoPagamento(String tipoPagamento){
        this.tipoPagamento=tipoPagamento;
    }
    
    
    // Metodo per inserire un ordine sul database tramite l'IdCliente 
    public static Ordine insertOrdine(int idCliente, String tipoPagamento, ConnectionFactory connectionFactory) {
        try (Connection conn = connectionFactory.getConnection()) {
            String sql = "INSERT INTO ORDINE (Id_Cliente, Tipo_Pagamento) VALUES (?, ?)";
            
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, idCliente);
                preparedStatement.setString(2, tipoPagamento);
    
                int righeInserite = preparedStatement.executeUpdate();
    
                if (righeInserite > 0) {
                    // Recupera l'ID generato automaticamente dall'insert
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int idOrdine = generatedKeys.getInt(1);
                        System.out.println("L'ordine è stato inserito con successo. ID Ordine: " + idOrdine);
                    } else {
                        System.out.println("Nessun ID Ordine generato.");
                    }
                } else {
                    System.out.println("Nessuna riga è stata inserita");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Errore nell'inserimento dell'ordine");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return null;
    }
    
    
    
    
}