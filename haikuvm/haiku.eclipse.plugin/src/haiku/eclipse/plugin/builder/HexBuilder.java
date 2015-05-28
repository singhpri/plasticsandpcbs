package haiku.eclipse.plugin.builder;

import haiku.eclipse.plugin.common.Utils;
import haiku.eclipse.plugin.common.Utils.BuildProblem;
import haiku.eclipse.plugin.preferences.PreferenceInitializer;

import java.io.IOException;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class HexBuilder extends IncrementalProjectBuilder {

  public static final String BUILDER_ID = "haiku.eclipse.plugin.builder.hexBuilder";
  private static final String MARKER_TYPE = "haiku.buildProblem";
  private static final String PICK_JAVA_FILE_ENTRY_POINT_MESSAGE =
      "Please provide a path to the java file containing "
          + "'public static void loop()' and 'public static void setup()' methods.";

  @SuppressWarnings("rawtypes")
  @Override
  protected IProject[] build(int kind, Map args, IProgressMonitor monitor) throws CoreException {

    try {
      super.forgetLastBuiltState();
      Utils.updateClassPathIfNecessary(getProject(), monitor);

      final IResource resource =
          getProject().findMember(PreferenceInitializer.getEntryPointJavaFile(getProject(), true));
      if (resource == null) {
        final IMarker marker = getProject().createMarker(MARKER_TYPE);
        marker.setAttribute(IMarker.MESSAGE, PICK_JAVA_FILE_ENTRY_POINT_MESSAGE);
        marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
        marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
        return null;
      } else {
        getProject().deleteMarkers(MARKER_TYPE, false, IResource.DEPTH_ZERO);
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
        final IMarker marker = resource.createMarker(MARKER_TYPE);
        marker
        .setAttribute(
            IMarker.MESSAGE,
            "Please make sure your Haiku entry point class has two methods: public "
                + "static loop(); public static setup(). Visit http://haiku-vm.sourceforge.net/ for more information.");
        marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
        marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
      } else {
        resource.deleteMarkers(MARKER_TYPE, false, IResource.DEPTH_INFINITE);
      }
    } catch (final IOException ex) {
      Utils.tryPrintToConsole(ex.toString());
    }
    return null;
  }
}
