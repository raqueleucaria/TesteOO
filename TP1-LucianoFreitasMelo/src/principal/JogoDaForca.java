/**
* Classe principal do jogo. 
*
* @author Luciano de Freitas Melo
*/


package principal;

public class JogoDaForca {

	public static void main(String[] args) {
		
		System.out.println("Ola, esse programa e destinado a uso e administracao do jogo da Forca! Aproveite!");
		System.out.println("PLUS ULTRA!");
		System.out.println();
		
		Gerenciador.temasPadrao();
		int escolhaMenu;
		do {
			imprimeMenu();
			System.out.print("Digite a opcao desejada: ");
			escolhaMenu = Utensilios.validarVariavel( 1, 4, "Essa nao e uma escolha valida, tente novamente: ");
			
			switch (escolhaMenu) {
				case 1:
					Gerenciador.gerenciarTemas();
					break;
				case 2:
					Gerenciador.gerenciarPalavras();
					break;
				case 3:
					JogarForca.jogar();
					break;
			}
			
		} while(escolhaMenu != 4);
		
		System.out.println();
		System.out.println("Obrigado por usar o nosso programa, ate mais!");
	
	}
	
	private static void imprimeMenu() {
		System.out.println("************** MENU *************");
		System.out.println();
		System.out.println("1. Gerenciar Temas");
		System.out.println("2. Gerenciar Palavras");
		System.out.println("3. Jogar");
		System.out.println("4. Sair");
		System.out.println();
	}
}
