/**
 * Escreva a descrição da classe KMeans aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Knn
{
    private static String[][] dadosTreino;
    private static String[][] dadosTeste;
    private static double[][] distancia;
    private static int k;
    
    
    public static void main(String args[]){
        //busca os dados do arquivo iris.data
        System.out.println("\nDados para Treino");
        dadosTreino = new String[690][10];
        carga("dataset-treino.data", dadosTreino);
        escreveDados(dadosTreino);
        
        System.out.println("\nDados para Teste");
        dadosTeste = new String[268][10];
        carga("dataset-teste.data", dadosTeste);
        escreveDados(dadosTeste);
        
        //define o número de vizinhos
        k = 9;
        
        executaKnn();
    }
    
    /**
     * Carrega dos dados do arquivo iris.data para a matriz dados. A classe da planta, o 5o atributo, não é considerado.
     */
    public static void carga(String nome, String[][]dados){
        Scanner ler = new Scanner(System.in);
         
        //System.out.printf("\nConteúdo do arquivo texto:\n");
        try {
            FileReader arq = new FileReader(nome);
            BufferedReader lerArq = new BufferedReader(arq);
 
            String linha = lerArq.readLine();
            
            int i = 0;
            
            while (linha != null) {
               // System.out.printf("%s\n", linha);

                String[] valor = linha.split(",");   
                
                linha = lerArq.readLine(); 
                
                if (valor.length == 10) {
                    for (int j = 0; j < 9; j++) {
                    	dados[i][j] = valor[j];
                    }
                    
                    if (valor[9].equals("3")) {
                    	dados[i][9] = "3";
                    }
                    else if (valor[9].equals("2")) {
                    	dados[i][9] = "2";
                    }
                    else if (valor[9].equals("1")) {
                    	dados[i][9]= "1";
                    } else {
                        dados[i][9]= "0";	
                    }
                    
                    i++;
                }
            }
            
            arq.close();
        } 
        catch (IOException e) {
                System.err.printf("Erro na abertura do arquivo: %s.\n",
                e.getMessage());
        }
    }
    
    /**
     * Escreve os dados 
     */
    public static void escreveDados(String[][] dados){
        //System.out.println("\n\n--------- D A D O S ---------");
        System.out.println("    lc11  lc12  lc13  lc21  lc22  lc23  lc31  lc32  lc33  classe");
        
        for (int i = 0; i < dados.length; i++) {
                System.out.print((i + 1) + ":");
                
                for(int j = 0; j < dados[i].length; j++) {
                    System.out.print(dados[i][j] + " ");
                }
                
                System.out.println();
        }
    }
    
    
    /**
     * Calcula a distância eucliadiana entre um dado corrente (atual) e uma amostra
     */
    public static double euclidiana(String[] atual, String[] amostra) {
      double soma = 0;
      
      double valor1 = 0;
      double valor2 = 0;
      
      for(int i = 0; i < atual.length-1; i++) {
    	  if (atual[i] == "b") {
        	  valor1 = 0.0;
          } else if (atual[i] == "x") {
        	  valor1 = 1.0;
          } else if (atual[i] == "o") {
        	  valor1 = 2.0;
          }
    	  
    	  if (amostra[i] == "b") {
        	  valor2 = 0.0;
          } else if (amostra[i] == "x") {
        	  valor2 = 1.0;
          } else if (amostra[i] == "o") {
        	  valor2 = 2.0;
          }
    	  
          soma += Math.pow(valor1 - valor2, 2);
      }
      
      return Math.sqrt(soma);
    }
    
    
    public static void executaKnn() {
        int acertos = 0;
        
    	for(int i = 0; i < dadosTeste.length; i++) {
		
    		distancia = new double[dadosTreino.length][2];
		
			for(int j = 0; j < dadosTreino.length; j++) {
				distancia[j][0] = euclidiana(dadosTeste[i], dadosTreino[j]);
				distancia[j][1] = Double.parseDouble(dadosTreino[j][9]);
    		}
	    		//System.out.println("Antes");
	    		//exibeDistancias(distancia);
	    		ordena(distancia);
	    		//System.out.println("Depois");
	    		//exibeDistancias(distancia);
	    		System.out.println("Classe Predita:" + moda(distancia));
	    		//break;
	    	}
    }
  
   public static void ordena(double [][]distancia){
   	double aux[];
   	
   	for (int i = 0; i < distancia.length - 1; i++) {
   		for (int j = 0; j < distancia.length - 1 - i; j++) {
   			if (distancia[j][0] > distancia[j + 1][0]) {
   				troca(distancia[j], distancia[j + 1]);
   			}
   		}
    }
   }
   
  public static void troca(double linha1[], double linha2[]) {
  	double aux;
  	
  	for (int i = 0; i < linha1.length; i++) {
  		aux = linha1[i];
  		linha1[i] = linha2[i];
  		linha2[i] = aux;
  	}
  }
  
  public static void exibeDistancias(double[][] distancia) {
	for (int i = 0; i < distancia.length; i++) {
		System.out.println("Distancia: " + distancia[i][0] + " Classe do vizinho: " + distancia[i][1]);
	}
  }
  
  public static int moda(double[][] distancia) {
  	int rotulo, cont, classe = -1, quant = 0;
  	
  	for (int c = 1; c <= 8; c++) {
  		cont = 0;
  		
  		for (int i = 0; i < k; i++){
  			if (distancia[i][1] == c) cont++;
  		}
  		
  		if (cont > quant){
  			quant = cont;
  			classe = c;
  		}
  	}
  	
  	return classe;
  }
    
}
