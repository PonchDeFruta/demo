package com.pxnch.demo.controller;

import com.pxnch.demo.models.File;
import com.pxnch.demo.response.ResponseFile;
import com.pxnch.demo.response.ResponseMessage;
import com.pxnch.demo.services.FileServices;
import com.pxnch.demo.services.StatusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;



@RestController
@ControllerAdvice
@RequestMapping("/api/fileManager")
public class FileControler {
    @Autowired
    private FileServices fileServices;
    @Autowired
    private StatusServiceImpl statusService;
    int estatusServicio = 1; // 1 online, 0 offline
    String nombreServicio ="Files";
/*Manejador de excepciones*/
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("El archivo excede los 2 MB permitidos"));
    }
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleFileNotFoundExc(FileNotFoundException exc) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("El archivo por cargar no se ha encontrado o no está disponible"));
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseMessage> handleIOException(IOException exc){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Error al procesar archivo o campo vacio"));
    }

    /*Requests*/
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        ResponseEntity<ResponseMessage> respuestaStatus;

        if (estatusServicio == 1) {
            statusService.setEstatusService(1, nombreServicio);

            if (file.getContentType() == null) {
                throw new IOException();
            }
            fileServices.store(file);
            statusService.setEstatusService(2, nombreServicio);
            respuestaStatus = ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Archivo subido correctamente"));
        } else if (estatusServicio == 0) {
            statusService.setEstatusService(0, nombreServicio);
            respuestaStatus = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("El servicio no se encuentra operando"));
        } else {
            // Manejar otros casos si es necesario
            respuestaStatus = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Error desconocido"));
        }

        return respuestaStatus;
    }
    @GetMapping("/files/{id}")
    public ResponseEntity<Object> getFile(@PathVariable UUID id) throws FileNotFoundException {
        ResponseEntity<Object> respuestaStatus;

        if (estatusServicio == 1) {
            // Lógica para obtener el archivo y enviar la respuesta adecuada
            File fileEntity = fileServices.getFile(id).orElseThrow(FileNotFoundException::new);
            respuestaStatus = ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_TYPE, fileEntity.getType())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getName() + "\"")
                    .body(fileEntity.getData());
            statusService.setEstatusService(2, nombreServicio);
        } else if (estatusServicio == 0) {
            // Manejar el caso cuando el servicio no está operando
            statusService.setEstatusService(0, nombreServicio);
            respuestaStatus = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("El servicio no se encuentra operando"));
        } else {
            // Manejar otros casos si es necesario
            respuestaStatus = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("Error desconocido"));
        }

        return respuestaStatus;
    }

    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles(){
        List<ResponseFile> files = fileServices.getAllFiles();
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }


}




