package br.edu.java.naryaftp.util;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import br.edu.java.naryaftp.model.MutableTreeNodeFTPFile;

/**
 * Classe que manipula um FTP, listando seus diretórios e arquivos
 * 
 * @author romulogarcia
 * @since 10/06/2013
 */
public class ListaFTP {
	private FTPClient ftp;

	/**
	 * Método que faz a listagem de diretórios e arquivos de um FTP e coloca-os
	 * num modelo de DefaultMutableTreeNode.
	 * 
	 * @param ftp
	 *            Recebe o objeto que manipula a conexão com o FTP.
	 * @param arvoreModelo
	 *            Objeto do tipo DefaultMutableTreeNode, que lista os arquivos
	 *            do FTP.
	 * @throws IOException
	 */
	public ListaFTP(FTPClient ftp, DefaultMutableTreeNode arvoreModelo)
			throws IOException {
		this.ftp = ftp;
		ftp.changeToParentDirectory();
		System.out.println(ftp.getReplyString());
		String diretorioRaiz = ftp.printWorkingDirectory();
		System.out.println(ftp.getReplyString());
		listaTudo(diretorioRaiz, arvoreModelo);
	}

	/**
	 * Método que lista os diretórios de um FTP
	 * 
	 * @param dir
	 *            Diretório que será listado.
	 * @return String[] Retorna uma lista de diretórios.
	 */
	public String[] listaDiretorios(String dir) {
		String[] listaDeDiretorios;
		ArrayList<String> listaDeDiretoriosCompleta = new ArrayList<String>();
		try {
			FTPFile[] diretorios = ftp.listDirectories(dir);
			System.out.println(ftp.getReplyString());

			for (FTPFile diretorioIn : diretorios) {
				listaDeDiretoriosCompleta.add(diretorioIn.getName());
			}

			listaDeDiretorios = new String[listaDeDiretoriosCompleta.size()];
			listaDeDiretorios = listaDeDiretoriosCompleta
					.toArray(listaDeDiretorios);
			return listaDeDiretorios;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Método que lista os arquivos de um FTP
	 * 
	 * @param diretorio
	 *            Diretório que será percorrido para ter seus arquivos listados.
	 * @param arvore
	 *            Objeto do tipo DefaultMutableTreeNode que representa a árvore
	 *            do diretório FTP.
	 */
	public void listaArquivos(String diretorio, DefaultMutableTreeNode arvore) {
		try {
			FTPFile[] arquivos = ftp.listFiles(diretorio);
			System.out.println(ftp.getReplyString());
			for (final FTPFile arquivo : arquivos) {
				if (arquivo.isFile()) {
					arvore.setAllowsChildren(true);
					MutableTreeNode arquivoDaArvore = new MutableTreeNodeFTPFile(
							arquivo);
					arquivoDaArvore.setUserObject(arquivo.toString());
					arvore.add(arquivoDaArvore);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que combina a listagem dos arquivos e diretórios de um FTP.
	 * Utiliza recursividade e pode gerar problemas de memória em servidores com
	 * muitos arquivos e diretórios.
	 * 
	 * @param diretorioRaiz
	 *            Diretório inicial (raiz) do FTP.
	 * @param arvoreModelo
	 *            Objeto do tipo DefaultMutableTreeNode que representa a árvore
	 *            do diretório FTP.
	 */
	public void listaTudo(String diretorioRaiz,
			DefaultMutableTreeNode arvoreModelo) {
		String[] diretoriosNaRaiz = listaDiretorios(diretorioRaiz);
		listaArquivos(diretorioRaiz, arvoreModelo);
		for (String diretorio : diretoriosNaRaiz) {
			DefaultMutableTreeNode diretorioAtual = new DefaultMutableTreeNode(
					diretorio);
			listaTudo(diretorioRaiz + "/" + diretorio, diretorioAtual);
			arvoreModelo.add(diretorioAtual);
		}
	}
}