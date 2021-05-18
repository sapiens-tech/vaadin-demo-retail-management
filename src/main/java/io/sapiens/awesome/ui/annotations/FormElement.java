package io.sapiens.awesome.ui.annotations;

import io.sapiens.awesome.ui.enums.FormElementType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface FormElement {
  FormElementType type() default FormElementType.TextField;

  String label() default "";

  String formSectionHeader() default "";
}