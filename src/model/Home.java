package model;

import DAO.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Home {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ottieni un'istanza della tua ConnectionFactory
        ConnectionFactory connectionFactory;
        try {
            connectionFactory = ConnectionFactory.getInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        System.out.print("Inserisci l'ID del cliente: ");
        int idCliente = scanner.nextInt();

        Cliente cliente = Cliente.retrieveCliente(idCliente, connectionFactory);

        if (cliente != null) {
            System.out.println("Cliente con ID " + idCliente + " trovato nel database.");
            System.out.println("Nome cliente: " + cliente.getNome());
        } else {
            System.out.println("Nessun cliente con ID " + idCliente + " trovato nel database.");
        }

        // Chiudi la connessione quando hai finito
        try {
            connectionFactory.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}
