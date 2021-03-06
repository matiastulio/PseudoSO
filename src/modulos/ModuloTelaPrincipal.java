package modulos;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import main.Main;
import models.Constantes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

/**
 * Classe que implementa a tela de visualiza��o do Dispacher
 * 
 * @author tulio.matias		-	06/06/2018
 */
public class ModuloTelaPrincipal extends JFrame implements ActionListener {
	
	private Main mainListener;

	private static final long serialVersionUID = 1L;
	private DefaultStyledDocument terminalView;
	private JTextPane painelTerminal;
	private JScrollPane scrollTerminal;
	private JScrollBar scrollVertical;
	private JPanel painelBotoes;
	private JFileChooser selecionador;

	private JButton botaoAddProcesso;
	private JButton botaoAddArquivo;
	private JButton botaoIniciarSO;

	private StyleContext contextoDeEstilo;
	private Style estiloTerminal;

	
	public final static Color DARK_GREEN = new Color(0, 153,0);
	public final static Color RED = Color.RED;
	/**
	 * Inicia a tela e recebe o listener da main
	 * @param main 
	 * @throws BadLocationException
	 * @see Jframe
	 */
	public ModuloTelaPrincipal(Main main) throws BadLocationException {
		this.mainListener = main;
		initialize();
	}

	/**
	 * Inicializa o conte�do da tela
	 * 
	 * @throws BadLocationException
	 */
	private void initialize() throws BadLocationException {
		terminalView = new DefaultStyledDocument();
		painelTerminal = new JTextPane(terminalView);
		scrollTerminal = new JScrollPane(painelTerminal);
		scrollVertical = scrollTerminal.getVerticalScrollBar();
		painelBotoes = new JPanel();
		contextoDeEstilo = new StyleContext();
		Dimension btnTamanho = new Dimension(100, 40);
		botaoAddProcesso = new JButton(Constantes.BOTAO_TXT_ADICIONAR_PROCESSO.getTexto());
		botaoAddArquivo = new JButton(Constantes.BOTAO_TXT_ADICIONAR_ARQUIVOS.getTexto());
		botaoIniciarSO = new JButton(Constantes.BOTAO_TXT_INICIAR_SO.getTexto());
		// cria o estilo do terminal
		estiloTerminal = contextoDeEstilo.addStyle("estiloTerminal", null);

		painelTerminal.setEditable(false);

		botaoAddProcesso.addActionListener(this);
		botaoAddProcesso.setActionCommand(Main.ARQ_PROCESSOS);
		botaoAddArquivo.addActionListener(this);
		botaoAddArquivo.setActionCommand(Main.ARQ_ESTRUTURA_ARQUIVOS);
		botaoIniciarSO.addActionListener(this);
		botaoIniciarSO.setActionCommand(Main.INICIAR_SO);
		botaoAddProcesso.setPreferredSize(btnTamanho);
		botaoAddArquivo.setPreferredSize(btnTamanho);
		botaoIniciarSO.setPreferredSize(btnTamanho);
		painelTerminal.setPreferredSize(new Dimension(200, 200));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(Constantes.TELA_PRINCIPAL_TITULO.getTexto());
		getContentPane().add(scrollTerminal, BorderLayout.CENTER);
		getContentPane().add(painelBotoes, BorderLayout.NORTH);
		painelBotoes.add(botaoAddProcesso);
		painelBotoes.add(botaoAddArquivo);
		painelBotoes.add(botaoIniciarSO);
		setMinimumSize(new Dimension(350, 200));
		this.pack();

	}

	
	/**
	 *  M�todo para verificar qual bot�o foi pressionado
	 * @see ActionEvent
	 * */
	@Override
	public void actionPerformed(ActionEvent source) {
		String sourceName = source.getActionCommand();
		if(!sourceName.equals(Main.INICIAR_SO)){
			File resposta = selecionaArquivo((JButton) source.getSource());
			if(resposta != null){	
				mainListener.validaArquivo(resposta,sourceName);
			}else{
				mainListener.invalidaArquivo(sourceName);
				printaNoTerminal(Constantes.FC_SELECIONAR_CANCELADO.getTexto(),RED);
			}		
		}else{
			mainListener.iniciarSO();		
		}
	}


	/**
	 * Escreve no terminal a String recebida com a cor selecionada
	 * 
	 * @param	texto	texto a ser escrito no terminal
	 * @param	cor		cor a ser colocada no terminal
	 * */
	synchronized public void printaNoTerminal(String texto, Color cor){
		EventQueue.invokeLater(new Runnable() {
			
			@Override
				public void run() {
					try {
						StyleConstants.setForeground(estiloTerminal, cor);
						terminalView.insertString(terminalView.getLength(),texto+Constantes.NEWLINE.getTexto(), estiloTerminal);
						revalidate();
						scrollVertical.setValue(scrollVertical.getMaximum()+1);
						revalidate();
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
			});
		

	}
	
	/**
	 * Escreve no terminal a String recebida com a cor padr�o
	 * 
	 * @param	texto	texto a ser escrito no terminal
	 * */
	public void printaNoTerminal(String texto){
		printaNoTerminal(texto,Color.BLACK);
	}
	
	/**
	 * Abre o selecionador de arquivos do Java e mostra apenas os com extens�o txt.
	 * 
	 * @param	botao	botao clicado na tela
	 * @return	Um arquivo se o filepicker voltou com sucesso, null caso contr�rio
	 * */
	public File selecionaArquivo(Component botao){
		selecionador = new JFileChooser();
	    FileNameExtensionFilter filtro = new FileNameExtensionFilter(null,"txt");//TODO: retirar a string daqui.
	    selecionador.setFileFilter(filtro);
		int retorno = selecionador.showDialog(botao, Constantes.FC_SELECIONAR.getTexto());
		
        if (retorno == JFileChooser.APPROVE_OPTION) {
            return selecionador.getSelectedFile();
        } else {
        	return null;
        }
	}
}
