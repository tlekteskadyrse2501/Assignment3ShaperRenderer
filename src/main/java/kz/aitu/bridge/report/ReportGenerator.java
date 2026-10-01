package kz.aitu.bridge.report;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.FileOutputStream;
import java.io.IOException;
import kz.aitu.bridge.exception.InvalidShapeParameterException;

public final class ReportGenerator {
    private ReportGenerator() {
    }

    public static void main(String[] args) {
        try (XWPFDocument document = new XWPFDocument()) {
            createTitlePage(document);
            createSection1(document);
            createSection2(document);
            createSection3(document);
            createSection4(document);
            createSection5(document);
            createSection6(document);
            saveDocument(document, "docs/Bridge_Pattern_Report.docx");
        } catch (Exception e) {
            throw new InvalidShapeParameterException("Failed to generate report: " + e.getMessage());
        }
    }

    private static void createTitlePage(XWPFDocument document) {
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setText("Assignment #3 - Bridge Pattern");
        titleRun.setBold(true);
        titleRun.setFontSize(20);
        titleRun.addBreak();
        titleRun.addBreak();

        addStandardText(document, "Course: ShP-2216 - Software Design Patterns");
        addStandardText(document, "Institution: Astana IT University");
        addStandardText(document, "Topic: Shape-Renderer");
        addStandardText(document, "Student: [Full name]");
        document.createParagraph().setPageBreak(true);
    }

    private static void createSection1(XWPFDocument document) {
        addHeading(document, "1. Introduction");
        addStandardText(document, "The Bridge structural design pattern was chosen to solve the problem of multiple orthogonal dimensions of variation. In this assignment, the two dimensions are what is drawn (the Shape) and how it is drawn (the Renderer).");
        addStandardText(document, "Without the Bridge pattern, inheritance leads to a combinatorial explosion of classes. For example, creating a VectorCircle, RasterCircle, VectorSquare, and RasterSquare. Adding a new Triangle class or AsciiRenderer would require multiplicative growth in the class hierarchy.");
        addStandardText(document, "The Bridge solution decouples an abstraction from its implementation by using composition instead of inheritance. The shape holds a reference to a renderer, delegating the low-level drawing work.");
        addStandardText(document, "Compared to similar patterns, Bridge is designed up front to let abstraction and implementation vary independently. Adapter is typically applied retroactively to make incompatible interfaces work together. Strategy shares the composition structure but focuses on interchangeable behaviors and algorithms, whereas Bridge separates a whole abstraction hierarchy from an implementation hierarchy.");
    }

    private static void createSection2(XWPFDocument document) {
        addHeading(document, "2. UML Class Diagram");
        addStandardText(document, "[UML class diagram will be inserted here]");
        addStandardText(document, "Legend and Roles:");
        addStandardText(document, "- Abstraction = Shape");
        addStandardText(document, "- Refined Abstractions = Circle and Square");
        addStandardText(document, "- Implementor = Renderer");
        addStandardText(document, "- Concrete Implementors = VectorRenderer and RasterRenderer");
        addStandardText(document, "- Client = ConsoleApplication / Main");
        addStandardText(document, "- The \"bridge\" is the composition link where Shape holds a reference to Renderer.");
    }

    private static void createSection3(XWPFDocument document) {
        addHeading(document, "3. Clean Code Principles");
        
        addStandardText(document, "Clean Code principle 1: Small Focused Classes");
        addStandardText(document, "Justification: Classes should have a single responsibility. Keeping them small ensures they are easily understood and tested. The Abstraction and Implementor roles are placed in separate packages.");
        addCodeBlock(document, "Before: class BigManager { drawAll() {...} renderPaths() {...} renderPixels() {...} }");
        addCodeBlock(document, "After: public interface Renderer { void renderCircle(double x, double y, double radius); }\npublic abstract class Shape { ... }");

        addStandardText(document, "Clean Code principle 2: Don't Pass Null and Don't Return Null");
        addStandardText(document, "Justification: Null checks clutter the code. Throwing an exception prevents passing invalid states into the application.");
        addCodeBlock(document, "Before: public void setRenderer(Renderer renderer) { this.renderer = renderer; }");
        addCodeBlock(document, "After: public final void setRenderer(Renderer renderer) {\n    if (renderer == null) {\n        throw new InvalidShapeParameterException(\"Cannot set a null renderer to Shape\");\n    }\n    this.renderer = renderer;\n}");

        addStandardText(document, "Clean Code principle 3: Use Exceptions Rather Than Return Codes (Contextual Context)");
        addStandardText(document, "Justification: Returning error flags obscures the happy path and delegates validation to the caller. We use unchecked exceptions to halt bad executions and present useful contextual messages.");
        addCodeBlock(document, "Before: public boolean createCircle(double radius) { if(radius <= 0) return false; ... }");
        addCodeBlock(document, "After: if (radius <= 0) {\n    throw new InvalidShapeParameterException(\"Cannot create Circle: radius must be positive but was \" + radius);\n}");

        addStandardText(document, "Clean Code principle 4: No Duplicated Logic Between Concrete Implementors");
        addStandardText(document, "Justification: Redundant code becomes hard to maintain. A shared base class encapsulates common formatting logic without violating the Implementor contract.");
        addCodeBlock(document, "Before: VectorRenderer and RasterRenderer both calling System.out.println with a similar complex structure multiple times.");
        addCodeBlock(document, "After: abstract class AbstractRenderer implements Renderer { protected final void printShape(...) {...} }");

        addStandardText(document, "Clean Code principle 5: No Comments (Code Explains Itself)");
        addStandardText(document, "Justification: Comments easily become outdated. Well-named methods, short classes, and a well-typed abstraction hierarchy remove the need to write explanatory comments.");
        addCodeBlock(document, "Before: // Sets the renderer \n public void setRenderer(Renderer r) { this.r = r; }");
        addCodeBlock(document, "After: public final void setRenderer(Renderer renderer) { this.renderer = renderer; }");

        addStandardText(document, "Clean Code principle 6: Open-Closed Principle (Backward Compatible Design)");
        addStandardText(document, "Justification: Adding a new concrete implementor like AsciiRenderer requires zero changes to the Shape abstraction.");
        addCodeBlock(document, "Before (Inheritance): class AsciiCircle extends Shape { ... } requires new subclasses for every shape.");
        addCodeBlock(document, "After (Bridge): class AsciiRenderer extends AbstractRenderer { ... } added without touching shapes.");
    }

    private static void createSection4(XWPFDocument document) {
        addHeading(document, "4. Implementation Overview");
        addStandardText(document, "Project uses Java 17 and Maven. It consists of the core bridge logic, console app, and a single `.docx` generator.");
        addStandardText(document, "To compile: mvn compile");
        addStandardText(document, "To run: mvn exec:java -Dexec.mainClass=\"kz.aitu.bridge.Main\"");
        
        XWPFTable table = document.createTable();
        XWPFTableRow row0 = table.getRow(0);
        row0.getCell(0).setText("Class");
        row0.addNewTableCell().setText("Role");
        row0.addNewTableCell().setText("Responsibility");

        addRow(table, "Shape", "Abstraction", "Interface to client, delegates to renderer");
        addRow(table, "Circle, Square", "Refined Abstraction", "Specific shapes defining their logic");
        addRow(table, "Renderer", "Implementor", "Low-level API for drawing primitives");
        addRow(table, "VectorRenderer, RasterRenderer", "Concrete Implementor", "Console output for drawing variants");
        addRow(table, "ConsoleApplication", "Client", "Interactive menu managing Shapes at runtime");

        addStandardText(document, "Sample Transcript:");
        addCodeBlock(document, "Select Renderer (1 = Vector, 2 = Raster):\nChoice: 1\nCircle created.\n4. Draw one shape\nShape index: 0\n[VECTOR] Circle path at (5.0, 5.0) with radius 10.0\n6. Switch shape renderer\nShape index: 0\nSelect Renderer (1 = Vector, 2 = Raster):\nChoice: 2\n[RASTER] Circle pixels at (5.0, 5.0) with radius 10.0");
    }

    private static void createSection5(XWPFDocument document) {
        addHeading(document, "5. Conclusion");
        addStandardText(document, "The Bridge pattern successfully separated our shape abstraction from the rendering implementations. The critical trade-off encountered is the increase in classes and indirection flow.");
        addStandardText(document, "While adding a new AsciiRenderer is straightforward without touching the abstraction, adding a new shape that strictly requires new drawing primitives (like renderTriangle) forces an update to the Renderer interface and all implementations.");
        addStandardText(document, "Therefore, Bridge comes with a minor performance overhead and represents structural overkill if there are very few rendering variants or if requirements remain static.");
    }

    private static void createSection6(XWPFDocument document) {
        addHeading(document, "6. GitHub Repository");
        addStandardText(document, "[GitHub repository link]");
        
        addHeading(document, "Tools used");
        addStandardText(document, "Java 17, Maven, IntelliJ IDEA, Apache POI (for generating this report only).");
    }

    private static void addHeading(XWPFDocument document, String text) {
        XWPFParagraph p = document.createParagraph();
        XWPFRun run = p.createRun();
        run.setText(text);
        run.setBold(true);
        run.setFontSize(14);
    }

    private static void addStandardText(XWPFDocument document, String text) {
        XWPFParagraph p = document.createParagraph();
        XWPFRun run = p.createRun();
        run.setText(text);
    }

    private static void addCodeBlock(XWPFDocument document, String code) {
        XWPFParagraph p = document.createParagraph();
        XWPFRun run = p.createRun();
        run.setText(code);
        run.setFontFamily("Courier New");
        run.setFontSize(10);
    }

    private static void addRow(XWPFTable table, String col1, String col2, String col3) {
        XWPFTableRow row = table.createRow();
        row.getCell(0).setText(col1);
        row.getCell(1).setText(col2);
        row.getCell(2).setText(col3);
    }

    private static void saveDocument(XWPFDocument document, String path) throws IOException {
        try (FileOutputStream out = new FileOutputStream(path)) {
            document.write(out);
        }
    }
}
