package apiminer.internal.analysis.category.type;

import apiminer.enums.Category;
import apiminer.enums.ElementType;
import apiminer.internal.analysis.category.TypeChange;
import apiminer.internal.util.UtilTools;
import gr.uom.java.xmi.UMLClass;
import gr.uom.java.xmi.UMLType;
import org.eclipse.jgit.revwalk.RevCommit;

public class RemoveSuperTypeChange extends TypeChange {
    public RemoveSuperTypeChange(UMLClass originalClass, UMLClass nextClass, UMLType removedUMLType, RevCommit revCommit){
        super(revCommit);
        this.setOriginalClass(originalClass);
        this.setNextClass(nextClass);
        this.setOriginalPath(UtilTools.getTypeDescriptionName(originalClass));
        this.setNextPath(UtilTools.getTypeDescriptionName(nextClass));
        this.setOriginalElement(removedUMLType.toString());
        this.setNextElement("");
        this.setCategory(Category.TYPE_REMOVE_SUPERCLASS);
        this.setDescription(isDescription());
        this.setJavadoc(isJavaDoc(this.getNextClass()));
        this.setDeprecated(checkDeprecated(this.getNextClass()));
        this.setBreakingChange(this.checkDeprecated(this.getOriginalClass()) ? false : true);
        this.setRevCommit(revCommit);
        if (this.getNextClass().isInterface()) {
            this.setElementType(ElementType.INTERFACE);
        } else if (this.getNextClass().isEnum()) {
            this.setElementType(ElementType.ENUM);
        } else {
            this.setElementType(ElementType.CLASS);
        }
    }

    private String isDescription() {
        String message = "";
        message += "<br>type <code>" + this.getNextPath() + "</code>";
        message += "<br>removed superType <code>" + this.getOriginalElement() + "</code>";
        message += "<br>";
        return message;
    }
}
