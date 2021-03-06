package haiku.eclipse.plugin.preferences;

import haiku.eclipse.plugin.Activator;
import haiku.eclipse.plugin.common.Utils;

import java.nio.file.Paths;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

  // Workspace preference constants.
  public static final String ARDUINO_LIB_PATH = "arduinoLibPathPreference";
  public static final String HAIKUVM_PATH = "haikuVMPathPreference";

  // Project preference constants.
  public static final String PROJECT_PREFERENCE = "haiku.eclipse.plugin.projectPreference";
  public static final String JAVA_ENTRY_POINT_CLASS_PREFERENCE = "entryPointClass";
  public static final String JAVA_ENTRY_POINT_CLASS_DEFAULT = "";

  @Override
  public void initializeDefaultPreferences() {
    final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    store.setDefault(ARDUINO_LIB_PATH, "");
    store.setDefault(HAIKUVM_PATH, Utils.tryAndInferHaikuVMBaseDir(null /* project */));
  }

  public static String getHaikuVMPath() {
    return Activator.getDefault().getPreferenceStore().getString(HAIKUVM_PATH);
  }

  public static String getArduinoLibraryPath() {
    return Activator.getDefault().getPreferenceStore().getString(ARDUINO_LIB_PATH);
  }

  public static String getEntryPointJavaFile(IProject project, boolean relative) {
    final IScopeContext projectScope = new ProjectScope(project);
    final IEclipsePreferences preferences = projectScope.getNode(PROJECT_PREFERENCE);
    final String path =
        preferences.get(JAVA_ENTRY_POINT_CLASS_PREFERENCE, JAVA_ENTRY_POINT_CLASS_DEFAULT);
    if (!relative) {
      return Paths.get(project.getLocation().toString(), path).toString();
    }
    return path;
  }

  public static void setEntryPointJavaFile(IProject project, IPath path)
      throws BackingStoreException {
    final IScopeContext projectScope = new ProjectScope(project);
    final IEclipsePreferences preferences = projectScope.getNode(PROJECT_PREFERENCE);
    if (path == null) {
      preferences.put(JAVA_ENTRY_POINT_CLASS_PREFERENCE, JAVA_ENTRY_POINT_CLASS_DEFAULT);
    } else {
      preferences.put(JAVA_ENTRY_POINT_CLASS_PREFERENCE, path.makeRelativeTo(project.getLocation())
          .toOSString());
    }
    preferences.flush();
  }

  public static boolean hasMissingPreferences() {
    final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    return store.getDefaultString(ARDUINO_LIB_PATH).equals(store.getString(ARDUINO_LIB_PATH))
        || store.getString(HAIKUVM_PATH).equals(null);
  }
}
