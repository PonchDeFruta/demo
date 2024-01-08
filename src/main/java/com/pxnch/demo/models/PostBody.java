package com.pxnch.demo.models;


import org.springframework.web.multipart.MultipartFile;

public class PostBody {
	
	private MultipartFile archivo = null;
	private int idWebService = 0;
	//String destino = "";
	//String fuente = "";

	@Override
	public String toString() {
		return "PostBody [archivo=" + archivo + ", idWebService=" + idWebService + "]";
	}
	public MultipartFile getArchivo() {
		return archivo;
	}
	public void setArchivo(MultipartFile archivo) {
		this.archivo = archivo;
	}
	public int getIdWebService() {
		return idWebService;
	}
	public void setIdWebService(int idWebService) {
		this.idWebService = idWebService;
	}
	
	
	
}
