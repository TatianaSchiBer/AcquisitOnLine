package model;

public class Ordine {
    private int idOrdine;
    private int idCliente;
    private String tipoPagamento;
    
    public Ordine(int idCliente, String tipoPagamento) {
        
        this.idCliente=idCliente;
        this.tipoPagamento=tipoPagamento;
    }
    
    public int getidOrdine() {
        return idOrdine;
    }
    
    public void setidOrdine(int idOrdine) {
        this.idOrdine=idOrdine;
        
    }
    
    public int getidCliente() {
        return idCliente;
    }
    
    public void setidCliente(int idCliente){
        this.idCliente=idCliente;
        
    }
    
    public String gettipoPagamento() {
        return tipoPagamento;
    }
    
    public void settipoPagamento(String tipoPagamento){
        this.tipoPagamento=tipoPagamento;
    }
    
    
}