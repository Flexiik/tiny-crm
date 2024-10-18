package cz.fencl.minicrm.application.controllers.test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.fencl.minicrm.pdf.services.PdfService;
import lombok.SneakyThrows;

@RestController
@RequestMapping("/test")
public class PdfController {
    @Autowired
    PdfService service;


    @SneakyThrows
    @GetMapping(value="/pdf")
    public ResponseEntity<byte[]> getPDF() {
        Path path = Paths.get("Sample1.pdf");
        byte[] data = Files.readAllBytes(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Here you have to set the actual filename of your pdf
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(data, headers, HttpStatus.OK);
        return response;
    }
}
