package my.ep2;
public class Principal{
      
    public static GetSet Componentes[] = new GetSet[26];
	
    public Principal(MemoriaRAM ram){
        Componentes[0] = new PC(); // PC
	Componentes[1] = new MAR(); // MAR
	Componentes[2] = new Registrador(); // MBR
	Componentes[3] = new Registrador(); //AX
        Componentes[4] = new Registrador(); //BX
        Componentes[5] = new Registrador(); //CX
	Componentes[6] = new Registrador(); //DX
	Componentes[7] = Componentes[2]; // Memoria p/ MBR
	Componentes[8] = new IR();
	Componentes[9] = ((IR)Componentes[8]).p1; //entrada p1
	Componentes[10] = ((IR)Componentes[8]).p2; //entrada p2
	Componentes[12] = new ULA();
	Componentes[11] = ((ULA)Componentes[12]).X;
	Componentes[13] = ram;
	Componentes[14] = Componentes[0];
	Componentes[15] = Componentes[2];
	Componentes[16] = Componentes[3];
	Componentes[17] = Componentes[4];
	Componentes[18] = Componentes[5];
	Componentes[19] = Componentes[6];
	Componentes[20] = ((IR)Componentes[8]).p2; // saida p2
	Componentes[21] = ((IR)Componentes[8]).p1; // saida p1
	Componentes[22] = ((ULA)Componentes[12]).AC_;
	Componentes[23] = Componentes[1];
	Componentes[24] = Componentes[2];
	Componentes[25] = Componentes[13];
        
        Componentes[0].Set("0"); //Inicializa 
        Componentes[3].Set("0"); 
        Componentes[4].Set("0");
        Componentes[5].Set("0");
        Componentes[6].Set("0");
    }
	static CAR car_ = new CAR();
	static CBR cbr_ = new CBR();
	static int t = 0, cicle = 0;        
	static boolean inicializa = false, condicaoPulo = true;        

	public static boolean[] convertePalavra(String palavra){
			char[] con = palavra.toCharArray();


			boolean[] convertida = new boolean[con.length];


			for(int i = 0; i < con.length; i++){
					if(con[i] == '0') convertida[i] = false;
					
					else{
							convertida[i] = true;
					}
			}
			
			return (convertida);
	}
                
	public static void recebePalavra(String palavra)
	{
            if(condicaoPulo == false){ return;}
            boolean palavraConvertida [] = convertePalavra(palavra);
        
                ((MemoriaRAM)Componentes[13]).AV(palavraConvertida[26]);
		((MemoriaRAM)Componentes[13]).Read(palavraConvertida[27]);
		((MemoriaRAM)Componentes[13]).Write(palavraConvertida[28]);

                 //System.out.print(palavra);
                // printBoolArray(palavraConvertida);
                 
		for(int i = 25; i >= 0; i--) // Primeiro saída e depois entrada
		{
				if(palavraConvertida[i] && i <= 13)
						abrePortaEntrada(i);
				else if(palavraConvertida[i])
						abrePortaSaida(i);
		}
	}		
        
	public void ButtonClick()
	{
		if(inicializa) 
		{
			palavraHelper();
		}
		else 
		{
			Ciclo(0); //Busca primeira instrução
			inicializa = true;
		}
	}


	public static void palavraHelper()
	{        
            if(t < cbr_.Get().size()){
                TestaCondicoes(cicle);
                    recebePalavra(cbr_.Get().get(t).toString());
                    t++;
                    
                    return;
            } else {cicle++;}


            if(cicle < 2) Ciclo(cicle);
            else
            {
                    cicle = 0;
                    Ciclo(cicle);
                    ((IR)Componentes[8]).conteudo_ir = ""; //Zera IR
            }
        
	}	
        
	static void abrePortaEntrada(int i) //P o registrador
	{
            if(i < 13 && i != 7)
                Componentes[i].Set(BarramentoInterno.Get());
            else 
                Componentes[i].Set(BarramentoExterno.Get());
	}        


	static void abrePortaSaida(int i) // P o barramento
	{
            if(i <  23 )
                BarramentoInterno.Set(Componentes[i].Get());
            else
                BarramentoExterno.Set(Componentes[i].Get());
	}        
        
        static void printBoolArray(boolean[] b)
        {
            for(int i = 0; i < b.length; i++)
            {
                System.out.print(b[i] + " ,");
            }
        }

	public static void Ciclo(int k)
	{
		//Ciclo de busca
		t = 0; // Nova instrucao
		condicaoPulo = true; 

		if(k == 0)
		{
                    car_.Set("111000000000");
                    cbr_.Set(MemoriaChip.busca(car_.Get()));
                    palavraHelper(); // Ciclo de busca 4 tempos
		}


		else if(k == 1)
		{
                    car_.Set(((IR)Componentes[8]).getUpcode());
                    cbr_.Set(MemoriaChip.busca(car_.Get())); 
                    palavraHelper(); // Roda instrucao do IR
		}
	}


        
	static void TestaCondicoes(int cicle)
	{
                if(cicle == 0) // Sinal para ULA do ciclo de busca
                {
                    ((ULA)Componentes[12]).SetUpcode("1110" +  ((IR)Componentes[8]).fetchUpcodeInc()); // O PC e incrementado de acordo com as CT
                    return;
                }           
                
		String upcode = ((IR)Componentes[8]).getIntrucao();
                
                if(upcode.equals("1000")) // jump equals
                    if(!((ULA)Componentes[12]).zero) condicaoPulo = false; // Nao executa

                if(upcode.equals("1001")) // jump not equals
                    if(((ULA)Componentes[12]).zero) condicaoPulo = false; // Nao executa
                    
                if(upcode.equals("1010")) // jump greater
                    if(((ULA)Componentes[12]).zero || ((ULA)Componentes[12]).negative) condicaoPulo = false; // Nao executa
                    
                if(upcode.equals("1011")) // jump greater equals
                    if(((ULA)Componentes[12]).negative) condicaoPulo = false; // Nao executa
                    
                if(upcode.equals("1100")) // jump lower
                    if(((ULA)Componentes[12]).zero || !((ULA)Componentes[12]).negative) condicaoPulo = false; // Nao executa
                    
                if(upcode.equals("1101")){ // jump lower equals
                    if(!((ULA)Componentes[12]).negative && !((ULA)Componentes[12]).zero) condicaoPulo = false; // Nao executa
                }
                    
                if(upcode.equals("0001") || upcode.equals("0010") || upcode.equals("0011") || upcode.equals("0100") || upcode.equals("0101") || upcode.equals("0110")) ((ULA)Componentes[12]).SetUpcode(upcode); // Verifica se é conta e manda um sinal para a ULA com apenas os 4 primeiros bits
               
             
	}
}