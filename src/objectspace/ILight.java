package objectspace;

public interface ILight {

	public boolean isPowered();
	public void setPowered(boolean pwr);

	public boolean isColored();
	public void setColored(boolean clr);

	public int getLuminosity();
	public void setLuminosity(int lum);

	@Override
	public boolean equals(Object o);
	
}