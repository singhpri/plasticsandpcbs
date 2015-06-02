package haiku.eclipse.plugin.common;

import haiku.eclipse.plugin.Activator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

public class Utils {

  private static final String CONSOLE_NAME_PREFIX = "HAIKU CONSOLE : ";
  private static final String WHICH_COMMAND = "which";
  private static final String HAIKU_COMMAND = "haiku.bat";
  private static final String HAIKU_COMMAND_LOCATION_RELATIVE_TO_HAIKU_BASE_DIR = "bin\\"
      + HAIKU_COMMAND;

  private static final String ECHO_COMMAND = "echo";
  private static final String TEMP_DIR_ENV_VAR = "%TEMP%";

  private static final String BOOTSTRAP_DOT_JAR_SRC_PATH = "/resources/bootstrap.jar";
  private static final String BOOTSTRAP_DOT_JAR_TGT_PATH = "/lib/bootstrap.jar";

  private static final String HAIKU_RT_DOT_JAR_SRC_PATH = "/resources/haikuRT.jar";
  private static final String HAIKU_RT_DOT_JAR_TGT_PATH = "/lib/haikuRT.jar";

  private static final List<String> HAIKU_JAR_LOCATIONS = ImmutableList.of("lib/pc/",
      "lib/examples/", "lib/nxt");

  public static IFile toIFile(IStructuredSelection structuredSelection) {
    final Object obj = structuredSelection.getFirstElement();
    IFile file = (IFile) Platform.getAdapterManager().getAdapter(obj, IFile.class);
    if (file == null) {
      if (obj instanceof IAdaptable) {
        file = (IFile) ((IAdaptable) obj).getAdapter(IFile.class);
      }
    }
    return file;
  }

  public static String tryAndInferHaikuVMBaseDir(IProject project) {
    return tryAndInferDir(project, HAIKU_COMMAND_LOCATION_RELATIVE_TO_HAIKU_BASE_DIR,
        WHICH_COMMAND, HAIKU_COMMAND);
  }

  public static String tryAndInferTempDir(IProject project) {
    return tryAndInferDir(project, null, ECHO_COMMAND, TEMP_DIR_ENV_VAR);
  }

  public static void updateClassPathIfNecessary(IProject project, IProgressMonitor monitor)
      throws JavaModelException, IOException {
    final IJavaProject javaProject = JavaCore.create(project);
    final String haikuBaseDir = tryAndInferHaikuVMBaseDir(project);
    final Set<IClasspathEntry> classPathEntrySet = Sets.newHashSet(javaProject.getRawClasspath());
    final Set<String> classPathStringSet =
        FluentIterable.from(classPathEntrySet).transform(new Function<IClasspathEntry, String>() {
          @Override
          public String apply(IClasspathEntry classpathEntry) {
            return classpathEntry.getPath().toOSString();
          }
        }).toSet();

    final Set<String> pathsToHaveSet = Sets.newHashSet();
    for (final String libPath : HAIKU_JAR_LOCATIONS) {
      final File dir = new File(haikuBaseDir + libPath);
      final FileFilter fileFilter = new WildcardFileFilter("*.jar");
      final File[] files = dir.listFiles(fileFilter);
      for (final File file : files) {
        pathsToHaveSet.add(file.getPath());
      }
    }

    final Set<String> toChange = Sets.difference(pathsToHaveSet, classPathStringSet);
    try {
      IFile file = addJar(project, BOOTSTRAP_DOT_JAR_SRC_PATH, BOOTSTRAP_DOT_JAR_TGT_PATH, monitor);
      if (file != null) {
        classPathEntrySet.add(JavaCore.newLibraryEntry(file.getFullPath(), null, null, false));
      }
      file = addJar(project, HAIKU_RT_DOT_JAR_SRC_PATH, HAIKU_RT_DOT_JAR_TGT_PATH, monitor);
      if (file != null) {
        classPathEntrySet.add(JavaCore.newLibraryEntry(file.getFullPath(), null, null, false));
      }
    } catch (final Exception ex) {
      Utils.tryPrintToConsole(project, ex.toString());
    }

    if (toChange.isEmpty()) {
      return;
    }

    for (final String path : toChange) {
      classPathEntrySet.add(JavaCore.newLibraryEntry(new Path(path), null, null));
    }
    javaProject.setRawClasspath(
        FluentIterable.from(classPathEntrySet).toArray(IClasspathEntry.class), null);
  }

  public static void printToConsole(IProject project, String toWrite) throws IOException {
    getConsole(project).newOutputStream().write(toWrite);
  }

  public static void tryPrintToConsole(IProject project, String toWrite) {
    try {
      getConsole(project).newOutputStream().write(toWrite);
    } catch (final IOException ex) {
    }
  }

  public static MessageConsole showConsole(IProject project) throws PartInitException {
    final MessageConsole console = getConsole(project);
    console.clearConsole();
    ConsolePlugin.getDefault().getConsoleManager().showConsoleView(console);
    return console;
  }

  public static MessageConsole getConsole(IProject project) {
    final ConsolePlugin plugin = ConsolePlugin.getDefault();
    final IConsoleManager conMan = plugin.getConsoleManager();
    final IConsole[] existing = conMan.getConsoles();
    String consoleName = CONSOLE_NAME_PREFIX;
    if (project != null) {
      consoleName = CONSOLE_NAME_PREFIX + project.getName();
    }
    for (final IConsole element : existing) {
      if (consoleName.equals(element.getName())) {
        return (MessageConsole) element;
      }
    }
    // no console found, so create a new one
    final MessageConsole myConsole = new MessageConsole(consoleName, null);
    conMan.addConsoles(new IConsole[] {myConsole});
    return myConsole;
  }

  private static String tryAndInferDir(IProject project, String dirLocationRelativeToCommand,
      String... commands) {
    final ProcessBuilder builder = new ProcessBuilder();
    builder.command(commands);
    BufferedReader reader = null;
    BufferedReader errorReader = null;
    try {
      final Process process = builder.start();
      reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
      String line = reader.readLine();
      if (line == null) {
        while ((line = errorReader.readLine()) != null) {
          Utils.tryPrintToConsole(project, line);
        }
        return null;
      }
      String location = line;
      if (dirLocationRelativeToCommand != null) {
        location = line.substring(0, line.indexOf(dirLocationRelativeToCommand));
      }
      if (new File(location).exists()) {
        return location;
      }
    } catch (final IOException ex) {
      Utils.tryPrintToConsole(project, ex.toString());
      try {
        if (reader != null) {
          reader.close();
        }
        if (errorReader != null) {
          errorReader.close();
        }
      } catch (final IOException exx) {
        Utils.tryPrintToConsole(project, exx.toString());
      }
    }
    return null;
  }

  private static IFile addJar(IProject project, String srcPath, String targetPath,
      IProgressMonitor monitor) throws CoreException {
    final URL srcURL = Activator.getDefault().getBundle().getEntry(srcPath);
    final IFile file = project.getFile(targetPath);
    if (file.exists()) {
      return null;
    }
    prepare(file.getParent());
    InputStream is = null;
    try {
      is = srcURL.openStream();
      file.create(is, true, monitor);
    } catch (final CoreException e) {
    } catch (final IOException e) {
    } finally {
      try {
        if (is != null) {
          is.close();
        }
      } catch (final IOException ignored) {
      }
    }
    return file;
  }

  private static void prepare(IContainer container) throws CoreException {
    if (!(container instanceof IFolder)) {
      return;
    }
    final IFolder folder = (IFolder) container;
    if (!folder.exists()) {
      prepare(folder.getParent());
      folder.create(false, false, null);
    }
  }
}
