package model;

/**
 * <p>Title: Element</p>
 *
 * <p>Description: An entity to hold a name and a list of associated names</p>
 *
 * <p>Copyright: Copyright © 2007</p>
 *
 * @author Lynn Robert Carter
 * @version 1.04
 */
public class Element {
	private String name;
	private int numAssociatedNames;
	private String[] associatedName;
	private String selectedAssociatedName;
	private String selectedUnassociatedName;

	/**
	 * These are the constructors method for the AccessItem class.
	 *
	 */
	public Element() {
		name =  "";
		numAssociatedNames = 0;
		associatedName = new String[10];
		for (int i=0; i<10; i++) associatedName[i] = null;
	}

	public Element(String n) {
		name =  n;
		numAssociatedNames = 0;
		associatedName = new String[10];
		for (int i=0; i<10; i++) associatedName[i] = null;
	}

	// Just the usual getters and setters
	public String getName() { return name; }

	public String[] getAssociatedNames() { return associatedName; }

	public void setName(String x) { name = x; }

	public void setAssociatedNames(String[] x) {
		for (int i=0; i<10; i++) associatedName[i] = x[i];
		numAssociatedNames = 0;
		while (numAssociatedNames < 10 && associatedName[numAssociatedNames] != null) numAssociatedNames++;
	}
	
	public int getNumAssociatedNames() { return numAssociatedNames; }

	public String getSelectedAssociatedName() { return selectedAssociatedName; }

	public void setSelectedAssociatedName(String nameParam) { selectedAssociatedName = nameParam; }

	public String getSelectedUnassociatedName() { return selectedUnassociatedName; }

	public void setSelectedUnassociatedName(String nameParam) { selectedUnassociatedName = nameParam; }
}
