package haiku.eclipse.plugin.builder;

import haiku.eclipse.plugin.common.MarkerUtils;
import haiku.eclipse.plugin.common.Utils;
import haiku.eclipse.plugin.common.Utils.BuildProblem;
import haiku.eclipse.plugin.preferences.PreferenceInitializer;

import java.io.IOException;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class HexBuilder extends IncrementalProjectBuilder {

  public static final String BUILDER_ID = "haiku.eclipse.plugin.builder.hexBuilder";

  @SuppressWarnings("rawtypes")
  @Override
  protected IProject[] build(int kind, Map args, IProgressMonitor monitor) throws CoreException {

    try {
      super.forgetLastBuiltState();
      Utils.updateClassPathIfNecessary(getProject(), monitor);

      final IResource resource =
          getProject().findMember(PreferenceInitializer.getEntryPointJavaFile(getProject(), true));
      if (resource == null) {
        MarkerUtils.addMarker(getProject(), MarkerUtils.MISSING_JAVA_ENTRY_POINT_MARKER);
        return null;
      } else {
        MarkerUtils.removeMarker(getProject(), MarkerUtils.MISSING_JAVA_ENTRY_POINT_MARKER);
      }
      final String entryPointJavaPath =
          PreferenceInitializer.getEntryPointJavaFile(getProject(), false);

      /*
       * if (entryPointJavaPath == null || entryPointJavaPath.trim().equals("")) {
       * Utils.printToConsole
       * ("Error: Please provide a path to the java file containing your 'loop' and 'setup' methods."
       * ); return null; }
       */
      Utils.tryPrintToConsole("Processing file: " + entryPointJavaPath);
      Utils.showConsole(getProject());
      final BuildProblem problem =
          Utils.runProcess(getProject(), Utils.getHaikuBaseDir() + "\\bin\\haiku.bat", "-v",
              "--Config", "arduinoIDE", entryPointJavaPath);
      if (problem != BuildProblem.NONE) {
        Utils.tryPrintToConsole("Please make sure your Haiku entry point class has a public "
            + "static loop(); public static setup()");
        MarkerUtils.addMarker(resource, MarkerUtils.JAVA_ENTRY_POINT_MISSING_METHODS_MARKER);
      } else {
        MarkerUtils.removeMarker(resource, MarkerUtils.JAVA_ENTRY_POINT_MISSING_METHODS_MARKER);
      }
    } catch (final IOException ex) {
      Utils.tryPrintToConsole(ex.toString());
    }
    return null;
  }
}
