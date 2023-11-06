package model;

import DAO.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class Home {
	public static void main(String[] args) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		// Dichiarazione di mappaProdotti
		Map<Integer, Prodotto> mappaProdotti = null;
		
		// Ottieni un'istanza della tua ConnectionFactory
		ConnectionFactory connectionFactory;
		Connection connection = null;
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
		int idCliente = 0;
		boolean condition = false;
		
		while (!condition) {
			try {
				System.out.print("Inserisci l'ID del cliente: ");
				idCliente = InputHandler.leggiInteroValido();
        		System.out.println("Hai inserito un intero valido: " + idCliente);
		
				cliente = Cliente.retrieveCliente(idCliente, connectionFactory);
		
				if (cliente != null) {
					System.out.println("Cliente con ID " + idCliente + " trovato nel database.");
					System.out.println("Nome cliente: " + cliente.getNome());
					break;
				} else {
					System.err.println("Nessun cliente con ID " + idCliente + " trovato nel database.");
					condition = false;
				}		
				
			} catch (Exception e) {
				System.err.println("Errore durante il recupero del cliente.");
			}
		}
		
		
		System.out.println("Questi sono i prodotti nel database : ");
		try {
			connection = connectionFactory.getConnection();
			mappaProdotti = Prodotto.caricaMappaProdotti(connection);
			Prodotto.stampaTuttiIProdotti(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.print("Inserisci l'ID del prodotto, finché non inserirai '0', " +
		"continuerai a iterare e inserire i prodotti nel carrello: ");
		int idProdotto = 0;
		
		Carrello carrello = new Carrello(mappaProdotti); // Crea un carrello con la mappa dei prodotti
		
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
			carrello.aggiungiAlCarrello(idProdotto, quantita);
			System.out.println("Prodotto aggiunto al carrello.");
		}
		
		System.out.println("Inserisci metodo di pagamento: ");
		String metodoPagamento = scanner.next();
		
		
		
		System.out.println("Questi sono i prodotti nel carrello : ");
		System.out.println("Cliente : " + idCliente + " " + cliente.getNome() + " " + cliente.getCognome());
		System.out.println("Metodo di pagamento : " + metodoPagamento);
		carrello.stampaCarrello();
		
		
		
		
		// Chiudi la connessione quando hai finito
		try {
			connectionFactory.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		scanner.close();
		
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

