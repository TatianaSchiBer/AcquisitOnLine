package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.ConnectionFactory;

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
    public static Cliente retrieveCliente(int idCliente, ConnectionFactory connectionFactory) {
        Cliente cliente = null;

        try {
            Connection conn = connectionFactory.getConnection();

            // Crea una query SQL per cercare il cliente per ID e ottenere il nome
            String sql = "SELECT Nome, Cognome FROM CLIENTE WHERE Id_Cliente = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, idCliente);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String nome = resultSet.getString("Nome");
                        String cognome = resultSet.getString("Cognome");

                        cliente = new Cliente(idCliente, nome, cognome);
                    }
                }
            }

            connectionFactory.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }
}

