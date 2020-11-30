# Barista

##### Note: This project has been retired. [Percolator](https://github.com/purduecsbridge/percolator) is a new grading framework that builds off of Barista's best aspects, while adding new functionality. Barista will continue to be hosted here, but will no longer receive updates. See [#18](https://github.com/purduecsbridge/barista/issues/18) for more information.

[![build](https://img.shields.io/github/workflow/status/purduecsbridge/barista/Deploy/main)](https://github.com/purduecsbridge/barista/actions?query=workflow%3A%22Deploy%22+branch%3Amain)
[![javadoc](https://img.shields.io/badge/docs-javadoc-blue)](https://purduecsbridge.github.io/barista)

Barista is a grading package for Java Gradescope assignments. It is meant to make the development and maintenance of programming assignments easier for courses using Java. How easy is it?

```java
import org.junit.Test;
import edu.purdue.cs.barista.TestCase;
import edu.purdue.cs.barista.TestSuite;

@TestSuite
public class HelloWorldTests {

  @Test
  @TestCase(name = "Hello test", points = 50.0)
  public void testHello() {
    // Test code here
  }
  
  @Test
  @TestCase(name = "Goodbye test", points = 50.0)
  public void testGoodbye() {
    // Test code here
  }

}
```

That's a test suite written using Barista. Pretty simple, right? Call the `GradescopeGrader.run()` method to run your test suites and automatically print the results in JSON.

Want more customization? Feel free to make your own test runner and create a new `GradescopeListener` object to run the tests.

Barista is built off of Tim Kutcher's [JGrade](https://github.com/tkutcher/jgrade), so there is even more customization to be had if you'd like to use some of JGrade's more advanced features.

## Installation
Instructions on how to add Barista to your Maven project can be found [here](https://github.com/purduecsbridge/barista/packages/).

## Contributing
Pull requests/issues are always welcome.

## License
Licensed under the [MIT License](LICENSE).
