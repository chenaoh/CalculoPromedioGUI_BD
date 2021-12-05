package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controlador.Coordinador;
import modelo.conexion.Conexion;
import modelo.vo.EstudianteVO;

public class EstudianteDAO{
	private Coordinador miCoordinador;

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}
	
	Connection connection = null;
	Conexion conexion = null;
	PreparedStatement preStatement = null;
	
	private String conectar() {
		conexion = new Conexion();
		String resultado=conexion.conectar();
		if (resultado.equals("conectado")) {
			connection = conexion.getConnection();
			preStatement = null;
		}else {
			JOptionPane.showMessageDialog(null, resultado,"Error",JOptionPane.ERROR_MESSAGE);
		}
		return resultado;
	}

	public String registrarEstudiante(EstudianteVO miEstudiante) throws SQLException {
		
		String resultado = "";
		
		if (!conectar().equals("conectado")) {
			return "error";
		}

		String consulta = "INSERT INTO estudiante (documento_est,nombre_est,nota1,nota2,nota3,promedio)"
				+ "  VALUES (?,?,?,?,?,?)";

		try {
			preStatement = connection.prepareStatement(consulta);
			preStatement.setString(1, miEstudiante.getDocumento());
			preStatement.setString(2, miEstudiante.getNombre());
			preStatement.setDouble(3,miEstudiante.getNota1());
			preStatement.setDouble(4,miEstudiante.getNota2());
			preStatement.setDouble(5,miEstudiante.getNota3());
			preStatement.setDouble(6,miEstudiante.getPromedio());
			preStatement.execute();

			resultado = "ok";
			
			
		}catch (SQLException e) {
			System.out.println("No se pudo registrar el estudiante, verifique que el documento no exista: " + e.getMessage());
			//e.printStackTrace();
			resultado = "error";
		}
		catch (Exception e) {
			System.out.println("No se pudo registrar el estudiante: " + e.getMessage());
			//e.printStackTrace();
			resultado = "error";
		}
		finally {
			preStatement.close();
			connection.close();
			conexion.desconectar();
		}
		return resultado;
	}
	

	public EstudianteVO consultarEstudiante(String idDocumento) throws SQLException {
		EstudianteVO miEstudiante=null;
		
		if (!conectar().equals("conectado")) {
			return miEstudiante;
		}
		
		ResultSet result=null;
		
		

		String consulta="SELECT documento_est,nombre_est,nota1,nota2,nota3,promedio"
				+ " FROM estudiante where documento_est= ? ";
		
		try {
			preStatement=connection.prepareStatement(consulta);
			preStatement.setString(1, idDocumento);
				
			result=preStatement.executeQuery();
				
			if(result.next()){
				miEstudiante=new EstudianteVO();
				miEstudiante.setDocumento(result.getString("documento_est"));
				miEstudiante.setNombre(result.getString("nombre_est"));
				miEstudiante.setNota1(result.getDouble("nota1"));
				miEstudiante.setNota2(result.getDouble("nota2"));
				miEstudiante.setNota3(result.getDouble("nota3"));
				miEstudiante.setPromedio(result.getDouble("promedio"));
			}		
			   
		} catch (SQLException e) {
			System.out.println("Error en la consulta de la persona: "+e.getMessage());
		}finally {
			result.close();
			preStatement.close();
			connection.close();
			conexion.desconectar();
		}
		return miEstudiante;
	}
	
	public ArrayList<EstudianteVO> consultarListaEstudiantes() throws SQLException {
		ArrayList<EstudianteVO> listaEstudiantes=new ArrayList<EstudianteVO>();
		
		if (!conectar().equals("conectado")) {
			return listaEstudiantes;
		}
		
		ResultSet result=null;
		
		EstudianteVO miEstudiante=null;

		
		String consulta="SELECT documento_est,nombre_est,nota1,nota2,nota3,promedio"
				+ " FROM estudiante ";
		
		try {
			preStatement=connection.prepareStatement(consulta);
				
			result=preStatement.executeQuery();
				
			while(result.next()==true){
				miEstudiante=new EstudianteVO();
				miEstudiante.setDocumento(result.getString("documento_est"));
				miEstudiante.setNombre(result.getString("nombre_est"));
				miEstudiante.setNota1(result.getDouble("nota1"));
				miEstudiante.setNota2(result.getDouble("nota2"));
				miEstudiante.setNota3(result.getDouble("nota3"));
				miEstudiante.setPromedio(result.getDouble("promedio"));
				listaEstudiantes.add(miEstudiante);
			}		
			   
		} catch (SQLException e) {
			System.out.println("Error en la consulta de personas: "+e.getMessage());
		}finally {
			result.close();
			preStatement.close();
			connection.close();
			conexion.desconectar();
		}
			return listaEstudiantes;
	}
	
	
	public String actualizaEstudiante(EstudianteVO estudianteVo) throws SQLException {
		String resultado="";

		if (!conectar().equals("conectado")) {
			return "error";
		}

		try{
			String consulta="UPDATE estudiante "
					+ "SET nombre_est = ? , "
					+ "nota1= ? , "
					+ "nota2= ? , "
					+ "nota3= ? , "
					+ "promedio= ?  "
					+ "WHERE documento_est= ?;";
			preStatement = connection.prepareStatement(consulta);

			preStatement.setString(1,estudianteVo.getNombre());
			preStatement.setDouble(2, estudianteVo.getNota1());
			preStatement.setDouble(3, estudianteVo.getNota2());
			preStatement.setDouble(4, estudianteVo.getNota3());
			preStatement.setDouble(5, estudianteVo.getPromedio());

			preStatement.setString(6, estudianteVo.getDocumento());
			preStatement.executeUpdate();
			
          resultado="ok";
          
        }catch(SQLException	 e){
            System.out.println("Ocurrió una excepcion de SQL "
            		+ "al momento de actualizar: "+e);
            resultado="error";
        }finally {
			preStatement.close();
			connection.close();
			conexion.desconectar();
		}
		return resultado;
	}

	public String eliminarEstudiante(String documento) throws SQLException {
		
		if (!conectar().equals("conectado")) {
			return "error";
		}
		
		String resp="";
		try {
			String sentencia="DELETE FROM estudiante WHERE documento_est= ? ";

			PreparedStatement statement = connection.prepareStatement(sentencia);
			statement.setString(1, documento);
			
			statement.executeUpdate();
						
			resp="ok";
			
		} catch (SQLException e) {
            System.out.println("Ocurrió una excepcion de SQL "
            		+ "al momento de eliminar "+e);
			resp="error";
		}finally {
			preStatement.close();
			connection.close();
			conexion.desconectar();
		}
		return resp;
	}

}
