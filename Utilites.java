package utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import auctions.AllAuctions;
import auctions.Auctions;
import users.*;
import com.odu.Accounts;

//Imports classes from Accounts.jar file in order to manage accounts.
public class Utilites {
	private Accounts customers = new Accounts("C");
	private Accounts auctioneers = new Accounts("A");
	private Accounts admin = new Accounts("ADMIN");

	private AllAuctions all = new AllAuctions();
	private ArrayList<Customer> customerList = new ArrayList<>();
	private ArrayList<Auctioneer> auctioneerList = new ArrayList<>();
	public ArrayList<Auctions> auctions = all.getAllAuctions();

	//Driver code called in main
	public void viewMainMenu() throws IOException {
		Scanner in = new Scanner(System.in);
		String choice = "";
		
		while(choice != "3") {
			System.out.println("1. Create an account   2. Sign in   3. Exit");
			choice = in.nextLine();
			
			switch(choice) {
				case "1": 
					customerAccountCreation(in);
					break;
				case "2":
					login(in);
					break;
				case "3":
					writeToAuctions();
					System.out.println("Quitting...");
					System.exit(0);
					break;
				default:
					System.out.println("Cannot perform this action.");
			}
		}
		
		in.close();
	}
	
	//Menu method for customer users
	private void viewCustomerMenu(String username, String password, Scanner in) throws IOException {
		Customer customer = new Customer(username, password, all);
		
		if(customerList.contains(customer) == false) {
			customerList.add(customer);
		}
		
		System.out.println("Hello, " + username);
		String choice = null;
		
		while(choice != "9") {
			System.out.println("1. View all auctions     2. Filter auctions" + "\n" +
					"3. View watch list     4. Add/Remove from watch list" + "\n" +
					"5. View your bids     6. Make a bid" + "\n" +
					"7. View your balance     8. Add to balance" + "\n" +
					"9. Sign out");
			choice = in.nextLine();
			
			if(choice.equals("9")) {
				break;
			}
			else {
				switch(choice) {
				case "1":
					all.printAuctions(auctions);
					break;
				case "2":
					all.printFilteredAuctions(in, auctions);
					break;
				case "3":
					customer.printWatchList(all);
					break;
				case "4":
					customer.controlWatchList(all, in, auctions);
					break;
				case "5":
					customer.printBids(all);
					break;
				case "6":
					customer.makeBid(in, all, customerList);
					break;
				case "7":
					System.out.println("Current balance: $" + customer.getAccountBalance());
					break;
				case "8":
					customer.addToAccountBalance(in);
					break;
				default:
					System.out.println("Invalid input. Please try again: ");
				}
			}
		}
	}

	//Menu method for admin users, who can create auctioneer users
	private void viewAdminMenu(String username, Scanner in) {
		System.out.println("Hello, " + username);
		String choice = null;
		while(choice != "2") {
			System.out.println("1. Create auctioneer account     2. Exit");
			choice = in.nextLine();
			
			if(choice.equals("1")) {
				System.out.println("Enter new username: ");
				String user = in.nextLine();
				System.out.println("Create a password: ");
				String pwd = in.nextLine();
				auctioneers.addAccount(user, pwd);
			}
			else if(choice.equals("2")) {
				break;
			}
			else {
				System.out.println("Invalid input. Try again: ");
			}
		}
	}

	//Menu method for auctioneer users, who can create, start, and end auctions that belong to them
	private void viewAuctioneerMenu(String username, String password, Scanner in) throws IOException {
		Auctioneer auctioneer = new Auctioneer(username, password, all);
		auctioneer.setAuctions(all);
		
		if(auctioneerList.contains(auctioneer) == false) {
			auctioneerList.add(auctioneer);
		}
		
		System.out.println("Hello, " + username);
		String choice = null;
		
		while(choice != "5") {
			System.out.println("1. Create an auction     2. View your auctions" + "\n" +
								"3. Start an auction     4. End an auction" + "\n" + 
								"5. Sign out");
			choice = in.nextLine();
			
			if(choice.equals("5") ) {
				break;
			}
			else {
				switch(choice) {
				case "1":
					auctioneer.addAuctions(in, all);
					break;
				case "2":
					auctioneer.printAuctions(all);
					break;
				case "3":
					auctioneer.startAuction(in, all);
					break;
				case "4":
					auctioneer.endAuction(in, all);
					break;
				default:
					System.out.println("Invalid input. Please try again: ");
				}
			}
		}
	}
	
	//Method that checks for account type before asking to log in
	private void login(Scanner in) throws IOException {
		boolean active = true;
		String userType;
		while(active == true) {
			System.out.println("What type of account are you logging into: C(customer), A(auctioneer), ADMIN");
			userType = in.nextLine();
			if(userType.equals("C") || userType.equals("A") || userType.equals("ADMIN")) {
				signIn(in, userType);
				break;
			} 
			else{
				System.out.println("Incorrect Input");
			}
		}
		
	}
	
	//Method that logs the user in depending on their account type
	//signIn() method from Accounts class is called to check if the username and password are correct
	private void signIn(Scanner in, String userType) throws IOException {
		System.out.println("Enter username: ");
		String username = in.nextLine();
		System.out.println("Enter password: ");
		String pwd = in.nextLine();
		
		switch(userType) {
			case "C":
				if(customers.signIn(username, pwd)) {
					viewCustomerMenu(username, pwd, in);
				}
				break;
			case "ADMIN":
				if(admin.signIn(username, pwd)) {
					viewAdminMenu(username, in);
				}
				break;
			case "A":
				if(auctioneers.signIn(username, pwd)) {
					viewAuctioneerMenu(username, pwd, in);
				}
				break;
			default:
				System.out.println("There was trouble signing in.");
		}
	}

	//Method that creates a customer user
	//Upon receiving a username and password, addAccount() method is called from the Accounts class to check if the user already exists
	//All users and their passwords are stored in .txt files for their respective types so they can always be accessed
	private void customerAccountCreation(Scanner in) {
		System.out.println("Enter new username: ");
		String user = in.nextLine();
		System.out.println("Create a password: ");
		String pwd = in.nextLine();
		customers.addAccount(user, pwd);
	}

	//Auctions are written to a .txt file before the program is exited
	private void writeToAuctions() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("Auctions.txt"));
		
		for(Auctions a : all.getAllAuctions()) {
			writer.write(a.getPostedTime()+","+a.getItemName()+","+a.getCategory()+","+
					a.getStartingAmount()+","+a.getCurrentBid()+","+a.getHighestBidder()+
					","+a.getAuctionStatus()+","+a.getAuctioneer()+"\n");
		}

		writer.close();
	}
}
