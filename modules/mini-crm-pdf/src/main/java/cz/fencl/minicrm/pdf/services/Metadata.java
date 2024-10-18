package cz.fencl.minicrm.pdf.services;

import lombok.Data;

@Data
public class Metadata {

	private float xMove = 0f;
	private float xMoveCumul = 0f;
	private boolean bold;
	private boolean italic;
	private double width;
	private int indent = 0;
	private int fontSize = 11;

	public Metadata(double width) {
		this.width = width;
	}

	public void setXMove(float value) {
		this.xMoveCumul += value;
		this.xMove = value;
	}
}
