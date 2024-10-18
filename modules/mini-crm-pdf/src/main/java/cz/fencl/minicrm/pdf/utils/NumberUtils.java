package cz.fencl.minicrm.pdf.utils;

import java.text.DecimalFormat;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NumberUtils {
	private static final String[] tensNames = { "", " deset", " dvacet", " třicet", " čtyřicet", " padesát", " šedesát",
			" sedmdesát", " osmdesát", " devadesát" };

	private static final String[] houndredNames = { "", " sto", " dvěstě", " třista", " čtyřista", " pětset",
			" šestset", " sedmset", " osmset", " devětset" };

	private static final String[] numNames = { "", " jedna", " dva", " tři", " čtyři", " pět", " šest", " sedm", " osm",
			" devět", " deset", " jedenáct", " dvanáct", " třináct", " čtrnáct", " patnáct", " šestnáct", " sedmnáct",
			" osmnáct", " devatenáct" };

	private static String convertToTextLessThanOneThousand(int number) {
		String soFar;

		if (number % 100 < 20) {
			soFar = numNames[number % 100];
			number /= 100;
		} else {
			soFar = numNames[number % 10];
			number /= 10;

			soFar = tensNames[number % 10] + soFar;
			number /= 10;
		}
		if (number == 0) {
			return soFar;
		}
		return houndredNames[number] + soFar;
	}

	public static String convertToText(long number) {
		// 0 to 999 999 999 999
		if (number == 0) {
			return "nula";
		}

		String snumber = Long.toString(number);

		// pad with "0"
		String mask = "000000000000";
		DecimalFormat df = new DecimalFormat(mask);
		snumber = df.format(number);

		// XXXnnnnnnnnn
		int billions = Integer.parseInt(snumber.substring(0, 3));
		// nnnXXXnnnnnn
		int millions = Integer.parseInt(snumber.substring(3, 6));
		// nnnnnnXXXnnn
		int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
		// nnnnnnnnnXXX
		int thousands = Integer.parseInt(snumber.substring(9, 12));

		String tradBillions;
		switch (billions) {
		case 0:
			tradBillions = "";
			break;
		case 1:
			tradBillions = convertToTextLessThanOneThousand(billions) + " miliarda ";
			break;
		case 2:
		case 3:
		case 4:
			tradBillions = convertToTextLessThanOneThousand(billions) + " miliardy ";
			break;
		default:
			tradBillions = convertToTextLessThanOneThousand(billions) + " miliard ";
		}
		String result = tradBillions;

		String tradMillions;
		switch (millions) {
		case 0:
			tradMillions = "";
			break;
		case 1:
			tradMillions = " milión ";
			break;
		case 2:
		case 3:
		case 4:
			tradMillions = convertToTextLessThanOneThousand(millions) + " miliony ";
			break;
		default:
			tradMillions = convertToTextLessThanOneThousand(millions) + " miliónů ";
		}
		result = result + tradMillions;

		String tradHundredThousands;
		switch (hundredThousands) {
		case 0:
			tradHundredThousands = "";
			break;
		case 1:
			tradHundredThousands = (millions > 0 ? "jeden" : "") + " tisíc ";
			break;
		case 2:
		case 3:
		case 4:
			tradHundredThousands = convertToTextLessThanOneThousand(hundredThousands) + " tisíce ";
			break;
		default:
			tradHundredThousands = convertToTextLessThanOneThousand(hundredThousands) + " tisíc ";
		}
		result = result + tradHundredThousands;

		String tradThousand;
		tradThousand = convertToTextLessThanOneThousand(thousands);
		result = result + tradThousand;

		// remove extra spaces!
		return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
	}

	public static void main(String[] args) {
		System.out.println("*** " + NumberUtils.convertToText(0));
		System.out.println("*** " + NumberUtils.convertToText(1));
		System.out.println("*** " + NumberUtils.convertToText(16));
		System.out.println("*** " + NumberUtils.convertToText(100));
		System.out.println("*** " + NumberUtils.convertToText(118));
		System.out.println("*** " + NumberUtils.convertToText(200));
		System.out.println("*** " + NumberUtils.convertToText(219));
		System.out.println("*** " + NumberUtils.convertToText(800));
		System.out.println("*** " + NumberUtils.convertToText(801));
		System.out.println("*** " + NumberUtils.convertToText(1316));
		System.out.println("*** " + NumberUtils.convertToText(2316));
		System.out.println("*** " + NumberUtils.convertToText(3316));
		System.out.println("*** " + NumberUtils.convertToText(4316));
		System.out.println("*** " + NumberUtils.convertToText(5316));
		System.out.println("*** " + NumberUtils.convertToText(21316));
		System.out.println("*** " + NumberUtils.convertToText(22316));
		System.out.println("*** " + NumberUtils.convertToText(23316));
		System.out.println("*** " + NumberUtils.convertToText(1000000));
		System.out.println("*** " + NumberUtils.convertToText(2000000));
		System.out.println("*** " + NumberUtils.convertToText(3000200));
		System.out.println("*** " + NumberUtils.convertToText(700000));
		System.out.println("*** " + NumberUtils.convertToText(9000000));
		System.out.println("*** " + NumberUtils.convertToText(9001000));
		System.out.println("*** " + NumberUtils.convertToText(123456789));
		System.out.println("*** " + NumberUtils.convertToText(2147483647));
		System.out.println("*** " + NumberUtils.convertToText(3000000010L));
	}
}
