package practice.guestregistry.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// https://github.com/eugenp/tutorials/blob/71b7954aed845bae4786d002e57fbc7cbcce0564/spring-security-modules/spring-security-core/src/main/java/com/baeldung/methodsecurity/annotation/IsViewer.java
@Target({ElementType.TYPE, ElementType.METHOD}) // Can use annotation on class (TYPE) and method (METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('USER')")
public @interface IsUser {
}
