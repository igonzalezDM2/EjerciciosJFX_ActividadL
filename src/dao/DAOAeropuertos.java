package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import enums.TipoAeropuerto;
import excepciones.AeropuertosException;
import model.Aeropuerto;
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
	
}
