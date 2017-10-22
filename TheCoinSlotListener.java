package ca.ucalgary.seng300.a1;
import org.lsmr.vending.*;
import org.lsmr.vending.hardware.*;

public class TheCoinSlotListener implements CoinSlotListener {
	private int credit; 
	
	/**
	 * constructor to initialize the listener and set the credit to 0
	 */
	public TheCoinSlotListener() {
		credit = 0;
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

	/**
	 *  if a valid coin has been inserted increase the current credit by the coins value
	 *  @param CoinSlot slot
	 *  		the slot that the coin came from
	 *  @param Coin coin
	 *  		the valid coin that was inserted 
	 */
	@Override
	public void validCoinInserted(CoinSlot slot, Coin coin) {
		credit += coin.getValue();
	}

	/**
	 *  if an invalid coin has been inserted do nothing for now
	 *  @param CoinSlot slot
	 *  		the slot that the coin came from
	 *  @param Coin coin
	 *  		the invalid coin that was inserted 
	 */
	@Override
	public void coinRejected(CoinSlot slot, Coin coin) {
		// nothing for now
		// probably trigger display message 
	}

	/**
	 * accessor method to retieve the current credit accumulated
	 */
	public int getCurrentCredit() {
		return credit;
	}
	
	/**
	 * method to deduct an amount from the accumulated credit when a pop has been bought
	 * 
	 * @param int price
	 * 			The amount that will be charged 
	 */
	public void valueCharged(int price) {
		if ((price > 0) && (price <= credit)) {
			credit -= price;
		}
	}
}
