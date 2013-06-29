package br.edu.java.naryaftp.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;

import br.edu.java.naryaftp.util.PwdSec;

/**
 * Classe responsável por manipular visualmente arquivos.
 * 
 * @author romulogarcia
 * @since 14/06/2013
 */
public class FileHandler {

	/**
	 * Método que mostra uma tela de salvamento para o usuário, utilizando o
	 * JFileChooser.
	 * 
	 * @param port
	 *            JSpinner contendo um valor de porta.
	 * @param server
	 *            JTextField contendo uma string de URL.
	 * @param usuario
	 *            JTextField contendo uma string de nome de usuário.
	 * @param senha
	 *            JPasswordField contendo uma senha.
	 * @param tipoLocal
	 *            String que indica se a conexão será ativa ou passiva.
	 * 
	 * @since 14/06/2013
	 */
	public static void salvar(JSpinner port, JTextField server,
			JTextField usuario, JPasswordField senha, String tipoLocal) {
		JFileChooser jfcSelecionar = new JFileChooser();
		FileNameExtensionFilter fnefExtensoes = new FileNameExtensionFilter(
				"Arquivos de configuração (*.conf)", "conf");
		jfcSelecionar.setFileFilter(fnefExtensoes);
		jfcSelecionar.setMultiSelectionEnabled(false);
		jfcSelecionar.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileChooserUI fcuiInterface = jfcSelecionar.getUI();
		Class<? extends FileChooserUI> fcClass = fcuiInterface.getClass();
		Method setFileName = null;
		try {
			setFileName = fcClass.getMethod("setFileName", String.class);
		} catch (NoSuchMethodException e2) {
			e2.printStackTrace();
		} catch (SecurityException e2) {
			e2.printStackTrace();
		}
		try {
			setFileName.invoke(fcuiInterface, "conexao.conf");
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e2) {
			e2.printStackTrace();
		}
		int res = jfcSelecionar.showSaveDialog(null);
		if (res == JFileChooser.APPROVE_OPTION) {
			String arq = jfcSelecionar.getSelectedFile().toString();
			if (!arq.endsWith(".conf")) {
				arq += ".conf";
			}
			File conf = new File(arq);
			conf.delete();
			try {
				conf.createNewFile();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			try {
				PrintWriter writer = new PrintWriter(conf);
				final int porta = Integer.parseInt(port.getValue().toString());
				final String host = server.getText();
				final String user = usuario.getText();
				final String pass = PwdSec.encripta(String.copyValueOf(senha
						.getPassword()));
				final String tipo = tipoLocal;
				writer.println(porta + "\n" + host + "\n" + user + "\n" + pass
						+ "\n" + tipo);
				writer.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Método que abre uma janela para o usuário informar onde deseja salvar o
	 * arquivo. Utiliza JFileChooser.
	 * 
	 * @param arquivo
	 *            String contendo o nome do arquivo para ser usado como
	 *            placeholder.
	 * @return File Arquivo selecionado pelo usuário.
	 */
	public static File salvar(String arquivo) {
		JFileChooser jfcSelecionar = new JFileChooser();
		String extensao;
		int pos = arquivo.lastIndexOf(".");
		if (pos == -1) {
			extensao = "";
		} else {
			extensao = arquivo.substring(pos + 1);
		}
		FileNameExtensionFilter fnefExtensoes = new FileNameExtensionFilter(
				"Arquivo *." + extensao, extensao);
		jfcSelecionar.setFileFilter(fnefExtensoes);
		jfcSelecionar.setMultiSelectionEnabled(false);
		jfcSelecionar.setFileSelectionMode(JFileChooser.FILES_ONLY);

		FileChooserUI fcuiInterface = jfcSelecionar.getUI();
		Class<? extends FileChooserUI> fcClass = fcuiInterface.getClass();
		Method setFileName = null;
		try {
			setFileName = fcClass.getMethod("setFileName", String.class);
		} catch (NoSuchMethodException e2) {
			e2.printStackTrace();
		} catch (SecurityException e2) {
			e2.printStackTrace();
		}
		try {
			setFileName.invoke(fcuiInterface, arquivo);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e2) {
			e2.printStackTrace();
		}
		int res = jfcSelecionar.showSaveDialog(null);
		if (res == JFileChooser.APPROVE_OPTION) {
			return jfcSelecionar.getSelectedFile();
		} else {
			return new File(System.getProperty("user.home") + arquivo);
		}
	}

	/**
	 * Método que mostra uma tela de seleção de arquivo para o usuário,
	 * utilizando o JFileChooser e retorna a configuração para o sistema.
	 * 
	 * @param spnPorta
	 *            JSpinner configurado para selecionar a porta do FTP.
	 * @param txtURL
	 *            JTextField configurado para receber a URL do FTP.
	 * @param txtUsuario
	 *            JTextField configurado para receber o usuário do FTP.
	 * @param jpfSenha
	 *            JPasswordField configurado para receber a senha do usuário do
	 *            FTP.
	 * 
	 * @return String Determina o tipo de conexão.
	 * @since 15/06/2013
	 */

	public static String abrir(JSpinner spnPorta, JTextField txtURL,
			JTextField txtUsuario, JPasswordField jpfSenha) {
		String tipo = "";
		JFileChooser jfcSelecionar = new JFileChooser();
		FileNameExtensionFilter fnefExtensoes = new FileNameExtensionFilter(
				"Arquivos de configuração (*.conf)", "conf");
		jfcSelecionar.setFileFilter(fnefExtensoes);
		jfcSelecionar.setMultiSelectionEnabled(false);
		jfcSelecionar.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfcSelecionar.showOpenDialog(null);
		File arq = jfcSelecionar.getSelectedFile();
		if (arq != null) {
			Scanner arquivo = null;
			try {
				arquivo = new Scanner(arq);
				spnPorta.setValue(Integer.parseInt(arquivo.nextLine()));
				txtURL.setText(arquivo.nextLine());
				txtUsuario.setText(arquivo.nextLine());
				jpfSenha.setText(PwdSec.decripta(arquivo.nextLine()));
				if (arquivo.nextLine().equals("pass")) {
					tipo = "pass";
				} else {
					tipo = "atv";
				}
				arquivo.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return tipo;
		} else {
			return "pass";
		}
	}

	/**
	 * Método para selecionar um arquivo, utilizando o JFileChooser.
	 * 
	 * @return File Arquivo selecionado pelo usuário.
	 */
	public static File selecionar() {
		JFileChooser jfcSelecionar = new JFileChooser();
		jfcSelecionar.setMultiSelectionEnabled(false);
		jfcSelecionar.setAcceptAllFileFilterUsed(true);
		jfcSelecionar.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfcSelecionar.showOpenDialog(null);
		File arquivo = jfcSelecionar.getSelectedFile();
		return arquivo;
	}
}