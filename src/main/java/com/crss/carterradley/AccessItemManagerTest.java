package com.crss.carterradley;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runners.MethodSorters;

import model.AccessItemManager;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccessItemManagerTest {

	@Rule
	public ErrorCollector ec = new ErrorCollector();
	

	@Test
	public void testSortDirectionAscending() {
		System.out.print("Running Test : testSortDirectionAscending\n\n");
		System.out.print("Insertion" + "\t" + "Expected" + "\t" + "Actual" + "\n");
		
		
		String[] names = { "One", "Two", "Three", "Four", "Five", "Six" };
		int[] accesses = { 0, 1, 2, 3, 4, 5 };
		int sortDirection = 0;
		Boolean flag = true;

		AccessItemManager accessManager = new AccessItemManager();

		String[] expectedOrderASC = { "Five", "Four", "One", "Six", "Three", "Two" };

		accessManager.establishSortedAccessList(names, accesses, names.length, sortDirection);

		for (int i = 0; i < accessManager.size(); i++) {
			try {
				assertEquals(expectedOrderASC[i], accessManager.getAccessName(i));
			} catch (Throwable t) {
				System.out.println(names[i] + "\t\t" + expectedOrderASC[i] + "\t\t" + accessManager.getAccessName(i));
				ec.addError(t);
				flag = false;
			}
			if (flag == true)
			System.out.println(names[i] + "\t\t" + expectedOrderASC[i] + "\t\t" + accessManager.getAccessName(i));
		}
		System.out.println("\n");

	}

	@Test
	public void testSortDirectionDescending() {
		System.out.print("Running Test : testSortDirectionDescending\n\n");
		System.out.print("Insertion" + "\t" + "Expected" + "\t" + "Actual" + "\n");
		
		String[] names = { "One", "Two", "Three", "Four", "Five", "Six" };
		int[] accesses = { 0, 1, 2, 3, 4, 5 };
		int sortDirection = 1;
		Boolean flag = true;

		String[] expectedOrderDESC = { "Two", "Three", "Six", "One", "Four", "Five" };

		AccessItemManager accessManager = new AccessItemManager();

		accessManager.establishSortedAccessList(names, accesses, names.length, sortDirection);

		for (int i = 0; i < accessManager.size(); i++) {
			try {
				assertEquals(expectedOrderDESC[i], accessManager.getAccessName(i));
			} catch (Throwable t) {
				System.out.println(names[i] + "\t\t" + expectedOrderDESC[i] + "\t\t" + accessManager.getAccessName(i));
				ec.addError(t);
				flag = false;
			}
			if (flag == true)
			System.out.println(names[i] + "\t\t" + expectedOrderDESC[i] + "\t\t" + accessManager.getAccessName(i));
		}
		System.out.println("\n");
	}

}
