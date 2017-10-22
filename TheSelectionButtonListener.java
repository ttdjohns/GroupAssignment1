package ca.ucalgary.seng300.a1;

import org.lsmr.vending.hardware.*;

/**
 * Listens for events emanating from a selection button.
 */
public class TheSelectionButtonListener implements SelectionButtonListener {
	private PopCanRack rack;
	private TheCoinSlotListener currentCred;
	private VendingMachine vend;
	private int thisButtonIndex;
	
	/**
	 * Constructor.  Initializes pointers to the corresponding pop rack, the coin slot listener, the whole vending machine
	 *   and the index number that the vending machine has given to it.
	 *   
	 * @param PopCanRack r
	 * 			The rack that holds the pop this button will dispence
	 * @param TheCoinSlotListener csl
	 * 			The CoinSlotListener that keeps track of the current credit
	 * @param VendingMachine vm
	 * 			The instance of vending machine that this listener will be part of.
	 * @param int index
	 * 			The index number that the vending machine has given to this product.
	 */
	public TheSelectionButtonListener(PopCanRack r, TheCoinSlotListener csl, VendingMachine vm, int index) {
		rack = r;
		currentCred = csl;
		vend = vm;
		thisButtonIndex = index;
	}
	
    /**
     * An event that is announced to the listener when the indicated button has
     * been pressed.
     * 
     * @param button
     *            The device on which the event occurred.
     */
    public void pressed(SelectionButton button) {
    	// check if pop is available 
        // check price of pop 
        // check if coin inserted is enough for pop
        // dispense pop
    	// charge the credit for pop.
    	if (vend.isSafetyEnabled() || (vend.getSelectionButton(thisButtonIndex)).isDisabled()) {
    		// do nothing
    	}
    	else if (currentCred.getCurrentCredit() < vend.getPopKindCost(thisButtonIndex)) {
    		// current credit is less than the price of the pop. Do nothing
    	}
    	else if (rack.size() <= 0) {
    		// display empty
    	}
    	else {
    		try {
				rack.dispensePopCan();
				currentCred.valueCharged(vend.getPopKindCost(thisButtonIndex)); 
			} catch (DisabledException | EmptyException | CapacityExceededException e) {
				// display error message. do not deduct money
			}
    	}
    }

    /**
     * Announces that the indicated hardware has been enabled.
     * 
     * @param hardware
     *            The device that has been enabled.
     */
	@Override
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		// nothing for now
	}

    /**
     * Announces that the indicated hardware has been disabled.
     * 
     * @param hardware
     *            The device that has been enabled.
     */
	@Override
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		// nothing for now
	 	
	}
    
    
}