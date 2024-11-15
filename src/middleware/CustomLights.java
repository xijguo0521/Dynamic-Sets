package middleware;

import middleware.aggregation.AverageAggregation;
import middleware.annotations.Aggregation;
import middleware.annotations.Config;
import middleware.annotations.Mixin;
import objectspace.ILight;

public interface CustomLights extends ILight, IDynSet<ILight> {
	
	// remix of existing function
	@Aggregation(clazz = AverageAggregation.class, method = "isPowered")
	public float getBrightness();
	
	// mixin of additional functions
	@Mixin(clazz = StepSwitch.class)
	@Config(name = "setps", value = "10")
	public StepSwitch stepSwitch();

}
