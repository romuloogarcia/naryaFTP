package br.edu.java.naryaftp.util;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import org.apache.commons.net.ftp.FTPClient;

/**
 * Classe que controla eventos de janela, em especial os processos de fechamento
 * de telas, finalizando a conexão e o aplicativo.
 * 
 * @author romulogarcia
 * @since 17/06/2013
 */
public class ListenerSair implements WindowListener {

	private FTPClient ftp;

	public FTPClient getFtp() {
		return ftp;
	}

	public void setFtp(FTPClient ftp) {
		this.ftp = ftp;
	}

	public ListenerSair(FTPClient ftp) {
		setFtp(ftp);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		new DesconectaFTP(ftp);

	}

	@Override
	public void windowClosed(WindowEvent e) {
		new DesconectaFTP(ftp);

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}