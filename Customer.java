package users;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import auctions.AllAuctions;
import auctions.Auctions;

public class Customer extends User{

	private float accountBalance;
	private ArrayList<Auctions> bids = new ArrayList<>();
	private ArrayList<Auctions> watchList = new ArrayList<>();
	
	public Customer(String username, String password, AllAuctions obj) {
		super(username, password);
		setAccountBalance(0);
		generateBids(obj);
	}

	private void generateBids(AllAuctions obj) {
		for(Auctions a : obj.getAllAuctions()) {
			if(a.getHighestBidder().equals(getUsername())) {
				bids.add(a);
			}
		}
	}
	
	public void addToAccountBalance(Scanner in) {
		System.out.println("Enter the amount you want to add to your balance: ");
		float choice = Float.parseFloat(in.nextLine());
		accountBalance += choice;
	}
	
	
	private void subtractFromAccountBalance(float subtraction) {
		float newBalance = accountBalance - subtraction;
		accountBalance = newBalance;
	}
	
	private void addAfterFailedBid(float addition, String previousTopBidder, ArrayList<Customer> customers) {
		for(Customer c : customers) {
			if(c.getUsername().equals(previousTopBidder)) {
				float b = c.getAccountBalance();
				c.setAccountBalance(b + addition);
				break;
			}
		}	
	}
	
	
	private void addToWatchList(Auctions obj) {
		watchList.add(obj);
	}
	
	private void removeFromWatchList(Auctions obj) {
		watchList.remove(obj);
	}
	
	public void printBids(AllAuctions obj) throws IOException {
		if(bids == null) {
			System.out.println("You haven't bidded on anything yet.");
		}
		else {
			obj.printAuctions(bids);
		}
	}
	
	public void printWatchList(AllAuctions obj) throws IOException {
		obj.printAuctions(watchList);
	}

	public void makeBid(Scanner in, AllAuctions obj, ArrayList<Customer> customers) throws IOException {
		if(customers != null) {
			for(Auctions a : obj.getAllAuctions()) {
				for(Customer c : customers) {
					c.setUsername(a.getHighestBidder());
				}
			}
		}
		
		obj.printAuctions(obj.getAllAuctions());
		System.out.println("Choose auction to bid on by entering the auction number: ");
		int choice = Integer.parseInt(in.nextLine());
		
		if(obj.getAllAuctions().get(choice).getAuctionStatus().equals("In Progress")) {
			System.out.println("Enter the amount of money you want to bid: ");
			float bid = Float.parseFloat(in.nextLine());
			float newCurrentBid = obj.getAllAuctions().get(choice).getCurrentBid();
			
			if(bid <= accountBalance && bid + newCurrentBid + obj.getAllAuctions().get(choice).getStartingAmount() <= accountBalance) {
				addAfterFailedBid(obj.getAllAuctions().get(choice).getCurrentBid(), 
						obj.getAllAuctions().get(choice).getHighestBidder(), customers);
				obj.getAllAuctions().get(choice).setHighestBidder(getUsername());
				obj.getAllAuctions().get(choice).setCurrentBid(bid + newCurrentBid);
				subtractFromAccountBalance(bid);
			}
			else {
				System.out.println("Cannot make bid.");
			}
		}
		else {
			System.out.println("Cannot make bid.");
		}
	}

	public void controlWatchList(AllAuctions obj, Scanner in, ArrayList<Auctions> all) throws IOException {
		int select = 0;
		System.out.println("1. Add an auction to watch list   2. Remove an auction from watch list");
		String choice = in.nextLine();
		
		switch(choice) {
			case "1":
				obj.printAuctions(all);
				System.out.println("Auctions are numbered accordingly. Choose auction to be added: ");
				select = Integer.parseInt(in.nextLine());
				addToWatchList(all.get(select));
				break;
			case "2":
				printWatchList(obj);
				System.out.println("Auctions are numbered accordingly. Choose auction to be removed: ");
				select = Integer.parseInt(in.nextLine());
				removeFromWatchList(watchList.get(select));
				break;
		}
	}

	public float getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(float accountBalance) {
		this.accountBalance = accountBalance;
	}
}
