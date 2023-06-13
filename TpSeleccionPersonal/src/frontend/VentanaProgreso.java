package frontend;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import backend.Sistema;
import backend.Sistema.TIPO;
import configuracion.Configuracion;
import datos.Datos;
import frontend.ControladorVentanas.VENTANA;
import observer.Observador;
import solver.SolverBacktracking;
import solver.SolverGoloso;

@SuppressWarnings("serial")
public class VentanaProgreso extends JFrame{
	
	JPanel contentPane;
	JProgressBar barraDeProgresoBacktracking;
	private JProgressBar barraDeProgresoGoloso;

	JButton btnIniciar;
	JButton btnCancelar;
	JButton btnVolver;
	JLabel  salir;
	
	Integer lideres;
	Integer arquitectos;
	Integer programadores;
	Integer testers;
	
	Sistema backtracking;
	WorkerProgress barProgressBacktraking;
	Sistema goloso;
	WorkerProgress barProgressGoloso;
	
	public VentanaProgreso() {
		// Setup basico
		setup();
		salirBoton();
		
		// Inicializar Variables
		incializarBarraProgress();
		inicializarBtnIniciar();
		incializarBtnCancerlar();
		inicializarBtnVolver();
		
		// Layout de los componentes 
		// en pantalla
		display();				
	}
	
	private void salirBoton() {
		salir = new JLabel("X");
		salir.setOpaque(true);
		salir.setBackground(Configuracion.COLORFONDO);
		salir.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 20));
		salir.setHorizontalAlignment(SwingConstants.CENTER);
		salir.setForeground(new Color(255, 255, 255));
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
	

	private void incializarBtnCancerlar() {
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFocusable(false);
		btnCancelar.setBackground(new Color(0, 0, 102));
		btnCancelar.setEnabled(false);
		
		btnCancelar.addActionListener( (e) -> {
			Thread detener_1 = new Thread(
				() -> cancelarWorkerProgress()
			);
			detener_1.start();
			
			Thread detener_2 = new Thread(
				() -> detenerSistema()
			);
			detener_2.start();
			
			btnCancelar.setEnabled(false);
			btnIniciar.setEnabled(true);
			btnVolver.setVisible(true);
		});

	}


	private synchronized void cancelarWorkerProgress() {
		barProgressBacktraking.cancel(true);
		barProgressGoloso.cancel(true);
	}

	@SuppressWarnings("deprecation")
	private synchronized void detenerSistema() {
		backtracking.stop();
		goloso.stop();
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

	private void incializarBarraProgress() {
		barraDeProgresoBacktracking = new JProgressBar();
		barraDeProgresoBacktracking.setStringPainted(true);
		barraDeProgresoBacktracking.setFont(new Font("Ebrima", Font.BOLD, 17));
		barraDeProgresoBacktracking.setForeground(new Color(0, 0, 0));
		barraDeProgresoBacktracking.setName("Progreso");
		barraDeProgresoBacktracking.setOpaque(true);
		barraDeProgresoBacktracking.setBorder(new LineBorder(new Color(0, 0, 51), 3));
		barraDeProgresoBacktracking.setBackground(new Color(255, 255, 255));
	}

	private void inicializarBtnIniciar() {
		btnIniciar = new JButton("Iniciar");
		btnIniciar.setFocusable(false);
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ejecutarBacktracking();
					ejecutarGoloso();
					
					btnIniciar.setEnabled(false);
					btnCancelar.setEnabled(true);
					btnVolver.setVisible(false);
					
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, e2.getLocalizedMessage());
				}
				
			}

		});
		btnIniciar.setForeground(new Color(255, 255, 255));
		btnIniciar.setBackground(new Color(0, 0, 102));
	}
	
	private void inicializarBtnVolver() {
		btnVolver = new JButton("<----");
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFocusable(false);
		btnVolver.setBackground(new Color(0, 0, 102));
		btnVolver.setVisible(false);
		btnVolver.addActionListener( (e) -> {
			reiniciar();
			ControladorVentanas.abrirVentana(VENTANA.MAIN, VENTANA.PROGRESO);
		});

	}

	private void display() {
		barraDeProgresoGoloso = new JProgressBar();
		barraDeProgresoGoloso.setStringPainted(true);
		barraDeProgresoGoloso.setOpaque(true);
		barraDeProgresoGoloso.setName("Progreso");
		barraDeProgresoGoloso.setForeground(Color.BLACK);
		barraDeProgresoGoloso.setFont(new Font("Ebrima", Font.BOLD, 17));
		barraDeProgresoGoloso.setBorder(new LineBorder(new Color(0, 0, 51), 3));
		barraDeProgresoGoloso.setBackground(Color.WHITE);
		
		JLabel lblBacktracking = new JLabel("BACKTRACKING");
		lblBacktracking.setForeground(Color.WHITE);
		lblBacktracking.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 17));
		
		JLabel lblGoloso = new JLabel("GOLOSO");
		lblGoloso.setForeground(Color.WHITE);
		lblGoloso.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 17));

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(22)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblBacktracking, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblGoloso, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(btnVolver, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
									.addGap(736)
									.addComponent(salir, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(barraDeProgresoGoloso, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)
										.addComponent(barraDeProgresoBacktracking, GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(btnIniciar, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED, 652, Short.MAX_VALUE)
											.addComponent(btnCancelar, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)))
									.addGap(22))))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(20)
							.addComponent(btnVolver))
						.addComponent(salir, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
					.addGap(148)
					.addComponent(lblBacktracking)
					.addGap(18)
					.addComponent(barraDeProgresoBacktracking, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addGap(83)
					.addComponent(lblGoloso, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(barraDeProgresoGoloso, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 194, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancelar)
						.addComponent(btnIniciar))
					.addGap(24))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void setearRequerimientos(Integer cantLideres, Integer cantArquitectos, Integer cantProgramadores, Integer cantTesters) {
		lideres = cantLideres; arquitectos = cantArquitectos; programadores = cantProgramadores; testers = cantTesters;
		
	}
	
	private void ejecutarBacktracking() throws Exception {
		backtracking = new Sistema();
		backtracking.setSolver(new SolverBacktracking(lideres, arquitectos, programadores, testers, Datos.listaDeEmpleados(), Datos.listaIncompatibilidades()));
		Observador observador = backtracking.registrarObservador(TIPO.PROGRESO);
		
		barProgressBacktraking = new WorkerProgress(barraDeProgresoBacktracking, observador); 
		
		barProgressBacktraking.execute(); // Corre en backgournd
		
		backtracking.start(); // Corre en un hilo
	}
	
	private void ejecutarGoloso() throws Exception {
		goloso = new Sistema();
		goloso.setSolver(new SolverGoloso(lideres, arquitectos, programadores, testers, Datos.listaDeEmpleados(), Datos.listaIncompatibilidades()));
		Observador observador = goloso.registrarObservador(TIPO.PROGRESO);
		
		barProgressGoloso = new WorkerProgress(barraDeProgresoGoloso, observador); 
		
		barProgressGoloso.execute(); // Corre en backgournd
		
		goloso.start(); // Corre en un hilo
	}
	
	public void avanzarSiguienteVentana() {
		if(barraDeProgresoBacktracking.getValue() == 100 && barraDeProgresoGoloso.getValue() == 100) {
			reiniciar();
			ControladorVentanas.avanzarHaciaVentanaEstadisticas(backtracking, goloso);
		}
	}
	
	
	public void reiniciar() {
		contentPane.removeAll();
		// Inicializar Variables
		incializarBarraProgress();
		inicializarBtnIniciar();
		incializarBtnCancerlar();
		inicializarBtnVolver();
		
		// Layout de los componentes 
		// en pantalla
		display();
	}
}

