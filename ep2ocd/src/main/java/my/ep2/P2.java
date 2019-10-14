package my.ep2;
public class P2 implements GetSet{
        
    private String conteudo_p2 = "";
    
    public String Get()
    {
        return (conteudo_p2);        
    }
        
    public void Set(String x)
    {
        conteudo_p2 = x;
    }
}