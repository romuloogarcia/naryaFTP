package br.edu.java.naryaftp.util;

import javax.swing.SwingUtilities;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import br.edu.java.naryaftp.ui.AlertUI;
import br.edu.java.naryaftp.ui.ConectandoUI;
import br.edu.java.naryaftp.ui.SystemFTP;

/**
 * Classe ConectaFTP - Fornece a classe para conectar ao servidor FTP.
 * 
 * @author romulogarcia
 * @since 04/04/2013
 */
public class ConectaFTP {

	/**
	 * Classe que faz a conexão com servidor FTP, e, caso a conexão seja
	 * possível, mostra a tela de manipulação do sistema
	 * 
	 * @param ftp
	 *            Recebe o objeto que manipula a conexão com o FTP.
	 * @param host
	 *            Host para conexão.
	 * @param porta
	 *            Porta para conexão.
	 * @param user
	 *            Usuário para conexão.
	 * @param pass
	 *            Senha para conexão.
	 */
	public ConectaFTP(FTPClient ftp, String host, int porta, String user,
			String pass, String tipo) {

		final ConectandoUI con = new ConectandoUI();
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				con.setVisible(true);
			}
		});
		try {
			ftp.connect(host, porta);
			System.out.println(ftp.getReplyString());
			if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				boolean sucesso = ftp.login(user, pass);
				System.out.println(ftp.getReplyString());
				if (sucesso) {
					if (tipo.equals("pass")){
						ftp.enterLocalPassiveMode();
						System.out.println(ftp.getReplyString());
					}else{
						ftp.enterLocalActiveMode();
						System.out.println(ftp.getReplyString());
					}
					new SystemFTP(ftp);
					con.dispose();
				} else {
					con.dispose();
					new AlertUI("Não foi possível se conectar ao servidor!",
							"erro");
					new DesconectaFTP(ftp);
				}
			} else {
				con.dispose();
				new AlertUI(
						"Não foi possível conectar-se ao servidor! Erro na conexão!",
						"erro");
				new DesconectaFTP(ftp);
			}
		} catch (Exception ex) {
			con.dispose();
			new AlertUI(
					"Não foi possível abrir a conexão com o servidor especificado!\nDetalhes técnicos do erro:\n"
							+ ex, "erro");
			ex.printStackTrace();
			new DesconectaFTP(ftp);
		}
	}
}