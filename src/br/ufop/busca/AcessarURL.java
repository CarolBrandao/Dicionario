package br.ufop.busca;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
 
public class AcessarURL {
 
   public static void main(String args[]) {
	  Scanner in = new Scanner(System.in);
	  
      while(true){
      
      System.out.println("PALAVRA: ");
      String palavra = in.nextLine();
      
      while(palavra.length() == 0) {
          System.out.println("Palavra vazia! Digite uma palavra v�lida.");
          palavra = in.nextLine();
       }
      
      // Pegando a url.
      String urlName = "http://www.dicio.com.br/"+palavra;
 
      try {
 
         URL url = new URL(urlName);
         HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
         BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
 
         String line = null;
 
         while( (line = br.readLine()) != null ){
        	 
        	 if (line.contains("description") && line.contains("<p itemprop=")){
        		 //System.out.println(line);
        		 
        		 // Se o conte�do da linha n�o for at� o fim do par�grafo ou at� a quebra de linha leia mais uma linha
        		 if( ! (line.contains("</p") ) &&  ! (line.contains("<br") )){
        			 line = line + br.readLine();
        			 //System.out.println(line);
        		 }
        		 // conferindo se tem a quebra de linha <br />  no meio da String
        		 while (line.contains("<br />") || line.contains("<br/>")){
        			//System.out.println(line);
        			 line = line.replace("<br/>"," "); 
        			line = line.replace("<br />"," ");
        			line = line + br.readLine();
        			
        		}

        		 // conferindo se tem a quebra de linha <br />  no meio da String
        		 while (line.contains("<strong>")){
         			//System.out.println(line);
         			line = line.replace("<strong>","");
         			line = line.replace("</strong>","");
         			line = line + br.readLine();
         			
         		}
            	 
        		 int inicio = line.indexOf(">");
        		 int fim = line.indexOf("</p>");
        		 String significado = line.substring(inicio+1, fim);
        		 System.out.println(significado);
        		 System.out.println();
        		 break;
        	 }
            
         }
 
         br.close();
         urlConnection.disconnect();
 
      } catch (MalformedURLException e){
         System.out.println("Erro ao criar URL. Formato inv�lido.");
    
      } catch (IOException e2) {
         System.out.println("Palavra n�o encontrada, talvez alguma letra tenha sido digitada errada.\nCertifique-se que a palavra n�o cont�m acentos gr�ficos ou cedilha");
         System.out.println();
      }
      }
 
   }


 
}