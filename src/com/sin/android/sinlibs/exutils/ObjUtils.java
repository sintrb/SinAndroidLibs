package com.sin.android.sinlibs.exutils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ObjUtils {
	public static void mergeObj(Object dst, Object src) {
		if(dst==null || src==null)
			return;
		for (Field fsrc : src.getClass().getFields()) {
			try {
				Field fdst = dst.getClass().getField(fsrc.getName());
				if (fdst == null)
					continue;
				int msrc = fsrc.getModifiers();
				int mdst = fdst.getModifiers();
				Object osrc = fsrc.get(src);
				Object odst = fsrc.get(dst);
				if (fdst.getType()==fsrc.getType() &&
						osrc != null &&
						(!(osrc instanceof Number) || ((Number)odst).intValue()==0) &&
//						fdst.isAccessible() && fsrc.isAccessible() &&
						!Modifier.isStatic(mdst) && !Modifier.isFinal(mdst) &&
						!Modifier.isStatic(msrc) && !Modifier.isFinal(msrc)) {
					fdst.set(dst, osrc);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
