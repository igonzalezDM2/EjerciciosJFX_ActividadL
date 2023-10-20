package dao;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import enums.TipoAeropuerto;
import excepciones.AeropuertosException;
import model.Aeropuerto;
import model.Direccion;
import utilities.Utilidades;

public class DAOAeropuertos extends DAOBase{
	
	public static List<Aeropuerto> getAeropuertos(TipoAeropuerto tipo, String busqueda) throws AeropuertosException {
		try(Connection con = getConexion()) {
			StringBuilder sb = new StringBuilder("select aeropuertos.id as id, "
					+ "aeropuertos.nombre as nombre, "
					+ "direcciones.pais as pais, "
					+ "direcciones.ciudad as ciudad, "
					+ "direcciones.id as direccionid, "
					+ "direcciones.calle as calle, "
					+ "direcciones.numero as numero, "
					+ "aeropuertos.anio_inauguracion as ano, "
					+ "aeropuertos.capacidad as capacidad, "
					+ "aeropuertos_publicos.id_aeropuerto as idpublico, "
					+ "aeropuertos_publicos.financiacion as financiacion, "
					+ "aeropuertos_publicos.num_trabajadores as numtrabajadores, "
					+ "aeropuertos_privados.id_aeropuerto as idprivado, "
					+ "aeropuertos_privados.numero_socios as numerosocios "
					+ "from aeropuertos\n"
					+ "inner join direcciones on direcciones.id = aeropuertos.id_direccion\n");
			
				sb.append("left join aeropuertos_publicos on aeropuertos.id = aeropuertos_publicos.id_aeropuerto\n");
				sb.append("left join aeropuertos_privados on aeropuertos.id = aeropuertos_privados.id_aeropuerto");
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sb.toString());
			
			List<Aeropuerto> aeropuertos = new LinkedList<>(); 
			
			while(rs.next()) {
				aeropuertos.add(Utilidades.mapAeropuerto(rs));
			}
			
			if (tipo != null) {
				//El campo nombre es NOT NULL, así que no hay por qué comprobar su nulidad
				return aeropuertos.stream().filter(ae -> tipo.equals(ae.getTipo()) && ae.getNombre().toLowerCase().contains(busqueda != null ? busqueda : "")).toList();
			}
			
			return aeropuertos;
			
		} catch (SQLException e) {
			throw new AeropuertosException(e);
		}
		
	}
	
	public static List<Aeropuerto> getAeropuertos(TipoAeropuerto tipo) throws AeropuertosException {
		return getAeropuertos(tipo, null);
	}
	
	public static void anadirAeropuerto(Aeropuerto aeropuerto) throws AeropuertosException {
		if (aeropuerto != null && aeropuerto.getDireccion() != null) {
			TipoAeropuerto tipo = aeropuerto.getTipo();
			
			String sqlDireccion = "INSERT INTO direcciones (pais, ciudad, calle, numero) VALUES (?, ?, ?, ?)";
			String sqlAeropuerto = "INSERT INTO aeropuertos(nombre, anio_inauguracion, capacidad, id_direccion, imagen) values (?, ?, ?, ?, ?)";
			String sqlAeropuertoPrivado = "INSERT INTO aeropuertos_privados (id_aeropuerto, numero_socios) values (?, ?)";
			String sqlAeropuertoPublico = "INSERT INTO aeropuertos_publicos (id_aeropuerto, financiacion, num_trabajadores) values (?, ?, ?)";
			
			int idDireccion = 0;
			int idAeropuerto = 0;
			try (Connection con = getConexion()) {
				Direccion direccion = aeropuerto.getDireccion();
				
				//PRIMERO SE INSERTA LA DIRECCIÓN Y SE OBTIENE SU ID GENERADO
				try (PreparedStatement psDireccion = con.prepareStatement(sqlDireccion, PreparedStatement.RETURN_GENERATED_KEYS)) {
					psDireccion.setString(1, direccion.getPais());
					psDireccion.setString(2, direccion.getCiudad());
					psDireccion.setString(3, direccion.getCalle());
					psDireccion.setInt(4, direccion.getNumero());
					
					psDireccion.executeUpdate();
					ResultSet rs = psDireccion.getGeneratedKeys();
					if (rs.next()) {
						idDireccion = rs.getInt(1);
					}
				}
				
				//SI LA DIRECCIÓN HA GENERADO UN ID, SE INSERTA EL AEROPUERTO
				if (idDireccion > 0) {
					try (PreparedStatement psAeropuerto = con.prepareStatement(sqlAeropuerto, PreparedStatement.RETURN_GENERATED_KEYS)) {
						psAeropuerto.setString(1, aeropuerto.getNombre());
						psAeropuerto.setInt(2, aeropuerto.getAnioInauguracion());
						psAeropuerto.setInt(3, aeropuerto.getCapacidad());
						psAeropuerto.setInt(4, idDireccion);
						if (aeropuerto.getImagen() != null) {							
							psAeropuerto.setBlob(5, new ByteArrayInputStream(aeropuerto.getImagen()));
						} else {
							psAeropuerto.setBlob(5, (Blob) null);
						}
						psAeropuerto.executeUpdate();
						
						ResultSet rs = psAeropuerto.getGeneratedKeys();
						if (rs.next()) {
							idAeropuerto = rs.getInt(1);
						}
					}
				} else {
					throw new AeropuertosException("No se pudo insertar la dirección");
				}
				//SI HA CREADO EL AEROPUERTO, SE CREA AEROPUERTO PÚBLICO O PRIVADO (O NINGUNO)
				if (idAeropuerto > 0) {
					if (TipoAeropuerto.PUBLICO.equals(tipo)) {
						try (PreparedStatement ps = con.prepareStatement(sqlAeropuertoPublico, PreparedStatement.RETURN_GENERATED_KEYS)) {
							ps.setInt(1, idAeropuerto);
							ps.setDouble(2, aeropuerto.getFinanciacion());
							ps.setInt(3, aeropuerto.getNumTrabajadores());
							ps.executeUpdate();
						}
					} else if (TipoAeropuerto.PRIVADO.equals(tipo)) {
						try (PreparedStatement ps = con.prepareStatement(sqlAeropuertoPrivado, PreparedStatement.RETURN_GENERATED_KEYS)) {
							ps.setInt(1, idAeropuerto);
							ps.setInt(2, aeropuerto.getNumeroSocios());
							ps.executeUpdate();
						}						
					}
				} else {
					throw new AeropuertosException("No se pudo insertar el aeropuerto");					
				}
				
			} catch (SQLException e) {
				throw new AeropuertosException(e);
			}
			
			//INSERTAR LOS IDS GENERADOS EN EL OBJETO PASADO COMO ARGUMENTO
			aeropuerto.getDireccion().setId(idDireccion);
			aeropuerto.setId(idAeropuerto);
			
		} else {			
			throw new AeropuertosException("Los datos introducidos están incompletos");
		}
	}
	
	
}
