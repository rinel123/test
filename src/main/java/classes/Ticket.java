package classes;

import java.time.LocalDateTime;

public class Ticket {
	
	private String passengerName;
	private int fromZone;
	private int toZone;
	private double price;
	private LocalDateTime departureTime;
	public Ticket(String passengerName, int fromZone, int toZone, double price, LocalDateTime departureTime) {
		super();
		this.passengerName = passengerName;
		this.fromZone = fromZone;
		this.toZone = toZone;
		this.price = price;
		this.departureTime = departureTime;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public int getFromZone() {
		return fromZone;
	}
	public void setFromZone(int fromZone) {
		this.fromZone = fromZone;
	}
	public int getToZone() {
		return toZone;
	}
	public void setToZone(int toZone) {
		this.toZone = toZone;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public LocalDateTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	


}
