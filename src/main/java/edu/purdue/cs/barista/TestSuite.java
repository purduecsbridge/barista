package edu.purdue.cs.barista;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@link TestSuite} annotation allows Gradescope to test methods
 * in a given class. It is not required to add this annotation to a
 * class that contains test cases, but it is best practice to do so.
 *
 * @author Andrew Davis, asd@alumni.purdue.edu
 * @version 1.0, 2019-10-17
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TestSuite {}
