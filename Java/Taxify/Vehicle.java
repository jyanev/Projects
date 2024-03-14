package taxify;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle implements IVehicle {
	
	private int id;
    private ITaxiCompany company;
    private List<IService> services;
    private VehicleStatus status;
    private ILocation location;
    private ILocation destination;
    private IStatistics statistics;
    private List<ILocation> route;
    private int passengers;
	
	public Vehicle(int id, ILocation location) {
		
		this.id = id;
		this.services = new ArrayList<IService>();
		this.status = VehicleStatus.FREE;
		this.location = location;
		this.destination = ApplicationLibrary.randomLocation(this.location);
		this.statistics = new Statistics();
		this.route = setDrivingRouteToDestination(this.location, this.destination);
		this.passengers = 0;
		
	}

    @Override
    public int getId() {
        return this.id;
    }
 
    @Override
    public ILocation getLocation() {
        // returns location;
    	return this.location;
    }

    @Override
    public ILocation getDestination() {
        // returns destination;
    	return this.destination;
    }
    
    @Override
    public IService getService() {
        // returns service;
    	return this.services.get(this.services.size()-1);
    }
    
    @Override
    public IStatistics getStatistics() {
        // returns statistics;
    	return this.statistics;
    }
    
    @Override
    public void setCompany(ITaxiCompany company) {
        this.company = company;
    }
    
    @Override
    public void pickService(IService service) {
        // pick a service, set destination to the service pickup location, and status to "pickup"
        
        //this.service = service;
    	this.services.add(service);
        this.destination = services.get(this.services.size()-1).getPickupLocation();
        this.route = this.setDrivingRouteToDestination(this.location, this.destination);
        this.status = VehicleStatus.PICKUP;
        this.passengers++;
        
    }
    
    /*
    @Override
    public String getStatus() {
    	return this.status.name();
    }
    */

    @Override
    public void startService() {
        // set destination to the service drop-off location, update the driving route, set status to "service"
    	this.destination = this.services.get(this.services.size()-1).getDropoffLocation();
    	this.route = setDrivingRouteToDestination(this.location, this.destination);
    	if (this.passengers == 2)
    		this.status = VehicleStatus.SHAREDSERVICE;
    	else
    		this.status = VehicleStatus.SERVICE;
    	
    }

    @Override
    public void endService() {
        // update vehicle statistics
    	
        this.statistics.updateBilling(this.calculateCost());
        this.statistics.updateDistance(this.services.get(this.services.size()-1).calculateDistance());
        this.statistics.updateServices();
        
        // if the service is rated by the user, update statistics
        
        if (this.services.get(this.services.size()-1).getStars() != 0) {
            this.statistics.updateStars(this.services.get(this.services.size()-1).getStars());
            this.statistics.updateReviews();
        }
        
        // set service to null, and status to "free"
        
        this.services.remove(this.services.size()-1);
        this.passengers--;
        
    	if (this.services.size() > 0) {
    		startService();
    	}
    	else {
	        this.destination = ApplicationLibrary.randomLocation(this.location);
	        this.route = setDrivingRouteToDestination(this.location, this.destination);
	        this.status = VehicleStatus.FREE;
    	}
    }

    @Override
    public void notifyArrivalAtPickupLocation() {
        // notify the company that the vehicle is at the pickup location and start the service
    	this.company.arrivedAtPickupLocation(this);
    	this.startService();
    }
        
    @Override
    public void notifyArrivalAtDropoffLocation() {
        this.company.arrivedAtDropoffLocation(this);
        this.endService();
     }
        
    @Override
    public boolean isFree() {
        // returns true if the status of the vehicle is "free" and false otherwise
    	if (this.status == VehicleStatus.FREE) {return true;}
    	return false;
    }   
    
    @Override
    public boolean isInService() {
        // returns true if the status of the vehicle is "free" and false otherwise
    	if (this.status == VehicleStatus.SERVICE) {return true;}
    	return false;
    }   
    
    @Override
    public void move() {
        // get the next location from the driving route
    	
    	if (!this.route.isEmpty()) {
        
	        this.location = this.route.get(0);        
	        this.route.remove(0);
    	
    	}

        if (this.route.isEmpty()) {
            if (this.services.isEmpty()) {
                // the vehicle continues its random route

                this.destination = ApplicationLibrary.randomLocation(this.location);
                this.route = setDrivingRouteToDestination(this.location, this.destination);
            }
            else {
                // checks if the vehicle has arrived to a pickup or drop off location

                ILocation origin = this.services.get(this.services.size()-1).getPickupLocation();
                ILocation destination = this.services.get(this.services.size()-1).getDropoffLocation();

                if (this.location.getX() == origin.getX() && this.location.getY() == origin.getY()) {

                    notifyArrivalAtPickupLocation();

                } else if (this.location.getX() == destination.getX() && this.location.getY() == destination.getY()) {

                    notifyArrivalAtDropoffLocation();

                }        
            }
        }
    }

    @Override
    public int calculateCost() {
        // returns the cost of the service as the distance
    	return this.services.get(this.services.size()-1).calculateDistance();
    }

    @Override
    public String showDrivingRoute() {
        String s = "";
       
           for (ILocation l : this.route)
               s = s + l.toString() + " ";
       
           return s;
    }
    
    @Override
    public int getPassengers() {
    	return this.passengers;
    }

    @Override
    public String toString() {
        return this.id + " at " + this.location + " driving to " + this.destination +
               ((this.status == VehicleStatus.FREE) ? " is free with path " + showDrivingRoute(): ((this.status == VehicleStatus.PICKUP) ? " to pickup user " +
               this.services.get(this.services.size()-1).getUser().getId() : " in service "));
    }
    
    private List<ILocation> setDrivingRouteToDestination(ILocation location, ILocation destination) {
        List<ILocation> route = new ArrayList<ILocation>();
        
        int x1 = location.getX();
        int y1 = location.getY();
        
        int x2 = destination.getX();
        int y2 = destination.getY();
        
        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);
       
        for (int i=1; i<=dx; i++) {
            x1 = (x1 < x2) ? x1 + 1 : x1 - 1;

            route.add(new Location(x1, y1));
        }
        
        for (int i=1; i<=dy; i++) {
            y1 = (y1 < y2) ? y1 + 1 : y1 - 1;
            
            route.add(new Location(x1, y1));
        }
        
        return route;
    } 	
    
    @Override
    public boolean isNear(ILocation other) {
    	if (ApplicationLibrary.distance(this.location, other) < 4) {
    		return true;
    	}
    	return false;
    	
    }

}
