package model;

import DAO.ConnectionFactory;

import java.sql.Connection;
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
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.print("Inserisci l'ID del prodotto, finchè non inserirai '0' " +
		"continuerà a iterare e inserire i prodotti nel carrello : ");
		int idProdotto = 0;    
		String metodoPagamento = "";                                          
		
		boolean productExists = false; 
		condition = false;
		
		while (!condition) {
			try {
				idProdotto = scanner.nextInt();
				
				if (idProdotto == 0) {
					break;
				}
				productExists = Prodotto.exists(idProdotto, connectionFactory.getConnection());
				
			} catch (SQLException e) {
				e.printStackTrace();
			}   
			
			if (productExists) {
				try {	
					System.out.print("Inserisci la quantità del prodotto: ");		
					int quantita = scanner.nextInt();
					boolean isQuantityValid = Prodotto.productQuantity(idProdotto, quantita, connectionFactory.getConnection());
					if (isQuantityValid) {
						System.out.println("La quantità inserita è valida.");
					}else {
						throw new Exception();
					}
				} catch (Exception e) {
					System.out.println("Quantità non valida");
				}
			}else {
				System.out.println("Prodotto non trovato");
			}
			
		}
		
		
		
		System.out.println("Creo l'ordine con i prodotti selezionati,scrivi un metodo di pagamento");
		
		metodoPagamento = scanner.next();
		
		Ordine.insertOrdine(idCliente, metodoPagamento, connectionFactory);
		
		
		// Chiudi la connessione quando hai finito
		try {
			connectionFactory.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		scanner.close();
		
	}
	
}