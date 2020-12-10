import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Order {
	
	// Declares necessary variables.
	private int CurrentNumItems = 0;
	private int MaxNumberOfItems = -1;
	private int TotalItems = 0;
	private double OrderSubtotal = 0;
	private double OrderTotal = 0;
	
	// Will be used below for making a transaction file.
	private String filename = "transactions.txt";
	
	// Creates the Arrays to be used to store the transactions.
	private ArrayList<String> items = new ArrayList<>(); //all confirmed items
	private StringBuilder viewOrder = new StringBuilder();
	private StringBuilder finishOrder = new StringBuilder();
	
	// Creates a transaction file to store completed shopping orders.
	File file = new File(filename);
	String[] itemInfo = new String[6];
	
	// Appends everything to the array finishOrder and returns the array.
	public String GetFinishOrder() {
		return this.finishOrder.toString();
	}
	public void SetFinishOrder(String Date, String Time) {
		this.SetOrderTotal();
		this.finishOrder.append("Date: " + Date + " " + Time);
		this.finishOrder.append(System.getProperty("line.separator"));
		this.finishOrder.append(System.getProperty("line.separator"));
		this.finishOrder.append("Number of line items: " + this.GetTotalOfItems());
		this.finishOrder.append(System.getProperty("line.separator"));
		this.finishOrder.append(System.getProperty("line.separator"));
		this.finishOrder.append("Item# /ID / Price / Qty / Disc %/ Subtotal");
		this.finishOrder.append(System.getProperty("line.separator"));
		this.finishOrder.append(System.getProperty("line.separator"));
		this.finishOrder.append(this.GetViewOrder());
		this.finishOrder.append(System.getProperty("line.separator"));
		this.finishOrder.append(System.getProperty("line.separator"));
		this.finishOrder.append("Order subtotal:   $" + new DecimalFormat("#0.00").format(this.GetOrderTotal()));
		this.finishOrder.append(System.getProperty("line.separator"));
		this.finishOrder.append(System.getProperty("line.separator"));
		this.finishOrder.append("Tax rate:     6%");
		this.finishOrder.append(System.getProperty("line.separator"));
		this.finishOrder.append(System.getProperty("line.separator"));
		this.finishOrder.append("Order total:      $" + new DecimalFormat("#0.00").format(this.GetOrderSubtotal()));
		this.finishOrder.append(System.getProperty("line.separator"));
		this.finishOrder.append(System.getProperty("line.separator"));
		this.finishOrder.append("Thanks for shopping at the Ye Olde Item Shoppe!");
	}
	
	// Adds the transaction to the array viewOrder and retrieves viewOrder in the form of a string.
	public String GetViewOrder() {
		return this.viewOrder.toString();
	}
	public void AddToViewOrder(String Order) {
		viewOrder.append(this.GetTotalOfItems() + ". " + Order);
		viewOrder.append(System.getProperty("line.separator"));
	}
	
	// Retrieves the array that is storing the item's info.
	public String[] getItemInfo() {
		return itemInfo;
	}

	// Stores the info passed into ItemInfo to be us
	public void SetItemInfo(String ItemID, String Title, String Price, String QuantityOfItem, String DiscountPercentage, String TotalDiscount) {
		itemInfo[0] = ItemID;
		itemInfo[1] = Title;
		itemInfo[2] = Price;
		itemInfo[3] = QuantityOfItem;
		itemInfo[4] = DiscountPercentage;
		itemInfo[5] = TotalDiscount;
	}

	// Returns the Total discount for shopping today.
	public double GetTotalDiscount(int Quantity, double ItemPrice) {
		
		// 0% discount.
		if(Quantity >= 1 && Quantity <= 4 )
			return (Quantity * ItemPrice); 
		
		// 10% discount.
		if(Quantity >= 5 && Quantity <= 9)
			return .10 * (Quantity * ItemPrice); 
		
		// 15% discount.
		if(Quantity >= 10 && Quantity <= 14)
			return .15 * (Quantity * ItemPrice); 
		
		// 20% discount.
		if(Quantity >= 15)
			return .20 * (Quantity * ItemPrice); 
	
		return 0.0;
	}
	
	// Gives the discount percentage based on number of items.
	public int GetDiscountPercentage(int Quantity) {
		
		// 0% discount
		if(Quantity >= 1 && Quantity <= 4 )
			return 0; 
		
		// 10% discount
		if(Quantity >= 5 && Quantity <= 9)
			return 10;
		
		// 15% discount
		if(Quantity >= 10 && Quantity <= 14)
			return 15; 
		
		// 20% discount
		if(Quantity >= 15)
			return 20; 
		
		return 0;
	}
	
	// Retrieves the filename of the order.
	public String ViewOrder() {
		return filename;
	}
	
	// Sets up the string to be placed into the transaction file and stores it in items array.
	public void PrepareTransaction() {
		String lineItem = new String();
		for(int i = 0; i< this.itemInfo.length; i++){
			lineItem += this.itemInfo[i] + ", "; 
		}
		items.add(lineItem);
	}
	
	// Prints out the transactions into the transaction file.
	public void PrintTransactions() throws IOException {
		SimpleDateFormat TransactionID = new SimpleDateFormat("yyMMddyyHHmm");
		SimpleDateFormat DateFormat = new SimpleDateFormat("MM/dd/yy");	
		SimpleDateFormat Time = new SimpleDateFormat("hh:mm:ss a z");
		
		Calendar calendar = Calendar.getInstance();
		Date Date = calendar.getTime();
		
		this.SetFinishOrder(DateFormat.format(Date), Time.format(Date));
		
		// Checks to see if transaction file exists already. If it doesn't then create one and do the necessary writing.
		if(file.exists() == false) {
			file.createNewFile();
		}
		
		PrintWriter OutputStream = new PrintWriter(new FileWriter(filename, true));
		
		for(int i = 0; i< this.items.size(); i++){
			OutputStream.append(TransactionID.format(Date) + ", ");
			String lineItem = this.items.get(i);
			OutputStream.append(lineItem);
			OutputStream.append(DateFormat.format(Date) + ", ");
			OutputStream.append(Time.format(Date));
			OutputStream.println();
		}	
		
		OutputStream.flush();
		OutputStream.close();
	}
	
	// Sets and retrieves the current number of items chosen.
	public int GetCurrentNumberOfItems() {
		return CurrentNumItems;
	}
	public void SetCurrentNumberOfItems(int CurrentNumItems) {
		this.CurrentNumItems = this.CurrentNumItems + CurrentNumItems;
	}
	
	// Sets and retrieves the total items chosen.
	public void SetTotalOfItems(int TotalItems) {
		this.TotalItems = TotalItems;
	}
	public int GetTotalOfItems() {
		return TotalItems;
	}
	
	// Sets and retrieves the max number of items.
	public void SetMaxNumberOfItems(int MaxNumberOfItems) {
		this.MaxNumberOfItems = MaxNumberOfItems;
	}
	public int GetMaxNumberOfItems() {
		return MaxNumberOfItems;
	}

	// Sets and retrieves order sub total.
	public double GetOrderSubtotal() {
		return OrderSubtotal;
	}
	public void SetOrderSubtotal(int Quantity, double ItemPrice) {
		this.OrderSubtotal = this.OrderSubtotal + this.GetTotalDiscount(Quantity, ItemPrice);
	}

	// Get and Set the Total of the order.
	public double GetOrderTotal() {
		return OrderTotal;
	}

	public void SetOrderTotal() {
		this.OrderTotal = this.OrderSubtotal + (.06 * this.OrderSubtotal);
	}
}