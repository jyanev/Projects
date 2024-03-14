package taxify;

import java.util.List;

public class TaxiCompany implements ITaxiCompany, ISubject {

	private String name;
    private List<IUser> users;
    private List<IVehicle> vehicles;
    private int totalServices;
    private IObserver observer;
    
    public TaxiCompany(String name, List<IUser> users, List<IVehicle> vehicles) {
        this.name = name;
        this.users = users;
        this.vehicles = vehicles;        
        this.totalServices = 0;
        
        // set the taxi company for users and vehicles
        
        for (int i=0; i<this.users.size(); i++)
            this.users.get(i).setCompany(this);
        
        for (int i=0; i<this.vehicles.size(); i++)
            this.vehicles.get(i).setCompany(this);
    }

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getTotalServices() {
		return this.totalServices;
	}

	@Override
	public boolean provideService(int user) {
		int userIndex = indexOfUserId(user);
        int vehicleIndex = findFreeVehicle();
        int sharedVehicleIndex;
        
        // if there is a free vehicle, assign a random pickup and drop-off location to the new service
        // the distance between the pickup and the drop-off location should be at least 3 blocks
        
        if (vehicleIndex != -1) {

        	ILocation origin, destination;
            
            do {
                origin = ApplicationLibrary.randomLocation();
            	destination = ApplicationLibrary.randomLocation(origin);
                
            } while (ApplicationLibrary.distance(origin, this.vehicles.get(vehicleIndex).getLocation()) < 3);
            
            
            // update the user status
            
            this.users.get(userIndex).setService(true);
            
            // create a service with the user, the pickup and the drop-off location

            Service service = new Service(this.users.get(userIndex), origin, destination);
            
            // check for shared rides
            
            sharedVehicleIndex = findNearOccupiedVehicle(origin);
            if (sharedVehicleIndex != -1 && this.users.get(userIndex).acceptSharedRide() && this.vehicles.get(sharedVehicleIndex).getService().getUser().acceptSharedRide()) {
            	
            	// assign the new service to in service vehicle
            	
            	this.vehicles.get(sharedVehicleIndex).pickService(service);
            	System.out.println("SHARED RIDE ACCEPTED");
            	System.out.println("LOCATION: " + origin.toString() + "\nPASSENGERS: " + String.valueOf(this.vehicles.get(sharedVehicleIndex).getPassengers()) + "\n~~~~~~~~~~~~~~~~\n");
            	notifyObservers("User " + this.users.get(userIndex).getId() + " requests a service from " + service.toString() + ", the ride is assigned to " +
                        this.vehicles.get(sharedVehicleIndex).getClass().getSimpleName() + " " + this.vehicles.get(sharedVehicleIndex).getId() + " at location " +
                        this.vehicles.get(sharedVehicleIndex).getLocation().toString());
            }
            else {
            	
            	// assign the new service to free vehicle
                this.vehicles.get(vehicleIndex).pickService(service);
                System.out.println("LOCATION: " + origin.toString() + "\nPASSENGERS: " + String.valueOf(this.vehicles.get(vehicleIndex).getPassengers()) + "\n~~~~~~~~~~~~~~~~\n");
                notifyObservers("User " + this.users.get(userIndex).getId() + " requests a service from " + service.toString() + ", the ride is assigned to " +
                        this.vehicles.get(vehicleIndex).getClass().getSimpleName() + " " + this.vehicles.get(vehicleIndex).getId() + " at location " +
                        this.vehicles.get(vehicleIndex).getLocation().toString());
            
            }

            // update the counter of services
            
            this.totalServices++;
            
            return true;
        }
        
        return false;
	}

	@Override
	public void arrivedAtPickupLocation(IVehicle vehicle) {
		// a vehicle arrives at the pickup location
        
        notifyObservers(String.format("%-8s",vehicle.getClass().getSimpleName()) + 
        			   vehicle.getId() + " loads user " + 
        			   vehicle.getService().getUser().getId());
	}

    
    @Override
    public void arrivedAtDropoffLocation(IVehicle vehicle) {
        // a vehicle arrives at the drop-off location
        
        IService service = vehicle.getService();       
        int user = service.getUser().getId();
        int userIndex = indexOfUserId(user);
       
        // the taxi company requests the user to rate the service, and updates its status

        this.users.get(userIndex).rateService(service);
        this.users.get(userIndex).setService(false);

        // update the counter of services
        
        this.totalServices--;    
        
        notifyObservers(String.format("%-8s",vehicle.getClass().getSimpleName()) + vehicle.getId() + " drops off user " + user);

	}
    
    @Override
	public void addObserver(IObserver observer) {
		this.observer = observer;
	}

	@Override
	public void notifyObservers(String message) {
		this.observer.updateObserver(message);
	}
    
    private int findFreeVehicle() {
        // finds a free vehicle and returns the index of the vehicle in the list, otherwise it returns -1
    	int index = 0;
    	for (IVehicle v : vehicles) {
    		if (v.isFree()) {
    			return index;
    		}
    		else {
    			++index;
    		}
    	}
    	return -1;
    }


    private int findNearOccupiedVehicle(ILocation other) {
    	int index = 0;
    	for (IVehicle v : vehicles) {
    		if (v.isInService() && v.isNear(other) && v.isInService()) {
    			return index;
    		}
    		index++;
    	}
    	return -1;
    }


    private int indexOfUserId(int id) {
    	// finds the index of a user with the input id in the list, otherwise it returns -1
    	int index = 0;
        for (IUser u : users) {
        	if (u.getId() == id) {
        		return index;
        	}
        	else {
        		++index;
        	}
        }
        return -1;
    }
}
