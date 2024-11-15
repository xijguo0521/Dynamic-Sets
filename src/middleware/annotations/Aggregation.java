package middleware.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import middleware.aggregation.AAggregations;

@Repeatable(Aggregations.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aggregation {
	Class<? extends AAggregations> clazz();
	String method();
}
