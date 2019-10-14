package my.ep2;
public class AC implements GetSet
{
	String conteudo = "";       
	ULA instance;        


	public AC (ULA ula_)
	{
		this.instance = ula_;
	}        

        public String GetACUI()
        {
            return conteudo;
        }
        
	public String Get()
	{
		instance.doOperation();
		return conteudo;
	}


	public void Set(String conteudo)
	{
		this.conteudo = conteudo;
	}
}