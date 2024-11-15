package objectspace;

public class ShadeImpl implements IShade{

	private float pos;
	
	public ShadeImpl(float pos) {
		this.pos = pos;
	}
	
	@Override
	public float getPosition() {
		return pos;
	}

	@Override
	public void up() {}

	@Override
	public void down() {}

	@Override
	public void stop() {}

	@Override
	public void setPosition(float pos) {
		this.pos = pos;
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof IShade)) {
			return false;
		}
		return ((IShade) o).getPosition() == this.pos;
	}
}
