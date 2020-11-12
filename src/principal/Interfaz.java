package principal;

//Importaciones
import javax.swing.JFrame;
import javax.swing.JPanel;

import planificador.Planificador;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;


//
public class Interfaz extends JFrame {
	

	private JPanel mainPanel;
	private final int X=1200,Y=700;
	

	
	
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	//Construir la Interfaz grafica
	public Interfaz() {
		

	mainPanel = new JPanel();
	setContentPane(mainPanel);
	mainPanel.setLayout(null);
	
	setSize(X, Y);
	setResizable(false);
	setUndecorated(true);  
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setLocationRelativeTo(null);
	
	JPanel Barra = new MotionPanel(this);
	Barra.setBackground(new Color(33, 33, 33));
	Barra.setBounds(0, 0, 1200, 80);
	mainPanel.add(Barra);
	Barra.setLayout(null);
	
	JButton btncerrar = new JButton("");
	btncerrar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) { 
			
		
		System.exit(0);
			
		}
	});
	btncerrar.setBackground(new Color(33, 33, 33));
	btncerrar.setForeground(Color.BLACK);
	btncerrar.setIcon(new ImageIcon(Interfaz.class.getResource("/imagenes/cerrar.png")));
	btncerrar.setBounds(1151, 11, 39, 35);
	Barra.add(btncerrar);
	
	JTabbedPane Pestanas = new JTabbedPane(JTabbedPane.TOP);
	Pestanas.setBounds(10, 91, 1180, 598);
	mainPanel.add(Pestanas);
	
	JPanel panel_principal = new JPanel();
	Pestanas.addTab("Principal", null, panel_principal, null);
	panel_principal.setLayout(null);
	
	JPanel panel_recursos = new JPanel();
	Pestanas.addTab("Recursos", null, panel_recursos, null);
	panel_recursos.setLayout(null);
	
	table = new JTable();
	table.setBounds(154, 51, 310, 471);
	panel_recursos.add(table);
	
	
	

	JLabel Impresora = new JLabel();
	Impresora.setIcon(new ImageIcon(Interfaz.class.getResource("/imagenes/impresora (3).png")));
	Impresora.setBounds(700, 51, 76, 88);
	panel_recursos.add(Impresora);
	
	JLabel lblNewLabel = new JLabel("."); 
	lblNewLabel.setIcon(new ImageIcon(Interfaz.class.getResource("/imagenes/reproductor-de-cd.png")));
	lblNewLabel.setBounds(700, 180, 76, 88);
	panel_recursos.add(lblNewLabel);
	
	JLabel lblNewLabel_1 = new JLabel(".");
	lblNewLabel_1.setIcon(new ImageIcon(Interfaz.class.getResource("/imagenes/modem.png")));
	lblNewLabel_1.setBounds(700, 310, 86, 70);
	panel_recursos.add(lblNewLabel_1);
	
	JLabel lblNewLabel_2 = new JLabel(".");
	lblNewLabel_2.setIcon(new ImageIcon(Interfaz.class.getResource("/imagenes/escaner.png")));
	lblNewLabel_2.setBounds(700, 449, 94, 70);
	panel_recursos.add(lblNewLabel_2);
	
	JLabel num_impre = new JLabel("2");
	num_impre.setFont(new Font("Tempus Sans ITC", Font.BOLD, 35));
	num_impre.setBounds(805, 63, 31, 65);
	panel_recursos.add(num_impre);
	
	JLabel num_cd = new JLabel("1");
	num_cd.setFont(new Font("Tempus Sans ITC", Font.BOLD, 35));
	num_cd.setBounds(805, 190, 31, 65);
	panel_recursos.add(num_cd);
	
	JLabel num_modem = new JLabel("1");
	num_modem.setFont(new Font("Tempus Sans ITC", Font.BOLD, 35));
	num_modem.setBounds(805, 310, 31, 65);
	panel_recursos.add(num_modem);
	
	JLabel num_scanner = new JLabel("2");
	num_scanner.setFont(new Font("Tempus Sans ITC", Font.BOLD, 35));
	num_scanner.setBounds(805, 449, 31, 65);
	panel_recursos.add(num_scanner);
	
	
	
	
	setVisible(true);	
	
	
	
	
	} 
	
	
	
	
	
	
	
	
	
	
	
							//METODO MAIN
	
	
	//METODO MAIN
	public static void main(String[] args) {
		
		//Interfaz Gui = new Interfaz ();
		//Pruebas del planficador
		new Planificador().start();  
	}
}
