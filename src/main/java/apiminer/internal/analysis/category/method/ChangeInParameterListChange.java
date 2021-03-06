package apiminer.internal.analysis.category.method;

import apiminer.enums.Category;
import apiminer.enums.ElementType;
import apiminer.internal.analysis.category.MethodChange;
import apiminer.internal.util.UtilTools;
import gr.uom.java.xmi.UMLClass;
import gr.uom.java.xmi.UMLOperation;
import gr.uom.java.xmi.diff.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.refactoringminer.api.Refactoring;

import java.util.Map;

public class ChangeInParameterListChange extends MethodChange {

    public ChangeInParameterListChange(Refactoring refactoring, Map<String, UMLClass> parentClassMap, Map<String, UMLClass> currentClassMap, RevCommit revCommit) {
        super(revCommit);
        UMLOperation originalOperation = null;
        UMLOperation nextOperation = null;
        switch (refactoring.getRefactoringType()) {
            case PARAMETERIZE_VARIABLE:
                RenameVariableRefactoring renameVariable = (RenameVariableRefactoring) refactoring;
                originalOperation = renameVariable.getOperationBefore();
                nextOperation = renameVariable.getOperationAfter();
                break;
            case MERGE_PARAMETER:
                MergeVariableRefactoring mergeVariable = (MergeVariableRefactoring) refactoring;
                originalOperation = mergeVariable.getOperationBefore();
                nextOperation = mergeVariable.getOperationAfter();
                break;
            case SPLIT_PARAMETER:
                SplitVariableRefactoring splitVariable = (SplitVariableRefactoring) refactoring;
                originalOperation = splitVariable.getOperationBefore();
                nextOperation = splitVariable.getOperationAfter();
                break;
            case CHANGE_PARAMETER_TYPE:
                ChangeVariableTypeRefactoring changeVariableType = (ChangeVariableTypeRefactoring) refactoring;
                originalOperation = changeVariableType.getOperationBefore();
                nextOperation = changeVariableType.getOperationAfter();
                break;
            case ADD_PARAMETER:
                AddParameterRefactoring addParameterRefactoring = (AddParameterRefactoring) refactoring;
                originalOperation = addParameterRefactoring.getOperationBefore();
                nextOperation = addParameterRefactoring.getOperationAfter();
                break;
            case REMOVE_PARAMETER:
                RemoveParameterRefactoring removeParameter = (RemoveParameterRefactoring) refactoring;
                originalOperation = removeParameter.getOperationBefore();
                nextOperation = removeParameter.getOperationAfter();
                break;
            case REORDER_PARAMETER:
                ReorderParameterRefactoring reorderParameter = (ReorderParameterRefactoring) refactoring;
                originalOperation = reorderParameter.getOperationBefore();
                nextOperation = reorderParameter.getOperationAfter();
                break;
        }
        if (originalOperation != null && nextOperation != null) {
            this.setOriginalClass(parentClassMap.get(originalOperation.getClassName()));
            this.setNextClass(currentClassMap.get(nextOperation.getClassName()));
            this.setOriginalOperation(originalOperation);
            this.setNextOperation(nextOperation);
            this.setOriginalPath(UtilTools.getTypeDescriptionName(this.getOriginalClass()));
            this.setNextPath(UtilTools.getTypeDescriptionName(this.getNextClass()));
            this.setOriginalElement(UtilTools.getMethodDescriptionName(this.getOriginalOperation()));
            this.setNextElement(UtilTools.getMethodDescriptionName(this.getNextOperation()));
            this.setCategory(Category.METHOD_CHANGE_PARAMETER_LIST);
            this.setDescription(isDescription());
            this.setJavadoc(isJavaDoc(this.getNextOperation()));
            this.setDeprecated(checkDeprecated(this.getNextClass(),this.getNextOperation()));
            this.setBreakingChange(this.checkDeprecated(this.getOriginalClass(), this.getOriginalOperation()) ? false : true);
            this.setRevCommit(revCommit);
            if (this.getNextOperation().isConstructor()) {
                this.setElementType(ElementType.CONSTRUCTOR);
            } else {
                this.setElementType(ElementType.METHOD);
            }
        }
    }

    private String isDescription() {
        String message = "";
        message += "<br>method <code>" + this.getOriginalElement() + "</code>";
        message += "<br>in <code>" + this.getOriginalPath() + "</code>";
        message += "<br>changed the list parameters";
        message += "<br>to <code>" + this.getNextElement() + "</code>";
        message += "<br>in <code>" + this.getNextPath() + "</code>";
        message += "<br>";
        return message;
    }
}
