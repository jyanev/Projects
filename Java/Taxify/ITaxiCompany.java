package taxify;

public interface ITaxiCompany {
	
	public String getName();    
    public int getTotalServices();
    public boolean provideService(int user);
    public void arrivedAtPickupLocation(IVehicle vehicle);
    public void arrivedAtDropoffLocation(IVehicle vehicle);

    // more methods will be declared in upcoming sprints

}
