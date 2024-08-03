package com.hamze.banking.system.shared.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ReflectionUtil {

    private static final Map<Class<?>, Map<String, List<Annotation>>> ANNOTATIONS_BY_CLASS_FIELD_MAP = new ConcurrentHashMap<>();
    private static final Map<Class<?>, List<String>> CLASS_FIELD_NAME_MAP = new ConcurrentHashMap<>();
    private static final Map<Class<?>, List<Field>> CLASS_FIELD_MAP = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <I> Class<I> getGenericParameterType(Object object, int parameterIndex) {

        Class<?> type = object.getClass();
        Type[] typeArguments;
        while (true) {
            Type genericSuperClass = type.getGenericSuperclass();
            assert genericSuperClass != null;
            if (genericSuperClass instanceof ParameterizedType) {
                typeArguments = ((ParameterizedType) genericSuperClass).getActualTypeArguments();
                break;
            }

            type = type.getSuperclass();
        }

        Type genericParameterType;
        if (typeArguments.length > parameterIndex) {
            genericParameterType = typeArguments[parameterIndex];
        } else {
            throw new IllegalArgumentException(
                    String.format("Generic parameter with index [%d] doesn't exist", parameterIndex));
        }

        if (genericParameterType instanceof ParameterizedType) {
            return (Class<I>) ((ParameterizedType) genericParameterType).getRawType();
        } else if (genericParameterType instanceof Class) {
            return (Class<I>) genericParameterType;
        } else {
            throw new IllegalArgumentException("Bad input object");
        }
    }

    public static List<String> getFieldNames(Class<?> clazz) {
        List<String> fieldNames = CLASS_FIELD_NAME_MAP.get(clazz);
        if (fieldNames == null) {
            cacheFields(clazz);
            fieldNames = CLASS_FIELD_NAME_MAP.get(clazz);
        }

        return fieldNames;
    }

    private static synchronized List<Field> cacheFields(Class<?> clazz) {
        List<Field> totalFields;
        if (!CLASS_FIELD_MAP.containsKey(clazz)) {
            totalFields = new ArrayList<>();
            Class<?> copyClass = clazz;
            while (copyClass != Object.class) {
                totalFields.addAll(Arrays.asList(copyClass.getDeclaredFields()));
                copyClass = copyClass.getSuperclass();
            }
            CLASS_FIELD_MAP.put(clazz, totalFields);

            List<String> fieldNames = new ArrayList<>(totalFields.size());
            for (Field field : totalFields) {
                fieldNames.add(field.getName());
            }
            CLASS_FIELD_NAME_MAP.put(clazz, fieldNames);
        } else {
            totalFields = CLASS_FIELD_MAP.get(clazz);
        }

        return totalFields;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Annotation> T getFieldAnnotation(Class<?> clazz, String fieldName, Class<T> annotationClass) {
        T targetAnnotation = null;

        List<Annotation> annotations = getFieldAnnotations(clazz, fieldName);
        for (Annotation annotation : annotations) {
            if (annotationClass.isInstance(annotation)) {
                targetAnnotation = (T) annotation;
                break;
            }
        }

        return targetAnnotation;
    }

    public static List<Annotation> getFieldAnnotations(Class<?> clazz, String fieldName) {

        Map<String, List<Annotation>> annotations = ANNOTATIONS_BY_CLASS_FIELD_MAP.get(clazz);

        if (annotations == null) {
            cacheFieldAnnotations(clazz);
        }

        return ANNOTATIONS_BY_CLASS_FIELD_MAP.get(clazz).get(fieldName);
    }

    public static List<Field> getFields(Class<?> clazz) {
        List<Field> fields = CLASS_FIELD_MAP.get(clazz);
        if (fields == null) {
            fields = cacheFields(clazz);
        }

        return fields;
    }

    public static Object getValue(Class<?> clazz, String fieldName, Object object) {

        if (object == null || fieldName == null || clazz == null) {
            return null;
        }

        Field field = ReflectionUtil.getField(clazz, fieldName);
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        Field targetField = null;
        List<Field> fields = getFields(clazz);

        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                targetField = field;
                break;
            }
        }

        return targetField;
    }

    private static synchronized void cacheFieldAnnotations(Class<?> clazz) {
        if (!ANNOTATIONS_BY_CLASS_FIELD_MAP.containsKey(clazz)) {
            Map<String, List<Annotation>> annotationsMap = new HashMap<>();
            List<Field> fields = getFields(clazz);

            for (Field field : fields) {
                annotationsMap.put(field.getName(), Arrays.asList(field.getAnnotations()));
            }

            ANNOTATIONS_BY_CLASS_FIELD_MAP.put(clazz, annotationsMap);
        }
    }
}
