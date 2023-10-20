package utilities;

import java.sql.ResultSet;
import java.sql.SQLException;

import enums.TipoAeropuerto;
import excepciones.AeropuertosException;
import model.Aeropuerto;
import model.Direccion;

public class Utilidades {
	
	private Utilidades() throws IllegalAccessException {
		throw new IllegalAccessException("Clase de utilidad");
	}
	
	public static Aeropuerto mapAeropuerto(ResultSet rs) throws AeropuertosException {
		try {
			Aeropuerto aeropuerto = new Aeropuerto()
					.setId(rs.getInt("id"))
					.setNombre(rs.getString("nombre"))
					.setAnioInauguracion(rs.getInt("ano"))
					.setCapacidad(rs.getInt("capacidad"))
					.setFinanciacion(rs.getDouble("financiacion"))
					.setNumTrabajadores(rs.getInt("numtrabajadores"))
					.setNumeroSocios(rs.getInt("numerosocios"))
					.setDireccion(new Direccion()
							.setCalle(rs.getString("calle"))
							.setCiudad(rs.getString("ciudad"))
							.setId(rs.getInt("direccionid"))
							.setNumero(rs.getInt("numero"))
							.setPais(rs.getString("pais"))
							);
			if (rs.getInt("idpublico") > 0) {
				aeropuerto.setTipo(TipoAeropuerto.PUBLICO);
			} else if (rs.getInt("idprivado") > 0) {
				aeropuerto.setTipo(TipoAeropuerto.PRIVADO);
			}
			
			return aeropuerto;
			
		} catch (SQLException e) {
			throw new AeropuertosException(e);
		}
	}
	
	public static double parseDouble(String str) throws AeropuertosException {
		if (str != null && !str.isBlank()) {
			try {
				return Double.parseDouble(str);
			} catch (NumberFormatException e) {/*QUE SALTE A LA EXCEPCIÓN*/}
		}
		throw new AeropuertosException("Formato de número decimal incorrecto");
	}
	
	public static int parseInt(String str) throws AeropuertosException {
		if (str != null && !str.isBlank()) {
			try {
				return Integer.parseInt(str);
			} catch (NumberFormatException e) {/*QUE SALTE A LA EXCEPCIÓN*/}
		}
		throw new AeropuertosException("Formato de número entero incorrecto");
	}
	
}
