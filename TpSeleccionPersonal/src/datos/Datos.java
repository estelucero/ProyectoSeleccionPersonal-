package datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import backend.Empleado;
import backend.Pair;

public class Datos {

	static String direccion = System.getProperty("user.dir") + "//RRHH//";
	static Gson gson =  new GsonBuilder().setPrettyPrinting().create();

	public static void main(String[] args) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		Datos.listaIncompatibilidades();
	}
	
	public static List<Empleado> listaDeEmpleados() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		File file = new File(direccion + "empleadosConFoto.json");
		if(!file.exists()) 
			throw new IllegalAccessError("El archivo empleadosConFoto.json no existe.");
		
		JsonParser parser = new JsonParser();
		JsonArray arr = (JsonArray) parser.parse(new FileReader(file));
		
		Empleado[] empleados =  gson.fromJson( arr.toString() , Empleado[].class);
		
		return Arrays.asList(empleados);
	}
	
	public static List<Pair<Empleado,Empleado>> listaIncompatibilidades() throws JsonIOException, JsonSyntaxException, FileNotFoundException{
		List<Pair<Empleado, Empleado>> incompatibilidades = new LinkedList<Pair<Empleado,Empleado>>();
		List<Pair<Integer, Integer>> idsIncompatibilidades = new LinkedList<Pair<Integer, Integer>>();
		
		File file = new File(direccion + "incompatibilidades.json");
		if(!file.exists()) {
			throw new IllegalArgumentException("El archivo incompatibilidades.json no existe.");
		}
		
		JsonParser parser = new JsonParser();
		JsonArray arr = (JsonArray) parser.parse(new FileReader(file));
		
		JsonArray[] arregloID = gson.fromJson(arr.toString(), JsonArray[].class);
		
		for(JsonArray a: arregloID) {
			Integer[] subArr = gson.fromJson(a, Integer[].class);
			Pair<Integer, Integer> incompatibilidad = new Pair<Integer, Integer>(subArr[0], subArr[1]);
			if(!idsIncompatibilidades.contains(incompatibilidad) && !subArr[0].equals(subArr[1])) {
				idsIncompatibilidades.add(incompatibilidad);
			}
		}
		List<Empleado> empleados = Datos.listaDeEmpleados();
		
		
		for(Pair<Integer, Integer> par: idsIncompatibilidades) {
			Integer id_1 = par.get_x();
			Integer id_2 = par.get_y();
			
			Empleado empleado_1 = null;
			Empleado empleado_2 = null;
			
			for(Empleado empleado: empleados) {
				if(empleado.getId().equals(id_1)) {
					empleado_1 = empleado;
				}
				
				if(empleado.getId().equals(id_2)) {
					empleado_2 = empleado;
				}
			}
		
			Pair<Empleado, Empleado> parIncompatibilidad = new Pair<Empleado, Empleado>(empleado_1, empleado_2);
			
			if(!incompatibilidades.contains(parIncompatibilidad) && 
					!parIncompatibilidad.get_x().equals(parIncompatibilidad.get_y())) {
				
				incompatibilidades.add(parIncompatibilidad);
			}
		}
		
		
		return incompatibilidades;
	}
	
	
}
