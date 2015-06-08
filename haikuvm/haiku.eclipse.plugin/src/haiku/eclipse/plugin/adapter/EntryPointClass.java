package haiku.eclipse.plugin.adapter;

import haiku.eclipse.plugin.preferences.PreferenceInitializer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ICompilationUnit;

public final class EntryPointClass {

  private final boolean isEntryPoint;

  public EntryPointClass(IResource resource) {
    if (!(resource instanceof ICompilationUnit)) {
      isEntryPoint = false;
      return;
    }
    IProject project = (IProject) resource.getAdapter(IProject.class);
    ICompilationUnit compilationUnit = (ICompilationUnit) resource;
    String javaEntryPointPath = PreferenceInitializer.getEntryPointJavaFile(project, true);
    if (javaEntryPointPath.equals(compilationUnit.getPath().toOSString())) {
      isEntryPoint = true;
    } else {
      isEntryPoint = false;
    }
  }

  public boolean isEntryPoint() {
    return isEntryPoint;
  }
}
