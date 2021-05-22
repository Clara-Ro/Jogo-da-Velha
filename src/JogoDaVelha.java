import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class JogoDaVelha {
	private static File arquivo = new File("ranking.obj");
	private static int jog;
	private static int[][] casa = new int[3][3];
	private static int linha, coluna, win;
	private static Jogador jogador1, jogador2;
	private static Jogador[]jogadores = new Jogador[50];
	private static int quantjogadores=0;
	private static long inicio;
	private static Scanner leitor = new Scanner(System.in);
	
	public static void setTempo() {
		inicio=System.currentTimeMillis();
	}
	
	public static long getTempo() {
		return(System.currentTimeMillis()-inicio)/1000;
	}
	
	public static void cadastro() {
		//cadastra o nome dos jogadores
		System.out.println("Digite o nome do jogador 1");
		String nome_jogador1=leitor.next();
		jogador1 = buscarJogador(nome_jogador1);
		System.out.println("Digite o nome do jogador 2");
		String nome_jogador2=leitor.next();
		jogador2 = buscarJogador(nome_jogador2);
	}
	
	public static void salvarJogadores() {
		try {
			ObjectOutputStream saida = new ObjectOutputStream(new FileOutputStream(arquivo));
			saida.writeObject(jogadores);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void lerJogadores() {
		try {
			ObjectInputStream saida = new ObjectInputStream(new FileInputStream(arquivo));
			jogadores=(Jogador[]) saida.readObject();
			while (jogadores[quantjogadores] != null && quantjogadores<50) {
				quantjogadores= quantjogadores + 1;
			}
		}catch(FileNotFoundException e) {
			//não faz nada
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Jogador buscarJogador(String nome) {
		Jogador jogador=null;
		for(int i=0;(i<quantjogadores)&&(jogador==null);i++) {
			if(jogadores[i] != null && jogadores[i].nome.equalsIgnoreCase(nome)) {
				jogador=jogadores[i];
			}
		}
		if(jogador==null) {
			jogador= new Jogador();
			jogador.nome=nome;
			if(quantjogadores<50) {
				jogadores[quantjogadores]=jogador;
				quantjogadores=quantjogadores +1;
			}
		} return jogador;
	}
	
	public static void imprimirJogadores() {
		lerJogadores();
		System.out.println("----- Resultado dos Jogadores -----");
		for (int i=0;i<quantjogadores;i++) {
			System.out.println("Nome: " + jogadores[i].nome); 
			System.out.println(" | vitórias:" + jogadores[i].vitorias); 
			System.out.println(" | derrotas:" + jogadores[i].derrotas); 
		}
	}
	
	public static void desenha(int x,int y){
		if(casa[x][y]==1) {
			System.out.print("X");
		}else if(casa[x][y]==2){
			System.out.print("O");
		}else {
			System.out.print(" ");
		}
	}
	public static void jogo() { 
		// aqui é feita a construção do tabuleiro 
		System.out.print("\n   1    2    3\n"); 
		System.out.print("1 "); 
		desenha(0, 0); 
		System.out.print("  | ");  
		desenha(0, 1); 
		System.out.print("  | "); 
		desenha(0, 2); 
		System.out.print("\n --------------\n2 ");  
		desenha(1, 0); 
		System.out.print("  | "); 
		desenha(1, 1); 
		System.out.print("  | "); 
		desenha(1, 2); 
		System.out.print("\n --------------\n3 "); 
		desenha(2, 0); 
		System.out.print("  | ");  
		desenha(2, 1); 
		System.out.print("  | "); 
		desenha(2, 2); 
	}
	
	public static void jogar(int jogador) {
		int i = 0;
		if(jogador==1) {
			jog =1;	
			System.out.println("\n Vez do jogador "+jogador1.nome);
		}else{
			jog=2;
			System.out.println("\n Vez do jogador "+jogador2.nome);
	}
	
	while(i==0) {
		linha=0;
		coluna=0;
		while(linha<1||linha>3) {
			System.out.print("Escolha a linha 1, 2 ou 3");
			linha=leitor.nextInt();
				//aviso de linha invalida caso o jogador digite um número menor que 1 ou maior que 3
				if (linha<1||linha>3) {
					System.out.println("Linha invalida! Escolha uma linha entre 1 e 3");
		}
	}
		while(coluna<1||coluna>3) {
			System.out.print("Escolha a coluna 1, 2 ou 3");
			coluna=leitor.nextInt();
				if (coluna<1||coluna>3) {
					System.out.println("Coluna invalida! Escolha uma coluna entre 1 e 3");
			}
	}
	//ajusta indices para começar do zero
	linha=linha-1;
	coluna=coluna-1;
		if(casa[linha][coluna]==0) {
			//se não estiver ocupado marca com o simbolo do jogador da vez
			casa[linha][coluna]=jog;
			i=1;
		}else {
			System.out.println("Posição ocupada!");
		}
	}
}
	public static void check() { 
		int i = 0; 
		//verificando se houve vencedor na Horizontal:          
		for (i = 0; i < 3; i++) { 
			if (casa[i][0] == casa[i][1] && casa[i][0] == casa[i][2]) { 
				if (casa[i][0] == 1) win = 1;
				if (casa[i][0] == 2) win = 2; 
			} 
		} 
		//verificando se houve vencedor na Vertical:
		for (i = 0; i < 3; i++) { 
			if (casa[0][i] == casa[1][i] && casa[0][i] == casa[2][i]) { 
				if (casa[0][i] == 1) win = 1; 
				if (casa[0][i] == 2) win = 2; 
			} 
		} 
		//verificando se houve vencedor na Diagonal de cima para baixo: 
		if (casa[0][0] == casa[1][1] && casa[0][0] == casa[2][2]) { 
			if (casa[0][0] == 1) win = 1; 
			if (casa[0][0] == 2) win = 2; 
		} 
		//verificando se houve vencedor na Diagonal de baixo para cima: 
		if (casa[0][2] == casa[1][1] && casa[0][2] == casa[2][0]) { 
			if (casa[0][2] == 1) win = 1; 
			if (casa[0][2] == 2) win = 2; 
		} 
	}
	
	public static void main(String[] args) {
		int i = 0;
		cadastro();
		lerJogadores();
		setTempo();
		//percorre o tabuleiro
		for(i=0;i<9;i++) {
			jogo(); //desenha o tabuleiro
			if(i%2==0) {
				jogar(2);
			}else {
				jogar(1);
			}
		check();//checa se alguem ganhou
		if (win==1||win==2) {
		// sai do laço antes de completar o tabuleiro, se alguém tiver vencido
			i=10;
		}
	}	
		jogo(); //desenha novamente o tabuleiro 
		
		System.out.println();
		System.out.print(win);
		if(win==1) {
			//informa o vencedor
			System.out.println("Jogador " + jogador1.nome + " ganhou!");
			jogador1.vitorias= jogador1.vitorias+1;
			jogador2.derrotas= jogador2.derrotas+1;
		}else if(win==2) {
			System.out.println("Jogador " + jogador2.nome + " ganhou!");
			jogador2.vitorias= jogador2.vitorias+1;
			jogador1.derrotas= jogador1.derrotas+1;
		}else {
			//se não houver vencedor
			System.out.println("Empatou");
		}
		System.out.println("O tempo total de jogo foi de " + getTempo() + "s ");
		//salvarJogadores();
		imprimirJogadores();
	}
}
