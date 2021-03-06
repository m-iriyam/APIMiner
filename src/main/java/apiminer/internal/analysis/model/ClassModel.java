package apiminer.internal.analysis.model;

import gr.uom.java.xmi.UMLClass;

public class ClassModel {
    private final UMLClass umlClass;
    private boolean isRefactored = false;

    public ClassModel(UMLClass umlClass){
        this.umlClass = umlClass;
    }

    public UMLClass getUmlClass() {
        return umlClass;
    }

    public boolean getIsRefactored() {
        return isRefactored;
    }

    public void setRefactored(boolean refactored) {
        isRefactored = refactored;
    }
}
