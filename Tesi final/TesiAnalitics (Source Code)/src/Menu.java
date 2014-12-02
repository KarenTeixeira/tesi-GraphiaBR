import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;


public class Menu {

	private JFrame frame;
	private String title;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Menu");
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setBounds(100, 100, 1400, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		title = "";
		
		JButton btnRioDeJaneiro = new JButton("Rio de Janeiro");
		btnRioDeJaneiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				title = "rio";
			}
		});
		
		btnRioDeJaneiro.setBounds(609, 252, 150, 40);
		frame.getContentPane().add(btnRioDeJaneiro);
		
		JButton btnRedeGlobo = new JButton("Rede Globo");
		btnRedeGlobo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				title = "globo";
				new AnaliticsWindow(title);
			}
		});
		
		btnRedeGlobo.setBounds(970, 252, 150, 40);
		frame.getContentPane().add(btnRedeGlobo);
		
		JButton btnRobertoMarinho = new JButton("Roberto Marinho");
		btnRobertoMarinho.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				title = "roberto";
				new AnaliticsWindow(title);
			}
		});
		
		btnRobertoMarinho.setBounds(232, 252, 150, 40);
		frame.getContentPane().add(btnRobertoMarinho);;
		
		JButton btnLoad = new JButton("Load Savestate");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = new File("savestate.txt");
				String dados = null;
				try {
					dados = new String(Files.readAllBytes(file.toPath()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	String[] values = dados.split("\n");
				title = values[0].replaceAll("\r", "");
				new AnaliticsWindow(title,true);
			}
		});

		btnLoad.setBounds(609, 406, 150, 40);
		frame.getContentPane().add(btnLoad);
		
	}

}
