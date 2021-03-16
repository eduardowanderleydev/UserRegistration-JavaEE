package beans;

public class BeanProduct {

	private long id;
	private String name;
	private int quantity;
	private double price;
	
	public BeanProduct () {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getTextPrice() {
		return Double.toString(price).replace('.', ',');
	}
	
}
