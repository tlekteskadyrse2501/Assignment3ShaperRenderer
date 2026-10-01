# Assignment #3 - Bridge Pattern

Course: ShP-2216 - Software Design Patterns
Institution: Astana IT University
Topic: Shape-Renderer
Student: [Full name]

## 1. Introduction
The Bridge structural design pattern was chosen to solve the problem of multiple orthogonal dimensions of variation. In this assignment, the two dimensions are what is drawn (the Shape) and how it is drawn (the Renderer).

Without the Bridge pattern, inheritance leads to a combinatorial explosion of classes. For example, creating a VectorCircle, RasterCircle, VectorSquare, and RasterSquare. Adding a new Triangle class or AsciiRenderer would require multiplicative growth in the class hierarchy.

The Bridge solution decouples an abstraction from its implementation by using composition instead of inheritance. The shape holds a reference to a renderer, delegating the low-level drawing work.

Compared to similar patterns, Bridge is designed up front to let abstraction and implementation vary independently. Adapter is typically applied retroactively to make incompatible interfaces work together. Strategy shares the composition structure but focuses on interchangeable behaviors and algorithms, whereas Bridge separates a whole abstraction hierarchy from an implementation hierarchy.

## 2. UML Class Diagram
[UML class diagram will be inserted here]

Legend and Roles:
- Abstraction = Shape
- Refined Abstractions = Circle and Square
- Implementor = Renderer
- Concrete Implementors = VectorRenderer and RasterRenderer
- Client = ConsoleApplication / Main
- The "bridge" is the composition link where Shape holds a reference to Renderer.

## 3. Clean Code Principles

### Clean Code principle 1: Small Focused Classes
**Justification:** Classes should have a single responsibility. Keeping them small ensures they are easily understood and tested. The Abstraction and Implementor roles are placed in separate packages.
- **Before:** `class BigManager { drawAll() {...} renderPaths() {...} renderPixels() {...} }`
- **After:** 
```java
public interface Renderer { void renderCircle(double x, double y, double radius); }
public abstract class Shape { ... }
```

### Clean Code principle 2: Don't Pass Null and Don't Return Null
**Justification:** Null checks clutter the code. Throwing an exception prevents passing invalid states into the application.
- **Before:** `public void setRenderer(Renderer renderer) { this.renderer = renderer; }`
- **After:** 
```java
public final void setRenderer(Renderer renderer) {
    if (renderer == null) {
        throw new InvalidShapeParameterException("Cannot set a null renderer to Shape");
    }
    this.renderer = renderer;
}
```

### Clean Code principle 3: Use Exceptions Rather Than Return Codes (Contextual Context)
**Justification:** Returning error flags obscures the happy path and delegates validation to the caller. We use unchecked exceptions to halt bad executions and present useful contextual messages.
- **Before:** `public boolean createCircle(double radius) { if(radius <= 0) return false; ... }`
- **After:** 
```java
if (radius <= 0) {
    throw new InvalidShapeParameterException("Cannot create Circle: radius must be positive but was " + radius);
}
```

### Clean Code principle 4: No Duplicated Logic Between Concrete Implementors
**Justification:** Redundant code becomes hard to maintain. A shared base class encapsulates common formatting logic without violating the Implementor contract.
- **Before:** VectorRenderer and RasterRenderer both calling System.out.println with a similar complex structure multiple times.
- **After:** `abstract class AbstractRenderer implements Renderer { protected final void printShape(...) {...} }`

### Clean Code principle 5: No Comments (Code Explains Itself)
**Justification:** Comments easily become outdated. Well-named methods, short classes, and a well-typed abstraction hierarchy remove the need to write explanatory comments.
- **Before:** `// Sets the renderer \n public void setRenderer(Renderer r) { this.r = r; }`
- **After:** `public final void setRenderer(Renderer renderer) { this.renderer = renderer; }`

### Clean Code principle 6: Open-Closed Principle (Backward Compatible Design)
**Justification:** Adding a new concrete implementor like AsciiRenderer requires zero changes to the Shape abstraction.
- **Before (Inheritance):** `class AsciiCircle extends Shape { ... }` requires new subclasses for every shape.
- **After (Bridge):** `class AsciiRenderer extends AbstractRenderer { ... }` added without touching shapes.

## 4. Implementation Overview
Project uses Java 17 and Maven. It consists of the core bridge logic, console app, and a single `.docx` generator.

To compile: `mvn compile`
To run: `mvn exec:java -Dexec.mainClass="kz.aitu.bridge.Main"`

| Class | Role | Responsibility |
| --- | --- | --- |
| Shape | Abstraction | Interface to client, delegates to renderer |
| Circle, Square | Refined Abstraction | Specific shapes defining their logic |
| Renderer | Implementor | Low-level API for drawing primitives |
| VectorRenderer, RasterRenderer | Concrete Implementor | Console output for drawing variants |
| ConsoleApplication | Client | Interactive menu managing Shapes at runtime |

**Sample Transcript:**
```
Select Renderer (1 = Vector, 2 = Raster):
Choice: 1
Circle created.
4. Draw one shape
Shape index: 0
[VECTOR] Circle path at (5.0, 5.0) with radius 10.0
6. Switch shape renderer
Shape index: 0
Select Renderer (1 = Vector, 2 = Raster):
Choice: 2
[RASTER] Circle pixels at (5.0, 5.0) with radius 10.0
```

## 5. Conclusion
The Bridge pattern successfully separated our shape abstraction from the rendering implementations. The critical trade-off encountered is the increase in classes and indirection flow.
While adding a new AsciiRenderer is straightforward without touching the abstraction, adding a new shape that strictly requires new drawing primitives (like renderTriangle) forces an update to the Renderer interface and all implementations.
Therefore, Bridge comes with a minor performance overhead and represents structural overkill if there are very few rendering variants or if requirements remain static.

## 6. GitHub Repository
[GitHub repository link]

**Tools used**
Java 17, Maven, IntelliJ IDEA, Apache POI (for generating this report only).
