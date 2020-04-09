package edu.purdue.cs.barista;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * The {@link TestSuite} annotation allows Gradescope to test methods
 * in a given class.
 *
 * @author Andrew Davis, drew@drewdavis.me
 * @version 1.0, 10/17/2019
 * @since 1.0
 */
@Target({ElementType.TYPE})
public @interface TestSuite {}
