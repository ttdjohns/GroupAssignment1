package ca.ucalgary.seng300.a1;

import org.lsmr.vending.*;
import org.lsmr.vending.hardware.*;

/**
 * Listens for events emanating from a delivery chute.
 *   Some lines are commented out for expected future expansion 
 */
public class DeliveryChuteListen implements DeliveryChuteListener {
	VendingMachine vend;
	//TheCoinSlotListener csListen;
	
	/**
	 * Constructor that will initialize the instance and will initialze the pointer to the vending machine that this 
	 *   chute is part of.
	 */
	public DeliveryChuteListen(VendingMachine vend) {
		this.vend = vend;
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
		// nothiing for now
	}

    /**
     * Indicates that an item has been delivered to the indicated delivery
     * chute.
     * 
     * @param chute
     *            The device on which the event occurred.
     */
	@Override
	public void itemDelivered(DeliveryChute chute) {
		// nothing for now
	}

    /**
     * Indicates that the door of the indicated delivery chute has been opened.
     *   Enables safety to prevent injury and tampering 
     * 
     * @param chute
     *            The device on which the event occurred.
     */
	@Override
	public void doorOpened(DeliveryChute chute) {
		vend.enableSafety();
		// chute.enable();
	}

    /**
     * Indicates that the door of the indicated delivery chute has been closed.
     *   disables safety to allow use of machine again.
     * 
     * @param chute
     *            The device on which the event occurred.
     */
	@Override
	public void doorClosed(DeliveryChute chute) {
		vend.disableSafety();
		// chute.disable();
	}

    /**
     * Indicates that the delivery chute will not be able to hold any more
     * items.
     *   Enables safety to prevent injury and tampering 
     * 
     * @param chute
     *            The device on which the event occurred.
     */
	@Override
	public void chuteFull(DeliveryChute chute) {
		// Display message that the delivery chute will not be able to hold any more items or the coin.
		vend.enableSafety();
		// display chute jammed
	}
 
}
