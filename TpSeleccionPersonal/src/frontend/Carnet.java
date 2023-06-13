package frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import backend.Empleado;
import backend.RRHH.ROLES;
import configuracion.Configuracion;

@SuppressWarnings("serial")
public class Carnet extends JPanel implements MouseListener{

	String nombre;
	String foto;
	Integer desempenio;
	ROLES rol;
	Integer id;
	
	Integer ancho;
	Integer alto;
	
	ImageIcon icon;
	
	JLabel lblFoto;
	
	JPanel marco;
	
	Empleado empleado;
	
	public Carnet(Empleado empleado, Integer width, Integer high) {
		this.empleado = empleado;
		
		nombre = empleado.getNombre();
		foto = empleado.getFoto();
		desempenio = empleado.getDesempenio();
		rol = empleado.getRol();
		id = empleado.getId();
		ancho = width;
		alto = high;
		
		this.setLayout(null);
		
		marco = new JPanel();
		marco.setBounds(2, 0, ancho, alto - 20);
		marco.setPreferredSize(new Dimension(ancho -2, alto-30));
		this.add(marco);
		
		
		this.setPreferredSize(new Dimension(ancho, alto));
		
		JLabel descripcion = new JLabel("ID: " + id + " " + nombre.split(" ")[0]);
		descripcion.setBounds(2, alto - 20, ancho - 2, 20);
		descripcion.setForeground(Color.white);
		descripcion.setOpaque(true);
		descripcion.setBackground(Configuracion.COLORFONDO);
		descripcion.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(descripcion);
		
		cargarImagen();
		
		addMouseListener(this);
	}
	
	private void cargarImagen() {
		icon = new ImageIcon(foto);
		JLabel lbl = new JLabel();
		lbl.setIcon(icon);
		lbl.setBounds(5, 5, marco.getWidth(), marco.getHeight());

		ajustarImagen();

	}

	private void ajustarImagen() {

		Image img = icon.getImage();
		Image nuevaImagen = img.getScaledInstance(marco.getWidth(), marco.getHeight(), java.awt.Image.SCALE_SMOOTH);
		Icon nuevoIcono = new ImageIcon(nuevaImagen);
	    lblFoto = new JLabel();
		lblFoto.setIcon(nuevoIcono);
		marco.removeAll();
		lblFoto.setBounds(5, 5, marco.getWidth(), marco.getHeight());
		lblFoto.setOpaque(true);
		marco.add(lblFoto);
	}

	
	
	/** Acciones **/
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		// Muestra la descripcion del empleado
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("Nombre: " + nombre + "\n");
		mensaje.append("Rol: " + rol + "\n");
		mensaje.append("Dempenio: " + desempenio + "\n");
		mensaje.append("ID: " + id + "\n");
		
		JFrame ventanita = new JFrame();
		ventanita.setLocationRelativeTo(null);
		ventanita.setVisible(true);
		JOptionPane.showMessageDialog(ventanita, mensaje);
		ventanita.dispose();
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {	}
	@Override
	public void mouseEntered(MouseEvent e) {

	}
	@Override
	public void mouseExited(MouseEvent e) {	
	}
	
	
	public Empleado getEmpleado() {
		return empleado;
	}
}
