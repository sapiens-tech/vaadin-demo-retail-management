package io.sapiens.awesome.ui.annotations;

import io.sapiens.awesome.ui.enums.FormFieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface FormField {
  FormFieldType type() default FormFieldType.TextField;

  String label() default "";

  FormGroup group() default @FormGroup();
}

@interface FormGroup {
  String name() default "";

  int order() default 0;
}
