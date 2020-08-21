package edu.purdue.cs.barista;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@link TestSuite} annotation allows Gradescope to test methods
 * in a given class.
 *
 * @author Andrew Davis, drew@drewdavis.me
 * @version 1.0, 2019-10-17
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TestSuite {}
