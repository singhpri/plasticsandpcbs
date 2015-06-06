package haiku.eclipse.plugin.common;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.google.common.collect.ImmutableMap;

public class MarkerUtils {

  public final static String MISSING_JAVA_ENTRY_POINT_MARKER = "haiku.missingJavaEntryPoint";
  public final static String JAVA_ENTRY_POINT_MISSING_METHODS_MARKER =
      "haiku.javaEntryPointMissingMethods";
  public final static String MISSING_ARDUINO_LIB_PATH_OR_HAIKUVM_PATH =
      "haiku.missingArduinoLibPathOrHaikuVMPath";
  public final static String HAIKU_BUILD_ERROR = "haiky.buildError";

  // Each of the keys in this map should appear as marker ids in the plugin.xml
  private static final Map<String, String> errorMarkerTypeToMessageMap = ImmutableMap.of(
      MISSING_JAVA_ENTRY_POINT_MARKER,
      "Please provide a path to the java file containing 'public static void loop()' and "
          + "'public static void setup()' methods.", JAVA_ENTRY_POINT_MISSING_METHODS_MARKER,
      "Please make sure your Haiku entry point class has two methods: public static loop();"
          + " public static setup(). Visit http://haiku-vm.sourceforge.net/ for more information.",
      MISSING_ARDUINO_LIB_PATH_OR_HAIKUVM_PATH,
      "Please set the path to the Arduino/Libraries folder, and the path to the HaikuVM directory",
      HAIKU_BUILD_ERROR,
      "HaikuBuildError");

  public static void addMarker(IResource resource, String markerType) throws CoreException {
    if (!errorMarkerTypeToMessageMap.containsKey(markerType)) {
      throw new NullPointerException("Unexpected request for " + markerType);
    }
    addMarker(resource, markerType, errorMarkerTypeToMessageMap.get(markerType));
  }

  public static void addMarker(IResource resource, String markerType, String message)
      throws CoreException {
    // Delete markers of this type so we always have one marker at most. Without this line, we end
    // up with multiple markers.
    removeMarker(resource, markerType);
    IMarker marker = resource.createMarker(markerType);
    marker.setAttribute(IMarker.MESSAGE, message);
    marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
    marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
    return;
  }

  public static void removeMarker(IResource project, String markerType) throws CoreException {
    project.deleteMarkers(MarkerUtils.MISSING_JAVA_ENTRY_POINT_MARKER, false, IResource.DEPTH_ONE);
  }

  /** Returns true and adds the marker if the condition is met. Otherwise, clears the marker. */
  public static boolean checkCondition(boolean condition, IResource resource, String markerType)
      throws CoreException {
    if (condition) {
      addMarker(resource, markerType);
    } else {
      removeMarker(resource, markerType);
    }
    return condition;
  }

  public static boolean checkCondition(boolean condition, IResource resource, String markerType,
      String message) throws CoreException {
    if (condition) {
      addMarker(resource, markerType, message);
    } else {
      removeMarker(resource, markerType);
    }
    return condition;
  }
}
