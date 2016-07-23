package br.edu.java.naryaftp.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * Classe para mostrar uma tela de carregamento para o usuário utilizando uma
 * JFrame. Recomenda-se a execução utilizando o SwingUtilities.invokeLater().
 * 
 * @author romulogarcia
 * @since 15/06/2013
 */
public class ConectandoUI extends JFrame {

	private static final long serialVersionUID = 8106421363593592410L;
	private JPanel pnlPrincipal;

	/**
	 * Método que mostra uma tela de carregamento para o usuário.
	 */
	public ConectandoUI() {
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 150);
		pnlPrincipal = new JPanel();
		pnlPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnlPrincipal);
		pnlPrincipal.setLayout(new GridLayout(2, 1, 1, 1));
		setLocationRelativeTo(null);

		JPanel pnlInfo = new JPanel();
		pnlInfo.setSize((pnlPrincipal.getWidth() / 2), 15);
		pnlPrincipal.add(pnlInfo);
		pnlInfo.setLayout(new BorderLayout(0, 0));

		JLabel lblConectandoAoServidor = new JLabel(
				"Comunicando-se com o servidor, por favor aguarde...");
		lblConectandoAoServidor.setHorizontalAlignment(SwingConstants.CENTER);
		pnlInfo.add(lblConectandoAoServidor, BorderLayout.CENTER);

		JPanel pnlBarra = new JPanel();
		pnlBarra.setSize((pnlPrincipal.getWidth() / 2), 15);
		pnlPrincipal.add(pnlBarra);
		pnlBarra.setLayout(new BorderLayout(0, 0));

		JProgressBar jpbProgresso = new JProgressBar();
		jpbProgresso.setIndeterminate(true);
		pnlBarra.add(jpbProgresso, BorderLayout.CENTER);
		jpbProgresso.revalidate();
	}
}