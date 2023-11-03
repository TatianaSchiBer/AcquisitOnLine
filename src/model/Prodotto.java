package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}

