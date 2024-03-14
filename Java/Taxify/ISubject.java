package taxify;

public interface ISubject {
	
	public void addObserver(IObserver observer);
	public void notifyObservers(String message);

}
