package taxify;

public class Statistics implements IStatistics {

	private int services;
	private int reviews;
	private double stars;
	private int distance;
	private int billing;
	

	public int getServices() {
		return this.services;
	}
	
    public int getReviews() {
    	return this.reviews;
    }
    
    public double getStars() {
    	double stars = (double) this.stars / (double) this.reviews;
    	
    	return Math.round(stars * 100.0)/100.0;
    }
    
    public int getDistance() {
    	return this.distance;
    }
    
    public int getBilling() {
    	return this.billing;
    }
    
    public void updateServices() {
    	this.services += 1;
    }
    
    public void updateReviews() {
    	this.reviews += 1;
    }
    
    public void updateStars(int stars) {
    	this.stars += stars;
    }
    
    public void updateDistance(int distance) {
    	this.distance += distance;
    }
    
    public void updateBilling(int billing) {
    	this.billing += billing;
    }

}
