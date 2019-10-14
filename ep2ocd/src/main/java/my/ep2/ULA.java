package my.ep2;
class ULA implements GetSet
{
	public boolean zero,overflow,negative;
	public Registrador X;
	private int ULA_R;
	private String dataUpcode = "";	
	private int value = 0;
        public AC AC_;
	private boolean isCMP = false;
        
	public ULA()
	{
		AC_ = new AC(this);
		X = new Registrador();
		ULA_R = 0;
	}
	
	public String Get(){ return "" + ULA_R;} 
        public String GetUpcode(){return dataUpcode;} 
	public void Set(String conteudo)
        {
            ULA_R = Integer.parseInt(conteudo);
            if(isCMP)
            {
                doOperation();
                isCMP = false;
            }
        } 
	public void SetUpcode(String upcode)
        {
            this.dataUpcode = upcode;
            String upcode_n = ""; 
			upcode_n += dataUpcode.charAt(0);
			upcode_n += dataUpcode.charAt(1);
			upcode_n += dataUpcode.charAt(2);
			upcode_n += dataUpcode.charAt(3);
            if(upcode_n.equals("0110")) isCMP = true;
        }
	
	public void doOperation()
	{
			String upcode = ""; 
			upcode += dataUpcode.charAt(0);
			upcode += dataUpcode.charAt(1);
			upcode += dataUpcode.charAt(2);
			upcode += dataUpcode.charAt(3);
			
			if(upcode.equals("0001"))
			{
				value = Integer.parseInt(X.Get()) + ULA_R;
			}
			
			if(upcode.equals("0010"))
			{
				value = Integer.parseInt(X.Get()) - ULA_R;
			}
			
			if(upcode.equals("0011"))
			{
				value = Integer.parseInt(X.Get()) * ULA_R;
			}
			
			if(upcode.equals("0100"))
			{
				//if(ULA_R == 0) return UI.Error();
				value = Integer.parseInt(X.Get()) / ULA_R;
			}
			
			if(upcode.equals("0101"))
			{
				//if(ULA_R == 0) //return UI.Error();
				value = Integer.parseInt(X.Get()) % ULA_R;
			}
			
			if(upcode.equals("0110"))
			{
				int cmp = Integer.parseInt(X.Get()) - ULA_R;
				
				if(value > Short.MAX_VALUE || value < Short.MIN_VALUE)
				{
					overflow = true;
					return;
				}
				if(cmp == 0) zero = true;
				if(cmp < 0) negative = true;
				return;
			}
			
			if(upcode.equals("1110"))
			{
				String ctNumber = "";
				for(int i = 4; i < dataUpcode.length(); i++) // Monta a CT a partir da string upcode
				{
					ctNumber += dataUpcode.charAt(i);
				}
				
				try
				{
                                    int k = Integer.parseInt(ctNumber);
                                    value = Integer.parseInt(X.Get()) + 1 + k; 
                                    AC_.Set(Integer.toHexString(value)); //int para Hex
				}
				catch (Exception e) {};
		
				return; //Nao mexe nas flags da ULA
			}

		if(value > Short.MAX_VALUE || value < Short.MIN_VALUE ) // 16 bits
		{
			overflow = true;
			return;
		}
		
		if(value == 0) zero = true;
		if(value < 0) negative = true;
		if(!overflow) AC_.Set(Integer.toString(value));
	}
}