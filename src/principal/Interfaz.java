package principal;

//Importaciones
import javax.swing.JFrame;
import javax.swing.JPanel;
import planificador.Planificador;
import planificador.Recursos;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;


//Esta clase permite visualizar la operatividad del Planificador
public class Interfaz extends JFrame {
	

	//Atributos
	private JPanel mainPanel;//Panel Princiopal
	private final int X=1200,Y=700;//Dimensiones de pantalla
	
	//Componentes de GUI
	private JTable table_1;//Tabla de Procesos
	private JTable table;//Tabla de Memoria
	
	//RECURSOS DEL SSOP
	private static JLabel num_impre;
	private static JLabel num_cd;
	private static JLabel num_modem;
	private static JLabel num_scanner;
	private static JLabel memoriaUsur;
	private static JLabel memoriaReal;
	
	
	
	//Construir la Interfaz grafica
	public Interfaz() {
		
								//PANEL PRINCIPAL Y VENTANA
		
		//Construir el panel principal
		mainPanel = new JPanel();
		mainPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		mainPanel.setLayout(null);
		
		//Establecer las caracteristicas de la ventana principal
		setSize(X, Y);
		setResizable(false);
		setUndecorated(true);  
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setContentPane(mainPanel);
		
		//Añadiendo el icono
		//Control de excepciones
		try {
			Image iconoPropio = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/appIcon.png"));
			this.setIconImage(iconoPropio);
		}catch(Exception e) {
			//Exepcion vacia
		}
		
		
		
								//BARRA PERSONALIZADA
		
		
		//Barra Personalizada
		JPanel Barra = new MotionPanel(this);
		Barra.setBackground(new Color(33, 33, 33));
		Barra.setBounds(0, 0, 1200, 80);
		mainPanel.add(Barra);
		Barra.setLayout(null);
		
		
		//Boton de cerrar
		JButton btncerrar = new JButton("");
		btncerrar.setBackground(new Color(33, 33, 33));
		btncerrar.setForeground(Color.BLACK);
		btncerrar.setIcon(new ImageIcon(Interfaz.class.getResource("/imagenes/cerrar.png")));
		btncerrar.setBounds(1141, 10, 39, 35);
		Barra.add(btncerrar);
		
		
		JLabel lblNewLabel_3 = new JLabel("Simulador del Sistema Operativo de Pruebas");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(122, 21, 425, 35);
		Barra.add(lblNewLabel_3);
		
		JLabel iconer = new JLabel("");
		imageResizer(iconer,"/imagenes/appIcon.png",68,63);
		iconer.setBounds(10, 7, 68, 63);
		Barra.add(iconer);
		
		
						//PESTANAS DE RECURSOS Y PRINCIPAL
		
		
		
		
		
		JTabbedPane Pestanas = new JTabbedPane(JTabbedPane.TOP);
		Pestanas.setBounds(10, 91, 1180, 598);
		mainPanel.add(Pestanas);
		
			//Panel principal
		
		JPanel panel_principal = new JPanel();
		Pestanas.addTab("Principal", null, panel_principal, null);
		panel_principal.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 38, 1155, 460);
		panel_principal.add(scrollPane);
		
		table_1 = new Tabla();
		scrollPane.setViewportView(table_1);
		
		JButton btnNewButton = new JButton("Iniciar");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 128, 0));
		btnNewButton.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 15));
		btnNewButton.setBounds(1005, 529, 85, 21);
		panel_principal.add(btnNewButton);
		
		
				//Panel de recursos
		
		JPanel panel_recursos = new JPanel();
		Pestanas.addTab("Recursos", null, panel_recursos, null);
		panel_recursos.setLayout(null);
		
		JLabel Impresora = new JLabel();
		Impresora.setIcon(new ImageIcon(Interfaz.class.getResource("/imagenes/impresora (3).png")));
		Impresora.setBounds(615, 56, 76, 88);
		panel_recursos.add(Impresora);
		
		JLabel lblNewLabel = new JLabel("."); 
		lblNewLabel.setIcon(new ImageIcon(Interfaz.class.getResource("/imagenes/reproductor-de-cd.png")));
		lblNewLabel.setBounds(615, 180, 76, 88);
		panel_recursos.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(".");
		lblNewLabel_1.setIcon(new ImageIcon(Interfaz.class.getResource("/imagenes/modem.png")));
		lblNewLabel_1.setBounds(615, 314, 86, 70);
		panel_recursos.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel(".");
		lblNewLabel_2.setIcon(new ImageIcon(Interfaz.class.getResource("/imagenes/escaner.png")));
		lblNewLabel_2.setBounds(615, 449, 94, 70);
		panel_recursos.add(lblNewLabel_2);
		
		num_impre = new JLabel("2");
		num_impre.setFont(new Font("Tempus Sans ITC", Font.BOLD, 35));
		num_impre.setBounds(718, 63, 31, 65);
		panel_recursos.add(num_impre);
		
		
		num_cd = new JLabel("1");
		num_cd.setFont(new Font("Tempus Sans ITC", Font.BOLD, 35));
		num_cd.setBounds(718, 449, 31, 65);
		panel_recursos.add(num_cd);
		
		num_modem = new JLabel("1");
		num_modem.setFont(new Font("Tempus Sans ITC", Font.BOLD, 35));
		num_modem.setBounds(718, 310, 31, 65);
		panel_recursos.add(num_modem);
		

		num_scanner = new JLabel("2");
		num_scanner.setFont(new Font("Tempus Sans ITC", Font.BOLD, 35));
		num_scanner.setBounds(718, 203, 31, 65);
		panel_recursos.add(num_scanner);
		
		memoriaReal = new JLabel("Memoria de tiempo real:");
		memoriaReal.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 15));
		memoriaReal.setBounds(836, 89, 209, 25);
		panel_recursos.add(memoriaReal);
		
		memoriaUsur = new JLabel("Memoria de tiempo usuario:");
		memoriaUsur.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 15));
		memoriaUsur.setBounds(836, 178, 329, 25);
		panel_recursos.add(memoriaUsur);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(27, 10, 345, 551);
		panel_recursos.add(scrollPane_1);
		
		table = new TablaMemoria();
		scrollPane_1.setViewportView(table);
		
		
		//Eventos
		btncerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { 
				//Cerrar la ventana
				System.exit(0);	
			}
		});
		
		//Iniciar la simulacion
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Iniciar la simulacion creando un nuevo Planificador y ejecutandolo
				new Planificador().start(); 
				btnNewButton.setEnabled(false);
			}
		});
		
		
		//VISIBILIDAD DE LA VENTANA
		setVisible(true);	
	
	} 
	
	
	
	
	/*Este metodo muestra el estado de todos los procesos y recursos mediante los distintos
	componentes de la vetana.*/
	public static void masterGui() {
		
		//Refrescar la informacion de los recursos
		num_impre.setText(Recursos.Impresoras+"");
		num_cd.setText(Recursos.CD+"");
		num_modem.setText(Recursos.Modem+"");
		num_scanner.setText(Recursos.Escaner+"");
	
		//Mostrar el Uso de las Memorias
		memoriaUsur.setText("Memoria de tiempo usuario:"+Recursos.MemoriaUsuario);
		memoriaReal.setText("Memoria de tiempo real:"+Recursos.MemoriaTiemporeal);
		
		TablaMemoria.RellenarMemoria();//Mostrar los procesos relacionados con alguna direccion de Memoria
		Tabla.MostrarProcesos();//Mostrar todas las propiedades de los procesos
		
	}
	
	

	//Cargador de imagenes
	public void imageResizer(JLabel componente,String dir,int x,int y) {
		try {
			
			URL imagenBuffer= Interfaz.class.getResource(dir);
			
			BufferedImage ima=ImageIO.read(imagenBuffer.openStream());
			
			Image imagenRedimensionada = ima.getScaledInstance(x,y, Image.SCALE_SMOOTH);
			ImageIcon icono=new ImageIcon(imagenRedimensionada);
			
			componente.setIcon(icono);
			
		}catch(Exception e) {
			componente.setText("");
		}
	}
	
	
	
	
	
	
							//METODO MAIN
	//METODO MAIN
	public static void main(String[] args) {
		//Crear la Interfaz
		Interfaz Gui = new Interfaz ();
		 
	}
}
