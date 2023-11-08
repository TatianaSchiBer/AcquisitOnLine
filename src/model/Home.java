package model;

import DAO.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

/**
 * Questa è la classe principale del programma.
 */
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
        clrscr();
        cliente = Cliente.eseguiLogin(connectionFactory);

        System.out.println("Cliente con ID " + cliente.getIdCliente() + " trovato nel database.");
        System.out.println("Nome cliente: " + cliente.getNome());

        System.out.println();

        int scelta = -1;
        Carrello carrello = new Carrello(mappaProdotti);

        while (scelta != 4) {
            visualizzaMenu(); // Visualizza il menu

            System.out.print("Scegli un'opzione: ");
            scelta = InputHandler.leggiInteroValido();

            gestisciScelta(scelta, connection, connectionFactory, mappaProdotti, carrello, scelta, cliente);
        }

        // Chiudi la connessione quando hai finito
        try {
            connectionFactory.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        scanner.close();
    }

    /**
     * Visualizza il menu delle opzioni.
     */
    private static void visualizzaMenu() {
        System.out.println("\n\nMenu:");
        System.out.println("1. Visualizza prodotti");
        System.out.println("2. Inserisci prodotti nel carrello");
        System.out.println("3. Visualizza carrello");
        System.out.println("4. Esci");
    }

    /**
     * Gestisce le scelte dell'utente.
     */
    public static void gestisciScelta(int scelta, Connection connection, ConnectionFactory connectionFactory, Map<Integer, Prodotto> mappaProdotti, Carrello carrello, int idCliente, Cliente cliente) throws SQLException {
        switch (scelta) {
            case 1:
                clrscr();
                Prodotto.stampaTuttiIProdotti(connection);
                break;
            case 2:
                clrscr();
                Prodotto.selezionaProdotti(connection, connectionFactory, mappaProdotti, carrello, idCliente);
                break;
            case 3:
                clrscr();
                Carrello.visualizzaCarrello(idCliente, cliente);
                carrello.stampaCarrello();
                break;
            case 4:
                clrscr();
                System.out.println("Chiusura del programma.");
                break;
            default:
                clrscr();
                System.out.println("Scelta non valida. Riprova.");
                break;
        }
    }

    public class InputHandler {
        private static Scanner scanner = new Scanner(System.in);

        // Legge un intero valido da input
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

    /**
     * Metodo per cancellare il terminale/console.
     */
    public static void clrscr() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }
}
