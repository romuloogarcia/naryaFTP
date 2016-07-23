package br.edu.java.naryaftp.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * Classe que mostra uma janela de sobre.
 * 
 * @author romulogarcia
 * @since 22/06/2013
 */
public class AboutNaryaFTP extends JFrame {

	private static final long serialVersionUID = 3958768650745549772L;
	private JPanel pnlPrincipal;

	/**
	 * Método que mostra uma janela de sobre.
	 */
	public AboutNaryaFTP() {
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 560, 325);
		setLocationRelativeTo(null);
		pnlPrincipal = new JPanel();
		pnlPrincipal.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(pnlPrincipal);
		pnlPrincipal.setLayout(new BorderLayout(0, 0));

		JPanel pnlConteudos = new JPanel();
		pnlConteudos.setBackground(Color.white);
		pnlPrincipal.add(pnlConteudos, BorderLayout.CENTER);
		pnlConteudos.setLayout(new BorderLayout(0, 0));

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(AboutNaryaFTP.class
				.getResource("/images/SobreTela.png")));
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		pnlConteudos.add(lblLogo, BorderLayout.CENTER);

		JPanel pnlInferior = new JPanel();
		pnlConteudos.add(pnlInferior, BorderLayout.SOUTH);

		JButton btnVerLicenca = new JButton("Ver Licença");
		btnVerLicenca.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop()
							.browse(new URL(
									"http://creativecommons.org/licenses/by-sa/3.0/")
									.toURI());
				} catch (IOException | URISyntaxException e1) {
					new AlertUI(
							"Acesse http://creativecommons.org/licenses/by-sa/3.0/ a partir do seu browser",
							"informacao");
					e1.printStackTrace();
				}
			}
		});
		pnlInferior.add(btnVerLicenca);

		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		pnlInferior.add(btnOK);
	}

}
