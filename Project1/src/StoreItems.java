public class StoreItems {
	private String ItemID;
	private String Title;
	private double Price;
	
	
	public void SetTheItemID(String ItemID) {
		this.ItemID = ItemID;
	}
	public String GetTheItemID() {
		return ItemID;
	}

	public void SetTheTitleOfItem(String Title) {
		this.Title = Title;
	}
	public String GetTheTitleOfItem() {
		return Title;
	}
	
	public void SetThePriceOfItem(double Price) {
		this.Price = Price;
	}
	public double GetThePriceOfItem() {
		return Price;
	}
}