package br.edu.java.naryaftp.model;

import java.util.Enumeration;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.commons.net.ftp.FTPFile;

/**
 * Classe que define um tipo de árvore para listar arquivos num FTP. Implementa
 * a classe MutableTreeNode.
 * 
 * @author romulogarcia
 * @since 14/06/2013
 */
public class MutableTreeNodeFTPFile implements MutableTreeNode {

	public FTPFile getArquivo() {
		return arquivo;
	}

	public void setArquivo(FTPFile arquivo) {
		this.arquivo = arquivo;
	}

	protected FTPFile arquivo;

	public MutableTreeNodeFTPFile(FTPFile arquivo2) {
		setArquivo(arquivo2);
	}

	/**
	 * Método toString() sofreu sobrecarga para retornar o nome do arquivo e não
	 * o ID do objeto.
	 */
	@Override
	public String toString() {
		return getArquivo().getName();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return null;
	}

	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public TreeNode getParent() {
		return null;
	}

	@Override
	public int getIndex(TreeNode node) {
		return 0;
	}

	@Override
	public boolean getAllowsChildren() {
		return false;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public Enumeration<?> children() {
		return null;
	}

	@Override
	public void insert(MutableTreeNode child, int index) {
	}

	@Override
	public void remove(int index) {
	}

	@Override
	public void remove(MutableTreeNode node) {
	}

	@Override
	public void setUserObject(Object object) {
	}

	@Override
	public void removeFromParent() {
	}

	@Override
	public void setParent(MutableTreeNode newParent) {
	}

}
