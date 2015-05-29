package haiku.eclipse.plugin.common;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.google.common.collect.ImmutableMap;

public class MarkerUtils {

  public final static String MISSING_JAVA_ENTRY_POINT_MARKER = "haiku.missingJavaEntryPoint";
  public final static String JAVA_ENTRY_POINT_MISSING_METHODS_MARKER =
      "haiku.missingJavaEntryPoint";

  private static final Map<String, String> errorMarkerTypeToMessageMap = ImmutableMap.of(
      MISSING_JAVA_ENTRY_POINT_MARKER,
      "Please provide a path to the java file containing 'public static void loop()' and "
          + "'public static void setup()' methods.",
      JAVA_ENTRY_POINT_MISSING_METHODS_MARKER,
      "Please make sure your Haiku entry point class has two methods: public static loop();"
          + " public static setup(). Visit http://haiku-vm.sourceforge.net/ for more information.");

  public static void addMarker(IResource resource, String markerType) throws CoreException {
    if (!errorMarkerTypeToMessageMap.containsKey(markerType)) {
      throw new NullPointerException("Unexpected request for " + markerType);
    }
    final IMarker marker = resource.createMarker(markerType);
    marker.setAttribute(IMarker.MESSAGE, errorMarkerTypeToMessageMap.get(markerType));
    marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
    marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
    return;
  }

  public static void removeMarker(IResource project, String markerType) throws CoreException {
    project.deleteMarkers(MarkerUtils.MISSING_JAVA_ENTRY_POINT_MARKER, false, IResource.DEPTH_ZERO);
  }
}
