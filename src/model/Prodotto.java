package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import DAO.ConnectionFactory;
import model.Home.InputHandler;

public class Prodotto {
    private int idProdotto;
    private String nome;
    private String descrizione;
    private double prezzo;
    private int quantita;
    
    public Prodotto(int idProdotto, String nome, String descrizione, double prezzo, int quantita) {
        this.idProdotto = idProdotto;
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.quantita = quantita;
    }
    
    
    // Metodi getter e setter per idProdotto, nome, descrizione, prezzo e quantita
    public int getIdProdotto() {
        return idProdotto;
    }
    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescrizione() {
        return descrizione;
    }
    
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public double getPrezzo() {
        return prezzo;
    }
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
    public int getQuantita() {
        return quantita;
    }
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
    // Metodo per recuperare un prodotto dal database tramite l'ID
    public static Prodotto getProdottoFromDatabase(int idProdotto, Connection connection) {
        String query = "SELECT Nome, Descrizione, Prezzo, Quantita FROM PRODOTTO WHERE Id_Prodotto = 2";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idProdotto);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                
                String nome = resultSet.getString("Nome");
                String descrizione = resultSet.getString("Descrizione");
                double prezzo = resultSet.getDouble("Prezzo");
                int quantita = resultSet.getInt("Quantità");
                Prodotto prodotto = new Prodotto(quantita, descrizione, descrizione, prezzo, quantita);
                prodotto.setIdProdotto(idProdotto);
                prodotto.setNome(nome);
                prodotto.setDescrizione(descrizione);
                prodotto.setPrezzo(prezzo);
                prodotto.setQuantita(quantita);
                return prodotto;
                
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Ritorna null se non viene trovato il prodotto
        
    }
    
    // Metodo per stampare tutti i prodotti dalla tabella PRODOTTO
    public static void stampaTuttiIProdotti(Connection connection) {
        String query = "SELECT Id_Prodotto, Nome, Descrizione, Prezzo, Quantità FROM PRODOTTO";
        
        try (PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int idProdotto = resultSet.getInt("Id_Prodotto");
                String nome = resultSet.getString("Nome");
                String descrizione = resultSet.getString("Descrizione");
                double prezzo = resultSet.getDouble("Prezzo");
                int quantita = resultSet.getInt("Quantità");
                
                System.out.println("ID Prodotto: " + idProdotto);
                System.out.println("Nome: " + nome);
                System.out.println("Descrizione: " + descrizione);
                System.out.println("Prezzo: " + prezzo);
                System.out.println("Quantità: " + quantita);
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean exists(int idProdotto, Connection connection) throws SQLException {
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM prodotto WHERE id_prodotto = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idProdotto);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                exists = count > 0;
            }
        }
        
        return exists;
    }
    
    
    public static boolean productQuantity(int idProdotto, int quantita, Connection connection) {
        String query = "SELECT quantità FROM prodotto WHERE id_prodotto = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idProdotto);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int quantitaDisponibile = resultSet.getInt("quantità");
                
                return quantitaDisponibile >= quantita && quantita > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Metodo per caricare tutti i prodotti in una mappa
    public static Map<Integer, Prodotto> caricaMappaProdotti(Connection connection) {
        String query = "SELECT Id_Prodotto, Nome, Descrizione, Prezzo, Quantità FROM PRODOTTO";
        Map<Integer, Prodotto> mappaProdotti = new HashMap<>();
        
        try (PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int idProdotto = resultSet.getInt("Id_Prodotto");
                String nome = resultSet.getString("Nome");
                String descrizione = resultSet.getString("Descrizione");
                double prezzo = resultSet.getDouble("Prezzo");
                int quantita = resultSet.getInt("Quantità");
                
                Prodotto prodotto = new Prodotto(idProdotto, nome, descrizione, prezzo, quantita);
                mappaProdotti.put(idProdotto, prodotto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return mappaProdotti;
    }
    
    public static void selezionaProdotti(Connection connection,ConnectionFactory connectionFactory, Map<Integer, 
    Prodotto> mappaProdotti, Carrello carrello, int idCliente) throws SQLException {
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
                            carrello.aggiungiAlCarrello(idProdotto, quantita);
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
    
}

