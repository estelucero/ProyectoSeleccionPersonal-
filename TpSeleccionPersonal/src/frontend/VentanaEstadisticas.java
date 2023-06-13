package frontend;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import backend.Empleado;
import backend.Sistema;
import configuracion.Configuracion;
import frontend.ControladorVentanas.VENTANA;

@SuppressWarnings("serial")
public class VentanaEstadisticas extends JFrame{

	JPanel contentPane;
	
	
	Sistema backtracking;
	Sistema goloso;
	JPanel galeria;
	
	JButton btnInicio;
	JButton btnSolucionBacktracking;
	JButton btnSolucionGoloso;
	JPanel envoltorioGaleria;
	public VentanaEstadisticas(){
		// Setup
		setup();
	}
	
	
	private void setup() {
		contentPane = new JPanel();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setSize(Configuracion.ANCHOVENTANA, Configuracion.ALTOVENTANA);
		contentPane.setBackground(Configuracion.COLORFONDO);
		setContentPane(contentPane);
		
		envoltorioGaleria = new JPanel();
		envoltorioGaleria.setOpaque(false);
		envoltorioGaleria.setBounds(21, 36, 858, 533);
		
		JButton btnSolucionBacktracking = new JButton("Backtracking");
		btnSolucionBacktracking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearGaleria(scrollGaleria,galeria, backtracking);
				setearLabelsBacktracking();
			}
		});
		btnSolucionBacktracking.setBounds(90, 605, 137, 23);
		btnSolucionBacktracking.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 11));
		btnSolucionBacktracking.setBorderPainted(false);
		btnSolucionBacktracking.setBackground(Color.WHITE);
		contentPane.setLayout(null);
		contentPane.add(envoltorioGaleria);
		envoltorioGaleria.setLayout(null);
		
		scrollGaleria = new JScrollPane((Component) null);
		scrollGaleria.setBounds(0, 0, 479, 533);
		envoltorioGaleria.add(scrollGaleria);
		
		lblTiempoTotal = new JLabel("Tiempo Total");
		lblTiempoTotal.setHorizontalAlignment(SwingConstants.LEFT);
		lblTiempoTotal.setForeground(Color.WHITE);
		lblTiempoTotal.setFont(new Font("Franklin Gothic Medium Cond", Font.PLAIN, 15));
		lblTiempoTotal.setBounds(512, 151, 189, 21);
		envoltorioGaleria.add(lblTiempoTotal);
		
		lblRatingDeLaSolucion = new JLabel("Rating de la Solucion");
		lblRatingDeLaSolucion.setHorizontalAlignment(SwingConstants.LEFT);
		lblRatingDeLaSolucion.setForeground(Color.WHITE);
		lblRatingDeLaSolucion.setFont(new Font("Franklin Gothic Medium Cond", Font.PLAIN, 15));
		lblRatingDeLaSolucion.setBounds(511, 249, 190, 21);
		envoltorioGaleria.add(lblRatingDeLaSolucion);
		
		lblIteracionesTotales_2 = new JLabel("Iteraciones Totales");
		lblIteracionesTotales_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblIteracionesTotales_2.setForeground(Color.WHITE);
		lblIteracionesTotales_2.setFont(new Font("Franklin Gothic Medium Cond", Font.PLAIN, 15));
		lblIteracionesTotales_2.setBounds(512, 71, 144, 21);
		envoltorioGaleria.add(lblIteracionesTotales_2);
		
		lblTitulo = new JLabel("BACKTRACKING");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Franklin Gothic Medium Cond", Font.PLAIN, 18));
		lblTitulo.setBounds(488, 24, 370, 21);
		envoltorioGaleria.add(lblTitulo);
		
		lblIteraciones = new JLabel("");
		lblIteraciones.setHorizontalAlignment(SwingConstants.LEFT);
		lblIteraciones.setForeground(Color.WHITE);
		lblIteraciones.setFont(new Font("Franklin Gothic Medium Cond", Font.PLAIN, 15));
		lblIteraciones.setBounds(512, 113, 132, 21);
		envoltorioGaleria.add(lblIteraciones);
		
		lblTiempo = new JLabel("");
		lblTiempo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTiempo.setForeground(Color.WHITE);
		lblTiempo.setFont(new Font("Franklin Gothic Medium Cond", Font.PLAIN, 15));
		lblTiempo.setBounds(512, 195, 132, 21);
		envoltorioGaleria.add(lblTiempo);
		
		lblRatingSolucion = new JLabel("");
		lblRatingSolucion.setHorizontalAlignment(SwingConstants.LEFT);
		lblRatingSolucion.setForeground(Color.WHITE);
		lblRatingSolucion.setFont(new Font("Franklin Gothic Medium Cond", Font.PLAIN, 15));
		lblRatingSolucion.setBounds(512, 302, 132, 21);
		envoltorioGaleria.add(lblRatingSolucion);
		
		lblIncompatibilidades = new JLabel("");
		lblIncompatibilidades.setHorizontalAlignment(SwingConstants.LEFT);
		lblIncompatibilidades.setForeground(Color.WHITE);
		lblIncompatibilidades.setFont(new Font("Franklin Gothic Medium Cond", Font.PLAIN, 15));
		lblIncompatibilidades.setBounds(512, 406, 132, 21);
		envoltorioGaleria.add(lblIncompatibilidades);
		
		lblCantidadIncompatibilidades = new JLabel("Cantidad de Incompatibilidades");
		lblCantidadIncompatibilidades.setHorizontalAlignment(SwingConstants.LEFT);
		lblCantidadIncompatibilidades.setForeground(Color.WHITE);
		lblCantidadIncompatibilidades.setFont(new Font("Franklin Gothic Medium Cond", Font.PLAIN, 15));
		lblCantidadIncompatibilidades.setBounds(512, 350, 190, 21);
		envoltorioGaleria.add(lblCantidadIncompatibilidades);
		contentPane.add(btnSolucionBacktracking);
		
		btnSolucionGoloso = new JButton("Goloso");
		btnSolucionGoloso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearGaleria(scrollGaleria,galeria, goloso);
				setearLabelsGoloso();
			}
		});
		btnSolucionGoloso.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 11));
		btnSolucionGoloso.setBorderPainted(false);
		btnSolucionGoloso.setBackground(Color.WHITE);
		btnSolucionGoloso.setBounds(299, 605, 137, 23);
		contentPane.add(btnSolucionGoloso);
		
		btnInicio = new JButton("Inicio");
		btnInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ControladorVentanas.abrirVentana(VENTANA.MAIN, VENTANA.ESTADISTICAS);
			}
		});
		btnInicio.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 11));
		btnInicio.setBorderPainted(false);
		btnInicio.setBackground(Color.WHITE);
		btnInicio.setBounds(726, 605, 153, 23);
		contentPane.add(btnInicio);
		
		salir = new JLabel("X");
		salir.setOpaque(true);
		salir.setHorizontalAlignment(SwingConstants.CENTER);
		salir.setForeground(Color.WHITE);
		salir.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 20));
		salir.setBackground(new Color(0, 0, 51));
		salir.setBounds(857, 0, 43, 41);
		salir.addMouseListener( new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				salir.setBackground(Configuracion.COLORFONDO);
				salir.setForeground(Color.white);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				salir.setBackground(Color.white);
				salir.setForeground(Configuracion.COLORFONDO);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
			
		});
		contentPane.add(salir);
		setLocationRelativeTo(null);
	}

	/** Seteo de labels **/
	private JLabel salir;
	private JScrollPane scrollGaleria;
	private JLabel lblTiempoTotal;
	private JLabel lblRatingDeLaSolucion;
	private JLabel lblIteracionesTotales_2;
	private JLabel lblTitulo;
	private JLabel lblIteraciones;
	private JLabel lblTiempo;
	private JLabel lblRatingSolucion;
	private JLabel lblCantidadIncompatibilidades;
	private JLabel lblIncompatibilidades;
	private void setearLabelsGoloso() {
		lblTitulo.setText("GOLOSO");
		lblIteraciones.setText(goloso.getIteraciones().toString());
		lblTiempo.setText(goloso.getTiempoMills().toString() + " milisegundos.");
		Integer desempenio = goloso.getDesempenio();
		lblRatingSolucion.setText(desempenio.toString());
		Integer cantidadIncompatibilidadesEnLaSolucion = goloso.cantIncompatibilidadesEquipoIdeal();
		lblIncompatibilidades.setText(cantidadIncompatibilidadesEnLaSolucion.toString());
	}
	
	private void setearLabelsBacktracking() {
		lblTitulo.setText("BACKTRACKING");
		lblIteraciones.setText(backtracking.getIteraciones().toString());
		lblTiempo.setText(backtracking.getTiempoTardado().toString() + " segundos.");
		Integer desempenio = backtracking.getDesempenio(); 
		lblRatingSolucion.setText(desempenio.toString());
		Integer cantidadIncompatibilidadesEnLaSolucion = backtracking.cantIncompatibilidadesEquipoIdeal();
		lblIncompatibilidades.setText(cantidadIncompatibilidadesEnLaSolucion.toString());
	}
	
	
	/** Seteo de sistemas **/
	public void setearGoloso(Sistema goloso) {
		this.goloso = goloso;
	}
	
	public void setearBacktracking(Sistema backtracking) {
		this.backtracking = backtracking;
		crearGaleria(scrollGaleria,galeria, backtracking);
		setearLabelsBacktracking();
	}
	
	public void mostrar(Sistema tipo) {
		crearGaleria(scrollGaleria, galeria, tipo);
	}

	/** Creacion de la galeria **/
	private Integer gap = 5;
	private void crearGaleria(JScrollPane scroll, JPanel galeriaPanel, Sistema sistema) {
		List<Empleado> empleados = sistema.getEquipoIdeal();
		galeria = new JPanel();
		galeria.setMaximumSize(new Dimension(430, 549));
		galeria.setLayout(new FlowLayout(FlowLayout.CENTER, 5 , 5));
		galeria.setPreferredSize(new Dimension(430, 549));
		
		scrollGaleria.setViewportView(galeria);
		scrollGaleria.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		for(Empleado empleado: empleados) {
			Carnet carnet = new Carnet(empleado, 100, 100);
			galeria.add(carnet);
		}
		
		galeria.setPreferredSize(calcular(galeria));
	}
	
	private Dimension calcular(JPanel jpGaleria) {
		if(jpGaleria.getComponents().length == 0) {
			return new Dimension(430, 549);
		}
		
		Dimension dim = null;
		
		Integer ancho = jpGaleria.getPreferredSize().width;
	
		int filas = 0;
		int columnas = 0;
		int anchoAux = 0;
		
		
		for(Component comp: jpGaleria.getComponents()) {
			
			anchoAux += gap;
			
			if(anchoAux + comp.getPreferredSize().getWidth() >= ancho ) {
				filas = 0;
				break;
			}
			
			anchoAux += comp.getPreferredSize().getWidth();
			columnas++;
		}
		
		if(jpGaleria.getComponents().length % columnas != 0) {
			filas++;
		}
		
		
		int largoAux = 0;
		largoAux += gap;
		int columAux = 0;
		for(int col = 0; col < jpGaleria.getComponents().length; col++) {
				
			if(columAux + 1 < columnas) {
				columAux++;
				
			}else {
				largoAux += jpGaleria.getComponents()[col].getPreferredSize().getHeight();
				largoAux += gap;
				columAux = 0;
			}			
		}
		
		if(filas > 0) {
			largoAux += jpGaleria.getComponents()[0].getPreferredSize().getHeight();
			largoAux += gap;
		}
		
		dim = new Dimension(ancho, largoAux);
		return dim;
	}
}
