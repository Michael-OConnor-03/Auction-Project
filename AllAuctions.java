package auctions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
//import java.util.Collections;
import java.util.Scanner;

public class AllAuctions {
	
	private ArrayList<Auctions> allAuctions = new ArrayList<>();
	
	public AllAuctions(){
		try {
			setAllAuctions();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setAllAuctions() throws IOException{
		if(new File("Auctions.txt").createNewFile()) {
			@SuppressWarnings("unused")
			File file = new File("Auctions.txt");
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("Auctions.txt"));
		String line;
		
		while((line = reader.readLine()) != null) {
			String[] arr = line.split(",");
			Auctions a = new Auctions(LocalDateTime.parse(arr[0]), arr[1], arr[2], Float.parseFloat(arr[3]), 
					Float.parseFloat(arr[4]), arr[5], arr[6], arr[7]);
			allAuctions.add(a);
		}
		
		reader.close();
	}

	public void addAuction(Auctions obj) {
		allAuctions.add(obj);
	}
	
	public ArrayList<Auctions> getAllAuctions() {
		return allAuctions;
	}

	public void printAuctions(ArrayList<Auctions> obj) throws IOException{
		if(allAuctions == null) {
			System.out.println("No auctions!");
			return;
		}
		
		int objIndex = 0;
		for(Auctions a : obj) {
			
			System.out.println(String.format("Auction No. %d     Status: %s     Item name: %s     Category: %s     Current bid: $%s0     Highest Bidder: %s", 
					objIndex, a.getAuctionStatus(), a.getItemName(), a.getCategory(), Float.toString(a.getCurrentBid()+a.getStartingAmount()), a.getHighestBidder()));
			objIndex++;
		}
	}
	
	public void printFilteredAuctions(Scanner in, ArrayList<Auctions> obj) throws IOException {
		ArrayList<Auctions> filteredAuctions = new ArrayList<>();
		if(allAuctions == null) {
			System.out.println("No auctions!");
			return;
		}
		System.out.println("What Category do you want to filter by?");
		System.out.println("\nAppliances\tClothing\tElectronics"
				+"\nFurniture\nToys");
		
		String choice = in.nextLine();

		for(Auctions x : obj) {
			if (x.getCategory().equals(choice)) {
				filteredAuctions.add(x);
			}
		}
		
		printAuctions(filteredAuctions);
	}
}
