package br.edu.java.naryaftp.util;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

import br.edu.java.naryaftp.ui.AlertUI;

/**
 * Classe responsável por fechar a conexão com o servidor FTP
 * 
 * @author romulogarcia
 * @since 06/04/2013
 */

public class DesconectaFTP {

	/**
	 * Classe que fecha a conexão FTP
	 * 
	 * @param ftp
	 *            Recebe o objeto que manipula a conexão com o FTP.
	 */
	public DesconectaFTP(FTPClient ftp) {
		try {
			ftp.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			new AlertUI(
					"Você foi desconectado do servidor.\nObrigado por utilizar o NaryaFTP!",
					"informacao");
		}
		System.exit(0);
	}
}