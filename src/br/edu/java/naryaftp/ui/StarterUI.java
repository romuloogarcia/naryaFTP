package br.edu.java.naryaftp.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import org.apache.commons.net.ftp.FTPClient;

import br.edu.java.naryaftp.util.ConectaFTP;
import br.edu.java.naryaftp.util.ListenerSair;

/**
 * Classe visual para conexão/login ao servidor
 * 
 * @author romulogarcia
 * @since 18/05/2013
 */
public class StarterUI extends JFrame {

	private static final long serialVersionUID = -4487211082408245919L;
	private JTextField txtUsuario;
	private JPasswordField pwfSenha;
	private JTextField txtURL;
	private JSpinner spnPorta;
	private JRadioButton rdbAtv;
	private JRadioButton rdbPass;

	Image i2 = new ImageIcon(this.getClass().getResource("/images/ico.png"))
			.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
	ImageIcon ico = new ImageIcon(i2);
	
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Classe que faz a inicialização e conexão junto ao servidor.
	 * 
	 * @param ftp
	 *            Recebe o objeto que manipula a conexão com o FTP.
	 * @since 18/05/2013
	 */
	public StarterUI(final FTPClient ftp) {
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setTitle("NaryaFTP - Start Screen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 430);
		setMinimumSize(new Dimension(600, 430));
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				StarterUI.class.getResource("/images/ico.png")));

		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(0, 0, this.getWidth(), 100);
		lblLogo.setIcon(new ImageIcon(StarterUI.class
				.getResource("/images/logoNaryaFTP.png")));

		JLabel lblBemVindo = new JLabel("Bem-vindo ao Narya FTP.");
		lblBemVindo.setHorizontalAlignment(SwingConstants.CENTER);
		lblBemVindo.setBounds(0, 111, this.getWidth(), 16);

		JLabel lblInstrucoes = new JLabel(
				"Por favor, digite abaixo os dados para efetuar o acesso ao FTP.");
		lblInstrucoes.setHorizontalAlignment(SwingConstants.CENTER);
		lblInstrucoes.setBounds(0, 139, this.getWidth(), 16);
		getContentPane().setLayout(null);
		getContentPane().add(lblLogo);
		getContentPane().add(lblBemVindo);
		getContentPane().add(lblInstrucoes);

		JPanel pnlDados = new JPanel();
		pnlDados.setBounds(0, 167, 600, 140);
		getContentPane().add(pnlDados);
		GridBagLayout gblDados = new GridBagLayout();
		gblDados.columnWidths = new int[] { 0, 0, 0, 0 };
		gblDados.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gblDados.columnWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gblDados.rowWeights = new double[] { 1.0, 1.0, 1.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		pnlDados.setLayout(gblDados);

		JPanel pnlDiv1 = new JPanel();
		GridBagConstraints gbc_pnlDiv1 = new GridBagConstraints();
		gbc_pnlDiv1.insets = new Insets(0, 0, 5, 0);
		gbc_pnlDiv1.fill = GridBagConstraints.BOTH;
		gbc_pnlDiv1.gridx = 2;
		gbc_pnlDiv1.gridy = 0;
		pnlDados.add(pnlDiv1, gbc_pnlDiv1);

		JLabel lblUrlDoServidor = new JLabel("   URL do Servidor:");
		GridBagConstraints gbc_lblUrlDoServidor = new GridBagConstraints();
		gbc_lblUrlDoServidor.anchor = GridBagConstraints.EAST;
		gbc_lblUrlDoServidor.insets = new Insets(0, 0, 5, 5);
		gbc_lblUrlDoServidor.gridx = 1;
		gbc_lblUrlDoServidor.gridy = 1;
		pnlDados.add(lblUrlDoServidor, gbc_lblUrlDoServidor);

		txtURL = new JTextField();
		GridBagConstraints gbc_txtURL = new GridBagConstraints();
		gbc_txtURL.insets = new Insets(0, 0, 5, 0);
		gbc_txtURL.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtURL.gridx = 2;
		gbc_txtURL.gridy = 1;
		pnlDados.add(txtURL, gbc_txtURL);
		txtURL.setColumns(10);

		JLabel lblPorta = new JLabel("Porta:");
		lblPorta.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblPorta = new GridBagConstraints();
		gbc_lblPorta.anchor = GridBagConstraints.EAST;
		gbc_lblPorta.insets = new Insets(0, 0, 5, 5);
		gbc_lblPorta.gridx = 1;
		gbc_lblPorta.gridy = 2;
		pnlDados.add(lblPorta, gbc_lblPorta);

		MaskFormatter mf = new MaskFormatter();
		try {
			mf.setMask("####");
			mf.setPlaceholder("0021");
		} catch (ParseException e1) {
		}
		mf.setValidCharacters("0123456789");

		JPanel pnlPortaConexao = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 2;
		pnlDados.add(pnlPortaConexao, gbc_panel);
		pnlPortaConexao.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		spnPorta = new JSpinner(new SpinnerNumberModel(21, 0, 65535, 1));
		pnlPortaConexao.add(spnPorta);
		spnPorta.setEditor(new JSpinner.NumberEditor(spnPorta, "######"));

		JLabel lblModoDeConexo = new JLabel("Modo de Conexão:");
		lblModoDeConexo.setHorizontalAlignment(SwingConstants.RIGHT);
		pnlPortaConexao.add(lblModoDeConexo);

		rdbAtv = new JRadioButton("ATV");
		buttonGroup.add(rdbAtv);
		pnlPortaConexao.add(rdbAtv);

		rdbPass = new JRadioButton("PASS");
		rdbPass.setSelected(true);
		buttonGroup.add(rdbPass);
		pnlPortaConexao.add(rdbPass);

		JLabel lblUsurio = new JLabel("Usuário:");
		lblUsurio.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblUsurio = new GridBagConstraints();
		gbc_lblUsurio.anchor = GridBagConstraints.EAST;
		gbc_lblUsurio.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsurio.gridx = 1;
		gbc_lblUsurio.gridy = 3;
		pnlDados.add(lblUsurio, gbc_lblUsurio);

		txtUsuario = new JTextField();
		GridBagConstraints gbc_txtUsuario = new GridBagConstraints();
		gbc_txtUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_txtUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsuario.gridx = 2;
		gbc_txtUsuario.gridy = 3;
		pnlDados.add(txtUsuario, gbc_txtUsuario);
		txtUsuario.setColumns(10);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblSenha = new GridBagConstraints();
		gbc_lblSenha.anchor = GridBagConstraints.EAST;
		gbc_lblSenha.insets = new Insets(0, 0, 5, 5);
		gbc_lblSenha.gridx = 1;
		gbc_lblSenha.gridy = 4;
		pnlDados.add(lblSenha, gbc_lblSenha);

		pwfSenha = new JPasswordField();
		GridBagConstraints gbc_jpfSenha = new GridBagConstraints();
		gbc_jpfSenha.insets = new Insets(0, 0, 5, 0);
		gbc_jpfSenha.fill = GridBagConstraints.HORIZONTAL;
		gbc_jpfSenha.gridx = 2;
		gbc_jpfSenha.gridy = 4;
		pnlDados.add(pwfSenha, gbc_jpfSenha);

		JPanel pnlDiv2 = new JPanel();
		GridBagConstraints gbc_pnlDiv2 = new GridBagConstraints();
		gbc_pnlDiv2.fill = GridBagConstraints.BOTH;
		gbc_pnlDiv2.gridx = 2;
		gbc_pnlDiv2.gridy = 5;
		pnlDados.add(pnlDiv2, gbc_pnlDiv2);

		JPanel pnlBotoes = new JPanel();
		pnlBotoes.setBounds(0, 326, 600, 32);
		getContentPane().add(pnlBotoes);
		pnlBotoes.setLayout(new GridLayout(1, 0, 0, 0));

		JButton btnAcessar = new JButton("Acessar");
		btnAcessar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final int porta = Integer.parseInt(spnPorta.getValue()
						.toString());
				final String host = txtURL.getText();
				final String user = txtUsuario.getText();
				final String pass = String.copyValueOf(pwfSenha.getPassword());
				final String tipo;
				if (rdbPass.isSelected())
					tipo = "pass";
				else
					tipo = "atv";
				setVisible(false);
				new Thread(new Runnable() {

					@Override
					public void run() {
						new ConectaFTP(ftp, host, porta, user, pass, tipo);

					}
				}).start();
			}
		});
		pnlBotoes.add(btnAcessar);

		JButton btnLimparCampos = new JButton("Limpar Campos");
		btnLimparCampos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtURL.setText("");
				spnPorta.setValue(21);
				txtUsuario.setText("");
				pwfSenha.setText("");
				rdbAtv.setSelected(false);
				rdbPass.setSelected(true);
			}
		});

		JButton btnSalvarConexao = new JButton("Salvar Conexão");
		btnSalvarConexao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tipoLocal;
				if (rdbPass.isSelected())
					tipoLocal = "pass";
				else
					tipoLocal = "atv";
				FileHandler.salvar(spnPorta, txtURL, txtUsuario, pwfSenha,
						tipoLocal);
			}
		});
		pnlBotoes.add(btnSalvarConexao);
		pnlBotoes.add(btnLimparCampos);

		JButton btnCancelarESair = new JButton("Cancelar e Sair");
		btnCancelarESair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int res = JOptionPane
						.showConfirmDialog(null,
								"Tem certeza que deseja sair?", "Sair?",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (res == JOptionPane.OK_OPTION) {
					new AlertUI("Obrigado por usar o NaryaFTP!", "informacao");
					StarterUI.this.dispose();
					System.exit(0);
				}
			}
		});
		pnlBotoes.add(btnCancelarESair);

		JMenuBar mnbMenu = new JMenuBar();
		setJMenuBar(mnbMenu);

		JMenu mnArquivo = new JMenu("Arquivo");
		mnbMenu.add(mnArquivo);

		JMenuItem mniAbrirConexo = new JMenuItem("Abrir conexão...");
		mniAbrirConexo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tipoLocal = FileHandler.abrir(spnPorta, txtURL,
						txtUsuario, pwfSenha);
				if (tipoLocal.equals("pass")) {
					rdbPass.setSelected(true);
					rdbAtv.setSelected(false);
				} else {
					rdbPass.setSelected(false);
					rdbAtv.setSelected(true);
				}
			}
		});
		mnArquivo.add(mniAbrirConexo);

		JMenuItem mniSalvarConexo = new JMenuItem("Salvar conexão...");
		mniSalvarConexo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tipoLocal;
				if (rdbPass.isSelected())
					tipoLocal = "pass";
				else
					tipoLocal = "atv";
				FileHandler.salvar(spnPorta, txtURL, txtUsuario, pwfSenha,
						tipoLocal);
			}
		});
		mnArquivo.add(mniSalvarConexo);

		JSeparator separator = new JSeparator();
		mnArquivo.add(separator);

		JMenuItem mniSair = new JMenuItem("Sair");
		mniSair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int res = JOptionPane
						.showConfirmDialog(null,
								"Tem certeza que deseja sair?", "Sair?",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (res == JOptionPane.OK_OPTION) {
					new AlertUI("Obrigado por usar o NaryaFTP!", "informacao");
					StarterUI.this.dispose();
					System.exit(0);
				}
			}
		});
		mnArquivo.add(mniSair);

		addWindowListener(new ListenerSair(ftp));
	}
}