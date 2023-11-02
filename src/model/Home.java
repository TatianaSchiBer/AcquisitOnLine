package model;
import java.util.Scanner;

public class Home {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Login");
		login();
		
		System.out.println("Benvenuti nello store");

		System.out.println("Ecco la lista");
		
		//Stampa lista da DB
		
		int prodotto;
		String ask;

		boolean condition = true;
		while (condition) {
			System.out.println("Digita il numero del prodotto che vuoi selezionare");
			prodotto = sc.nextInt();
			System.out.println("Se vuoi scegliere altro inserisci y altrimenti n");  //Inserire richiesta quantità
			ask = sc.nextLine();
			if (ask == "n") {
				System.out.println("Hai scelto "+ prodotto);
				break;
			}

		}
		
		System.out.println("Ecco il tuo carrello");
		stampMap();
		//Stampa valori mappa tramite metodo

	}

	private static void stampMap() {
		//Implementa metodo di stampa della mappa
		
	}

	private static void login() {
		boolean condition = false ;

																//Implememtare codice database
		while (!condition) {
			try {
				System.out.println("Inserisci codice cliente");
				Scanner sc = new Scanner(System.in);
				int codice_cliente = sc.nextInt();
				condition = true;
			} catch (Exception e) {
				System.err.println("Codice cliente invalido");
			}
			
		}
		
		
	}

}
