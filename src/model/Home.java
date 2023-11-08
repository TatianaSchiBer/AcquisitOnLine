package model;

import DAO.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class Home {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        // Dichiarazione della mappaProdotti
        Map<Integer, Prodotto> mappaProdotti = null;

        // Ottieni un'istanza della tua ConnectionFactory
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        Connection connection = connectionFactory.getConnection();
        try {
            connectionFactory = ConnectionFactory.getInstance();
            // Carica i prodotti in mappaProdotti
            mappaProdotti = Prodotto.caricaMappaProdotti(connectionFactory.getConnection());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            scanner.close();
            return;
        }

        Cliente cliente = new Cliente(0, null, null);

        // Effettua il login
        cliente = eseguiLogin(connectionFactory);

        if (cliente == null) {
            System.err.println("Nessun cliente trovato nel database. Chiusura del programma.");
            connectionFactory.close();
            scanner.close();
            return;
        }

        System.out.println("Cliente con ID " + cliente.getIdCliente() + " trovato nel database.");
        System.out.println("Nome cliente: " + cliente.getNome());

        System.out.println();

        int scelta = -1;
        Carrello carrello = new Carrello(mappaProdotti);

        while (scelta != 4) {
            visualizzaMenu(); // Chiamata al nuovo metodo per visualizzare il menu

            System.out.print("Scegli un'opzione: ");
            scelta = InputHandler.leggiInteroValido();

            gestisciScelta(scelta, connection, mappaProdotti, carrello, scelta, cliente);
        }

        // Chiudi la connessione quando hai finito
        try {
            connectionFactory.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        scanner.close();
    }

    private static Cliente eseguiLogin(ConnectionFactory connectionFactory) {
        Cliente cliente = new Cliente(0, null, null);
        boolean condition = false;

        while (!condition) {
            try {
                System.out.print("Inserisci l'ID del cliente: ");
                int idCliente = InputHandler.leggiInteroValido();

                cliente = Cliente.retrieveCliente(idCliente, connectionFactory);

                if (cliente != null) {
                    condition = true;
                } else {
                    System.err.println("Nessun cliente con ID " + idCliente + " trovato nel database.");
                }

            } catch (Exception e) {
                System.err.println("Errore durante il recupero del cliente.");
            }
        }

        return cliente;
    }

    private static void visualizzaMenu() {
        System.out.println("Menu:");
        System.out.println("1. Visualizza prodotti");
        System.out.println("2. Inserisci prodotti nel carrello");
        System.out.println("3. Visualizza carrello");
        System.out.println("4. Esci");
    }
    private static void gestisciScelta(int scelta, Connection connection, Map<Integer, Prodotto> mappaProdotti, Carrello carrello, int idCliente, Cliente cliente) throws SQLException {
        switch (scelta) {
            case 1:
                Prodotto.stampaTuttiIProdotti(connection);
                break;
            case 2:
                selezionaProdotti(connection, mappaProdotti, carrello);
                break;
            case 3:
                System.out.println("Questi sono i prodotti nel carrello : ");
                System.out.println("Cliente : " + idCliente + " " + cliente.getNome() + " " + cliente.getCognome());
                carrello.stampaCarrello();
                break;
            case 4:
                System.out.println("Chiusura del programma.");
                break;
            default:
                System.out.println("Scelta non valida. Riprova.");
                break;
        }
    }

    private static void selezionaProdotti(Connection connection, Map<Integer, Prodotto> mappaProdotti, Carrello carrello) {
        System.out.print("Inserisci l'ID del prodotto, finché non inserirai '0', " +
                "continuerai a iterare e inserire i prodotti nel carrello: ");
        int idProdotto = 0;

        while (true) {
            idProdotto = InputHandler.leggiInteroValido();
            int quantita = 0;
            if (idProdotto == 0) {
                break;
            }

            if (mappaProdotti.containsKey(idProdotto)) {
                boolean condition1 = false;
                while (!condition1) {
                    try {
                        System.out.print("Inserisci la quantità del prodotto: ");
                        quantita = InputHandler.leggiInteroValido();

                        if (Prodotto.productQuantity(idProdotto, quantita, connection)) {
                            System.out.println("Quantità valida.");
                            carrello.aggiungiAlCarrello(idProdotto, quantita);
                            System.out.println("Prodotto aggiunto al carrello.");
                            break;
                        } else {
                            System.out.println("Quantità non valida.");
                            condition1 = false;
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        System.out.println("Reinserisci la quantità del prodotto: ");
                    }
                }
            } else {
                System.out.println("Prodotto non trovato.");
            }
        }
    }

	public class InputHandler {
    private static Scanner scanner = new Scanner(System.in);

    public static int leggiInteroValido() {
        while (true) {
            try {
                String input = scanner.next();
                int valore = Integer.parseInt(input);
                return valore;
            } catch (NumberFormatException e) {
                System.err.println("Input non valido. Inserisci un valore intero.");
            }
        }
    }

    public static void chiudiScanner() {
        scanner.close();
    }
}
}


