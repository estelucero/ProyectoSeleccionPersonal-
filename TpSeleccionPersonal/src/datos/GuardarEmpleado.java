package datos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import backend.Empleado;
import backend.RRHH.ROLES;
import configuracion.Configuracion;
import frontend.ControladorVentanas;
import frontend.ControladorVentanas.VENTANA;

@SuppressWarnings("serial")
public class GuardarEmpleado extends JFrame {
	private JTextField nombre;
	private JTextField id;
	private JLabel lblDesempenio;

	private JButton btnColocarFoto;
	private JPanel mostrarFoto;
	private String direccionFoto;
	private JLabel foto;
	private ImageIcon icon;

	private JPanel contentPane;
	private JLabel salir;
	
	JLabel lblPuesto;
	JLabel lblNombre;
	JLabel textID;
	@SuppressWarnings("rawtypes")
	JComboBox rol;
	@SuppressWarnings("rawtypes")
	JComboBox desempenio;
	
	JButton guardar;
	
	public static void main(String[] args) {
		GuardarEmpleado empleado = new GuardarEmpleado();
		empleado.setVisible(true);
	}

	public GuardarEmpleado() {
		// Setup
		setup();
		initComponents();
		salirBoton();
	}

	private void initComponents() {

		datosEmpleadosDisplay();

		colocarFotoDisplay();

		guardarDisplay();

		mostrarDisplay();
	}

	private void mostrarDisplay() {
	}

	private void guardarDisplay() {
		guardar = new JButton("Guardar");
		guardar.setBounds(397, 517, 89, 20);
		contentPane.add(guardar);
		guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					guardar(Integer.valueOf(id.getText()), nombre.getText(), (ROLES) rol.getSelectedItem(),
							direccionFoto, Integer.valueOf(desempenio.getSelectedItem().toString()));
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void datosEmpleadosDisplay() {
		lblNombre = new JLabel("Nombre Completo");
		lblNombre.setForeground(Color.WHITE);
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setBounds(159, 362, 211, 20);
		contentPane.add(lblNombre);
		
		nombre = new JTextField();
		nombre.setFont(UIManager.getFont("Button.font"));
		nombre.setBounds(398, 362, 270, 20);
		contentPane.add(nombre);
		nombre.setColumns(10);

		lblPuesto = new JLabel("Puesto");
		lblPuesto.setForeground(Color.WHITE);
		lblPuesto.setHorizontalAlignment(SwingConstants.CENTER);
		lblPuesto.setBounds(159, 394, 211, 20);
		contentPane.add(lblPuesto);

		textID = new JLabel("Id");
		textID.setForeground(Color.WHITE);
		textID.setHorizontalAlignment(SwingConstants.CENTER);
		textID.setBounds(159, 425, 211, 20);
		contentPane.add(textID);

		id = new JTextField();
		id.setColumns(10);
		id.setBounds(398, 425, 270, 20);
		contentPane.add(id);

		rol = new JComboBox();

		rol.setModel(new DefaultComboBoxModel(
				new ROLES[] { ROLES.LIDERPROYECTO, ROLES.PROGRAMADOR, ROLES.ARQUITECTO, ROLES.TESTER }));

		rol.setBounds(398, 393, 270, 22);
		contentPane.add(rol);

		lblDesempenio = new JLabel("Desempe\u00F1o");
		lblDesempenio.setForeground(Color.WHITE);
		lblDesempenio.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesempenio.setBounds(159, 456, 211, 20);
		contentPane.add(lblDesempenio);

		desempenio = new JComboBox();
		desempenio.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5" }));
		desempenio.setBounds(398, 455, 270, 22);
		contentPane.add(desempenio);
	}

	private void colocarFotoDisplay() {
		mostrarFoto = new JPanel();
		mostrarFoto.setBackground(Color.WHITE);
		mostrarFoto.setBounds(323, 56, 233, 209);
		contentPane.add(mostrarFoto);
		mostrarFoto.setLayout(null);

		foto = new JLabel("");
		foto.setHorizontalAlignment(SwingConstants.CENTER);
		foto.setBounds(0, 0, 233, 209);
		mostrarFoto.add(foto);

		btnColocarFoto = new JButton("Colocar Foto");
		btnColocarFoto.setFocusable(false);
		btnColocarFoto.addActionListener(e -> ColocarFoto());
		btnColocarFoto.setBounds(323, 287, 233, 23);
		contentPane.add(btnColocarFoto);
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
		contentPane.setLayout(null);		
	}
	
	private void salirBoton() {
		salir = new JLabel("X");
		salir.setBounds(854, 0, 46, 34);
		contentPane.add(salir);
		salir.setOpaque(true);
		salir.setBackground(Configuracion.COLORFONDO);
		salir.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 20));
		salir.setHorizontalAlignment(SwingConstants.CENTER);
		salir.setForeground(new Color(255, 255, 255));
		
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ControladorVentanas.actualizarGaleria();
				ControladorVentanas.abrirVentana(VENTANA.MAIN, VENTANA.GUARDAREMPLEADO);
			}
		});
		btnVolver.setBounds(25, 596, 89, 20);
		contentPane.add(btnVolver);
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
	}


	/**************** FUNCIONES *****************/
	static final String pathCarpetaEmpleados = System.getProperty("user.dir") + "//RRHH//";
	static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	static JsonReader reader;

	static final Path pathCarpetaProyecto = Paths.get(System.clearProperty("user.dir"));
	static final File empleadosJsonArchivo = new File(pathCarpetaEmpleados + "empleadosConFoto.json");
	static final String pathCarpetaImagenes = pathCarpetaEmpleados + "//imagenes//";
	private JButton btnVolver;

	private static void guardar(Integer ID, String nombre, ROLES rol, String foto, Integer desempenio)
			throws Exception {

//		File file = new File(direccion + "empleados.json");
		File file = new File(pathCarpetaEmpleados + "empleadosConFoto.json");

		// Si el archivo no existe lo crea
		if (!file.exists())
			file.createNewFile();

		// Verificacion
		if (verificacionExistencia(ID, nombre, rol, foto, desempenio)) {

			// Creacion del empleado JSON
			JsonObject empleado = new JsonObject();

			empleado.addProperty("_id", ID); // Agregado
			empleado.addProperty("_nombre", nombre);
			empleado.addProperty("_rol", rol.name());
			empleado.addProperty("_foto", foto);
			empleado.addProperty("_desempenio", desempenio);

			JsonParser parser = new JsonParser();
			JsonArray empleados;

			// Parseando
			// Si el archivo esta vacio genero un nuero array de empleados
			if (parser.parse(new FileReader(file)).isJsonNull()) {
				empleados = new JsonArray();
			} else {
				empleados = (JsonArray) parser.parse(new FileReader(file));
			}

			// Añado al array
			empleados.add(empleado);

			// Escribo un nuevo empleado en el array
			escribir(empleados, file);
			JFrame ventanita = new JFrame();
			JOptionPane.showMessageDialog(ventanita, "Se guardo con exito.");
			ventanita.dispose();
		}
	}

	private static void escribir(JsonArray empleados, File file) throws IOException {
		FileWriter writer = new FileWriter(file);
		writer.write(gson.toJson(empleados));
		writer.flush();
		writer.close();
	}

	private static boolean verificacionExistencia(Integer ID, String nombre, ROLES rol, String foto, Integer desempenio)
			throws Exception {
		File file = new File(pathCarpetaEmpleados + "empleadosConFoto.json");
		JsonParser parser = new JsonParser();

		Empleado nuevoEmpleado = null;

		if (parser.parse(new FileReader(file)).isJsonNull()) {
			nuevoEmpleado = new Empleado(ID, nombre, rol, foto, desempenio);
			return true;
		}

		JsonArray arr = (JsonArray) parser.parse(new FileReader(file));

		nuevoEmpleado = new Empleado(ID, nombre, rol, foto, desempenio);
		Empleado[] empleados = gson.fromJson(arr.toString(), Empleado[].class);

		for (Empleado empleado : empleados) {
			if (empleado.equals(nuevoEmpleado)) {
				throw new Exception("El empleado ya existe.");
			}
		}

		return true;
	}

	protected void ColocarFoto() {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(pathCarpetaEmpleados + "imagenes//"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpeg", "jpg", "png"));

		fileChooser.setAcceptAllFileFilterUsed(true);

		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			// Revisar que en todos los sistemas la ruta sea correcta.
			direccionFoto = selectedFile.getAbsolutePath();
			direccionFoto = obtenerPathRelativo();

			// Carga de la imagen seleccionada
			cargarImagen();

		}

	}

	private String obtenerPathRelativo() {
		// Create file objects
		Path pathFoto = Paths.get(direccionFoto);

		// convert the absolute path to relative path
		Path pathRelativo = pathCarpetaProyecto.relativize(pathFoto);

		return pathRelativo.toString();
	}

	private void cargarImagen() {
		icon = new ImageIcon(direccionFoto);
		Image img = icon.getImage();
		int alto = img.getHeight(mostrarFoto);
		int ancho = img.getWidth(mostrarFoto);

		JLabel lbl = new JLabel();
		lbl.setIcon(icon);
		mostrarFoto.add(lbl);
		lbl.setBounds(5, 5, ancho, alto);

		ajustarImagen();

	}

	private void ajustarImagen() {

		Dimension d = mostrarFoto.getSize();
		int alto = d.height;
		int ancho = d.width;

		Image img = icon.getImage();
		Image nuevaImagen = img.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
		Icon nuevoIcono = new ImageIcon(nuevaImagen);
		JLabel lbl = new JLabel();
		lbl.setIcon(nuevoIcono);
		mostrarFoto.removeAll();
		lbl.setBounds(0, 0, ancho, alto);
		mostrarFoto.add(lbl);
		repaint();
	}

}
