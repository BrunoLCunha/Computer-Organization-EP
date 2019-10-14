package my.ep2;
import java.util.ArrayList;

/*
O CBR atua em conjunto com o CAR, indicando as palavras para determinado upcode
Isso tudo (CAR e CBR) vai ser usado na classe Principal.java
*/
public class CBR{
	private ArrayList palavras = new ArrayList();
	
	ArrayList Get(){
		return palavras;
	}
	
	void Set(ArrayList novasPalavras){
		palavras = novasPalavras;
                //System.out.print(novasPalavras + "        ");
	}	
}