# Barista

Barista is a grading package for Java Gradescope assignments. It is meant to make the development and maintenance of programming assignments easier for courses using Java. How easy is it?

```java
import edu.purdue.cs.barista.TestCase;
import edu.purdue.cs.barista.TestSuite;

@TestSuite
public class HelloWorldTests {

  @TestCase(name = "Hello test", points = 50.0)
  public void testHello() {
    // Test code here
  }
  
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
Instructions on how to add Barista to your Maven project can be found [here](https://github.com/pucsbridge/barista/packages/).

## Contributing
Pull requests/issues are always welcome.

## License
Licensed under the [MIT License](LICENSE).
