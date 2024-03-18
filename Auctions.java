package auctions;

import java.time.LocalDateTime;

public class Auctions {
	private LocalDateTime timePosted; 
	private String name, category;
	private float startingAmount, currentBid;
	public String highestBidder;
	public String auctioneer;
	private String auctionStatus;
	
	public Auctions(LocalDateTime timePosted, String name, String category, float startingAmount, 
			float currentBid, String highestBidder, String auctionStatus, String auctioneer) {
		if(timePosted!=null) {this.timePosted = timePosted;}
		else {setPostedTime();}
		this.name = name;
		this.category = category;
		this.startingAmount = startingAmount;
		this.currentBid = currentBid;
		if(highestBidder != null) {this.highestBidder = highestBidder;}
		else {setHighestBidder("N/A");}
		if(auctionStatus != null) {this.auctionStatus = auctionStatus;}
		else {setAuctionStatus("Upcoming");}
		this.auctioneer = auctioneer;
	}
	
	private void setPostedTime() {
		this.timePosted = LocalDateTime.now();
	}
	
	public LocalDateTime getPostedTime() {
		return timePosted;
	}
	
	public void setName(String name) {
		if(this.name != null) {
			this.name = name;
		}
	}
	
	public String getItemName() {
		return name;
	}
	
	public void setCategory(String category) {
		if(this.category != null) {
			this.category = category;
		}
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setStartingAmount(float startingAmount) {
		this.startingAmount = startingAmount;
	}
	
	public float getStartingAmount() {
		return startingAmount;
	}
	
	public void setCurrentBid(float currentBid) {
		this.currentBid = currentBid;
	}
	
	public float getCurrentBid() {
		return currentBid;
	}
	
	public void setHighestBidder(String highestBidder) {
		this.highestBidder = highestBidder;
	}
	
	public String getHighestBidder() {
		return highestBidder;
	}
	
	public void setAuctionStatus(String auctionStatus) {
		this.auctionStatus = auctionStatus;
	}
	
	public String getAuctionStatus() {
		return auctionStatus;
	}
	
	public void setAuctioneer(String auctioneer) {
		this.auctioneer = auctioneer;
	}
	
	public String getAuctioneer() {
		return auctioneer;
	}
}
