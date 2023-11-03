package model;

import DAO.ConnectionFactory;
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
            scanner.close();
            return;
        }

        int idCliente = 0;
        boolean condition = false;
        while (!condition) {
            try {
                System.out.print("Inserisci l'ID del cliente: ");
                idCliente = scanner.nextInt();

                Cliente cliente = Cliente.retrieveCliente(idCliente, connectionFactory);

                System.out.println("Cliente con ID " + idCliente + " trovato nel database.");
                System.out.println("Nome cliente: " + cliente.getNome());

                break;
            } catch (Exception e) {
                System.err.println("Nessun cliente con ID " + idCliente + " trovato nel database.");
            }
        }


        System.out.println("Questi sono i prodotti nel database : ");
        try {
            Prodotto.stampaTuttiIProdotti(connectionFactory.getConnection());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println();
        System.out.print("Inserisci l'ID del prodotto, finchè non inserirai '0' " +
                                                            "continuerà a iterare e inserire i prodotti nrl carrello : ");
        int idProdotto = scanner.nextInt();
     

        // Chiudi la connessione quando hai finito
        try {
            connectionFactory.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}
