package taxify;

public interface IUser {
	
	public int getId();
    public String getFirstName();
    public String getLastName();
    public boolean getService();
    public void setService(boolean service);
    public void requestService();
    public void rateService(IService service);
    public void setCompany(ITaxiCompany company);
    public boolean acceptSharedRide();
    public String toString();
    
}
