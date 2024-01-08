package com.pxnch.demo.models;


import org.springframework.stereotype.Service;

@Service
public class ResponseBody {

	private String mensaje ;
	private String estatus ;
	private String exepcion ;
	
	
	@Override
	public String toString() {
		return "ResponseBody [mensaje=" + mensaje + ", estatus=" + estatus + ", exepcion=" + exepcion + "]";
	}
	
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public void setExepcion(String exepcion) {
		this.exepcion = exepcion;
	}
	
	
}
