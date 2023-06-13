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

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import backend.Empleado;
import backend.Pair;
import configuracion.Configuracion;
import datos.Datos;
import frontend.ControladorVentanas.VENTANA;

@SuppressWarnings("serial")
public class VentanaMain extends JFrame {

	JPanel contentPane;
	private JTextField cantidadLideres;
	private JTextField cantProgramadores;
	private JTextField cantArquitectos;
	private JTextField cantTesters;

	JPanel marcoInputs;
	JLabel salir;

	// -- Donde se iran cambiando las cosas
	JPanel envoltorioGaleria;

	// -- Galeria --
	JScrollPane scrollGaleria;
	JPanel galeria;

	GroupLayout gl_contentPane;

	JLabel titulo_1;
	JLabel lblLideres;
	JLabel lblProgramador;
	JLabel lblArquitecto;
	JLabel lblTester;
	private JLabel lblRequerimientos;

	JButton iniciarBoton;

	List<Empleado> empleadosDisponibles;

	public VentanaMain() {

		// Setup basico de todas las ventanas
		setup();

		// Inicializa las variables del formulario
		inicializarFormulario();

		// Disenio Main
		salirBoton();

		// Galeria de empleados
		gl_contentPane = galeriaDisplay();

		// Formulario posicionamiento
		formularioDisplay(gl_contentPane);

		// Mostrar imagenes
		mostrarImagenes();

	}

	private Integer gap = 5;
	private JButton btnMostrarIncompatibilidades;
	private JButton iniciarBoton_2;
	private JButton btnMostrarPersonal;

	private void mostrarImagenes() {
		try {
			empleadosDisponibles = Datos.listaDeEmpleados();
			galeria = new JPanel();
			galeria.setMaximumSize(new Dimension(584, 518));
			galeria.setLayout(new FlowLayout(FlowLayout.CENTER, gap, gap));
			galeria.setPreferredSize(new Dimension(584, 518));

			scrollGaleria.setViewportView(galeria);
			scrollGaleria.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

			crearGaleria();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, "No se pudieron cargar las imagenes");
		}

	}

	private void crearGaleria() {
		for (Empleado empleado : empleadosDisponibles) {

			Carnet carnet = new Carnet(empleado, 140, 140);
			galeria.add(carnet);
		}

		galeria.setPreferredSize(calcular(galeria));
	}

	private Dimension calcular(JPanel jpGaleria) {
		Dimension dim = null;

		Integer ancho = jpGaleria.getPreferredSize().width;

		int filas = 0;
		int columnas = 0;
		int anchoAux = 0;

		for (Component comp : jpGaleria.getComponents()) {

			anchoAux += gap;

			if (anchoAux + comp.getPreferredSize().getWidth() >= ancho) {
				filas = 0;
				break;
			}

			anchoAux += comp.getPreferredSize().getWidth();
			columnas++;
		}

		if (jpGaleria.getComponents().length % columnas != 0) {
			filas++;
		}

		int largoAux = 0;
		largoAux += gap;
		int columAux = 0;
		for (int col = 0; col < jpGaleria.getComponents().length; col++) {

			if (columAux + 1 < columnas) {
				columAux++;

			} else {
				largoAux += jpGaleria.getComponents()[col].getPreferredSize().getHeight();
				largoAux += gap;
				columAux = 0;
			}
		}

		if (filas > 0) {
			largoAux += jpGaleria.getComponents()[0].getPreferredSize().getHeight();
			largoAux += gap;
		}

		dim = new Dimension(ancho, largoAux);
		return dim;
	}

	private void setup() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
		setSize(Configuracion.ANCHOVENTANA, Configuracion.ALTOVENTANA);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBackground(Configuracion.COLORFONDO);
		setLocationRelativeTo(null);
	}

	private void inicializarFormulario() {
		marcoInputs = new JPanel();
		marcoInputs.setMaximumSize(new Dimension(32767, 600));
		marcoInputs.setBorder(new LineBorder(new Color(0, 0, 51), 5));
		marcoInputs.setBackground(new Color(0, 0, 51));

		titulo_1 = new JLabel("Seleccion De Personal");
		titulo_1.setOpaque(true);
		titulo_1.setBackground(new Color(0, 0, 51));
		titulo_1.setForeground(new Color(255, 255, 255));
		titulo_1.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 18));
		titulo_1.setHorizontalAlignment(SwingConstants.CENTER);
		titulo_1.setHorizontalTextPosition(SwingConstants.CENTER);

		cantidadLideres = new JTextField();
		cantidadLideres.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 11));
		cantidadLideres.setColumns(10);

		lblLideres = new JLabel("Lider De Proyecto");
		lblLideres.setForeground(new Color(255, 255, 255));
		lblLideres.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 11));
		lblLideres.setHorizontalAlignment(SwingConstants.CENTER);
		lblLideres.setHorizontalTextPosition(SwingConstants.CENTER);

		lblProgramador = new JLabel("Programador");
		lblProgramador.setHorizontalTextPosition(SwingConstants.CENTER);
		lblProgramador.setHorizontalAlignment(SwingConstants.CENTER);
		lblProgramador.setForeground(new Color(255, 255, 255));
		lblProgramador.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 11));

		cantProgramadores = new JTextField();
		cantProgramadores.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 11));
		cantProgramadores.setColumns(10);

		lblArquitecto = new JLabel("Arquitecto");
		lblArquitecto.setHorizontalTextPosition(SwingConstants.CENTER);
		lblArquitecto.setHorizontalAlignment(SwingConstants.CENTER);
		lblArquitecto.setForeground(new Color(255, 255, 255));
		lblArquitecto.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 11));

		cantArquitectos = new JTextField();
		cantArquitectos.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 11));
		cantArquitectos.setColumns(10);

		lblTester = new JLabel("Tester");
		lblTester.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTester.setHorizontalAlignment(SwingConstants.CENTER);
		lblTester.setForeground(new Color(255, 255, 255));
		lblTester.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 11));

		cantTesters = new JTextField();
		cantTesters.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 11));
		cantTesters.setColumns(10);
	}

	private void salirBoton() {
		salir = new JLabel("X");
		salir.setOpaque(true);
		salir.setBackground(Configuracion.COLORFONDO);
		salir.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 20));
		salir.setHorizontalAlignment(SwingConstants.CENTER);
		salir.setForeground(new Color(255, 255, 255));
		salir.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

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
			public void mouseClicked(MouseEvent e) {
			}

		});
	}

	private void formularioDisplay(GroupLayout gl_contentPane) {

		lblRequerimientos = new JLabel("Ingrese los requerimientos de su equipo");
		lblRequerimientos.setHorizontalTextPosition(SwingConstants.CENTER);
		lblRequerimientos.setHorizontalAlignment(SwingConstants.CENTER);
		lblRequerimientos.setForeground(Color.WHITE);
		lblRequerimientos.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 11));

		iniciarBoton = new JButton("Iniciar Seleccion");
		iniciarBoton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Abrir la ventana de Progreso

				try {
					pasarLosRequerimientos();
					ControladorVentanas.abrirVentana(VENTANA.PROGRESO, VENTANA.MAIN);

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, e2.getMessage());
				}

			}

		});
		iniciarBoton.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 11));
		iniciarBoton.setBorderPainted(false);
		iniciarBoton.setBackground(new Color(255, 255, 255));
		GroupLayout gl_marcoInputs = new GroupLayout(marcoInputs);
		gl_marcoInputs.setHorizontalGroup(gl_marcoInputs.createParallelGroup(Alignment.LEADING)
				.addComponent(titulo_1, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
				.addGroup(gl_marcoInputs.createSequentialGroup().addContainerGap()
						.addComponent(lblRequerimientos, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
						.addContainerGap())
				.addGroup(Alignment.TRAILING,
						gl_marcoInputs.createSequentialGroup().addContainerGap()
								.addComponent(lblLideres, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(cantidadLideres, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
				.addGroup(gl_marcoInputs.createSequentialGroup().addGap(64).addComponent(iniciarBoton)
						.addContainerGap(71, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING,
						gl_marcoInputs.createSequentialGroup().addContainerGap()
								.addComponent(lblProgramador, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(cantProgramadores, GroupLayout.PREFERRED_SIZE, 96,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
				.addGroup(gl_marcoInputs.createSequentialGroup().addContainerGap()
						.addComponent(lblArquitecto, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(cantArquitectos, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
				.addGroup(gl_marcoInputs.createSequentialGroup().addContainerGap()
						.addComponent(lblTester, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(cantTesters, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_marcoInputs.setVerticalGroup(gl_marcoInputs.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_marcoInputs.createSequentialGroup().addGap(52)
						.addComponent(titulo_1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE).addGap(53)
						.addComponent(lblRequerimientos, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addGap(43)
						.addGroup(gl_marcoInputs.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblLideres, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(cantidadLideres))
						.addGap(18)
						.addGroup(gl_marcoInputs.createParallelGroup(Alignment.LEADING)
								.addComponent(cantProgramadores, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblProgramador, GroupLayout.PREFERRED_SIZE, 20,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_marcoInputs.createParallelGroup(Alignment.LEADING)
								.addComponent(cantArquitectos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblArquitecto, GroupLayout.PREFERRED_SIZE, 20,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_marcoInputs.createParallelGroup(Alignment.LEADING)
								.addComponent(cantTesters, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTester, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 224, Short.MAX_VALUE).addComponent(iniciarBoton)
						.addContainerGap()));
		marcoInputs.setLayout(gl_marcoInputs);
		contentPane.setLayout(gl_contentPane);
	}

	private GroupLayout galeriaDisplay() {
		envoltorioGaleria = new JPanel();
		envoltorioGaleria.setOpaque(false);

		btnMostrarIncompatibilidades = new JButton("Mostrar Incompatibilidades");
		btnMostrarIncompatibilidades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mostrarIncompatibilidades();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(contentPane, e);
				}

			}
		});
		btnMostrarIncompatibilidades.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 11));
		btnMostrarIncompatibilidades.setBorderPainted(false);
		btnMostrarIncompatibilidades.setBackground(Color.WHITE);

		iniciarBoton_2 = new JButton("Guardar Empleado");
		iniciarBoton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ControladorVentanas.abrirVentana(VENTANA.GUARDAREMPLEADO, VENTANA.MAIN);
			}
		});
		iniciarBoton_2.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 11));
		iniciarBoton_2.setBorderPainted(false);
		iniciarBoton_2.setBackground(Color.WHITE);

		btnMostrarPersonal = new JButton("Mostrar Personal");
		btnMostrarPersonal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarImagenes();
			}
		});
		btnMostrarPersonal.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 11));
		btnMostrarPersonal.setBorderPainted(false);
		btnMostrarPersonal.setBackground(Color.WHITE);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addComponent(marcoInputs, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE).addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false).addGroup(gl_contentPane
						.createSequentialGroup()
						.addComponent(btnMostrarPersonal, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(iniciarBoton_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addGap(18).addComponent(btnMostrarIncompatibilidades).addGap(19))
						.addComponent(salir, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE).addGroup(
								gl_contentPane
										.createSequentialGroup().addComponent(envoltorioGaleria,
												GroupLayout.PREFERRED_SIZE, 604, GroupLayout.PREFERRED_SIZE)
										.addGap(10)))));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(marcoInputs, GroupLayout.PREFERRED_SIZE, 639, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(salir, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(
										envoltorioGaleria, GroupLayout.PREFERRED_SIZE, 535, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnMostrarIncompatibilidades)
										.addComponent(btnMostrarPersonal, GroupLayout.PREFERRED_SIZE, 23,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(iniciarBoton_2, GroupLayout.PREFERRED_SIZE, 23,
												GroupLayout.PREFERRED_SIZE))))
				.addContainerGap()));
		envoltorioGaleria.setLayout(null);

		scrollGaleria = new JScrollPane();
		scrollGaleria.setBounds(10, 11, 584, 513);
		envoltorioGaleria.add(scrollGaleria);
		return gl_contentPane;
	}

	protected void mostrarIncompatibilidades() throws Exception {
		List<Pair<Empleado, Empleado>> incompatibilidades = Datos.listaIncompatibilidades();

		Integer tamanioCarnet = 140;

		JPanel galeriaIncompatibilidadaes = new JPanel();
		galeriaIncompatibilidadaes.setMaximumSize(new Dimension(584, 518));
		galeriaIncompatibilidadaes.setLayout(new FlowLayout(FlowLayout.CENTER, gap, gap));
		galeriaIncompatibilidadaes.setPreferredSize(new Dimension(584, 518));

		scrollGaleria.setViewportView(galeriaIncompatibilidadaes);

		for (Pair<Empleado, Empleado> incompatibilidad : incompatibilidades) {
			JPanel contenedor = new JPanel();
			contenedor.setPreferredSize(new Dimension((tamanioCarnet * 2) + 10, tamanioCarnet + 10));
			contenedor.setBackground(Color.RED);
			contenedor.setLayout(new FlowLayout(FlowLayout.CENTER));

			Carnet carnet_1 = new Carnet(incompatibilidad.get_x(), tamanioCarnet, tamanioCarnet);
			Carnet carnet_2 = new Carnet(incompatibilidad.get_y(), tamanioCarnet, tamanioCarnet);

			contenedor.add(carnet_1);
			contenedor.add(carnet_2);

			galeriaIncompatibilidadaes.add(contenedor);
		}

		galeriaIncompatibilidadaes.setPreferredSize(calcular(galeriaIncompatibilidadaes));
	}

	private Integer validarRequerimiento(String requerimiento) {
		if (!EsNumero(requerimiento)) {
			throw new IllegalArgumentException("El requerimiento debe ser un numero entero positivo.");
		} else if (Integer.valueOf(requerimiento) < 0) {
			throw new IllegalArgumentException("El numero debe ser entero positivo");
		}

		return Integer.valueOf(requerimiento);
	}

	private boolean EsNumero(String numero) {
		try {
			@SuppressWarnings("unused")
			Integer num = Integer.valueOf(numero);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void pasarLosRequerimientos() {

		Integer lideres = validarRequerimiento((String) cantidadLideres.getText());
		Integer arquitectos = validarRequerimiento((String) cantArquitectos.getText());
		Integer programadores = validarRequerimiento((String) cantProgramadores.getText());
		Integer testers = validarRequerimiento((String) cantTesters.getText());

		ControladorVentanas.setearRequerimientos(lideres, arquitectos, programadores, testers);
	}

	public void actualizarGaleria() {
		mostrarImagenes();
	}
}
