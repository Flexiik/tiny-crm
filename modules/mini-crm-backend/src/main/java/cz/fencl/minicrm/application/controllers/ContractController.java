package cz.fencl.minicrm.application.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cz.fencl.minicrm.application.model.ContractDto;
import cz.fencl.minicrm.db.persistence.model.Contract;
import cz.fencl.minicrm.db.persistence.repositories.ContractRepository;
import cz.fencl.minicrm.pdf.services.PdfService;
import lombok.SneakyThrows;

@RestController
@RequestMapping(value = "/contract", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContractController {
	private Gson gson = new Gson();

	@Autowired
	private ContractRepository repository;

	@Autowired
	private PdfService pdfService;

	// Get All
	@GetMapping
	public List<ContractDto> getContracts() {
		List<ContractDto> contracts = new ArrayList<>();
		repository.findAll().forEach(c -> contracts.add(toDto(c)));
		return contracts;
	}

	@GetMapping("/{id}")
	public ContractDto getContract(@PathVariable Long id) {
		return toDto(repository.findById(id).orElse(null));
	}

	@SneakyThrows
	@GetMapping(value = "/{id}/sod", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> generateSodPdf(@PathVariable Long id) {
		Contract contract = repository.findById(id).orElse(null);
		if (contract == null) {
			return null; // TODO
		}
		String pdfName = pdfService.generateSodPdf(contract);
		Path path = Paths.get(pdfName);
		byte[] data = Files.readAllBytes(path);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		// Here you have to set the actual filename of your pdf
		headers.setContentDispositionFormData(pdfName, pdfName);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<>(data, headers, HttpStatus.OK);
		return response;
	}

	@SneakyThrows
	@GetMapping(value = "/{id}/ssv", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> generateSsvPdf(@PathVariable Long id) {
		Contract contract = repository.findById(id).orElse(null);
		if (contract == null) {
			return null; // TODO
		}
		String pdfName = pdfService.generateSsvPdf(contract);
		Path path = Paths.get(pdfName);
		byte[] data = Files.readAllBytes(path);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		// Here you have to set the actual filename of your pdf
		headers.setContentDispositionFormData(pdfName, pdfName);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<>(data, headers, HttpStatus.OK);
		return response;
	}

	private ContractDto toDto(Contract contract) {
		return gson.fromJson(gson.toJson(contract), ContractDto.class);
	}
}
