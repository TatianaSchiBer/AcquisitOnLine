package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cliente {
    private int idCliente;
    private String nome;
    private String cognome;

    public Cliente(int idCliente, String nome, String cognome) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.cognome = cognome;
    }

    // Metodi getter e setter per idCliente, nome e cognome

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    // Metodo per recuperare un cliente dal database tramite l'ID
    public static Cliente getClienteFromDatabase(int idCliente, Connection connection) {
        String query = "SELECT Nome, Cognome FROM CLIENTE WHERE Id_Cliente = 2";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCliente);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String nome = resultSet.getString("Nome");
                String cognome = resultSet.getString("Cognome");

                Cliente cliente = new Cliente(idCliente, cognome, cognome);
                cliente.setIdCliente(idCliente);
                cliente.setNome(nome);
                cliente.setCognome(cognome);

                return cliente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Ritorna null se non viene trovato il cliente
    }
}

