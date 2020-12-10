/* Name: Michael Heinzman
 Course: CNT 4714 – Fall 2020
 Assignment title: Project 1 – Event-driven Enterprise Simulation
 Date: Sunday September 13, 2020
 The Pictures are in the folder "Pictures Of It Working".
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ItemStore extends JFrame {
	private static final long serialVersionUID = 1L;
	private ArrayList<StoreItems> Inventory;
	private Order Order = new Order();
	
	// Text Fields.
	private JTextField JFItemID = new JTextField();
	private JTextField JFItemInfo = new JTextField();
	private JTextField JFQuantity = new JTextField();
	private JTextField JFNumberOfItems = new JTextField();
	private JTextField JFTotalItems = new JTextField();

	// Buttons.
	private JButton JBProcessItem = new JButton("Process Item #1");
	private JButton JBConfirmItem = new JButton("Confirm Item #1");
	private JButton JBViewOrder = new JButton("View Order");
	private JButton JBFinishOrder = new JButton("Finish Order");
	private JButton JBNewOrder = new JButton("New Order");
	private JButton JBExitOrder = new JButton("Exit");

	//JLabels.
	JLabel JLSubtotal = new JLabel("Order Subtotal for 0 item(s):");
	JLabel JLItemID = new JLabel("Enter Item ID for Item #1:");
	JLabel JLQuantity = new JLabel("Enter Quantitiy for Item #1:");
	JLabel JLItemInfo = new JLabel("Item #1 Info:");

	
	public ItemStore() throws FileNotFoundException 
	{
		this.GetInventoryFromFile();
	
		// Creates the first panel for the text inputs and outputs.
		JPanel Panel1 = new JPanel(new GridLayout(5,2));
		Panel1.add(new JLabel("Enter number of items in this order:"));
		Panel1.add(JFNumberOfItems);
		Panel1.add(JLItemID);
		Panel1.add(JFItemID);
		Panel1.add(JLQuantity);
		Panel1.add(JFQuantity);
		Panel1.add(JLItemInfo);
		Panel1.add(JFItemInfo);
		Panel1.add(JLSubtotal);
		Panel1.add(JFTotalItems);
		
		// Creates the second panel and adds the buttons to it.
		JPanel Panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		Panel2.add(JBProcessItem);
		Panel2.add(JBConfirmItem);
		Panel2.add(JBViewOrder);
		Panel2.add(JBFinishOrder);
		Panel2.add(JBNewOrder);
		Panel2.add(JBExitOrder);
		
		// Disable Text Fields.
		this.JFTotalItems.setEnabled(false);
		this.JFItemInfo.setEnabled(false);
		
		// Disable Buttons.
		this.JBConfirmItem.setEnabled(false);
		this.JBViewOrder.setEnabled(false);
		this.JBFinishOrder.setEnabled(false);
		
		// Add Panels.
		add(Panel1, BorderLayout.NORTH);
		add(Panel2, BorderLayout.SOUTH);
		
		
		// Listeners for Buttons.
		JBProcessItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Retrieve Information
				int NumOfItemsInOrder = Integer.parseInt(JFNumberOfItems.getText());
				int QuantityOfItem = Integer.parseInt(JFQuantity.getText());
				String ItemID = JFItemID.getText();
				
				// Set the max number of items.
				if(Order.GetMaxNumberOfItems() == -1 && NumOfItemsInOrder > 0) {
					Order.SetMaxNumberOfItems(NumOfItemsInOrder);
					JFNumberOfItems.setEnabled(false);
				}
				
				// Search for a Item.
				StoreItems Item = LinearSearch(ItemID);
				if(Item != null) 
				{
					Order.SetItemInfo(Item.GetTheItemID() + "", Item.GetTheTitleOfItem(), Item.GetThePriceOfItem() + "", 
										QuantityOfItem + "", Order.GetDiscountPercentage(QuantityOfItem) + "", 
										Order.GetTotalDiscount(QuantityOfItem, Item.GetThePriceOfItem()) + "");
					
					String ItemInfo = Item.GetTheItemID() + Item.GetTheTitleOfItem() +  " $" + Item.GetThePriceOfItem() + " " 
										+ QuantityOfItem + " " + Order.GetDiscountPercentage(QuantityOfItem) + "% " 
										+ Order.GetTotalDiscount(QuantityOfItem, Item.GetThePriceOfItem());
					
					JFItemInfo.setText(ItemInfo);
					JBProcessItem.setEnabled(false);
					JBConfirmItem.setEnabled(true);

					Order.SetOrderSubtotal(QuantityOfItem, Item.GetThePriceOfItem());
					JFTotalItems.setEnabled(false);
					JFItemInfo.setEnabled(false);
				}
				
				// Item was not found give error message.
				else 
					JOptionPane.showMessageDialog(null, "Item ID " + ItemID + " not in file.");
			}
		});
		
		// Button Actions. Self explanatory. Actions are put in order of user experience.
		JBConfirmItem.addActionListener(new ActionListener(){		
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				int numOfItemsInOrder = Integer.parseInt(JFNumberOfItems.getText());
				Integer.parseInt(JFItemID.getText());
				int quantityOfItem = Integer.parseInt(JFQuantity.getText());
				
				if(numOfItemsInOrder > Order.GetMaxNumberOfItems())
					System.out.println("went over qantity");
				
				// Manage Items.
				Order.SetCurrentNumberOfItems(quantityOfItem);
				Order.SetTotalOfItems(Order.GetTotalOfItems() + 1);
				JOptionPane.showMessageDialog(null, "Item #" + Order.GetTotalOfItems() + " accepted");
				Order.PrepareTransaction();
				Order.AddToViewOrder(JFItemInfo.getText());
				
				// Enable the buttons and set active or inactive.
				JBConfirmItem.setEnabled(false);
				JFNumberOfItems.setEnabled(false);
				JBProcessItem.setEnabled(true);
				JBViewOrder.setEnabled(true);
				JBFinishOrder.setEnabled(true);

				
				// Update GUIs.
				JBProcessItem.setText("Process Item #" + (Order.GetTotalOfItems() + 1));
				JBConfirmItem.setText("Confirm Item #" + (Order.GetTotalOfItems() + 1));
				
				JFItemID.setText("");
				JFQuantity.setText("");
				JFTotalItems.setText("$" +  new DecimalFormat("#0.00").format(Order.GetOrderSubtotal()));
				
				JLSubtotal.setText("Order subtotal for " + Order.GetCurrentNumberOfItems() + " item(s)");
				JLItemID.setText("Enter Item ID for Item #" + (Order.GetTotalOfItems() + 1) + ":");
				JLQuantity.setText("Enter quantity for Item #" + (Order.GetTotalOfItems() + 1) + ":");
				if(Order.GetCurrentNumberOfItems() < Order.GetMaxNumberOfItems())
				JLItemInfo.setText("Item #" + (Order.GetTotalOfItems() + 1) + " info:");
				
				// Last order.
				if(Order.GetCurrentNumberOfItems() >= Order.GetMaxNumberOfItems()) {
					JLItemID.setVisible(false);
					JLQuantity.setVisible(false);
					JBProcessItem.setEnabled(false);
					JBConfirmItem.setEnabled(false);
				}
			}
		});
		
		JBViewOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, Order.GetViewOrder());
			}
		});
		
		JBFinishOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Order.PrintTransactions();
					JOptionPane.showMessageDialog(null, Order.GetFinishOrder());

				} catch (IOException e1) {
					e1.printStackTrace();
				}
				ItemStore.super.dispose(); //dispose frame
			}
		});
		
		JBNewOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ItemStore.super.dispose();
				try {
					ItemStore.main(null);
				} catch (FileNotFoundException exception) {
					exception.printStackTrace();
				}
			}
		});
		
		JBExitOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ItemStore.super.dispose(); //dispose frame
			}
		});
	}

	// Searches the inventory for the ItemID so that it can return the specific Item with that ID.
	public StoreItems LinearSearch( String ItemID) {
		for(int i = 0; i < Inventory.size(); i++) {
			StoreItems CurrentItem = Inventory.get(i);
			if(CurrentItem.GetTheItemID().equals(ItemID))
				return CurrentItem;
		}
		return null;
	}
	
	// Retrieve data from the inventory file.
	public void GetInventoryFromFile() throws FileNotFoundException {
		
		// Create a list to store Items.
		this.Inventory = new ArrayList<StoreItems>();
		File file = new File("inventory.txt");
		Scanner InventoryFile = new Scanner(file);
		
		// Scan file named inventory
		while (InventoryFile.hasNextLine()) {
			
			// Parse lines into three strings to be stored in an array.
			String Item = InventoryFile.nextLine();
			
			if (Item.equals(""))
				break;
			
			String[] ItemInfo = Item.split(",");

			// Create a new Item to store the information that was taken from the inventory file.
			StoreItems item = new StoreItems();
			item.SetTheItemID(ItemInfo[0]);
			System.out.println(ItemInfo[0]);
			item.SetTheTitleOfItem(ItemInfo[1]);
			item.SetThePriceOfItem(Double.parseDouble(ItemInfo[2]));
			
			// Add Item to list.
			Inventory.add(item);
		}
		
		// Closes the File.
		InventoryFile.close();
	}

	// Returns the array inventory, which holds the data from the file.
	public ArrayList<StoreItems> GetInventory() {
		return Inventory;
	}

	// Stores the array passed inside inventory to be accessed later.
	public void SetInventory(ArrayList<StoreItems> Inventory) {
		this.Inventory = Inventory;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		ItemStore ItemStoreFrame = new ItemStore();
		ItemStoreFrame.pack();
		ItemStoreFrame.setTitle("Item Store");
		ItemStoreFrame.setLocationRelativeTo(null);
		ItemStoreFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ItemStoreFrame.setVisible(true);
	}
}