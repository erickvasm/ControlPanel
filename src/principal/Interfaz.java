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
import javax.swing.JScrollPane;


//
public class Interfaz extends JFrame {
	

	private JPanel mainPanel;
	private final int X=1500,Y=700;
	
	
	private static final long serialVersionUID = 1L;
	private JTable table_1;
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
	Barra.setBounds(0, 0, 1500, 80);
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
	btncerrar.setBounds(1451, 10, 39, 35);
	Barra.add(btncerrar);
	
	JLabel lblNewLabel_3 = new JLabel("Simulador del Sistema Operativo de Pruebas");
	lblNewLabel_3.setForeground(Color.WHITE);
	lblNewLabel_3.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 18));
	lblNewLabel_3.setBounds(10, 21, 425, 35);
	Barra.add(lblNewLabel_3);
	
	JTabbedPane Pestanas = new JTabbedPane(JTabbedPane.TOP);
	Pestanas.setBounds(10, 91, 1480, 598);
	mainPanel.add(Pestanas);
	
	JPanel panel_principal = new JPanel();
	Pestanas.addTab("Principal", null, panel_principal, null);
	panel_principal.setLayout(null);
	
	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setBounds(10, 38, 1455, 460);
	panel_principal.add(scrollPane);
	
	table_1 = new Tabla();
	scrollPane.setViewportView(table_1);
	
	JButton btnNewButton = new JButton("Iniciar");
	btnNewButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			
			new Planificador().start(); 
			btnNewButton.setEnabled(false);
			
		}
	});
	btnNewButton.setForeground(new Color(255, 255, 255));
	btnNewButton.setBackground(new Color(0, 128, 0));
	btnNewButton.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 15));
	btnNewButton.setBounds(1305, 529, 85, 21);
	panel_principal.add(btnNewButton);
	
	JPanel panel_recursos = new JPanel();
	Pestanas.addTab("Recursos", null, panel_recursos, null);
	panel_recursos.setLayout(null);
	
	
	

	JLabel Impresora = new JLabel();
	Impresora.setIcon(new ImageIcon(Interfaz.class.getResource("/imagenes/impresora (3).png")));
	Impresora.setBounds(737, 48, 76, 88);
	panel_recursos.add(Impresora);
	
	JLabel lblNewLabel = new JLabel("."); 
	lblNewLabel.setIcon(new ImageIcon(Interfaz.class.getResource("/imagenes/reproductor-de-cd.png")));
	lblNewLabel.setBounds(737, 185, 76, 88);
	panel_recursos.add(lblNewLabel);
	
	JLabel lblNewLabel_1 = new JLabel(".");
	lblNewLabel_1.setIcon(new ImageIcon(Interfaz.class.getResource("/imagenes/modem.png")));
	lblNewLabel_1.setBounds(737, 325, 86, 70);
	panel_recursos.add(lblNewLabel_1);
	
	JLabel lblNewLabel_2 = new JLabel(".");
	lblNewLabel_2.setIcon(new ImageIcon(Interfaz.class.getResource("/imagenes/escaner.png")));
	lblNewLabel_2.setBounds(737, 449, 94, 70);
	panel_recursos.add(lblNewLabel_2);
	
	JLabel num_impre = new JLabel("2");
	num_impre.setFont(new Font("Tempus Sans ITC", Font.BOLD, 35));
	num_impre.setBounds(870, 63, 31, 65);
	panel_recursos.add(num_impre);
	
	JLabel num_cd = new JLabel("1");
	num_cd.setFont(new Font("Tempus Sans ITC", Font.BOLD, 35));
	num_cd.setBounds(870, 445, 31, 65);
	panel_recursos.add(num_cd);
	
	JLabel num_modem = new JLabel("1");
	num_modem.setFont(new Font("Tempus Sans ITC", Font.BOLD, 35));
	num_modem.setBounds(870, 310, 31, 65);
	panel_recursos.add(num_modem);
	
	JLabel num_scanner = new JLabel("2");
	num_scanner.setFont(new Font("Tempus Sans ITC", Font.BOLD, 35));
	num_scanner.setBounds(870, 185, 31, 65);
	panel_recursos.add(num_scanner);
	
	JLabel memoriaReal = new JLabel("Memoria de tiempo real");
	memoriaReal.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 15));
	memoriaReal.setBounds(1108, 89, 209, 25);
	panel_recursos.add(memoriaReal);
	
	JLabel memoriaUsur = new JLabel("Memoria de tiempo usuario");
	memoriaUsur.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 15));
	memoriaUsur.setBounds(1108, 243, 225, 25);
	panel_recursos.add(memoriaUsur);
	
	JScrollPane scrollPane_1 = new JScrollPane();
	scrollPane_1.setBounds(27, 10, 345, 551);
	panel_recursos.add(scrollPane_1);
	
	table = new TablaMemoria();
	scrollPane_1.setViewportView(table);
	
	
	
	
	setVisible(true);	
	
	
	
	
	} 
	
	
	
	
	
	
	
	
	
	
	
							//METODO MAIN
	
	
	//METODO MAIN
	public static void main(String[] args) {
		
		Interfaz Gui = new Interfaz ();
		 
	}
}
