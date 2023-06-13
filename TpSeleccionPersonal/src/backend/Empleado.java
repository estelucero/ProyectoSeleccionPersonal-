package backend;

import backend.RRHH.ROLES;

public class Empleado {
	private Integer _id;
	private String _nombre;
	private ROLES _rol;
	private String _foto;
	private Integer _desempenio;
	public Empleado(Integer id,String nombre,ROLES rol,String foto,Integer desempenio) {
		
		_nombre=nombre;
		_rol=rol;
		_foto=foto;
		if(desempenio<1 || desempenio>5) {
			throw new IllegalArgumentException("Error el desempenio tiene que ser entre 1-5");
		}
		_desempenio=desempenio;
		
		if(id<0) {
			throw new IllegalArgumentException("Error la id  tiene que ser mayor a 0");
		}
		_id=id;
	}
	
	
	@Override
	public int hashCode() {
		int clave=3;
		return (this.getId()/clave)+this.getNombre().length();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		return this._id.equals(other.getId()) ;
	}
	
	public String getNombre() {
		return _nombre;
	}
	public ROLES getRol() {
		return _rol;
	}
	public String getFoto() {
		return _foto;
	}


	
	public Integer getDesempenio() {
		return _desempenio;
	}
	public Integer getId() {
		return _id;
	}


	public boolean mismoRol(Empleado empleado) {
	return this._rol.equals(empleado.getRol());	
	}

	@Override
	public String toString() {
		return this.getId()+"";
	}


	
	

}
