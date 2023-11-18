package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import DAO.ConnectionFactory;

public class Carrello {
    private Map<Integer, Integer> prodottiCarrello; // La chiave è l'ID del prodotto, il valore è la quantità
    private Map<Integer, Prodotto> mappaProdotti; // La chiave è l'ID del prodotto, il valore è l'oggetto Prodotto
    
    public Carrello(Map<Integer, Prodotto> mappaProdotti) {
        prodottiCarrello = new HashMap<>();
        this.mappaProdotti = mappaProdotti;
    }
    
    // Aggiungi un prodotto al carrello con una determinata quantità
    public void aggiungiAlCarrello(int idProdotto, int quantita) throws ClassNotFoundException, SQLException {
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        Connection connection = connectionFactory.getConnection();
        
        if (quantita < 1) {
            System.out.println("La quantità deve essere maggiore di zero.");
            return;
        }
        
        if (prodottiCarrello.containsKey(idProdotto)) {
            // Se il prodotto è già nel carrello, aggiungi la quantità specificata
            int quantitaAttuale = prodottiCarrello.get(idProdotto);
            int sommaQuantità = quantitaAttuale + quantita;
            if (!Prodotto.productQuantity(idProdotto, sommaQuantità, connection)) {
                System.out.println("Quantità non disponibile in stock");
                System.out.println("Riprova");
                return;
            }
            System.out.println("Quantità valida.");
            System.out.println("Prodotto aggiunto al carrello.");
            System.out.println("Inserisci altri prodotti o digita 0 per terminare l'ordine");
            prodottiCarrello.put(idProdotto, sommaQuantità);
        } else {
            // Se il prodotto non è nel carrello, aggiungi il prodotto con la quantità specificata
            System.out.println("Quantità valida.");
            System.out.println("Prodotto aggiunto al carrello.");
            prodottiCarrello.put(idProdotto, quantita);
        }
    }
    
    public static void visualizzaCarrello(int idCliente, Cliente cliente) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String metodoPagamento = "";
        boolean condition = false;
        while (!condition) {
            System.out.println("Inserisci il metodo di pagamento  ");
            System.out.println("scegli tra carta o contanti : ");
            
            metodoPagamento = scanner.next().toLowerCase();
            
            if (metodoPagamento.equals("carta") || metodoPagamento.equals("contanti")) {
                Home.clrscr();
                break;
            }else{
                System.out.println("Inserisci il metodo di pagamento valido ");
            }
            
        }
        
        System.out.println("Questi sono i prodotti nel carrello : ");
        System.out.println("Cliente : " + idCliente + " " + cliente.getNome() + " " + cliente.getCognome() 
        + "\nMetodo di pagamento : " + metodoPagamento);
    }
    
    // Metodo per stampare il carrello con nome e prezzo dei prodotti
    public void stampaCarrello() {
        System.out.println("Prodotti nel carrello:\n\n");
        double prezzoTotale = 0.0;
        
        for (Map.Entry<Integer, Integer> entry : prodottiCarrello.entrySet()) {
            int idProdotto = entry.getKey();
            int quantita = entry.getValue();
            
            Prodotto prodotto = mappaProdotti.get(idProdotto);
            String nomeProdotto = prodotto.getNome();
            double prezzoProdotto = prodotto.getPrezzo();
            double prezzoTotaleProdotto = prezzoProdotto * quantita;
            
            System.out.println("Nome Prodotto: " + nomeProdotto);
            System.out.println("Quantità: " + quantita);
            System.out.println("Prezzo unitario: " + prezzoProdotto);
            System.out.println("Prezzo totale: " + prezzoTotaleProdotto);
            System.out.println();
            
            prezzoTotale += prezzoTotaleProdotto;
        }
        
        System.out.println("\n\nPrezzo totale del carrello: " + prezzoTotale + "\n\n");
    }
}

