package ca.ucalgary.seng300.a1.test;
import org.lsmr.vending.*;
import org.lsmr.vending.hardware.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import ca.ucalgary.seng300.a1.*;

/**
 *   This Class is used to test the functionality of the listener classes 
 *   Only the listeners that are needed to accomplish the most basic fundimental actions were implemented
 *   100% code coverage was achieved in TheCoinSlotListener, DeliveryChuteListen and TheSelectionButtonListener 
 */

public class GroupAssignment1Tests {

	private VendingMachine vend;
	private TheCoinSlotListener csListen;	
	private DeliveryChuteListen dcListen;
	
	
	/**
	 * A method to prepare a vending machine to the basic specifications outlined by the Client 
	 * canadian coins
	 * 6 buttons/kinds of pop
	 * 200 coins in each coin rack
	 * 10 pops per rack
	 * 200 coins can be stored in each recepticle 
	 */
	@Before
	public void setup() {
    	int[] coinKind = {5, 10, 25, 100, 200};
    	int selectionButtonCount = 6;
    	int coinRackCapacity = 200;		// probably a temporary value
    	int popCanRackCapacity = 10;
    	int receptacleCapacity = 200; 
    	vend = new VendingMachine(coinKind, selectionButtonCount, coinRackCapacity, popCanRackCapacity, receptacleCapacity);
    	
    	//for (int i = 0; i < vend.getNumberOfCoinRacks(); i++) {
    	//	TheCoinRackListener crListen = new TheCoinRackListener(vend);
    	//	(vend.getCoinRack(i)).register(crListen);
    	//}
    	
    	//TheDisplayListener dListen = new TheDisplayListener();
		//(vend.getDisplay()).register(dListen);
		
		csListen = new TheCoinSlotListener();
		(vend.getCoinSlot()).register(csListen);
		
		//TheCoinReceptacleListener crListen = new TheCoinReceptacleListener(vend);
		//(vend.getCoinReceptacle()).register(crListen);
		
		//crListen = new TheCoinReceptacleListener(vend);
		//(vend.getStorageBin()).register(crListen);
		
		dcListen = new DeliveryChuteListen(vend);
		(vend.getDeliveryChute()).register(dcListen);
		
		// exact change light
		//TheIndicatorLightListener eclListen = new TheIndicatorLightListener(vend);
		//(vend.getExactChangeLight()).register(dcListen);
		
		// out of order light
		//TheIndicatorLightListener oooListen = new TheIndicatorLightListener(vend);
		//(vend.getOutOfOrderLight()).register(dcListen);
		
		for (int i = 0; i < vend.getNumberOfSelectionButtons(); i++) {
    		TheSelectionButtonListener sbListen = new TheSelectionButtonListener(vend.getPopCanRack(i), csListen, vend, i);
    		(vend.getSelectionButton(i)).register(sbListen);
 //  		PopCanRackListen pcrListen = new PopCanRackListen(vend);
 //   		(vend.getPopCanRack(i)).register(pcrListen);
    	}
		
		//for (int i = 0; i < vend.getNumberOfPopCanRacks(); i++) {
    	//	
    	//	
    	//}
		List<String> popCanNames = new ArrayList<String>();
		popCanNames.add("Coke"); 
		popCanNames.add("Pepsi"); 
		popCanNames.add("Sprite"); 
		popCanNames.add("Mountain dew"); 
		popCanNames.add("Water"); 
		popCanNames.add("Iced Tea");
		
		PopCan popcan = new PopCan("Coke");
		try {
			vend.getPopCanRack(0).acceptPopCan(popcan);
		} catch (CapacityExceededException | DisabledException e) {
			e.printStackTrace();
		};
		
		List<Integer> popCanCosts = new ArrayList<Integer>();
		for (int i = 0; i < 6; i++) {
			popCanCosts.add(200);
		}
		vend.configure(popCanNames, popCanCosts);
    	
    }
	
	/**
	 * Ensure the CoinSlotListener begins with 0 credit
	 */
	@Test
	public void testGettingZeroCreditValue() {
		TheCoinSlotListener csListener = new TheCoinSlotListener();
		assertEquals(csListener.getCurrentCredit(), 0);
	}
	
	/**
	 * Ensure the CoinSlotListener properly increases amount of current credit
	 */
	@Test
	public void testCreditIncreased() {
		Coin coin = new Coin(5);
		TheCoinSlotListener csListener = new TheCoinSlotListener();
		csListener.validCoinInserted(vend.getCoinSlot(), coin);
		assertEquals(csListener.getCurrentCredit(), 5);		
	}

	/**
	 * Ensure the CoinSlotListener properly decreases amount of current credit when 
	 *    a price is charged 
	 */
	@Test
	public void testValueCharged() {
		Coin coin = new Coin(5);
		TheCoinSlotListener csListener = new TheCoinSlotListener();
		csListener.validCoinInserted(vend.getCoinSlot(), coin);
		csListener.valueCharged(2);
		assertEquals(csListener.getCurrentCredit(), 3);				
	}
	
	/**
	 * Ensure the CoinSlotListener properly does not increase amount of current credit
	 *   when the coin type is invalid
	 */
	@Test
	public void testCoinRejected() {
		Coin coin = new Coin(7);
		TheCoinSlotListener csListener = new TheCoinSlotListener();
		csListener.coinRejected(vend.getCoinSlot(), coin);
		assertEquals(csListener.getCurrentCredit(), 0);			
	}

	/**
	 * Ensure the DeliveryChuteListener properly disables the chute when the door is open
	 */
	@Test
	public void testDeliveryChuteDisable() {
		dcListen.doorOpened(vend.getDeliveryChute());
		assertTrue(vend.getDeliveryChute().isDisabled());
	}
	
	/**
	 * Ensure the DeliveryChuteListener properly enables the chute when the door is closed again
	 */
	@Test
	public void testDeliveryChuteEnable() {
		dcListen.doorOpened(vend.getDeliveryChute());
		dcListen.doorClosed(vend.getDeliveryChute());
		assertFalse(vend.getDeliveryChute().isDisabled());
	}

	/**
	 * Ensure the DeliveryChuteListener properly disables the chute when the chute is full
	 */
	@Test
	public void testDeliveryChuteFull() {
		dcListen.chuteFull(vend.getDeliveryChute());
		assertTrue(vend.getDeliveryChute().isDisabled());
	}	

	/**
	 * Ensure the selection button listener does not deliver if the button is pressed but the button is disabled
	 */
	@Test
	public void testButtonPressedDisabled() {
		SelectionButton button = vend.getSelectionButton(0);
		button.disable();
		button.press();
		assertTrue(vend.getDeliveryChute().removeItems().length==0);
	}
	
	/**
	 * Ensure the selection button listener does not deliver if the button is pressed but the vending machine safety 
	 *   is enabled
	 */
	@Test
	public void testButtonPressedSafety() {
		SelectionButton button = vend.getSelectionButton(0);
		vend.enableSafety();
		button.press();
		assertTrue(vend.getDeliveryChute().removeItems().length==0);
	}
	
	/**
	 * Ensure the selection button listener does not deliver if the button is pressed there is not enough credit
	 */
	@Test
	public void testButtonPressedNotEnoughCred() {
		vend.getSelectionButton(0).press();
		assertTrue(vend.getDeliveryChute().removeItems().length==0);		
	}
	
	/**
	 * Ensure the selection button listener returns a single pop can when there is enough money, nothing is 
	 *    disabled and the button is pressed 
	 */
	@Test
	public void testButtonPressedDeliverPop() {
		Coin coin = new Coin(200);
		try {
			vend.getCoinSlot().addCoin(coin);
			vend.getSelectionButton(0).press();
			Deliverable[] items= vend.getDeliveryChute().removeItems();
			assertTrue((items.length==1) && (items[0].toString().equals("Coke")));				
		} catch (DisabledException e) {
			assertTrue(false);
		}
	}
	
	/**
	 * Ensure the selection button listener does not deliver if the button is pressed there is no pop in the rack
	 */
	@Test
	public void testButtonPressedPopRackEmpty() {
		Coin coin = new Coin(200);
		try {
			vend.getCoinSlot().addCoin(coin);
			vend.getSelectionButton(0).press();
			vend.getDeliveryChute().removeItems();
			vend.getCoinSlot().addCoin(coin);
			vend.getSelectionButton(0).press();
			assertTrue(vend.getDeliveryChute().removeItems().length==0);				
		} catch (DisabledException e) {
			assertTrue(false);
		}
	}
	
	/**
	 * Ensure the selection button listener does not deliver and the PopCanRack is disabled
	 *   if there is sufficiant credit, the button is pressed but the pop can rack is disabled
	 */
	@Test
	public void testButtonPressedPopRackDisabled() {
		Coin coin = new Coin(200);
		try {
			vend.getPopCanRack(0).disable();
			vend.getCoinSlot().addCoin(coin);
			vend.getSelectionButton(0).press();	
			assertTrue((vend.getPopCanRack(0).isDisabled()) && (vend.getDeliveryChute().removeItems().length==0));
		} catch (DisabledException e) {
			assertTrue(false);
		}
	}
	
	/**
	 * method to destroy the vending machine after each test in order to not affect the following test
	 */
	@After
	public void tearDown() {
		vend = null; 
		csListen = null;
		dcListen = null;
	} 
	
} 
