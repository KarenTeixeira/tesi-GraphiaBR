import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jfree.ui.RefineryUtilities;


public class AnaliticsWindow {

	private JFrame frame;
	private static int correto;
	private static List<String> corretoList = new ArrayList<String>();
	private static int incorreto;
	private static List<String> incorretoList = new ArrayList<String>();
	private static int id;
	private int total;
	private Texto texto = new Texto();

	/**
	 * Create the application.
	 */
	public AnaliticsWindow(final String title, final boolean load) {
		File file = new File("savestate.txt");
		String dados = null;
		try {
			dados = new String(Files.readAllBytes(file.toPath()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	String[] values = dados.split("\n");
		id = Integer.parseInt(values[1].replaceAll("\r", "")); 
		correto = Integer.parseInt(values[2].replaceAll("\r", "")); 
		incorreto = Integer.parseInt(values[3].replaceAll("\r", "")); 
		String[] corrects = values[4].split("&");
		if(!corrects[0].contains("vazio")){
			for (String correct : corrects) {
				corretoList.add(correct);
			}
		}
		String[] incorrects = values[5].split("&");
		if(!incorrects[0].contains("vazio")){
			for (String incorrect : incorrects) {
				incorretoList.add(incorrect);
			}
		}

		
		initialize(title);
		frame.setVisible(true);
	}
	
	public AnaliticsWindow(final String title) {
		initialize(title);
		id = 0; 
		correto = 0;
		incorreto = 0;
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(final String title) {
		texto = generetaTexts(id, title);
		frame = new JFrame();
		frame.setTitle("Análise dar árvores");
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.getContentPane().setFont(new Font("Arial", Font.PLAIN, 14));
		frame.setBounds(100, 100, 1400, 700);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JScrollPane normalTextPane = new JScrollPane();
		normalTextPane.setBounds(10, 11, 1342, 177);
		frame.getContentPane().add(normalTextPane);

		normalTextPane.getViewport().setViewPosition(new Point(0, 0)); 
		
		final JTextArea normaltextArea = new JTextArea();
		normaltextArea.setEditable(false);
		normalTextPane.setViewportView(normaltextArea);
		normaltextArea.setText(texto.getNormal());
		
		final JScrollPane parsedTextPane = new JScrollPane();
		parsedTextPane.setBounds(10, 198, 1342, 329);
		frame.getContentPane().add(parsedTextPane);
		
		parsedTextPane.getViewport().setViewPosition(new Point(0, 0)); 
		
		final JTextArea parsedTextArea = new JTextArea();
		parsedTextPane.setViewportView(parsedTextArea);
		parsedTextArea.setEditable(true);
		parsedTextArea.setText(texto.getParsed());
		
		final JTextArea correctCountTextArea = new JTextArea();
		correctCountTextArea.setForeground(Color.GREEN);
		correctCountTextArea.setFont(new Font("Arial", Font.BOLD, 14));
		correctCountTextArea.setBounds(1307, 574, 31, 24);
		frame.getContentPane().add(correctCountTextArea);
		correctCountTextArea.setBackground(SystemColor.menu);
		correctCountTextArea.setText(String.valueOf(correto));
		
		final JTextArea incorrectCountTextArea = new JTextArea();
		incorrectCountTextArea.setForeground(Color.RED);
		incorrectCountTextArea.setFont(new Font("Arial", Font.BOLD, 14));
		incorrectCountTextArea.setBounds(862, 575, 31, 24);
		frame.getContentPane().add(incorrectCountTextArea);
		incorrectCountTextArea.setBackground(SystemColor.menu);
		incorrectCountTextArea.setText(String.valueOf(incorreto));
		
		final JButton btnShowChart = new JButton("Show Chart");
		btnShowChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Double correctPercent = 0.0;
				Double incorrectPercent = 0.0;
				try {
					correctPercent = (double) ((double)(correto*100)/(double)(correto+incorreto));

					incorrectPercent = (double) (((double)incorreto*100)/(double)(correto+incorreto));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        final Chart demo = new Chart("Total de corretos e incorretos", correctPercent, incorrectPercent);
		        demo.pack();
		        RefineryUtilities.centerFrameOnScreen(demo);
		        demo.setVisible(true);
			}
		});
		btnShowChart.setBounds(1017, 573, 156, 26);
		btnShowChart.setEnabled(false);
		frame.getContentPane().add(btnShowChart);
		
		final JButton btnCorrect = new JButton("Correto");
		final JButton btnIncorrect = new JButton("Incorreto");
		
		btnCorrect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				correto++;
				corretoList.add(texto.getNormal());
				id++;
				texto = generetaTexts(id, title);
				normaltextArea.setText(texto.getNormal());
				parsedTextArea.setText(texto.getParsed());
				correctCountTextArea.setText(String.valueOf(correto));


				if(correto+incorreto == total-1){
					btnCorrect.setEnabled(false);
					btnIncorrect.setEnabled(false);
					btnShowChart.setEnabled(true);
					
					double correctPercent = (double) ((double)(correto*100)/(double)(correto+incorreto));

					double incorrectPercent = (double) (((double)incorreto*100)/(double)(correto+incorreto));
					
					String name = title+"_relatório_final.txt";
					PrintWriter writer = null;
					try {
						writer = new PrintWriter(name);
						writer.println(title);
				    	writer.println();
				    	writer.println("Número de corretos:");
				    	writer.println(String.valueOf(correto));
				    	writer.println(String.valueOf(correctPercent)+"%");
				    	writer.println();
				    	writer.println("Número de incorretos:");
				    	writer.println(String.valueOf(incorreto));
				    	writer.println(String.valueOf(incorrectPercent)+"%");
				    	writer.println();
				    	writer.println();
				    	writer.println("Senteças corretas:");
				    	for (String corretos : corretoList) {
					    	writer.println(corretos);
						}
				    	writer.println();
				    	writer.println();
				    	writer.println("Senteças incorretas:");
				    	for (String incorretos : incorretoList) {
					    	writer.println(incorretos);
						}
					} catch (FileNotFoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}
		});
		btnCorrect.setBounds(1198, 573, 89, 26);
		frame.getContentPane().add(btnCorrect);
		
		
		btnIncorrect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Correcao(texto, id, title);
				incorreto++;
				incorretoList.add(texto.getNormal());
				id++;
				texto = generetaTexts(id, title);
				normaltextArea.setText(texto.getNormal());
				parsedTextArea.setText(texto.getParsed());
				incorrectCountTextArea.setText(String.valueOf(incorreto));
				if(correto+incorreto == total-1){
					btnCorrect.setEnabled(false);
					btnIncorrect.setEnabled(false);
					btnShowChart.setEnabled(true);
					
					double correctPercent = (double) ((double)(correto*100)/(double)(correto+incorreto));

					double incorrectPercent = (double) (((double)incorreto*100)/(double)(correto+incorreto));
					
					new File("Relatorios").mkdir();
					String name = "Relatorios/"+title+"_relatório_final.txt";
					
					PrintWriter writer = null;
					try {
						writer = new PrintWriter(name);
						writer.println(title);
				    	writer.println();
				    	writer.println("Número de corretos:");
				    	writer.println(String.valueOf(correto));
				    	writer.println(String.valueOf(correctPercent)+"%");
				    	writer.println();
				    	writer.println("Número de incorretos:");
				    	writer.println(String.valueOf(incorreto));
				    	writer.println(String.valueOf(incorrectPercent)+"%");
				    	writer.println();
				    	writer.println();
				    	writer.println("Senteças corretas:");
				    	for (String corretos : corretoList) {
					    	writer.println(corretos);
						}
				    	writer.println();
				    	writer.println();
				    	writer.println("Senteças incorretas:");
				    	
				    	for (String incorretos : incorretoList) {
					    	writer.println(incorretos);
						}
					    
					} catch (FileNotFoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					writer.close();
				}
			}
		});
		btnIncorrect.setBounds(900, 572, 89, 26);
		frame.getContentPane().add(btnIncorrect);
		
		
		
		JTextArea txtrLegenda = new JTextArea();
		txtrLegenda.setBackground(SystemColor.menu);
		txtrLegenda.setText("Legenda:\r\n\"V\": verbo\t\t\"DET\": determinante\t\t\"ADV\": adv\u00E9rbio\r\n\"N\": substantivo\t\t\"PUNC\"/\"PNT: pontua\u00E7\u00E3o\t\t\"CJ\"/\"CONJ\": conjun\u00E7\u00E3o\r\n\"ADP\": adposi\u00E7\u00E3o\t\"PRS\": pronome (n\u00E3o possessivo) \t\"CARD\": cardinal\r\n\"PRON\": pronome\t\"INTJ\": interjei\u00E7\u00E3o\t\t\"ART\": artigo\r\n\"ADJ\"/\"A\": adjetivo\t\"NUM\": n\u00FAmero\t\t\t\"P\": preposi\u00E7\u00E3o\r\n\r\n");
		txtrLegenda.setBounds(10, 538, 816, 112);
		frame.getContentPane().add(txtrLegenda);
		
		JButton btnSave = new JButton("Save State");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = "savestate.txt";
				
				PrintWriter writer = null;
				try {
					writer = new PrintWriter(name);
					writer.println(title); 
					writer.println(String.valueOf(id)); 
					writer.println(String.valueOf(correto)); 
					writer.println(String.valueOf(incorreto)); 
					if(corretoList.isEmpty()){
						writer.print("vazio");
			    	}else{
						int i = 0;
						for (String correto : corretoList) {
							
							writer.print(correto);
							if(i < corretoList.size()-1){
								writer.print("&");
							}
							i++;
						}
			    	}
					writer.println();
					if(incorretoList.isEmpty()){
						writer.print("vazio");
			    	}else{
						int j = 0;
						for (String incorreto : incorretoList) {
							writer.print(incorreto);
							if(j < incorretoList.size()-1){
								writer.print("&");
							}
							j++;
						}
			    	}
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				writer.close();
				frame.dispose();
			}
		});
		btnSave.setBounds(1042, 626, 108, 23);
		frame.getContentPane().add(btnSave);
			
	}
	
	public Texto generetaTexts(final int value, final String title){
		File file = null;
		File file2 = null;
		if(title.equals("roberto")){
			file = new File("parse_roberto_marinho.txt");
			file2 = new File("roberto_marinho.txt");
		}else if(title.equals("globo")){
			file = new File("parse_rede_globo.txt");
			file2 = new File("rede_globo.txt");
		}else if(title.equals("rio")){
			file = new File("parse_rio_de_janeiro.txt");
			file2 = new File("rio_de_janeiro.txt");
		}
		
		try {
			String dados = new String(Files.readAllBytes(file.toPath()));
			dados = dados.replaceAll(" \\(", "  \\(");
	    	String[] sentencasParseadas = dados.split("\n");

			String dados2 = new String(Files.readAllBytes(file2.toPath()));
	    	String[] sentencasTextuais = dados2.split("\n");
	    	total = sentencasParseadas.length;
	    	
	    	String formatacao = sentencasParseadas[value].replace("))", ")¬)");
	    	formatacao = formatacao.replace("))", ")¬)");
	    	String[] sentecaSeparada = formatacao.split("  |¬");

	    	int count = 0;
	    	int index = 0;
	    	String textoFinalParseado = "";
	    	for (String string : sentecaSeparada) {
	    		String indent = "";
	    		char abre = '(';
	    		char fecha = ')';
	    		if(index>0){
			    	if(!(sentecaSeparada[index-1].charAt(sentecaSeparada[index-1].length() - 1) == fecha  && sentecaSeparada[index].charAt(0) == abre)){
			    		if(sentecaSeparada[index-1].charAt(sentecaSeparada[index-1].length() - 1) == sentecaSeparada[index].charAt(0)){
				    		count--;
				    		for(int i=0; i<count; i++){
				    			indent += "      ";
				    		}
				    		indent+= ")";
				    		string = string.replaceAll("\\)", indent);
				    	}else{
				    		count++;
				    		for(int i=0; i<count; i++){
				    			indent += "      ";
				    		}
				    		indent+= "(";
				    		string = string.replaceAll("\\(", indent);
				    	}
				    		
					}else{
						for(int i=0; i<count; i++){
			    			indent += "      ";
			    		}
			    		indent+= "(";
			    		string = string.replaceAll("\\(", indent);
					}
			    	
	    		}
				
				index++;
				textoFinalParseado += string;
				textoFinalParseado += "\n";
				
			}
	    	
	    	byte[] normalBytes = sentencasTextuais[value].getBytes( Charset.forName("UTF-8" ));
	    	byte[] parsedBytes = textoFinalParseado.getBytes( Charset.forName("UTF-8" ));
	    	
	    	String normalString = new String( normalBytes, Charset.forName("UTF-8") );
	    	String parsedString = new String( parsedBytes, Charset.forName("UTF-8") );
	    	
	    	Texto texto = new Texto();
			texto.setNormal(normalString);
			texto.setParsed(parsedString);
			return texto;
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
