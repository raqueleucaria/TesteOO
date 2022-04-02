/**
* Classe responsavel pela implementacao de metodos que sao utilizados
* diversas vezes no desenvolvimento do programa
*
* @author Luciano de Freitas Melo
*/

package principal;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Utensilios {
	
	static Scanner ler = new Scanner(System.in);

	// L� uma string do usuario, primeiro limpando o buffer e recebendo qualquer valor que estiver na linha.					
	public static String lerString() {
		ler.nextLine();
		String variavel = ler.nextLine().toLowerCase();
		
		return variavel;
	}
	
	// Formata uma string qualquer em uma string capitalizada, ou seja, com a primeira letra maiuscula e o resto 
	// minuscula
	public static String capitalize(String palavra) {
		palavra = palavra.toLowerCase();
		palavra = palavra.substring(0,1).toUpperCase().concat(palavra.substring(1));
		return palavra;
	}
	
	/*
	 * Validar variavel do tipo inteiro
	 * 	L� um inteiro do usuario, e valida esse valor de duas formas:
	 * 		1. Se o usuario digitou um inteiro, caso contrario retorna uma mensagem de erro padrao e pede novamente a digitacao.
	 * 		2. Se o valor digitado esta entre os parametros aceitos, isso e feito pelas variaveis minimoAceitavel e maximoAceitavel,
	 * 		da seguinte forma:
	 * 							minimoAceitavel < valor recebido < maximoAceitavel
	 * 		Caso o valor recebido nao esteja dentro desse intervalo, retorna uma mensagem de erro ao usuario (tambem
	 * 		definida por um parametro) e pede a leitura novamente do valor, ate que seja digitado um valor valido.
	 * 
	 * Exemplo de formato de mensagem de erro: "Numero digitado invalido, tente novamente: "
	 */
	public static int validarVariavel(int minimoAceitavel, int maximoAceitavel, String mensagemErro) {
		boolean valorInvalido;
		int variavel = 0;
		do {
			valorInvalido = false;
			try {
				variavel = ler.nextInt();
				if (variavel < minimoAceitavel || variavel > maximoAceitavel) {
					valorInvalido = true;
					System.out.print(mensagemErro);
				}
			}
			catch (InputMismatchException erro){
				valorInvalido = true;
				ler.nextLine();
				System.out.print("Valor digitado invalido, por favor insira um NUMERO: ");
			}
			
		}while(valorInvalido);
		
		return variavel;
	}

	// Verifica se a letra no parametro e uma letra e se e um digito unico.
	public static boolean letraValida (String letra) {
		boolean digitoUnico = letra.length() == 1; // Verifica se o usuario digitou apenas um digito.
		boolean ehLetra = Character.isLetter(letra.charAt(0)); // Verifica se o chute e uma letra do alfabeto.
		
		if (!digitoUnico)
			System.out.print("Digite apenas uma letra por vez, tente novamente: ");
			
		else if (!ehLetra)
			System.out.print("Isso nao e uma letra, tente novamente: ");
			
		return (digitoUnico && ehLetra ? true : false);
	}

	// Verifica se a array dada no parametro contem o valor respectivo.
	public static boolean arrayContem(char[] array, char valor) {
        boolean valorEncontrado = false;

        for(int i = 0; i < array.length; i++) {
            if(array[i] == valor) {
                valorEncontrado = true;
            }
        }
        return valorEncontrado; 
    }
	
	public static boolean arrayContem(String[] array, String valor) {
        boolean valorEncontrado = false;

        for(int i = 0; i < array.length; i++) {
            if(array[i] != null && array[i].equals(valor)) {
                valorEncontrado = true;
            }
        }
        return valorEncontrado; 
    }

	// Retorna o tamanho da array, mas ignorando valores do tipo null na contagem.
	public static int arrayLengthNotNull(String[] array) {
		int arrayLength = 0;
	
		for (String value : array)
			if (value != null)
				arrayLength++;
		
		return arrayLength;
	}	
}