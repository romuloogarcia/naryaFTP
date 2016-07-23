package br.edu.java.naryaftp.ui;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 * Classe para controlar os campos onde o usuário insere informações no sistema.
 * 
 * @author romulogarcia
 * @since 05/04/2013
 */
public class InputUI {
	/**
	 * Classe que mostra uma mensagem para o usuário e retorna uma string
	 * 
	 * @param msg
	 *            Mensagem que será exibida para o usuário.
	 * @return String Retorna o que o usuário proveu como resposta.
	 */
	public static String text(String msg) {
		String input = JOptionPane.showInputDialog(msg);
		return input;
	}

	/**
	 * Classe que mostra uma mensagem para o usuário e retorna um string em
	 * formato de senha
	 * 
	 * @param msg
	 *            Mensagem que será exibida para o usuário.
	 * @return String Retorna o que o usuário proveu como resposta.
	 */
	public static String pass(String msg) {
		JPasswordField pwfSenha = new JPasswordField();
		JOptionPane.showConfirmDialog(null, pwfSenha, msg,
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		String senha = String.copyValueOf(pwfSenha.getPassword());
		return senha;
	}

}
