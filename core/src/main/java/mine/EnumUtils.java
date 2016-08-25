package mine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnumUtils {

	@SuppressWarnings("unchecked")
	public static <T> T callValueOf(Class<T> enu, String value) {
		
		try {
			Method method = enu.getMethod("valueOf", new Class[]{ String.class });
			Object obj = method.invoke(null, value);
			return (T)obj;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}
}
