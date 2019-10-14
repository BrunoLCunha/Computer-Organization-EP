package my.ep2;
public class IR implements GetSet{
        
        public String conteudo_ir = "";
        
        public P1 p1 = new P1(); 
        public P2 p2 = new P2();
        int ct_numbers = 0;        
        
        public String Get()
        {        
            return(conteudo_ir + p1.Get() + p2.Get());
        }
        
        public String getUpcode()
        {
            return conteudo_ir;
        }
        
        public int fetchUpcodeInc()
        {
            return ct_numbers ;
        }


        public void Set(String entrada)
        {        
            //System.out.print(entrada);
            ct_numbers = 0;
            String ir_content[] = entrada.split("\\+");
            conteudo_ir = ir_content[0];

             // Manda sinal para a ula incrementar o pc de acordo com o n de CT
            if(!ir_content[1].equals("n/a")) ct_numbers++;
            if(!ir_content[2].equals("n/a")) ct_numbers++;
            
            if(ct_numbers == 1) // Se p1 nao tiver nada, p1 recebe p2 e vice-versa 
            {
                if(ir_content[1].equals("n/a")) ir_content[1] = ir_content[2];
                else ir_content[2] = ir_content[1];
            }

            p1.Set(ir_content[1]);
            p2.Set(ir_content[2]);
        }
        
        public String getIntrucao()
        {
            String c = "";

            for(int i = 0; i < 4; i++)
            {
                c += conteudo_ir.charAt(i);
            }

            return(c);
        }
}