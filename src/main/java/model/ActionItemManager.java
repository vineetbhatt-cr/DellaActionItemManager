package model;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * <p>
 * Title: ActionItemManager
 * </p>
 *
 * <p>
 * Description: A class to organize and manage all known action items
 * </p>
 *
 * <p>
 * Copyright: Copyright � 2007
 * </p>
 *
 * @author Lynn Robert Carter
 * @version 1.04
  * Many thanks to Harry Sameshima for his original work.
*/
public class ActionItemManager {

	//---------------------------------------------------------------------------------------------------------------------
	// Constants

	private static final int noItemSelected = -1;	// Added for Della01

	// Added for Della02 (start)
	public static final int sortingFactorNone = 0;				// The constants for sorting factors
	public static final int sortingFactorCreationDate = 1;
	public static final int sortingFactorDueDate = 2;
	public static final String[] sortingFactorStrings = {"None", "Creation Date", "Due Date"};

	public static final int sortingDirectionSmallToLarge = 0;	// The constants for sorting direction
	public static final int sortingDirectionLargeToSmall = 1;
	public static final String[] sortDirectionStrings = {"Small to Large", "Large to Small"};
	// Added for Della02 (end)

	private transient SimpleDateFormat dateFormat = null;

	public static final int statusOpen = 0;			// The constants for Open and Close
	public static final int statusClosed = 1;
	public static final String[] statusStrings = {"Open", "Closed"};

	private boolean editChangesPending;

	//---------------------------------------------------------------------------------------------------------------------
	// Attributes
	
	// Added for Della01
	private ArrayList <ActionItem> aiList = null;

	private ActionItem currentActionItem = null;	// Currently displayed action item
	private ActionItem emptyActionItem = null;		// The standard empty action item
    
    // Added for Della01
    private AccessItemManager accessManager = null;
    private int sortDirection = 0;
	
	// Added for Della02 (start)
	private int sortFactor1 = 0;
	private int sortFactor2 = 0;
	// Added for Della02 (end)

	// Added for Della04 (start)
	private ElementList memberList = null;
	// Added for Della04 (end)

	//---------------------------------------------------------------------------------------------------------------------

	/**
	 * The ActionItemManager class constructor.
	 * 
	 */
	public ActionItemManager() {
		currentActionItem = new ActionItem();
		emptyActionItem = new ActionItem();
		emptyActionItem.setCreatedDate(null);
        
    	// Added for Della01
        aiList = new ArrayList <ActionItem>();
        accessManager = new AccessItemManager();

		// Added for Della04
		memberList = new ElementList();
	}

    /**
     * reestablishActionItemAccessList is used to rebuild the access list any time some factor at the
     * heart of this list changes.  So far, these are the events that cause the list to be updated:
     * 		1. New action item added to the list of action items
     * 		2. An existing action item is updated
     * 
     * Added for Della01
     */
    private void reestablishActionItemAccessList(){
     	int resultListSize = 0;
     	int inputListSize = aiList.size();
    	String[] names = new String[inputListSize];
    	int[] nameIndexes = new int[inputListSize];

    	for (int i = 0; i < inputListSize; i++){
    		// Updated for Della02
    		ActionItem ai = aiList.get(i);
    		names[resultListSize] = getSortFactorText(ai, sortFactor1) + getSortFactorText(ai, sortFactor2) + ai.getActionItemName();
    		nameIndexes[resultListSize] = i;
    		resultListSize++;
    	}
    	accessManager.establishSortedAccessList(names, nameIndexes, resultListSize, sortDirection);
    }

    /**
     * get an array of the processed action item names from the access list
     * @return the array of names
     * 
     * Added for Della01
     */
    public String[] getActionItemNames(){
		int listSize = accessManager.size();
    	String[] names = new String[listSize];
    	for (int i = 0; i < listSize; i++) {
    		names[i] = accessManager.getAccessName(i);
    	}
    	
    	return names;
    }
    
	/**
	 * Create a new action item and add it to the list of action items
	 * @param name String
	 * @param description String
	 * @param resolution String
	 * @param status String
	 * @param dueDateStr String
	 * @return ActionItem
	 * 
	 * Added for Della01
	 */
	public ActionItem createActionItem(String name, String description,
			String resolution, String status,
			String dueDateStr) throws Exception {
		ActionItem ai = new ActionItem(name, description, resolution, status);
		validateActionItem(ai, name, dueDateStr, true, 0);
		setCurrentActionItem(ai);
		aiList.ensureCapacity((1 + aiList.size()/10)*10);	// This expands the list, if necessary, in blocks of 10 names
		aiList.add(ai);
		reestablishActionItemAccessList();
		return ai;
	}
    
	/**
	 * Update an existing action item based on the parameters pass to the routine
	 * @param name String
	 * @param description String
	 * @param resolution String
	 * @param status String
	 * @param dueDateStr String
	 * @return ActionItem
	 */
	public ActionItem updateActionItem(String name, String description,
			String resolution, String status,
			String dueDateStr, 
			int itemIndex)	// Added for Della01
	throws Exception {
		if (itemIndex == noItemSelected) {	// Added for Della01
			throw new Exception("No action item has been selected to update!   ");
		}
		// Just allocate a new action item and save it.  The inner workings of
		// this method will change drastically in Task 01 so I don't care about
		// being wasteful now.
		ActionItem ai = new ActionItem(name, description, resolution, status);
        
        // Added for Della01
        int actualItemIndex = accessManager.getAccessIndex(itemIndex);

		// Check if there are problems with the modifications.
		validateActionItem(ai, name, dueDateStr, false, actualItemIndex);	// Modified for Della01

        // We passed the tests so it's ok to set the new current action item
    	// Added for Della01
    	aiList.set(actualItemIndex, ai);
		reestablishActionItemAccessList();

		// We passed the tests so it's ok to set the new current action item
		setCurrentActionItem(ai);
		return ai;
	}

	/**
	 * Check the parameters to see if the action item can be added to the list of action items.
	 * @param name String
	 * @param dueDateStr String
	 * @return boolean
	 * @throws an exception if there are any problems with the input.
	 */
    private void validateActionItem(ActionItem ai, String name, String dueDateStr, 
			boolean flag, int itemIndex) throws Exception {	// Modified for Della01
		if (name.trim().length() == 0) {
			throw new Exception("The Action Item Name must not be empty!   ");
		}

		Date dueDate = null;
		if (dueDateStr.length() != 0) {
			try {
				dueDate = dateFormat.parse(dueDateStr);
			}
			catch (ParseException ex) {
				throw new Exception("Please use the requested date format!   ");
			}
		}
		ai.setDueDate(dueDate);
		
		// Added for Della01
		ActionItem aiTemp = new ActionItem();
		if(flag){
			// This checks for a create action... no duplications allowed
			for (int i=0;i<aiList.size();i++){
				aiTemp = (ActionItem)aiList.get(i);
				if(aiTemp.getActionItemName().equalsIgnoreCase(name))
					throw new Exception("Action Item name already exists!   ");
			}
		}
		else {
			// This checks for an update action... the only allowed duplication is for
			// the action item being updated... that is, the name has not changed!
			for (int i=0; i < aiList.size(); i++){
				if (i != itemIndex)
				{
					aiTemp = (ActionItem)aiList.get(i);
					if(aiTemp.getActionItemName().equalsIgnoreCase(name))
						throw new Exception("New Action Item name already exists!   ");
				}
			}
		}
	}

	/**
	 * Get the action item index for the items whose name matches the provided parameter
	 * @param aiName	String	- This is the name of the action item to find
	 * @return	the item index of the found action item is returned
	 * 
	 * Added for Della02
	 */
	public int getActionItemIndex(String aiName){
		ActionItem temp = new ActionItem();
		int result = -1;
		for (int i = 0; i < accessManager.size(); i++){
			temp = (ActionItem)aiList.get(accessManager.getAccessIndex(i));
			if(temp.getActionItemName().equals(aiName)) {
				result = i;
				break;
			}
		}
		return result;
	}

	/**
	 * getSortFactorText (private) is used to generate text for the sorting name to be
	 * generated based on the various action sorting factors
	 * 
	 * @param ai	- the action item whose fields are to be used
	 * @param sortingFactor	- the sorting factor requested
	 * @return a string based on the action item and the specified factor
	 * 
	 * Added for Della02
	 */
    private String getSortFactorText(ActionItem ai, int sortingFactor){
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	String tempFactor = new String("");
    	if (sortingFactor == 1) // The first choice is the creation date
    		if (ai.getCreatedDate() != null)
    			tempFactor = dateFormat.format(ai.getCreatedDate()) + ": ";
    		else
    			tempFactor = ": ";
    	else
    		if (sortingFactor == 2) // The second choice is the due date
    			if (ai.getDueDate() != null)
    				tempFactor = dateFormat.format(ai.getDueDate()) + ": ";
    			else
    				tempFactor = "- no due date -: ";
    	return tempFactor;
    }

	/**
	 * Remove a specific Assigned Member
	 * 
	 * @param name
	 * 
	 * Added for Della04
	 */
	public void removeAssignedMember(String name){
 		memberList.setUnaddedName(name);
		memberList.findAndRemoveElement(name);
	}

	// The usual getters and setters

	/**
	 * Get the current action item 
	 * @return	- The current action item
	 */
	public ActionItem getCurrentActionItem() {
		if (currentActionItem == null) 
			return emptyActionItem;
		return currentActionItem;
	}

	public void setCurrentActionItem(ActionItem x) { currentActionItem = x; }

	public void clearCurrentActionItem() { currentActionItem = emptyActionItem; }

	public void setDateFormatChecker() { dateFormat = new SimpleDateFormat("yyyy-MM-dd"); }

	public void setEditChangesPending(boolean flag){ editChangesPending = flag; }

	public boolean getEditChangesPending(){ return editChangesPending; }
	
    // Added for Della01
    public ArrayList<ActionItem> getActionItemList(){ return aiList; }

	// Added for Della02
	public String getCurrentActionItemName() {
		if (currentActionItem==null) return "";
		return currentActionItem.getActionItemName();
	}

	public int getSortDirection() { return sortDirection; }

	public int getSortFactor1() { return sortFactor1; }

	public int getSortFactor2() { return sortFactor2; }

	public void setSortDirection(int sdir) {
		sortDirection = sdir;
		reestablishActionItemAccessList();
	}

	public void setSortFactor1(int sf1) {
		sortFactor1 = sf1;
		reestablishActionItemAccessList();
	}

	public void setSortFactor2(int sf2) {
		sortFactor2 = sf2;
		reestablishActionItemAccessList();
	}
    
    /**
     * Get the action item based on the access index
     * @param accessIndex	int	- This is the access index to find the actual index of the Action Item
     * @return	- The found action item is returned
     * 
     * Added for Della01
     */
	public ActionItem getActionItem(int accessIndex){
		if (accessIndex==noItemSelected) return emptyActionItem;
		return (ActionItem)aiList.get(accessManager.getAccessIndex(accessIndex));
	}

	// Added for Della04
	public String getMember(int x) { return memberList.getName(x); }

	public ElementList getMemberList() { return memberList; }

	public int getMemberListSize() {
		int memberSize = 0;
		for (int i = 0; i <memberList.getListSize(); i++) {
			if (memberList.get(i) == null) break;
			if (memberList.getName(i).compareTo("") == 0) break;
			memberSize++;
		}
		return memberSize;
	}

}
