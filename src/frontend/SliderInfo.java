package frontend;

import xml_start.XMLFormatException;

/**
 * @author Paulo, Brian
 *
 */
public class SliderInfo {

	private String title;
	private double min;
	private double max;
	private boolean isContinuous;
	private double defaultNum;

	public SliderInfo(String s) throws XMLFormatException {
		setupSlider(s);
	}

	/**
	 * Used to create an object to store information from the RuleSets object. This
	 * object is useful in sending all relevant information used to programmatically
	 * create sliders
	 * 
	 * @param title
	 * @param min
	 * @param max
	 * @param isContinuous
	 * @param defaultNum
	 */
	public SliderInfo(String title, double min, double max, boolean isContinuous, double defaultNum) {
		this.title = title;
		this.min = min;
		this.max = max;
		this.isContinuous = isContinuous;
		this.defaultNum = defaultNum;
	}

	/**
	 * Reads the slider information from the XML files
	 * 
	 * @param s
	 * @throws XMLFormatException
	 */
	private void setupSlider(String s) throws XMLFormatException {

		String[] sliderArray = s.split("\\s+");
		if (sliderArray.length != 4) {
			throw new XMLFormatException();
		}

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
		return defaultNum;
	}
}
