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
public class BarramentoExterno {
    private static String content_s = "";
    
    public static void Set(String content)
    {
       // System.out.print("Barramento Externo: " + content);
        content_s = content;
    }
    
    public static String Get()
    {
        return content_s;
    }
}
