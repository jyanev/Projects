package taxify;

import java.util.List;

public class ApplicationSimulator implements IApplicationSimulator, IObserver {

	private ITaxiCompany company;
    private List<IUser> users;
    private List<IVehicle> vehicles;
    
    public ApplicationSimulator(ITaxiCompany company, List<IUser> users, List<IVehicle> vehicles) {
        this.company = company;
        this.users = users;
        this.vehicles = vehicles;
    }
    
	@Override
	public void show() {
		// shows the status of the vehicles
        
        System.out.println("\n" + this.company.getName() + " status \n");

        for (int i=0; i<this.vehicles.size(); i++) {
            System.out.println(this.vehicles.get(i).toString());
        }  
	}

	@Override
	public void showStatistics() {
		System.out.println("\n" + this.company.getName() + " statistics\n");
		for (IVehicle v : this.vehicles) {
			System.out.println(StringPadding.rightPadding(v.getClass().getSimpleName(), 8) + 
							   StringPadding.rightPadding(String.valueOf(v.getId()), 3) +
							   StringPadding.rightPadding(String.valueOf(v.getStatistics().getServices()) + " services", 12) + 
							   StringPadding.rightPadding(String.valueOf(v.getStatistics().getDistance()) + " km.", 6) + 
							   StringPadding.rightPadding(String.valueOf(v.getStatistics().getBilling()) + " eur.", 7) +
							   StringPadding.rightPadding(String.valueOf(v.getStatistics().getReviews()) + " reviews", 10) + 
							   StringPadding.rightPadding(String.valueOf(v.getStatistics().getStars() + " stars"), 8));
		}

	}

	@Override
	public void update() {
		// moves the vehicles to their next location
        
        for (int i=0; i<this.vehicles.size(); i++) {
               this.vehicles.get(i).move();
        }	
    }

	@Override
	public void requestService() {
		for (IUser u : users) {
			if (!u.getService()) {
				u.requestService();
				break;
			}
		}
	}

	@Override
	public int getTotalServices() {
		return this.company.getTotalServices();
	}
	
	@Override
	public void updateObserver(String message) {
		System.out.println(message);
	}

}
