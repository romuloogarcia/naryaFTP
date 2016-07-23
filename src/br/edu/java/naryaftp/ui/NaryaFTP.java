package br.edu.java.naryaftp.ui;

import org.apache.commons.net.ftp.FTPClient;

/**
 * Classe principal do aplicativo NaryaFTP.
 * 
 * @author romulogarcia
 * @since 03/04/2013
 * 
 */

/*
 * Project -> Generate Javadoc -> Next -> em Extra Javadoc Exportar JavaDoc com
 * "-encoding UTF-8 -charset UTF-8 -docencoding UTF-8"
 */
public class NaryaFTP {
	/**
	 * Classe principal do aplicativo, inicia o objeto responsável por manter a
	 * conexão e chama a tela de início
	 * 
	 */
	public static void main(String[] args) {
		FTPClient ftp = new FTPClient();
		new StarterUI(ftp).setVisible(true);
	}
}