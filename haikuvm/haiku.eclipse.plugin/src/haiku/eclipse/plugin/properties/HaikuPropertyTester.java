package haiku.eclipse.plugin.properties;

import haiku.eclipse.plugin.preferences.PreferenceInitializer;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;

public class HaikuPropertyTester extends PropertyTester {

  private static final String ENTRYPOINTCLASS = "isEntryPoint";

  @Override
  public boolean test(Object resource, String property, Object[] args, Object expectedValue) {
    if (ENTRYPOINTCLASS.equals(property)) {
      if (!(resource instanceof ICompilationUnit)) {
        return false;
      }
      ICompilationUnit compilationUnit = (ICompilationUnit) resource;
      IProject project = (IProject) compilationUnit.getAdapter(IProject.class);
      String javaEntryPointPath = PreferenceInitializer.getEntryPointJavaFile(project, true);
      if (javaEntryPointPath.equals(compilationUnit.getPath().toOSString())) {
        return true;
      } else {
        return false;
      }
    }
    return false;
  }

}
