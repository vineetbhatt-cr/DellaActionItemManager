package persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.thoughtworks.xstream.security.NoTypePermission;

/**
 * <p>
 * Title: DataManager
 * </p>
 *
 * <p>
 * Description: Read and write data to the persistent store, an xml file
 * </p>
 *
 * <p>
 * Copyright: Copyright � 2005
 * </p>
 *
 * @author Harry Sameshima
 * @version 1
 */
public class DataManager {
	private static final String storageFile = "Della.xml";

	public DataManager() {
	}

	/**
	 * Read our persistent store into an object
	 * 
	 * @return Object
	 */
	public static Object readData() {

		// Does the persistent store exist?
		File file = new File(storageFile);
		if (!file.exists()) {
			return null;
		}

		// Yes, so let's deserialize the object
		XStream x = new XStream();

		// Security Framework Initialization - Introduced as part of XStream Version
		// Upgrade
		// to resolve com.thoughtworks.xstream.security.ForbiddenClassException while
		// data retrieval from XML

		// clear out existing permissions and set own ones
		x.addPermission(NoTypePermission.NONE);
		// allow any type from the given package
		//x.allowTypesByWildcard(new String[] { "control.*", "model.*" });
		x.addPermission(AnyTypePermission.ANY);//changed as part of Della04, analyze later on

		Object result = null;
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			ObjectInputStream oin = x.createObjectInputStream(in);
			result = oin.readObject();
			oin.close();
		} catch (IOException ex) {
			System.out.println("IO exception reading " + storageFile);
			System.exit(0);
		} catch (ClassNotFoundException ex) {
			System.out.println("Class not found exception while reading " + storageFile);
			System.exit(0);
		}
		return result;
	}

	/**
	 * Write out an object to the persistent store so we can save data
	 * 
	 * @param o Object
	 */
	public static void writeData(Object o) {

		try {
			XStream x = new XStream();
			FileWriter fw = new FileWriter(storageFile);
			ObjectOutputStream out = x.createObjectOutputStream(fw);
			out.writeObject(o);
			out.close();
		} catch (IOException ex) {
			System.out.println("IO Exception writing " + storageFile);
			System.exit(0);
		}
	}
}
