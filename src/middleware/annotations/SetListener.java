package middleware.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Repeatable(SetListeners.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface SetListener {
	Class<?> clazz();
}
