package cz.fencl.minicrm.pdf.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import cz.fencl.minicrm.db.persistence.model.Contract;
import cz.fencl.minicrm.db.testdata.TestingDataHolder;
import cz.fencl.minicrm.pdf.utils.NumberUtils;
import lombok.SneakyThrows;

@Service
public class PdfService {

	private static final String ADDRESS_PATTERN = "%s %s, %s %s";
	private static final Gson gson = new Gson();

	@Autowired
	private TestingDataHolder holder;

	@Autowired
	private PdfGenerator generator;

//	@PostConstruct
//	public void testInit() {
//		generateSsvPdf(holder.getContract());
//		generateSodPdf(holder.getContract());
//		generatePmPdf(holder.getContract());
//	}

	public String generatePmPdf(Contract contract) {
		List<String> template = readResources("templates/PM.txt");
		template = fillContractData(contract, template);
		String pdfName = "PM-" + contract.getNumber() + ".pdf";
		generator.generatePdf(pdfName, template);
		return pdfName;
	}

	public String generateSodPdf(Contract contract) {
		List<String> template = readResources("templates/SOD.txt");
		template = fillContractData(contract, template);
		String pdfName = "SOD-" + contract.getNumber() + ".pdf";
		generator.generatePdf(pdfName, template);
		return pdfName;
	}

	public String generateSsvPdf(Contract contract) {
		List<String> template = readResources("templates/SSV.txt");
		template = fillContractData(contract, template);
		String pdfName = "SSV-" + contract.getNumber() + ".pdf";
		generator.generatePdf(pdfName, template);
		return pdfName;
	}

	@SneakyThrows
	private List<String> readResources(String resource) {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				getClass().getClassLoader().getResourceAsStream(resource), Charset.forName("UTF-8")));
		List<String> ret = new ArrayList<>();
		for (String line; (line = br.readLine()) != null; ret.add(line))
			;
		return ret;
	}

	private List<String> fillContractData(Contract contract, List<String> input) {
		String connected = String.join("\n", input);
		connected = fillData(contract, connected, "contract");
		connected = connected.replaceAll("<contract.price.words>", NumberUtils.convertToText(contract.getPrice()));
		connected = connected.replaceAll("<contract.address>",String.format(ADDRESS_PATTERN, contract.getAddressStreet(), contract.getAddressNumber(), contract.getAddressPin(), contract.getAddressCity()));
		connected = fillData(contract.getCustomer(), connected, "customer");
		connected = fillData(contract.getEmployee(), connected, "employee");
		connected = fillData(contract.getEmployee().getCompany(), connected, "company");
		connected = cleanup(connected);
		return Arrays.asList(connected.split("\n"));
	}

	private String cleanup(String input) {
		return input.replaceAll("	", "   ");
	}

	private String fillData(Object o, String input, String entityName) {
		String output = input;
		for (Entry<String, Object> e : toMap(o).entrySet()) {
			Object value = e.getValue();
			if (value instanceof Double && ((Double) value) % 1 < 0.01) {
				value = ((Double) value).longValue();
			}
			output = output.replaceAll("<" + entityName + "." + e.getKey() + ">", "" + value);
		}
		return output;
	}

	private Map<String, Object> toMap(Object o) {
		return gson.fromJson(gson.toJson(o), Map.class);
	}
}
