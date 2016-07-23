package br.edu.java.naryaftp.ui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Classe responsável por exibir as mensagens de erro, avisos e informações do
 * aplicativo.
 * 
 * @author romulogarcia
 * @since 05/04/2013
 * 
 */
public class AlertUI {

	Image i2 = new ImageIcon(this.getClass().getResource("/images/ico.png"))
			.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	ImageIcon ico = new ImageIcon(i2);

	/**
	 * Classe que exibe mensagens em janela gráfica para o usuário. O parâmetro
	 * msg espera uma string qualquer. Tipos de alertas disponíveis para o
	 * parâmetro erro: erro, aviso e informação.
	 * 
	 * @param msg
	 *            Mensagem que será exibida para o usuário.
	 * @param erro
	 *            Tipo de mensagem: utilize "erro", "aviso" ou "informacao"
	 *            neste campo.
	 */
	public AlertUI(String msg, String erro) {
		switch (erro) {
		case "erro":
			JOptionPane.showMessageDialog(null, msg, "ERRO!",
					JOptionPane.ERROR_MESSAGE);
			break;
		case "aviso":
			JOptionPane.showMessageDialog(null, msg, "Aviso",
					JOptionPane.WARNING_MESSAGE);
			break;
		case "informacao":
			JOptionPane.showMessageDialog(null, msg, "Informação",
					JOptionPane.INFORMATION_MESSAGE);
			break;
		}
	}

	/**
	 * Classe que exibe mensagens em janela gráfica para o usuário. O parâmetro
	 * msg espera uma string qualquer. Assume-se que seja uma janela de
	 * informação.
	 * 
	 * @param msg
	 *            Mensagem que será exibida para o usuário.
	 */
	public AlertUI(String msg) {
		JOptionPane.showMessageDialog(null, msg, "NaryaFTP",
				JOptionPane.INFORMATION_MESSAGE, ico);
	}
}
