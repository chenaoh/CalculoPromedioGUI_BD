package modelo.operaciones;

import java.awt.Color;
import java.util.ArrayList;

import controlador.Coordinador;
import modelo.vo.EstudianteVO;

public class Procesos {

	ArrayList<EstudianteVO> listaPersonas;
	String mensje;
	private Coordinador miCoordinador;
	
	public Procesos() {
		listaPersonas=new ArrayList<EstudianteVO>();
	}
	
	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}
	
	public double calcularPromedio(double n1,double n2,double n3) {
		
		double prom=(n1+n2+n3)/3;
		
		return prom;
		
	}

	public String calcularDefinitiva(double promedio) {

		if (promedio>=3.5) {
			return "GANA";
		}else {
			return "PIERDE";
		}	
	}

	public double calcularPromedio(EstudianteVO est) {

		double prom=(est.getNota1()+est.getNota2()+est.getNota3())/3;
		System.out.println(prom);
		prom=Math.round(prom*100.0)/100.0;	
		return prom;
	}

	public void registrarEnBD(EstudianteVO estudiante) {
		int bandera=0;
		if (listaPersonas.size()>0) 
		{			
			for (EstudianteVO persona : listaPersonas) {
				if (persona.getDocumento().equals(estudiante.getDocumento())) {
					bandera=1;
					break;
				}
			}
			
			if (bandera==0) {
				System.out.println("Se registra en la BD");
				listaPersonas.add(estudiante);
			}else {
				System.out.println("La persona ya existe!");
			}
			
		}else {
			System.out.println("Se registra en la BD");
			listaPersonas.add(estudiante);
		}
	}

	public void imprimirListaEstudiantes() {
		for (EstudianteVO persona : listaPersonas) {
			System.out.println(persona);
		}
	}
	
	public ArrayList<EstudianteVO> getListaPersonas(){
		return listaPersonas;
	}

	public EstudianteVO obtenerEstudiante(String doc) {
		EstudianteVO p=null;
		for (EstudianteVO persona : listaPersonas) {
			if (persona.getDocumento().equals(doc)) {
				p= persona;
			}
		//	System.out.println(persona);
		}
		return p;
		
	}
	
}
