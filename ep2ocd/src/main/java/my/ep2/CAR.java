/*
O CAR coloca armazena um upcode 
*/
package my.ep2;
public class CAR implements GetSet{
	private static String upcode = "";
	
	public String Get(){
		return upcode;
	}
	
	public void Set(String novoUpcode){
		upcode = novoUpcode;
	}	
}
