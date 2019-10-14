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
public class BarramentoInterno {
     private static String content_b = "";
    
    public static void Set(String content)
    {
        // System.out.print("Barramento Interno: " + content);
        content_b = content;
    }
    
    public static String Get()
    {
        return content_b;
    }
}
