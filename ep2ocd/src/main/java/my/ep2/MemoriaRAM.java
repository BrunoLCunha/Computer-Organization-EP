/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package my.ep2;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author meste
 */
public class MemoriaRAM implements GetSet {
    //aloca hashmap para a armazenar os upcodes
    private Map<String,String> memoriaHash = new HashMap<>();
    //apenas para ter uma ultima referencia de memoria
    private int ultimoEndereco = 0;
    String adress_line = "0";
    public boolean adress_valid = false, read, write;
    //retorna a quantidade de dados armazenados na memoria
    public int getSize() {return memoriaHash.size();}
    
    //checa se existe o endereco na memoria
    public boolean existeEndereco(String endereco){
        return memoriaHash.containsKey(endereco);
    }
    
    public void AV(boolean adress_valid)
    {
        this.adress_valid = adress_valid;
    }
    
    public void Read(boolean read)
    {
        this.read = read;
    }
    
    public void Write(boolean write)
    {
        this.write = write;
    }
    
    //retorna o upcode naquele endereco caso exista, se o upcode conter CT ou [CT], retorna o upcode + CT1 + CT2
    public String Get(){
        //System.out.print("\nPegando da memória: " + memoriaHash.get(adress_line));
        if (memoriaHash.containsKey(adress_line) && read && memoriaHash.get(adress_line).length() != 12) return memoriaHash.get(adress_line); // É uma CT
        if (memoriaHash.containsKey(adress_line) && read){
            if (memoriaHash.get(adress_line).substring(4, 8).equals("0100") || memoriaHash.get(adress_line).substring(4, 8).equals("1001")
                    || memoriaHash.get(adress_line).substring(8, 12).equals("0100") || memoriaHash.get(adress_line).substring(8, 12).equals("1001")){
               
                boolean existeL1 = false,existeL2 = false,existeL3 = false;
                char[] nextLine0 = {}, nextLine1 = {}, nextLine2 = {};
                
                if(memoriaHash.containsKey(adress_line)) existeL1= true;
                if(memoriaHash.containsKey(((Integer)(Integer.parseInt(adress_line) + 1)).toString())) existeL2= true;
                if(memoriaHash.containsKey(((Integer)(Integer.parseInt(adress_line) + 2)).toString())) existeL3= true;
                
                int adressTmp = Integer.parseInt(adress_line);
                
                if(existeL1) nextLine0 = memoriaHash.get(adress_line).toCharArray(); //Ver se esta amazenando com endereco em hex ou int
                if(existeL2) nextLine1 = memoriaHash.get(((Integer)(adressTmp + 1)).toString()).toCharArray(); 
                if(existeL3) nextLine2 = memoriaHash.get(((Integer)(adressTmp + 2)).toString()).toCharArray(); 
                
                boolean existeCt = false;
                boolean existeCt2 = false;
                
                if(!existeL1) return "";
                if(nextLine0.length != 12) 
                {
                    return memoriaHash.get(adress_line); // Uma constante
                }
                else // Um upcode
                {
                    if(existeL2)
                    if(nextLine1.length != 12) // Um upcode tem 12 chars, que e a diferenca entre uma constante qualquer
                    {
                        existeCt = true;
                        
                        if(existeL3)
                        if(nextLine2.length != 12)
                            existeCt2 = true;
                    }
                }
                
                return memoriaHash.get(adress_line) + "+" + (existeCt ? memoriaHash.get(((Integer)(Integer.parseInt(adress_line)+1)).toString()):"n/a") + "+" + (existeCt2 ? memoriaHash.get(((Integer)(Integer.parseInt(adress_line)+2)).toString()):"n/a");
            }

            if(memoriaHash.get(adress_line).substring(0, 4).equals("0111") || memoriaHash.get(adress_line).substring(0, 4).equals("1000")
                    || memoriaHash.get(adress_line).substring(0, 4).equals("1001") || memoriaHash.get(adress_line).substring(0, 4).equals("1010")
                    || memoriaHash.get(adress_line).substring(0, 4).equals("1011") || memoriaHash.get(adress_line).substring(0, 4).equals("1100")
                    || memoriaHash.get(adress_line).substring(0, 4).equals("1101")){

                return memoriaHash.get(adress_line) + "+" + memoriaHash.get(((Integer)(Integer.parseInt(adress_line)+1)).toString()) + "+n/a";
            }
            return memoriaHash.get(adress_line) + "+n/a+n/a";
        }
        return "";
    }
    
    //modifica o valor naquele endereco
    public void Set(String content){
        if(adress_valid) 
        {
            adress_line = content;
            System.out.print("\nAdress Valid: " + content);
        }
        else if(write) {
            memoriaHash.put(adress_line, content);
            System.out.print("\nConteúdo: " + memoriaHash.get(adress_line));
        }
    }
    
    //escreve todos os enderecos e seus conteudos
    public void printaHash(){
        for (int i = 0; i < memoriaHash.size(); i++){
            System.out.println("Endereco: "+Integer.toString(i)+" Conteudo: "+memoriaHash.get(Integer.toString(i)));
        }
    }
    
    //armazena um novo upcode na memoria convertendo uma linha do arquivo txt
    //exemplo: 	(MOV AX, [BX]), converte para upcode ficando 000000000110 e assim adiciona na memoria no proximo endereco disponivel
    public void add(String line){
        String[] opcodesInstrucao = { "MOV", "ADD", "SUB", "MUL", "DIV", "MOD", "CMP", "JMP", "JE", "JNE", "JG", "JGE", "JGL", "JLE"};
        String[] opcodesRegistros = { "AX", "BX", "CX", "DX", "CT", "[AX]", "[BX]", "[CX]", "[DX]", "[CT]" };
        if (line.contains(":")) return;

        String[] lineSplitted = line.split(" ");
        boolean isJump = false;
        String InstrucaoOpcode = "", RegistroOpcode = "", RegistroOpcode2 = "";
        for (int i = 0; i < opcodesInstrucao.length; i++){
            if (lineSplitted[0].equals(opcodesInstrucao[i]))
            {
                InstrucaoOpcode = Integer.toBinaryString(i);
                while(InstrucaoOpcode.length() < 4){
                    InstrucaoOpcode = "0" + InstrucaoOpcode;
                }
                isJump = i >= 7;
            break;
            }
        }
       

        String number = isJump ? "" : extractNumber(lineSplitted[1]);
        String number2 = isJump ? "" : extractNumber(lineSplitted[2]);
        for (int i = 0; i < opcodesRegistros.length && lineSplitted.length > 1 && !isJump; i++){
            if (number.length() > 0){
                int CTType = 4;
                if (lineSplitted[1].contains("["+number+"]")) CTType = 9;
                RegistroOpcode = Integer.toBinaryString(CTType);
                while(RegistroOpcode.length() < 4){
                    RegistroOpcode = "0" + RegistroOpcode;
                }
        break;
            }

            if (lineSplitted[1].contains(opcodesRegistros[i]))
            {
                RegistroOpcode = Integer.toBinaryString(i);
                while(RegistroOpcode.length() < 4){
                    RegistroOpcode = "0" + RegistroOpcode;
                }
            }
        }
        for (int i = 0; i < opcodesRegistros.length && lineSplitted.length > 2 && !isJump; i++){
            if (number2.length() > 0){
                int CTType = 4;
                if (lineSplitted[2].contains("["+number2+"]")) CTType = 9;
                RegistroOpcode2 = Integer.toBinaryString(CTType);
                while(RegistroOpcode2.length() < 4){
                    RegistroOpcode2 = "0" + RegistroOpcode2;
                }
        break;
            }

            if (lineSplitted[2].contains(opcodesRegistros[i]))
            {
                RegistroOpcode2 = Integer.toBinaryString(i);
                while(RegistroOpcode2.length() < 4){
                    RegistroOpcode2 = "0" + RegistroOpcode2;
                }
            }
        }
        String result = "";
        if (InstrucaoOpcode.length() > 0) result += InstrucaoOpcode;
        if (RegistroOpcode.length() > 0) result += RegistroOpcode;
        else result += "0000";
        if (RegistroOpcode2.length() > 0) result += RegistroOpcode2;
        else result += "0000";

        //adiciona na memoria o upcode
        memoriaHash.put(Integer.toString(ultimoEndereco++), result);
        if (isJump){
            memoriaHash.put(Integer.toString(ultimoEndereco++), Integer.toString(Integer.decode(lineSplitted[1])));
        }
        
        if (number.length() > 0) {
            memoriaHash.put(Integer.toString(ultimoEndereco++), number);
        }
        if (number2.length() > 0) {
            memoriaHash.put(Integer.toString(ultimoEndereco++), number2);
        }
    }
    
    //extrai mumeros inteiros em uma string
    public static String extractNumber(final String str) {
        
        if(str == null || str.isEmpty()) return "";
        
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for(char c : str.toCharArray()){
            if(Character.isDigit(c)){
                sb.append(c);
                found = true;
            } else if(found){
                break;
            }
        }
        
        return sb.toString();
    }
    
}
