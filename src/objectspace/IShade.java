package objectspace;

public interface IShade {
	public float getPosition();
	public void up();
	public void down();
	public void stop();
	public void setPosition(float pos);
	
	@Override
	public boolean equals(Object o);
}
