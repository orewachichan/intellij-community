package com.siyeh.ig.confusing;

import com.intellij.codeInspection.InspectionManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiMethod;
import com.siyeh.ig.BaseInspection;
import com.siyeh.ig.BaseInspectionVisitor;
import com.siyeh.ig.GroupNames;
import com.siyeh.ig.MethodInspection;

public class MethodNamesDifferOnlyByCaseInspection extends MethodInspection {

    public String getDisplayName() {
        return "Method names differing only by case";
    }

    public String getGroupDisplayName() {
        return GroupNames.CONFUSING_GROUP_NAME;
    }

    public String buildErrorString(Object arg) {
        return "Method names '#ref' and '" + arg + "' differ only by case";
    }

    public BaseInspectionVisitor createVisitor(InspectionManager inspectionManager, boolean onTheFly) {
        return new OverloadedMethodsWithSameNumberOfParametersVisitor(this, inspectionManager, onTheFly);
    }

    private static class OverloadedMethodsWithSameNumberOfParametersVisitor extends BaseInspectionVisitor {
        private OverloadedMethodsWithSameNumberOfParametersVisitor(BaseInspection inspection,
                                                                   InspectionManager inspectionManager, boolean isOnTheFly) {
            super(inspection, inspectionManager, isOnTheFly);
        }

        public void visitMethod(PsiMethod method) {
            if (method.isConstructor()) {
                return;
            }
            final PsiIdentifier nameIdentifier = method.getNameIdentifier();
            if (nameIdentifier == null) {
                return;
            }
            final String methodName = method.getName();
            if (methodName == null) {
                return;
            }
            final PsiClass aClass = method.getContainingClass();
            if (aClass == null) {
                return;
            }
            final PsiMethod[] methods = aClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                final String testMethName = methods[i].getName();
                if (testMethName != null && !methodName.equals(testMethName) &&
                        methodName.equalsIgnoreCase(testMethName)) {
                    registerError(nameIdentifier, testMethName);
                }
            }
        }
    }

}
