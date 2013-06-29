package br.edu.java.naryaftp.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;

import br.edu.java.naryaftp.util.DesconectaFTP;
import br.edu.java.naryaftp.util.ListaFTP;
import br.edu.java.naryaftp.util.ListenerSair;

/**
 * Classe que implementa a parte visual do sistema
 * 
 * @author romulogarcia
 * @since 01/06/2013
 */

public class SystemFTP extends JFrame {

	private static final long serialVersionUID = 7002106576497195545L;
	private JPanel pnlPrincipal;
	private JMenuBar mnbMenu;
	private JMenu mnArquivo;
	private JTree jtrServer;
	private FTPClient ftp;
	private DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("/");
	private JScrollPane jspScroll;
	private JPanel pnlBarraInferior;
	private JButton btnEnviarArquivos;
	private JButton btnBaixarSelecionados;
	private JButton btnExcluirSelecionados;
	private JButton btnNovoDiretrio;
	private JPanel pnlBarraSuperior;
	private JButton btnAtualizar;
	private JSeparator separator;
	private JMenuItem mniSobre;
	private long total;
	private JButton btnRenomear;

	public FTPClient getFtp() {
		return ftp;
	}

	public void setFtp(FTPClient ftp) {
		this.ftp = ftp;
	}

	/**
	 * Método que retorna o diretório atual selecionado na JTree.
	 * 
	 * @return String Diretório selecionado na JTree.
	 */
	public String diretorioFTP() {
		String[] adr = new String[jtrServer.getSelectionPath().getPath().length];
		StringBuilder sbEndereco = new StringBuilder();
		for (int i = 0; i < adr.length; i++) {
			sbEndereco.append(jtrServer.getSelectionPath().getPath()[i]
					.toString());
			if ((i != 0) && (i != (adr.length - 1)))
				sbEndereco.append("/");
		}
		return sbEndereco.toString();
	}

	/**
	 * Atualiza o JTree que contém a lista de diretórios/arquivos.
	 * 
	 * @param ftp
	 *            Recebe o objeto que manipula a conexão com o FTP.
	 */
	public void atualizaDiretorios(final FTPClient ftp) {
		try {
			ftp.changeWorkingDirectory("/");
			System.out.println(ftp.getReplyString());
			raiz = new DefaultMutableTreeNode("/");
			new ListaFTP(ftp, raiz);
			jtrServer.setModel(new DefaultTreeModel(raiz, true));
			jtrServer.setSelectionRow(0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Método que envia um arquivo para o servidor FTP e mostra o progresso numa
	 * janela gráfica com uma barra de progresso.
	 * 
	 * @param ftp
	 *            Recebe o objeto que manipula a conexão com o FTP.
	 * @param arquivoLocal
	 *            Arquivo local no sistema que será criado.
	 */
	public void enviaArquivos(final FTPClient ftp, final File arquivoLocal) {
		final InputStream subindo;
		try {
			subindo = new FileInputStream(arquivoLocal);
			ftp.changeWorkingDirectory(diretorioFTP());
			System.out.println(ftp.getReplyString());
			new Thread(new Runnable() {

				@Override
				public void run() {
					final JFrame frmJanela = new JFrame();

					frmJanela.setResizable(false);
					frmJanela
							.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
					frmJanela.setBounds(100, 100, 450, 150);
					JPanel pnlCentral = new JPanel();
					pnlCentral.setBorder(new EmptyBorder(5, 5, 5, 5));
					frmJanela.setContentPane(pnlCentral);
					pnlCentral.setLayout(new GridLayout(2, 1, 1, 1));
					frmJanela.setLocationRelativeTo(null);

					JPanel pnlInfo = new JPanel();
					pnlInfo.setSize((pnlCentral.getWidth() / 2), 15);
					pnlCentral.add(pnlInfo);
					pnlInfo.setLayout(new BorderLayout(0, 0));

					JLabel lblConectandoAoServidor = new JLabel(
							"Enviando arquivo, por favor aguarde...");
					lblConectandoAoServidor
							.setHorizontalAlignment(SwingConstants.CENTER);
					pnlInfo.add(lblConectandoAoServidor, BorderLayout.CENTER);

					JPanel pnlBarra = new JPanel();
					pnlBarra.setSize((pnlCentral.getWidth() / 2), 15);
					pnlCentral.add(pnlBarra);
					pnlBarra.setLayout(new BorderLayout(0, 0));

					final JProgressBar jpbProgresso = new JProgressBar();
					jpbProgresso.setMinimum(0);
					jpbProgresso.setMaximum(100);
					jpbProgresso.setIndeterminate(false);
					pnlBarra.add(jpbProgresso, BorderLayout.CENTER);

					frmJanela.setVisible(true);

					new Thread(new Runnable() {

						@Override
						public void run() {

							ftp.setCopyStreamListener(new CopyStreamListener() {

								@Override
								public void bytesTransferred(
										long totalBytesTransferred,
										int bytesTransferred, long streamSize) {
									total += bytesTransferred;
									long stream = arquivoLocal.length();
									long porcento = ((total * 100) / stream);
									jpbProgresso.setValue((int) porcento);
									jpbProgresso.setStringPainted(true);
									jpbProgresso.repaint();
								}

								@Override
								public void bytesTransferred(
										CopyStreamEvent event) {

								}
							});
							try {
								ftp.appendFile(arquivoLocal.getName(), subindo);
								subindo.close();
								frmJanela.dispose();
								new AlertUI(
										"Arquivo enviado com sucesso!\nLocalização do mesmo no servidor:\n"
												+ diretorioFTP() + "/"
												+ arquivoLocal.getName());
								atualizaDiretorios(ftp);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}).start();
				}
			}).start();
			System.out.println(ftp.getReplyString());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Método que baixa um arquivo do servidor FTP e enquanto isso mostra na
	 * tela uma mensagem para o usuário.
	 * 
	 * @param ftp
	 *            Recebe o objeto que manipula a conexão com o FTP.
	 */
	public void baixarArquivo(final FTPClient ftp) {
		final String arquivoRemoto = diretorioFTP();
		final File arquivoLocal = FileHandler.salvar(jtrServer
				.getSelectionPath().getLastPathComponent().toString());
		try {
			final OutputStream baixando = new BufferedOutputStream(
					new FileOutputStream(arquivoLocal));
			new Thread(new Runnable() {

				@Override
				public void run() {
					boolean baixou = false;

					final JFrame frmJanela = new JFrame();

					frmJanela.setResizable(false);
					frmJanela
							.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
					frmJanela.setBounds(100, 100, 450, 150);
					JPanel pnlCentral = new JPanel();
					pnlCentral.setBorder(new EmptyBorder(5, 5, 5, 5));
					frmJanela.setContentPane(pnlCentral);
					pnlCentral.setLayout(new GridLayout(2, 1, 1, 1));
					frmJanela.setLocationRelativeTo(null);

					JPanel pnlInfo = new JPanel();
					pnlInfo.setSize((pnlCentral.getWidth() / 2), 15);
					pnlCentral.add(pnlInfo);
					pnlInfo.setLayout(new BorderLayout(0, 0));

					JLabel lblConectandoAoServidor = new JLabel(
							"Baixando arquivo, por favor aguarde...");
					lblConectandoAoServidor
							.setHorizontalAlignment(SwingConstants.CENTER);
					pnlInfo.add(lblConectandoAoServidor, BorderLayout.CENTER);

					JPanel pnlBarra = new JPanel();
					pnlBarra.setSize((pnlCentral.getWidth() / 2), 15);
					pnlCentral.add(pnlBarra);
					pnlBarra.setLayout(new BorderLayout(0, 0));

					final JProgressBar jpbProgresso = new JProgressBar();
					jpbProgresso.setIndeterminate(true);
					pnlBarra.add(jpbProgresso, BorderLayout.CENTER);

					frmJanela.setVisible(true);

					try {
						baixou = ftp.retrieveFile(arquivoRemoto, baixando);
						baixando.close();
						frmJanela.dispose();
						if (baixou) {
							new AlertUI(
									"Arquivo salvo com sucesso!\nSalvo em: "
											+ arquivoLocal.getAbsolutePath());
						}
						atualizaDiretorios(ftp);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Método que renomeia um arquivo ou pasta no servidor FTP.
	 * 
	 * @param ftp
	 *            Recebe o objeto que manipula a conexão com o FTP.
	 */
	public void renomeiaFTP(final FTPClient ftp) {
		String caminho = diretorioFTP();
		int pos = caminho.lastIndexOf("/");
		String novo = caminho.substring(0, pos + 1);
		novo += InputUI.text("Digite o novo nome para "
				+ caminho.substring(pos + 1));
		System.out.println(diretorioFTP());
		System.out.println(novo);
		try {
			ftp.rename(diretorioFTP(), novo);
			System.out.println(ftp.getReplyString());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		atualizaDiretorios(ftp);
	}

	/**
	 * Método que cria um diretório no FTP.
	 * 
	 * @param ftp
	 *            Recebe o objeto que manipula a conexão com o FTP.
	 */
	public void criaDiretorio(final FTPClient ftp) {
		try {
			ftp.changeWorkingDirectory(diretorioFTP());
			System.out.println(ftp.getReplyString());
			ftp.makeDirectory(InputUI.text("Digite um nome para o diretório:"));
			System.out.println(ftp.getReplyString());
			atualizaDiretorios(ftp);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Deleta uma pasta ou arquivo do ftp.
	 * 
	 * @param ftp
	 *            Recebe o objeto que manipula a conexão com o FTP.
	 */
	public void deletaFTP(final FTPClient ftp) {
		int res = JOptionPane
				.showConfirmDialog(
						null,
						"Tem certeza que deseja excluir o seguinte diretório ou pasta:\n"
								+ diretorioFTP()
								+ "?\nCertifique-se que a pasta está vazia para apagá-la.\nEsta ação é DEFINITIVA e IRREVERSÍVEL!",
						"Exclusão de Pasta/Arquivo", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);
		if (res == JOptionPane.OK_OPTION) {
			try {
				ftp.deleteFile(diretorioFTP());
				System.out.println(ftp.getReplyString());
				ftp.removeDirectory(diretorioFTP());
				System.out.println(ftp.getReplyString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		atualizaDiretorios(ftp);
	}

	/**
	 * Cria a janela gráfica
	 * 
	 * @param ftp
	 *            Recebe o objeto que manipula a conexão com o FTP.
	 * @throws IOException
	 */
	public SystemFTP(final FTPClient ftp) throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 600));
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setFtp(ftp);
		mnbMenu = new JMenuBar();
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		System.out.println(ftp.getReplyString());

		mnArquivo = new JMenu("Arquivo");

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
					new DesconectaFTP(ftp);
				}
			}
		});

		mniSobre = new JMenuItem("Sobre");
		mniSobre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutNaryaFTP().setVisible(true);
			}
		});
		mnArquivo.add(mniSobre);

		separator = new JSeparator();
		mnArquivo.add(separator);

		mnArquivo.add(mniSair);
		pnlPrincipal = new JPanel();
		pnlPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnlPrincipal);

		new ListaFTP(ftp, raiz);
		pnlPrincipal.setLayout(new BorderLayout(0, 0));

		jspScroll = new JScrollPane();
		pnlPrincipal.add(jspScroll);

		jtrServer = new JTree();
		jtrServer.setShowsRootHandles(true);
		jspScroll.setViewportView(jtrServer);
		jspScroll.setSize(pnlPrincipal.getWidth(), pnlPrincipal.getHeight());
		jtrServer.setModel(new DefaultTreeModel(raiz, true));
		jtrServer.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		jtrServer.setSelectionRow(0);

		pnlBarraInferior = new JPanel();
		pnlPrincipal.add(pnlBarraInferior, BorderLayout.SOUTH);

		btnNovoDiretrio = new JButton("Novo Diretório");
		btnNovoDiretrio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				criaDiretorio(ftp);
			}
		});
		btnNovoDiretrio.setIcon(new ImageIcon(SystemFTP.class
				.getResource("/images/folder.png")));
		pnlBarraInferior.add(btnNovoDiretrio);

		btnEnviarArquivos = new JButton("Enviar Arquivo");
		btnEnviarArquivos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				total = 0;
				final File arquivoLocal = FileHandler.selecionar();
				enviaArquivos(ftp, arquivoLocal);
			}
		});
		btnEnviarArquivos.setIcon(new ImageIcon(SystemFTP.class
				.getResource("/images/upload2.png")));
		pnlBarraInferior.add(btnEnviarArquivos);

		btnBaixarSelecionados = new JButton("Baixar Selecionado");
		btnBaixarSelecionados.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				baixarArquivo(ftp);
			}
		});
		btnBaixarSelecionados.setIcon(new ImageIcon(SystemFTP.class
				.getResource("/images/download-icon.png")));
		pnlBarraInferior.add(btnBaixarSelecionados);

		btnExcluirSelecionados = new JButton("Excluir Selecionado");
		btnExcluirSelecionados.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deletaFTP(ftp);
			}
		});

		btnRenomear = new JButton("Renomear");
		btnRenomear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				renomeiaFTP(ftp);
			}
		});
		btnRenomear.setIcon(new ImageIcon(SystemFTP.class
				.getResource("/images/rename.gif")));
		pnlBarraInferior.add(btnRenomear);
		btnExcluirSelecionados.setIcon(new ImageIcon(SystemFTP.class
				.getResource("/images/delete.gif")));
		pnlBarraInferior.add(btnExcluirSelecionados);

		pnlBarraSuperior = new JPanel();
		pnlPrincipal.add(pnlBarraSuperior, BorderLayout.NORTH);

		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setIcon(new ImageIcon(SystemFTP.class
				.getResource("/images/refresh_icon.png")));
		btnAtualizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atualizaDiretorios(ftp);
			}
		});
		pnlBarraSuperior.add(btnAtualizar);

		mnbMenu.add(mnArquivo);
		setJMenuBar(mnbMenu);

		setVisible(true);

		addWindowListener(new ListenerSair(ftp));
	}
}