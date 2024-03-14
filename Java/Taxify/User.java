package taxify;

public class User implements IUser {
	
	private int id;
    private String firstName;
    private String lastName;
    private ITaxiCompany company;
    private boolean service;
    
    public User(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.service = false;
    }

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getFirstName() {
		return this.firstName;
	}

	@Override
	public String getLastName() {
		return this.lastName;
	}

	@Override
	public boolean getService() {
		return this.service;
	}

	@Override
	public void setService(boolean service) {
		this.service = service;
	}

	@Override
	public void requestService() {
		System.out.println("\n~~~~~~~~~~~~~~~~\nUSER ID: " + String.valueOf(this.id) + " REQUESTED SERVICE");
		this.company.provideService(this.id);
	}

	@Override
	public void rateService(IService service) {
		// users rate around 50% of the services (1 to 5 stars)
        
        if (ApplicationLibrary.rand() % 2 == 0)
            service.setStars(ApplicationLibrary.rand(5) + 1);
	}
	
	@Override
	public void setCompany(ITaxiCompany company) {
		this.company = company;
	}
	
	@Override
	public boolean acceptSharedRide() {
		if (ApplicationLibrary.rand() % 2 == 0) {
			System.out.println("USER: " + String.valueOf(this.id) + " ACCEPTED SHARED RIDE" );
			return true;
		}
		System.out.println("USER: " + String.valueOf(this.id) + " DECLINED SHARED RIDE" );
		return false;
	}
	


}
