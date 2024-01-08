package com.pxnch.demo.controller;

import com.pxnch.demo.config.GeneracionReporte;
import com.pxnch.demo.models.Ruta;
import com.pxnch.demo.models.Saldo;
import com.pxnch.demo.models.User;
import com.pxnch.demo.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@RestController
@RequestMapping("/generarReporte")
public class GenerarReporteController {

    @Autowired
    private Ruta ruta;

    @Autowired
    private GeneracionReporte generacionReporte;

    private ResponseMessage mensaje;

    @GetMapping
    public ResponseEntity<ResponseMessage> generarReporte() throws UnsupportedEncodingException {
        generacionReporte.generarPDF();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Archivo descargado en la siguiente ruta: C:/Users/ferpo/Documents/ReportesApis "));
    }
}





