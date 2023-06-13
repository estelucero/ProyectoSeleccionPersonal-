package frontend;

import java.util.EnumMap;

import javax.swing.JFrame;

import backend.Sistema;
import datos.GuardarEmpleado;

public class ControladorVentanas {
	
	private static VentanaMain ventanaMain = new VentanaMain();
	private static VentanaProgreso ventanaProgreso = new VentanaProgreso();
	private static VentanaEstadisticas ventanaEstadisticas = new VentanaEstadisticas();
	private static GuardarEmpleado ventanaGuardarEmpleado = new GuardarEmpleado();
	

	
	public enum VENTANA {
		MAIN, PROGRESO, ESTADISTICAS, GUARDAREMPLEADO
	}
	
	
	private static EnumMap<VENTANA, JFrame> ventanas = cargarVentanas();

	private static EnumMap<VENTANA, JFrame> cargarVentanas() {
		EnumMap<VENTANA, JFrame> vent = new EnumMap<VENTANA, JFrame>(VENTANA.class);
		
		vent.put(VENTANA.MAIN, ventanaMain);
		vent.put(VENTANA.PROGRESO, ventanaProgreso);
		vent.put(VENTANA.ESTADISTICAS, ventanaEstadisticas);		
		vent.put(VENTANA.GUARDAREMPLEADO, ventanaGuardarEmpleado);
		
		return vent;
	}
	
	public static void abrirVentana(VENTANA ventana) {
		ventanas.get(ventana).setVisible(true);
	}
	
	public static void cerrarVentana(VENTANA ventana) {
		ventanas.get(ventana).setVisible(false);
	}
	
	public static void abrirVentana(VENTANA ventanaAbrir, VENTANA ventanaCerrar) {
		abrirVentana(ventanaAbrir);
		cerrarVentana(ventanaCerrar);
	}

	public static void setearRequerimientos(Integer cantLideres, Integer cantArquitectos, Integer cantProgramadores, Integer cantTesters) {
		ventanaProgreso.setearRequerimientos(cantLideres, cantArquitectos, cantProgramadores, cantTesters);;
	}
	

	public static void actualizarGaleria() {
		ventanaMain.actualizarGaleria();
		
	}
	
	public static void setearGolosoEnEstadisticas(Sistema backtracking) {
		ventanaEstadisticas.setearGoloso(backtracking);
	}
	
	public static void setearBacktrackingEnEstadisticas(Sistema goloso) {
		ventanaEstadisticas.setearBacktracking(goloso);
	}

	public static void avanzarAEstadistica() {
		ventanaProgreso.avanzarSiguienteVentana();
	}
	
	public static void avanzarHaciaVentanaEstadisticas(Sistema backtracking, Sistema goloso) {
		ventanaProgreso.setVisible(false);
		ventanaEstadisticas.setearBacktracking(backtracking);
		ventanaEstadisticas.setearGoloso(goloso);
		ventanaEstadisticas.setVisible(true);
	}
	
	public static void reiniciarProgresos() {
		ventanaProgreso.reiniciar();
	}
	
}
