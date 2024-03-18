package users;

import java.util.Scanner;
import com.odu.Accounts;

public class Admin extends User{
	
	public Admin(String username, String password) {
		super(username, password);
	}
	
	//Asks for a username and password for a new auctioneer account. Calls the addAccount() method from the jar file
	public void createAuctioneer(Scanner in, Accounts obj) {
		System.out.println("Enter a new username: ");
		setUsername(in.nextLine());
		System.out.println("Create a password: ");
		setPassword(in.nextLine());
		obj.addAccount(getUsername(), getPassword());
	}
}
