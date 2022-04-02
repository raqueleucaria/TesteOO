/**
* Classe responsavel pela gerencia de uma jogada. 
*
* @author Luciano de Freitas Melo
*/


package principal;

import java.security.SecureRandom;

public class JogarForca {
	private static final int LIMITE_ERROS = 5; // Numero de tentativas que o usuario tem para acertar a palavra.
	
	private static char[] palavraSecreta; // Guarda a palavra secreta que sera usada durante o jogo, com os valores separados das letras.
	
	public static void jogar() {
		String jogarNovamente; // Recebe S ou N, dependendo se o usuario deseja jogar novamente ou nao.
		
		do {
			jogarRodada();
			jogarNovamente = validarJogarNovamente();
			System.out.println();
			
		}while (jogarNovamente.equals("S") ? true : false);

	}
	
	private static String validarJogarNovamente() {
		String input;
		
		do {
			input = Utensilios.ler.next().toUpperCase();
			
		} while (!JogarNovamenteValido(input));
		
		return input;
	}

	private static boolean JogarNovamenteValido(String input) {
		boolean inputValido = true;
		
		if(!Utensilios.letraValida(input))
			inputValido = false;
		
		else if (!input.equals("S") && !input.equals("N")) {
			inputValido = false;
			System.out.print("So e permitido digitar S (para SIM) ou N (para N�O): ");
		}
	
		return inputValido;
	}

	private static void jogarRodada() {
		
		imprimeMensagemAbertura();
		int posicaoTema = selecionaTema();
		
		palavraSecreta = escolherPalavraSecreta(Gerenciador.temasEPalavras[posicaoTema]);; 
																    
		
		char[] letrasAcertadas = inicializaLetrasAcertadas(); // E onde guardaremos as letras ja acertadas pelo usuario.
		char[] letrasErradas = new char[26]; // Armazena as letras erradas que o usuario digitou.
		
		boolean enforcado = false; // Diz se o jogador errou o limite de vezes, ou seja, foi enforcado.
		boolean ganhou = false; // Diz se o jogador acertou a palavra ou nao.
		
		int erros = 0; // Conta quantas vezes o usuario errou a letra da palavra.
		char chute; // Guarda os chutes do jogador durante as rodadas da forca.
		
		while (!enforcado && !ganhou) {
			imprimeLetrasAcertadas(letrasAcertadas);
			imprimeLetrasErradas(letrasErradas);
			System.out.println();
			
			System.out.print("Digite uma letra: ");
			chute = pedeChute(letrasErradas, letrasAcertadas);
			
			if (Utensilios.arrayContem(palavraSecreta, chute)) // Ou seja, verifica se o chute do usuario tem na palavra secreta.
				marcaChuteCorreto(chute, letrasAcertadas);
				
			else {
				letrasErradas[erros ++] = chute;
				desenhaForca(erros);
				imprimeTentativasRestantes(erros);
			}
			
			enforcado = erros == LIMITE_ERROS;
			ganhou = acertouPalavra(letrasAcertadas);
		}
		
		System.out.printf("%nA palavra secreta era: %s%n", new String(palavraSecreta));
		if (ganhou)
			imprimeMensagemVencedor();
		else
			imprimeMensagemPerdedor();
		
		
	}

	private static void imprimeMensagemAbertura() {
		System.out.println();
		System.out.println("*********************************");
	    System.out.println("** Bem vindo ao jogo da Forca! **");
	    System.out.println("*********************************");
	    System.out.println();
	}

	// Apresenta os temas para o usuario e pede para que ele selecione um deles, retornando a posicao do tema escolhido por ele
	private static int selecionaTema() {
		Gerenciador.imprimeTemas();
		
		System.out.printf("Digite o numero do tema que deseja jogar: ");
		
		int posicaoTemaEscolhido = Gerenciador.escolhaTema();
			
		while(Gerenciador.temaSemPalavras(posicaoTemaEscolhido)) {
			System.out.print("O tema escolhido nao tem palavras armazenadas, tente outro: ");
			posicaoTemaEscolhido = Gerenciador.escolhaTema();		
		}
		return posicaoTemaEscolhido;
	}


	// Escolhe uma palavra aleatoriamente dentre a lista de palavras dada no parametro
	private static char[] escolherPalavraSecreta(String[] palavras) {
		SecureRandom randomNumbers = new SecureRandom();
		
		// randomNumbers ira gerar um numero aleatorio entre 1 e a quantidade de palavras armazenadas.
		// Utilizamos numeros a partir do 1, pois palavras tem o nome do tema como sendo o indice 0.
		int posicaoPalavra = randomNumbers.nextInt(1, Utensilios.arrayLengthNotNull(palavras));
																	 
		return palavras[posicaoPalavra].toUpperCase().toCharArray(); // Colocamos a palavra em maiuscula para melhor apresentacao ao usuario.
	
	}

	// Cria uma array com o caracter "_" para cada letra da palavra secreta, e tambem respeitando os espacos da palavra.
	// Exemplo: se palavra secreta e teco teco, o metodo cria a array {'_', '_', '_', '_', ' ', '_', '_', '_', '_'}
	private static char[] inicializaLetrasAcertadas() {
		int tamanhoPalavraSecreta = palavraSecreta.length;
		
		char[] letras = new char[tamanhoPalavraSecreta]; // letras e onde guardaremos os "_" de acordo com o tamanho da palavra secreta															 
		
		for (int i = 0; i < tamanhoPalavraSecreta; i++ ) {
			if(Character.isSpaceChar((palavraSecreta[i])))
				letras[i] = ' ';
			else
				letras[i] = '_';
		}
		return letras;
	}
	
	private static void imprimeLetrasAcertadas(char[] letrasAcertadas) {
		System.out.printf("%nPalavra: ");
		
		for (char letra : letrasAcertadas) {
			if (letra == ' ')
				System.out.print("  ");
			else 
				System.out.printf("%c ",letra);
		}
		
		System.out.println();
	}

	private static void imprimeLetrasErradas(char[] letrasErradas) {
		System.out.print("Letras tentadas: ");
		
		for (char letra : letrasErradas) {
			if (letra != 0)
			System.out.printf("%c  ", letra);
		}
		System.out.println();
	}

	private static char pedeChute(char[] letrasTentadas, char[] letrasAcertadas) {
		String chuteStr; // Utilizaremos o chute como string, pois o usuario pode digitar mais de um caracter na linha de comando e queremos poder
						 // avaliar essa situacao.
		
		do {
			chuteStr = Utensilios.ler.next().toUpperCase();
			
		}while(!chuteValido(chuteStr, letrasTentadas, letrasAcertadas));
		
		return chuteStr.charAt(0); // ao fim transformamos em char o chute para trabalharmos no restante do codigo.
	}

	private static boolean chuteValido(String chute, char[] letrasErradas, char[] letrasAcertadas) {
		char chuteChar = chute.charAt(0); // transforma o chute, que e uma string, em char, pois algumas funcionalidades so sao possiveis 
										  // com um tipo especifico.
		
		boolean chuteValido = true;
		// Verifica se o chute ja foi tentado pelo usuario, buscando tanto nas letras erradas, como nas letras acertadas.
		boolean chuteRepetido = Utensilios.arrayContem(letrasErradas, chuteChar) || Utensilios.arrayContem(letrasAcertadas, chuteChar);
															   	
		if (!Utensilios.letraValida(chute)) // Se a letra nao for valida, o chute tambem nao e valido.
			chuteValido = false;
		
		else if (chuteRepetido) {
			System.out.print("Voce ja tentou essa letra! digite novamente: ");
			chuteValido = false;
		}
			
		return chuteValido;
	}

	private static void marcaChuteCorreto(char chute, char[] letrasAcertadas) {
		int index = 0;
		
		for(char letra : palavraSecreta) {
			if (chute == letra)
				letrasAcertadas[index] = chute;	
			index++;
		}		
	}

	private static void desenhaForca(int erros) {
		System.out.println("  _______     ");
		System.out.println(" |/      |    ");

	    switch(erros) {
		    case 1:
		    	System.out.println(" |      (_)   ");
		    	System.out.println(" |            ");
		    	System.out.println(" |            ");
		    	System.out.println(" |            ");
		        break;
		    
		    case 2:    
		    	System.out.println(" |      (_)   ");
		        System.out.println(" |       |    ");
		        System.out.println(" |            ");
		        System.out.println(" |            ");
		        break;
		        
		    case 3:
		    	System.out.println(" |      (_)   ");
		    	System.out.println(" |      \\|/   ");
		    	System.out.println(" |            ");
		        System.out.println(" |            ");
		        break;
		    
		    case 4:
		    	System.out.println(" |      (_)   ");
		        System.out.println(" |      \\|/   ");
		        System.out.println(" |       |    ");
		        System.out.println(" |            ");
		        break;
		    
		    case 5:
		    	System.out.println(" |      (_)   ");
		        System.out.println(" |      \\|/   ");
		        System.out.println(" |       |    ");
		        System.out.println(" |      / \\   ");
		        break;
	    }
	    System.out.println(" |            ");
	    System.out.println("_|___         ");
	    System.out.println();
	}
	
	private static void imprimeTentativasRestantes (int erros) {
		int tentativasRestantes = LIMITE_ERROS - erros;
		
		if (erros != LIMITE_ERROS) {
			if (tentativasRestantes == 1)
				System.out.println("Voce errou, resta somente UMA tentativa!");
			else
			System.out.printf("Voce errou, restam %d tentativas!%n", tentativasRestantes);
		}
	}

	// Retorna true caso nao haja mais lacunas ('_') na array de letras acertadas.
	private static boolean acertouPalavra(char[] letrasAcertadas) {
		return !Utensilios.arrayContem(letrasAcertadas, '_');
	}

	private static void imprimeMensagemVencedor() {
		System.out.println("       ___________      ");
	    System.out.println("      '._==_==_=_.'     ");
	    System.out.println("      .-\\:      /-.    ");
	    System.out.println("     | (|:.     |) |    ");
	    System.out.println("      '-|:.     |-'     ");
	    System.out.println("        \\::.    /      ");
	    System.out.println("         '::. .'        ");
	    System.out.println("           ) (          ");
	    System.out.println("         _.' '._        ");
	    System.out.println("        '-------'       ");
	    System.out.println();
	    System.out.println("Parabens, voce acertou a palavra. Deseja jogar novamente? (S/N)");
	}

	private static void imprimeMensagemPerdedor() {
		System.out.println("    _______________         ");
	    System.out.println("   /               \\       ");
	    System.out.println("  /                 \\      ");
	    System.out.println("//                   \\/\\  ");
	    System.out.println("\\|   XXXX     XXXX   | /   ");
	    System.out.println(" |   XXXX     XXXX   |/     ");
	    System.out.println(" |   XXX       XXX   |      ");
	    System.out.println(" |                   |      ");
	    System.out.println(" \\__      XXX      __/     ");
	    System.out.println("   |\\     XXX     /|       ");
	    System.out.println("   | |           | |        ");
	    System.out.println("   | I I I I I I I |        ");
	    System.out.println("   |  I I I I I I  |        ");
	    System.out.println("   \\_             _/       ");
	    System.out.println("     \\_         _/         ");
	    System.out.println("       \\_______/           ");
	    System.out.println();
	    System.out.println("Voce perdeu! Deseja tentar novamente? (S/N)");
	}
		
}

