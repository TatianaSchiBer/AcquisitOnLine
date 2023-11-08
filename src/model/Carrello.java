package model;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Carrello {
    private Map<Integer, Integer> prodottiCarrello; // La chiave è l'ID del prodotto, il valore è la quantità
    private Map<Integer, Prodotto> mappaProdotti; // La chiave è l'ID del prodotto, il valore è l'oggetto Prodotto

    public Carrello(Map<Integer, Prodotto> mappaProdotti) {
        prodottiCarrello = new HashMap<>();
        this.mappaProdotti = mappaProdotti;
    }

    // Aggiungi un prodotto al carrello con una determinata quantità
    public void aggiungiAlCarrello(int idProdotto, int quantita) {
        if (quantita < 1) {
            System.out.println("La quantità deve essere maggiore di zero.");
            return;
        }

        if (prodottiCarrello.containsKey(idProdotto)) {
            // Se il prodotto è già nel carrello, aggiungi la quantità specificata
            int quantitaAttuale = prodottiCarrello.get(idProdotto);
            prodottiCarrello.put(idProdotto, quantitaAttuale + quantita);
        } else {
            // Se il prodotto non è nel carrello, aggiungi il prodotto con la quantità specificata
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
        + " Metodo di pagamento : " + metodoPagamento);
    }

    // Metodo per stampare il carrello con nome e prezzo dei prodotti
    public void stampaCarrello() {
        System.out.println("Prodotti nel carrello:");
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

        System.out.println("Prezzo totale del carrello: " + prezzoTotale);
    }
}

