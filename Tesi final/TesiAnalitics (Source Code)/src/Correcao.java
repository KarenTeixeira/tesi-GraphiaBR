import java.awt.Font;
import java.awt.Frame;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Correcao {

	private JFrame frame;

	public Correcao(final Texto texto, final int id, final String title) {
		initialize(texto,id, title);
		frame.setVisible(true);
	}

	private void initialize(final Texto texto, final int id, final String title) {
		frame = new JFrame();
		frame.setTitle("Correção de árvore");
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setBounds(100, 100, 1400, 700);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JScrollPane normalTextPane = new JScrollPane();
		normalTextPane.setBounds(10, 11, 1342, 177);
		frame.getContentPane().add(normalTextPane);
		
		final JTextArea normaltextArea = new JTextArea();
		normaltextArea.setEditable(false);
		normalTextPane.setViewportView(normaltextArea);
		normaltextArea.setText(texto.getNormal());
		
		
		final JScrollPane parsedTextPane = new JScrollPane();
		parsedTextPane.setBounds(10, 198, 1342, 329);
		frame.getContentPane().add(parsedTextPane);
		
		final JTextArea parsedTextArea = new JTextArea();
		parsedTextPane.setViewportView(parsedTextArea);
		parsedTextArea.setEditable(true);
		parsedTextArea.setText(texto.getParsed());
		
		JButton btnSalva = new JButton("Salvar");
		btnSalva.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSalva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new File("sentenças_corrigidas_"+ title).mkdir();
	    		String name = "sentenças_corrigidas_"+ title+"/"+title+"_id_"+ id +".txt";
				
				PrintWriter writer = null;
				try {
					writer = new PrintWriter(name);
					writer.println("id = " + id);
			    	writer.println();
			    	writer.println("Senteça Normal:");
			    	writer.println(texto.getNormal());
			    	writer.println();
			    	writer.println("Sentença Parseada Incorreta:");
			    	writer.println(texto.getParsed());
			    	writer.println();
			    	writer.println("Sentença Parseada Corrigida:");
			    	writer.println(parsedTextArea.getText());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
	    		writer.close();
				frame.dispose();
			}
		});
		btnSalva.setBounds(1117, 593, 150, 50);
		
		frame.getContentPane().add(btnSalva);
		JTextArea txtrLegenda = new JTextArea();
		txtrLegenda.setBackground(SystemColor.menu);
		txtrLegenda.setText("Legenda:\r\n\"V\": verbo\t\t\"DET\": determinante\t\t\"ADV\": adv\u00E9rbio\r\n\"N\": substantivo\t\t\"PUNC\"/\"PNT: pontua\u00E7\u00E3o\t\t\"CJ\"/\"CONJ\": conjun\u00E7\u00E3o\r\n\"ADP\": adposi\u00E7\u00E3o\t\"PRS\": pronome (n\u00E3o possessivo) \t\"CARD\": cardinal\r\n\"PRON\": pronome\t\"INTJ\": interjei\u00E7\u00E3o\t\t\"ART\": artigo\r\n\"ADJ\"/\"A\": adjetivo\t\"NUM\": n\u00FAmero\t\t\t\"P\": preposi\u00E7\u00E3o\r\n\r\n");
		txtrLegenda.setBounds(10, 538, 816, 112);
		frame.getContentPane().add(txtrLegenda);
	}

}
