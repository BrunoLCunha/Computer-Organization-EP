package my.ep2;
class Registrador implements GetSet
{
	private String valor;
	
	public void Set(String valor)
	{
		this.valor = valor;
	}
	
	public String Get()
	{
		return this.valor;
	}
}