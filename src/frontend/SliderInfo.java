package frontend;

import xml_start.XMLFormatException;

public class SliderInfo {

	private String title;
	private double min;
	private double max;
	private boolean isContinuous;
	
	public SliderInfo(String s) throws XMLFormatException {
		setupSlider(s);
	}
	
	public SliderInfo(String title, double min, double max, boolean isContinuous) {
		this.title = title;
		this.min = min;
		this.max = max;
		this.isContinuous = isContinuous;
	}
	
	private void setupSlider(String s) throws XMLFormatException {
		
		String[] sliderArray = s.split("\\s+");
		if (sliderArray.length != 4) {throw new XMLFormatException();}
		
		title = sliderArray[0];
		min = Double.valueOf(sliderArray[1]);
		max = Double.valueOf(sliderArray[2]);
		isContinuous = sliderArray[3].equals("true");
		
		return;
		
	}

	public String getTitle() {
		return title;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public boolean isContinuous() {
		return isContinuous;
	}
	
	public double getDefault() {
		return (max-min)/2;
	}
}
