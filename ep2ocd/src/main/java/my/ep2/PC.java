/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.ep2;

/**
 *
 * @author JP
 */
public class PC implements GetSet{
    private String valor;
	
	public void Set(String valor)
	{
            this.valor = valor;
	}
	
	public String Get()
	{
		return ((Integer)Integer.parseInt(valor, 16)).toString();
	}
}
