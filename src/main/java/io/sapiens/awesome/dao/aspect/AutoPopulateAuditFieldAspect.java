package io.sapiens.awesome.dao.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AutoPopulateAuditFieldAspect {
  public static final String AUDIT_FIELD_MODEL_PACKAGE_NAME = ".model.";
  public static final String AUDIT_FIELD_COLLECTION_TYPE_NAME = "java.util.List";
  private static final String AUDIT_FIELD_NAME_DATE_CREATED = "createdOn";
  private static final String AUDIT_FIELD_NAME_DATE_UPDATED = "updatedOn";
  private static final String AUDIT_FIELD_NAME_CREATED_BY = "createdBy";
  private static final String AUDIT_FIELD_NAME_UPDATED_BY = "updatedBy";
  private static final String AUDIT_FIELD_NAME_CHANGE_LOG = "changeLog";
}
