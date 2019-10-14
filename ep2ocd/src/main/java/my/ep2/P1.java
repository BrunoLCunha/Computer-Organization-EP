package my.ep2;
public class P1 implements GetSet{
        
        private String conteudo_p1 = "";
        
        public String Get()
        {               
            return (conteudo_p1);        
        }
        
        public void Set(String x){
                
                conteudo_p1 = x;
        }
}