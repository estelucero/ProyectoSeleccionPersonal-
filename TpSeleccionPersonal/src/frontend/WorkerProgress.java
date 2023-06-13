package frontend;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import observer.Observador;

public class WorkerProgress extends SwingWorker<Void, Void>{
	private JProgressBar progreso;
	private Observador observador; 
	
	public WorkerProgress(JProgressBar barraDeProgreso, Observador observador) {
		progreso = barraDeProgreso;
		this.observador = observador;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		progreso.setMaximum(100);
		progreso.setMinimum(0);
		while(observador.salida() < 100.0) {
			progreso.setValue(observador.salida().intValue());
			Thread.sleep(200);
		}
		progreso.setValue(observador.salida().intValue());
		return null;
	}
		
	
	@Override
	protected void done() {
		if(isCancelled()) {
			JOptionPane.showMessageDialog(progreso.getParent(), "Se ha detenido la ejecucion.");
		}else {
			ControladorVentanas.avanzarAEstadistica();
		}
	}
	
	
}
