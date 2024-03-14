package taxify;

import java.util.ArrayList;
import java.util.List;

public class TestProgram {
	
	public static void main(String[] args) {
		
		
		List<IUser> users = new ArrayList<IUser>();
    	
		users.add(new User(1, "Fred", "OBrien"));
		users.add(new User(2, "John", "OBrien"));

    	users.add(new User(3, "Janet", "Chris"));
    	users.add(new User(4, "Bob", "Jordan"));
    	users.add(new User(5, "Henry", "McCalister"));
    	users.add(new User(6, "Lily", "Brand"));
    	users.add(new User(7, "Bryant", "Hopper"));
    	users.add(new User(8, "Zach", "Christon"));
    	users.add(new User(9, "Rosie", "Flower"));
    	users.add(new User(10, "Greg", "Choopie"));
    	users.add(new User(11, "Brendan", "OReilly"));
    	users.add(new User(12, "Lylant", "Jordy"));
    	users.add(new User(13, "Nick", "Rostie"));
    	users.add(new User(14, "Joseph", "McBrien"));
    	users.add(new User(15, "Elise", "Darby"));


		
		
    	List<IVehicle> vehicles = new ArrayList<IVehicle>();
    	
    	vehicles.add(new Shuttle(1, ApplicationLibrary.randomLocation()));
    	vehicles.add(new Shuttle(2, ApplicationLibrary.randomLocation()));

    	vehicles.add(new Shuttle(3, ApplicationLibrary.randomLocation()));
    	
    	vehicles.add(new Shuttle(4, ApplicationLibrary.randomLocation()));
    	vehicles.add(new Shuttle(5, ApplicationLibrary.randomLocation()));
    	vehicles.add(new Taxi(6, ApplicationLibrary.randomLocation()));
    	vehicles.add(new Taxi(7, ApplicationLibrary.randomLocation()));
    	vehicles.add(new Taxi(8, ApplicationLibrary.randomLocation()));
    	vehicles.add(new Taxi(9, ApplicationLibrary.randomLocation()));
    	vehicles.add(new Taxi(10, ApplicationLibrary.randomLocation()));
/*
    	vehicles.add(new Scooter(11, ApplicationLibrary.randomLocation()));
    	vehicles.add(new Scooter(12, ApplicationLibrary.randomLocation()));
    	vehicles.add(new Bicycle(13, ApplicationLibrary.randomLocation()));
    	vehicles.add(new Bicycle(14, ApplicationLibrary.randomLocation()));
*/  	
	
		TaxiCompany taxify = new TaxiCompany("Taxify", users, vehicles);
		ApplicationSimulator application = new ApplicationSimulator(taxify, users, vehicles);
		
		taxify.addObserver(application);
		
		// running simulation
		
		application.show();


		for (int i=0 ; i<5 ; i++) {
			application.requestService();
		}
		
		do {
			
			System.out.println("total services: " + String.valueOf(application.getTotalServices()));
			
			application.show();
			application.update();
			
			if (ApplicationLibrary.rand() % 4 == 0)
				application.requestService();
			
		} while (application.getTotalServices() != 0);

		application.showStatistics();
		
	}

}
