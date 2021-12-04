package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controlador.Coordinador;
import modelo.conexion.Conexion;
import modelo.vo.EstudianteVO;

public class EstudianteDAO{
	private Coordinador miCoordinador;

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}

	public String registrarEstudiante(EstudianteVO miEstudiante) {
		
		String resultado = "";

		Connection connection = null;
		Conexion conexion = new Conexion();
		PreparedStatement preStatement = null;

		connection = conexion.getConnection();
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
			conexion.desconectar();
		}

		return resultado;
		
	}
	
	public EstudianteVO consultarEstudiante(String idDocumento) {
		Connection connection=null;
		Conexion miConexion=new Conexion();
		PreparedStatement statement=null;
		ResultSet result=null;
		
		EstudianteVO miEstudiante=null;
		
		connection=miConexion.getConnection();
		
		String consulta="SELECT documento_est,nombre_est,nota1,nota2,nota3,promedio"
				+ " FROM estudiante where documento_est= ? ";
		
		try {
			if (connection!=null) {
				
				statement=connection.prepareStatement(consulta);
				statement.setString(1, idDocumento);
				
				result=statement.executeQuery();
				
				while(result.next()==true){
					miEstudiante=new EstudianteVO();
					miEstudiante.setDocumento(result.getString("documento_est"));
					miEstudiante.setNombre(result.getString("nombre_est"));
					miEstudiante.setNota1(result.getDouble("nota1"));
					miEstudiante.setNota2(result.getDouble("nota2"));
					miEstudiante.setNota3(result.getDouble("nota3"));
					miEstudiante.setPromedio(result.getDouble("promedio"));
				}		
				   miConexion.desconectar();
			}else{
				miEstudiante=null;
			}			   
		} catch (SQLException e) {
			System.out.println("Error en la consulta de la persona: "+e.getMessage());
		}
			return miEstudiante;
	}
	
	public ArrayList<EstudianteVO> consultarListaEstudiantes() {
		Connection connection=null;
		Conexion miConexion=new Conexion();
		PreparedStatement statement=null;
		ResultSet result=null;
		
		EstudianteVO miEstudiante=null;
		ArrayList<EstudianteVO> listaEstudiantes=new ArrayList<EstudianteVO>();
		
		connection=miConexion.getConnection();
		
		String consulta="SELECT documento_est,nombre_est,nota1,nota2,nota3,promedio"
				+ " FROM estudiante ";
		
		try {
			if (connection!=null) {
				
				statement=connection.prepareStatement(consulta);
				
				result=statement.executeQuery();
				
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
				   miConexion.desconectar();
			}			   
		} catch (SQLException e) {
			System.out.println("Error en la consulta de personas: "+e.getMessage());
		}
			return listaEstudiantes;
	}
	
	
	public String actualizaEstudiante(EstudianteVO estudianteVo) {
		String resultado="";
		Connection connection=null;
		Conexion miConexion=new Conexion();
		connection=miConexion.getConnection();
		try{
			String consulta="UPDATE estudiante "
					+ "SET nombre_est = ? , "
					+ "nota1= ? , "
					+ "nota2= ? , "
					+ "nota3= ? , "
					+ "promedio= ?  "
					+ "WHERE documento_est= ?;";
			PreparedStatement preStatement = connection.prepareStatement(consulta);

			preStatement.setString(1,estudianteVo.getNombre());
			preStatement.setDouble(2, estudianteVo.getNota1());
			preStatement.setDouble(3, estudianteVo.getNota2());
			preStatement.setDouble(4, estudianteVo.getNota3());
			preStatement.setDouble(5, estudianteVo.getPromedio());

			preStatement.setString(6, estudianteVo.getDocumento());
			preStatement.executeUpdate();
			
          resultado="ok";
          
          miConexion.desconectar();

        }catch(SQLException	 e){
            System.out.println("Ocurrió una excepcion de SQL "
            		+ "al momento de actualizar: "+e);
            resultado="error";
        }
		catch(Exception	e){
            System.out.println("Ocurrió una excepcion al "
            		+ "momento de actualizar: "+e);
            resultado="error";
        }
		return resultado;
	}

	public String eliminarEstudiante(String documento) {
		Connection connection=null;
		Conexion miConexion=new Conexion();
		connection=miConexion.getConnection();
		
		String resp="";
		try {
			String sentencia="DELETE FROM estudiante WHERE documento_est= ? ";

			PreparedStatement statement = connection.prepareStatement(sentencia);
			statement.setString(1, documento);
			
			statement.executeUpdate();
						
			resp="ok";
            statement.close();
            miConexion.desconectar();
			
		} catch (SQLException e) {
            System.out.println(e.getMessage());
			resp="error";
		}
		return resp;
	}

}
