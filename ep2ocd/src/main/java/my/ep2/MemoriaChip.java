package my.ep2;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
A memória do chip é um hashMap com chaves String (upodes) indicando as palavras para esses upcodes(um arraylist de palavras)
*/

public class MemoriaChip{
    //Cria um HashMap com uma chave String (upcode) e um ArrayList de palavras
    static Map<String,ArrayList> M = new HashMap<String,ArrayList>();
    
    @SuppressWarnings("unchecked")
    static void popularMemoriaChip(){
        try{
            //Tenta ler o arquivo
            FileReader arq = new FileReader("palavras.txt");
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();      
            char palavra[] = linha.toCharArray();
      
            //O arraylist vai armazenar uma lista de palavras  
            ArrayList words = new ArrayList();

            String aux = "";
            String upcode = "";
			String upcodeAux = "";
            String word = "";
            int upcodeInt = 0;
            int cont = 1;
            int i = 1;
            char[] cadaUpcode = new char[3];
            String cadaUpcodeString = "";
        
            //Enquanto existirem linhas que não foram lidas no arquivo
            while(linha != null){
                palavra = linha.toCharArray();
                upcode = "";
                word = "";
				upcodeAux = "";
                words = new ArrayList();
                //System.out.print(linha + "\n");
                i = 1;
                //Pelo padrão do arquivo, antes da letra 'w' de cada linha está o upcode     
                while(palavra[i] != 'w'){
                    upcode += palavra[i];
                    i++;
                }
                i++;

                //Pelo padrão do arquivo, entre a letra'w' e o símbolo ';' temos a(s) palavra(s)    
                while(palavra[i] != ';'){
                    //Pelo padrão do arquivo, quando se acha o símbolo '+' é porque temos mais de uma palavra para a instrução, logo, devemos adicionar cada uma no arraylist
                    if(palavra[i] == '+'){
                        words.add(word);
                        //System.out.print(word);
						word = "";
                        i++;
                    }
                    else {
                        word += palavra[i];
                        i++;
                    }
                }
                
                words.add(word);
								
                if(upcode.length() == 3){
                    cadaUpcode = upcode.toCharArray(); //Cada digito do upcode de uma instrução
                    for (int atual = 0; atual <= 2; atual++){
			String temp = String.valueOf(cadaUpcode[atual]);
                        upcodeInt = Integer.parseInt(temp);
                        cadaUpcodeString = Integer.toBinaryString(upcodeInt);

                        //Padroniza o valor em binário de todos upcodes, colocando os zeros à esquerda para montar uma String de exatos 12 bits (4 bits em cada um dos 3 laços)
                        aux = cadaUpcodeString;
                        cadaUpcodeString = "0000";
                        cadaUpcodeString += aux;
                        cadaUpcodeString = cadaUpcodeString.substring(aux.length()); //Deixa apenas os últimos 4 dígitos da String
                        upcodeAux += cadaUpcodeString;
                    }
                    upcode = upcodeAux;
                }

                //JUMPS -> caso o upcode só tenha um ou dois dígitos
                else{
                    upcodeInt = Integer.parseInt(upcode);
                    cadaUpcodeString = Integer.toBinaryString(upcodeInt);
                    
                    aux = cadaUpcodeString;
                    cadaUpcodeString = "0000";
                    cadaUpcodeString += aux;
                    cadaUpcodeString = cadaUpcodeString.substring(aux.length()); //Deixa apenas os últimos 4 dígitos da String
                    
                    cadaUpcodeString += "00000000"; //Coloca 8 bits 0 à direita do upcode do jmp
		    upcode = cadaUpcodeString;
                }
           		
		//--->COLOCA NO HASHMAP O UPCODE E O ARRAYLIST DE PALAVRAS<---
		//System.out.println(upcode);
		M.put(upcode, words);
	
		linha = lerArq.readLine();
            }
			
            arq.close();
        
	}catch(IOException e){
            e.getMessage();
            System.out.println(e);
        }
    }
  
    public static ArrayList busca(String upcode){
        //System.out.print(upcode);
	return M.get(upcode);
    }

}

/*
Créditos
https://docs.oracle.com/javase/6/docs/api/java/lang/Integer.html#toBinaryString(int)
https://www.devmedia.com.br/hashmap-java-trabalhando-com-listas-key-value/29811
https://www.devmedia.com.br/lendo-dados-de-txt-com-java/23221
*/
