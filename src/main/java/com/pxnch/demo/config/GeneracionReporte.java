package com.pxnch.demo.config;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pxnch.demo.models.Status;
import com.pxnch.demo.repository.StatusRepository;
import com.pxnch.demo.services.StatusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.List;

@Component
public class GeneracionReporte {

    @Autowired
    private StatusServiceImpl statusServiceImpl;
    @Autowired
    private StatusRepository statusRepository;
    private String rutaArchivo = "C:/Users/ferpo/Documents/ReportesApis";


// Resto del código...


    public void generarPDF() {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
            document.open();

            // Agregar título al PDF
            Paragraph titulo = new Paragraph("Informe de Status");
            document.add(titulo);

            // Obtener la lista de Status desde la base de datos
            List<Status> statusList = statusServiceImpl.getAllStatus();

            // Crear tabla con encabezados
            PdfPTable table = new PdfPTable(5);
            table.addCell("ID");
            table.addCell("Status");
            table.addCell("Status Name");
            table.addCell("Status Description");
            table.addCell("Timestamp");

            // Llenar la tabla con datos de la base de datos
            for (Status status : statusList) {
                table.addCell(status.getIdStatus().toString());
                table.addCell(String.valueOf(status.getStatus()));
                table.addCell(status.getStatusName());
                table.addCell(status.getStatusDescription());
                table.addCell(status.getTimeStmap().toString());
            }

            // Agregar la tabla al PDF
            document.add(table);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
