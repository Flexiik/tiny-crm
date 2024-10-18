package cz.fencl.minicrm.pdf.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;

@Component
public class PdfGenerator {
	private static final float defaultFontSize = 11;
	private static final float xOffset = 50;
	private static final float itemOffset = 25;
	private static PDType0Font font;
	private static PDType0Font fontItalic;
	private static PDType0Font fontBold;
	private static PDType0Font fontItalicBold;
	private static double fontHeight;
	private static double a4Height;
	private static double header;
	private static double footer;
	private static double pageWidth;
	private static final List<String> GLUERS = Arrays.asList("o", "od", "z", "za", "ze", "bez", "k", "ku", "u", "v",
			"po", "pod", "při", "na", "nad", "s", "se", "a", "an", "the", "i");
	private static final List<String> TAGS = Arrays.asList("<bold>", "<center>", "<block>", "<item>", "<list-item>",
			"<italic>", "<indent>");

	static {
		font = null;
		try (PDDocument doc = new PDDocument()) {
			font = PDType0Font.load(doc, PdfGenerator.class.getClassLoader().getResourceAsStream("font/times.ttf"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		fontHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * defaultFontSize;
		a4Height = PDRectangle.A4.getHeight();
		header = 0.15 * a4Height;
		footer = 0.15 * a4Height;
		pageWidth = PDRectangle.A4.getWidth() - xOffset - 10;
	}

	@SneakyThrows
	public void generatePdf(String pdfName, List<String> data) {
		PDDocument doc = new PDDocument();
		PDPageContentStream cont = newPage(doc);
		double position = header;
		Metadata metadata = new Metadata(pageWidth);
		for (String line : data) {
			if(line.contains("<font")) {
				int fontSize = Integer.parseInt(line.substring(line.indexOf("<font") + "<font".length(), line.indexOf(">", line.indexOf("<font"))));
				metadata.setFontSize(fontSize);
				cont.setFont(font, fontSize);
			}
			
			if (line.startsWith("<block")) {
				if (line.startsWith("<block-")) {
					int blockLength = Integer.parseInt(line.substring("<block-".length(), line.indexOf(">")));
					if (position + fontHeight * blockLength + footer > a4Height) {
						cont.endText();
						cont.close();
						cont = newPage(doc);
						position = header;
					}
				}
				writeBlockLine(cont, line, metadata);
				cont.newLine();
				cleanMetadataAfterLine(metadata);
				continue;
			}

			float lineOffset = resolveIndents(line, metadata);
			line = line.replaceAll("<indent>", "");// indent was taken care of in resolveIndents, clean them
			List<String> subLines = splitLine(line, lineOffset, metadata);
			for (String subLine : subLines) {
				// Pokud je item na poslednim radku stranky tak ho dame na dalsi stranku
				if ((subLine.startsWith("<item>") && position + fontHeight + footer > a4Height)
						|| position + footer > a4Height) {
					cont.endText();
					cont.close();
					cont = newPage(doc);
					position = header;
				}
				writeLine(cont, subLine, metadata);
				cont.newLine();
				metadata.setXMove(0f);
				position += fontHeight;
			}
			cleanMetadataAfterLine(metadata);
			cont.setFont(font, metadata.getFontSize());
		}

		cont.endText();
		cont.close();
		doc.save(pdfName);
		doc.close();
	}

	private void writeBlockLine(PDPageContentStream cont, String line, Metadata metadata) {
		String[] blocks = line.substring(line.indexOf(">") + 1).split("<block>");
		metadata.setWidth(pageWidth / blocks.length);
		for (int i = 0; i < blocks.length; i++) {
			if (!blocks[i].replaceAll(" ", "").isEmpty()) {
				metadata.setXMove((float) (i * pageWidth / blocks.length));
				writeLine(cont, blocks[i], metadata);
			}
		}
		metadata.setWidth(pageWidth);
	}

	private float resolveIndents(String line, Metadata metadata) {
		float offset = 0;
		if (line.contains("<item>")) {
			String innerString = line.substring("<item".length(), line.indexOf(">"));
			int innerIndent = innerString.isEmpty() ? 1 : Integer.parseInt(innerString);
			offset += innerIndent * xOffset;
		}

		int indent = metadata.getIndent();
		while (line.startsWith("<indent>")) {
			indent += 1;
			metadata.setIndent(indent);
			line = line.substring("<indent>".length());
		}
		return offset + indent * xOffset;
	}

	private void cleanMetadataAfterLine(Metadata metadata) {
		metadata.setBold(false);
		metadata.setItalic(false);
		metadata.setIndent(0);
		metadata.setXMove(0f);
		metadata.setXMoveCumul(0f);
		metadata.setFontSize((int) defaultFontSize);
	}

	@SneakyThrows
	private void writeLine(PDPageContentStream cont, String line, Metadata metadata) {
		String rawText = getRawText(line);
		if (line.contains("<center>")) {
			float rawWidth = getStringWidth(rawText, font, metadata);
			metadata.setXMove(metadata.getXMove() + (float) ((metadata.getWidth() - rawWidth) / 2));
		} else if (metadata.getIndent() > 0) {
			metadata.setXMove(metadata.getXMove() + itemOffset * metadata.getIndent());
		}

		if (metadata.getXMove() > 0) {
			cont.newLineAtOffset(metadata.getXMove(), 0);
		}

		if (line.startsWith("<item")) {
			String innerString = line.substring("<item".length(), line.indexOf(">"));
			int innerIndent = innerString.isEmpty() ? 1 : Integer.parseInt(innerString);
			metadata.setIndent(metadata.getIndent() + innerIndent);
			String itemText = line.split(" ")[0].substring(line.indexOf(">") + 1);
			writeWithFont(cont, itemText, metadata);
			cont.newLineAtOffset(-metadata.getXMove(), 0); //musime vzdycky zacinat na zacatku radku, jinak se to proste dosere
			metadata.setXMove(metadata.getXMove() + innerIndent * itemOffset);
			cont.newLineAtOffset(metadata.getXMove(), 0);
			line = line.substring(line.indexOf(line.split(" ")[1]));
		}

		writeWithFont(cont, line, metadata);
		cont.newLineAtOffset(-metadata.getXMove(), 0);
	}

	private int getIndent(Map<String, Object> metadata) {
		return (int) metadata.getOrDefault("indent", 0);
	}

	private float getXMove(Map<String, Object> metadata) {
		return (float) metadata.getOrDefault("xMove", 0f);
	}

	private String getRawText(String text) {
		String raw = text;
		if (raw.contains("<") && raw.contains(">")) {
			raw = raw.replaceAll("<[^>]*>", "");
		}
		return raw;
	}

	@SneakyThrows
	private void writeWithFont(PDPageContentStream cont, String text, Metadata metadata) {
		boolean italic = metadata.isItalic();
		boolean bold = metadata.isBold();
		if (text.contains("<bold>") || text.contains("<italic>") || bold || italic) {
			for (int i = 0; i < text.length();) {
				int boldIndex = text.indexOf("<bold>", i) < 0 ? 10000 : text.indexOf("<bold>", i);
				int italicIndex = text.indexOf("<italic>", i) < 0 ? 10000 : text.indexOf("<italic>", i);
				if (italic && bold) {
					cont.setFont(fontItalicBold, metadata.getFontSize());
				} else if (italic) {
					cont.setFont(fontItalic, metadata.getFontSize());
				} else if (bold) {
					cont.setFont(fontBold, metadata.getFontSize());
				} else {
					cont.setFont(font, metadata.getFontSize());
				}

				int iNew = Math.min(text.length(), Math.min(boldIndex, italicIndex));
				String subText = getRawText(text.substring(i, iNew));

//				// Italic a Bold pisma jsou tlustsi // TODO ale jen kdyz center .... :D a jeste je to ted blbe
//				if(i == 0 && iNew == text.length() && (italic || bold)) {
//					float plainWidth = getStringWidth(subText, font);
//					if(bold && italic) {
//						cont.newLineAtOffset(metadata.getXMove() - (getStringWidth(subText, fontItalicBold) - plainWidth)/2, 0);
//					} else if(bold) {
//						cont.newLineAtOffset(metadata.getXMove() - (getStringWidth(subText, fontBold) - plainWidth)/2, 0);
//					} else {
//						cont.newLineAtOffset(metadata.getXMove() - (getStringWidth(subText, fontItalic) - plainWidth)/2, 0);
//					}
//					cont.newLineAtOffset(metadata.getXMove(), 0);
//				}
				if (!subText.isEmpty()) {
					cont.showText(subText);
				}

				if (iNew == boldIndex) {
					bold = !bold;
					iNew += "<bold>".length();
				}
				if (iNew == italicIndex) {
					italic = !italic;
					iNew += "<italic>".length();
				}
				i = iNew;
			}

			metadata.setItalic(italic);
			metadata.setBold(bold);
			cont.setFont(font, metadata.getFontSize());
		} else {
			cont.showText(getRawText(text));
		}
	}

	@SneakyThrows
	private PDPageContentStream newPage(PDDocument doc) {
		font = PDType0Font.load(doc, PdfGenerator.class.getClassLoader().getResourceAsStream("font/times.ttf"));
		fontBold = PDType0Font.load(doc, PdfGenerator.class.getClassLoader().getResourceAsStream("font/timesbd.ttf"));
		fontItalic = PDType0Font.load(doc, PdfGenerator.class.getClassLoader().getResourceAsStream("font/timesi.ttf"));
		fontItalicBold = PDType0Font.load(doc,
				PdfGenerator.class.getClassLoader().getResourceAsStream("font/timesbi.ttf"));
		PDPage page = new PDPage();
		doc.addPage(page);
		PDPageContentStream cont = new PDPageContentStream(doc, page);
		cont.beginText();
		cont.setFont(font, defaultFontSize);
		cont.setLeading(16f); // Vyska radku
		cont.newLineAtOffset(xOffset, 700); // Zacatek stranky
		return cont;
	}

	@SneakyThrows
	private List<String> splitLine(String input, double offset, Metadata metadata) {
		if (input.isEmpty()) {
			return Arrays.asList("");
		}
		List<String> output = new ArrayList<>();
		String line = "";
		float tagsWidth = 0;
		for (String word : input.split(" ")) {
			String lineBefore = line;
			if (!line.isEmpty() || word.isEmpty()) {
				line += " ";
			}

			for (String tag : TAGS) {
				if (word.contains(tag)) {
					tagsWidth += getStringWidth(tag, font, metadata);
				}
			}
			line += word;
			float width = getStringWidth(line + (GLUERS.contains(word) ? "1234567" : ""), font, metadata) - tagsWidth;
			if (width > pageWidth - offset) {
				output.add(lineBefore);
				line = word;
				tagsWidth = 0;
			}
		}
		output.add(line);
		return output;
	}

	@SneakyThrows
	private float getStringWidth(String string, PDType0Font font, Metadata metadata) {
		return font.getStringWidth(string) / 1000 * metadata.getFontSize();
	}

	int[] possibleWrapPoints(String text) {
		String[] split = text.split(" ");
		int[] ret = new int[split.length];
		ret[0] = split[0].length();
		for (int i = 1; i < split.length; i++)
			ret[i] = ret[i - 1] + split[i].length();
		return ret;
	}

}
