package objectspace;

public class LightImpl implements ILight {

	private boolean pwr;
	private boolean clr;
	private int lum;

	public LightImpl(boolean pwr, boolean clr, int lum) {
		this.pwr = pwr;
		this.clr = clr;
		this.lum = lum;
	}

	@Override
	public boolean isPowered() {
		return pwr;
	}

	@Override
	public void setPowered(boolean pwr) {
		this.pwr = pwr;
	}

	@Override
	public boolean isColored() {
		return clr;
	}

	@Override
	public void setColored(boolean clr) {
		this.clr = clr;
	}

	@Override
	public int getLuminosity() {
		return lum;
	}

	@Override
	public void setLuminosity(int lum) {
		this.lum = lum;
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof ILight)) {
			return false;
		}
		return ((ILight) o).getLuminosity() == this.lum &&
				((ILight) o).isPowered() == this.pwr &&
				((ILight) o).isColored() == this.clr;
	}
	
	@Override
	public String toString() {
		String out = "";
		out +=  this.getClass().getSimpleName() + " ";
		out += "pwr:" + this.isPowered() + ", ";
		out += "lum:" + this.getLuminosity() + ", ";
		out += "clr:" + this.isColored();
		return out;
		
	}
}
