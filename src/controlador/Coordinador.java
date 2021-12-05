package controlador;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import modelo.dao.EstudianteDAO;
import modelo.operaciones.Procesos;
import modelo.vo.EstudianteVO;
import vista.gui.VentanaConsulta;
import vista.gui.VentanaConsultaGeneral;
import vista.gui.VentanaConsultaPersonas;
import vista.gui.VentanaOperaciones;

public class Coordinador {

	private VentanaOperaciones ventanaOperaciones;
	private VentanaConsulta ventanaConsulta;
	private VentanaConsultaGeneral ventanaConsultaGeneral;
	private VentanaConsultaPersonas ventanaConsultaPersonas;
	private Procesos misProcesos;
	private EstudianteDAO miEstudianteDAO;
	
	public void setVentanaOperaciones(VentanaOperaciones ventanaOperaciones) {
		this.ventanaOperaciones=ventanaOperaciones;
	}

	public void setVentanaConsulta(VentanaConsulta ventanaConsulta) {
		this.ventanaConsulta=ventanaConsulta;
	}

	public void setVentanaConsultaGeneral(VentanaConsultaGeneral ventanaConsultaGeneral) {
		this.ventanaConsultaGeneral=ventanaConsultaGeneral;
	}

	public void setVentanaConsultaPersonas(VentanaConsultaPersonas ventanaConsultaPersonas) {
		this.ventanaConsultaPersonas=ventanaConsultaPersonas;
	}

	public void setProcesos(Procesos misProcesos) {
		this.misProcesos=misProcesos;
	}
	
	public void setEstudianteDao(EstudianteDAO miEstudianteDAO) {
		this.miEstudianteDAO=miEstudianteDAO;
	}

	public void mostrarVentanaPrincipal() {
		ventanaOperaciones.setVisible(true);
	}

	public double calcularPromedio(double nota1, double nota2, double nota3) {
		return misProcesos.calcularPromedio(nota1, nota2, nota3);
	}

	public double calcularPromedio(EstudianteVO estudiante) {
		
		return misProcesos.calcularPromedio(estudiante);
	}

	public String calcularDefinitiva(double promedio) {
		
		return misProcesos.calcularDefinitiva(promedio);
	}

	public String registrarEnBD(EstudianteVO estudiante)  {
		//misProcesos.registrarEnBD(estudiante);
		try {
			return miEstudianteDAO.registrarEstudiante(estudiante);
		} catch (SQLException e) {
			//e.printStackTrace();
			return "error";
		}
	}

	public void mostrarVentanaConsultaPersonas() {

		if (getListaPersonas().isEmpty()) {
			JOptionPane.showMessageDialog(null, "No hay información en la lista");
		}else {
			ventanaConsultaPersonas.llenarTabla();
			ventanaConsultaPersonas.setVisible(true);
		}
	}

	public ArrayList<EstudianteVO> getListaPersonas() {
		
		//return misProcesos.getListaPersonas();
		try {
			return miEstudianteDAO.consultarListaEstudiantes();
		} catch (SQLException e) {
			//e.printStackTrace();
			return new ArrayList<EstudianteVO>();
		}
	}

	public void mostrarConsultarEstudiante() {
		ventanaConsulta.setVisible(true);
	}

	public void mostrarVentanaConsultaGeneral() {
		ventanaConsultaGeneral.llenarAreaTexto();
		ventanaConsultaGeneral.setVisible(true);
	}

	public EstudianteVO obtenerEstudiante(String documento) {
		
		try {
			return miEstudianteDAO.consultarEstudiante(documento);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String actualizarEstudiante(EstudianteVO miEstudiante)  {
		try {
			return miEstudianteDAO.actualizaEstudiante(miEstudiante);
		} catch (SQLException e) {
			//e.printStackTrace();
			return "error"; 
		}
	}

	public String eliminarEstudiante(String documento)  {
		try {
			return miEstudianteDAO.eliminarEstudiante(documento);
		} catch (SQLException e) {
			//e.printStackTrace();
			return "error"; 
		}
	}


}
