package my.ep2;
public class Barramento implements GetSet{
        
    private String conteudo = "";
        
    public String Get()
    {
            return conteudo;
    }        

    public void Set(String conteudo)
    {
            this.conteudo = conteudo;
    }              
}