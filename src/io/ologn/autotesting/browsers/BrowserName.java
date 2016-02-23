package io.ologn.autotesting.browsers;

/**
 * An enum for browser names
 * @author sli
 */
public enum BrowserName {
	
	ANDROID("Android"),
	CHROME("Chrome"),
	FIREFOX("Firefox"),
	HTMLUNIT("HtmlUnit"),
	IE("IE"),
	IOS("iOS"),
	OPERA("Opera"),
	SAFARI("Safari"),
	OTHER("Other");
	
	private final String toString;
	
	/**
	 * Constructor.
	 * @param toString
	 */
	BrowserName(String toString) {
		this.toString = toString;
	}
	
	@Override
	public String toString() {
		return toString;
	}
	
}
