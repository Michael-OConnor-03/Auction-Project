package users;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;

import auctions.AllAuctions;
import auctions.Auctions;

public class Auctioneer extends User{
	private ArrayList<Auctions> auctions = new ArrayList<>();
	
	public Auctioneer(String username, String password, AllAuctions obj) {
		super(username, password);
		setAuctions(obj);
	}
	
	public ArrayList<Auctions> getAuctions() {
		return auctions;
	}

	public void setAuctions(AllAuctions obj) {
		for(Auctions a : obj.getAllAuctions()) {
			if(a.auctioneer.equals(getUsername()) && auctions.contains(a) == false) {
				auctions.add(a);
			}
		}
	}
	
	public void addAuctions(Scanner in, AllAuctions obj) throws IOException {
		System.out.println("Do you want to manually upload an auction? (Y or N)");
		String choice = in.nextLine();
		if(choice.equals("Y")||choice.equals("y")) {
			manualAddition(in, obj);
		} else if(choice.equals("n")||choice.equals("N")) {
			System.out.println("Returning...");
		} else {
			System.out.println("Incorrect Input.");
		}
	}
	
	private void writeToAuctions(Auctions obj) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter("Auctions.txt", true));
		
		writer.append(obj.getPostedTime()+","+obj.getItemName()+","+obj.getCategory()+","+
		obj.getStartingAmount()+","+obj.getCurrentBid()+","+obj.getHighestBidder()+
		","+obj.getAuctionStatus()+","+obj.getAuctioneer()+"\n");
		
		writer.close();
	}
	
	private void manualAddition(Scanner in, AllAuctions allAuctionsObj) throws IOException {
		System.out.println("Enter the name of the item: ");
		String name = in.nextLine();

		System.out.println("Enter the category of the item: ");
		String cat = in.nextLine();
		
		System.out.println("Enter the starting amount for the item: ");
		float amt = Float.parseFloat(in.nextLine());
		
		Auctions obj = new Auctions(LocalDateTime.now(), name, cat, amt, 0, "N/A", "Upcoming", getUsername());
		
		if(allAuctionsObj.getAllAuctions().contains(obj) == false) {
			allAuctionsObj.getAllAuctions().add(obj);
			auctions.add(obj);
			writeToAuctions(obj);
		}
	}
	
	public void startAuction(Scanner in, AllAuctions obj) throws IOException {
		obj.printAuctions(auctions);
		System.out.println("Choose auction to start (enter auction number): ");
		int choice = Integer.parseInt(in.nextLine());
		
		Auctions a = auctions.get(choice);
		String status = auctions.get(choice).getAuctionStatus();
		
		if(status.equals("Upcoming") && status.equals("Completed") == false) {
			for(int i = 0; i < obj.getAllAuctions().size(); i++) {
				if(obj.getAllAuctions().get(i).equals(a)) {
					a.setAuctionStatus("In Progress");
					auctions.get(choice).setAuctionStatus("In Progress");
					obj.getAllAuctions().set(i, a);
				}
			}
		}
	}

	public void endAuction(Scanner in, AllAuctions obj) throws IOException {
		obj.printAuctions(auctions);
		System.out.println("Choose auction to end (enter auction number): ");
		int choice = Integer.parseInt(in.nextLine());
		
		Auctions a = auctions.get(choice);
		String status = auctions.get(choice).getAuctionStatus();
		
		if(status.equals("Upcoming") == false && status.equals("In Progress")) {
			for(int i = 0; i < obj.getAllAuctions().size(); i++) {
				if(obj.getAllAuctions().get(i).equals(a)) {
					auctions.get(choice).setAuctionStatus("Completed");
					a.setAuctionStatus("Completed");
					obj.getAllAuctions().set(i, a);
				}
			}
		}
	}
	
	public void printAuctions(AllAuctions obj) throws IOException {
		obj.printAuctions(auctions);
	}
	
}
