package haiku.eclipse.plugin.common;

import haiku.eclipse.plugin.Activator;
import haiku.eclipse.plugin.preferences.PreferenceInitializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
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

  private static final String CONSOLE_NAME = "HAIKU CONSOLE";
  private static final String WHICH_COMMAND = "which";
  private static final String HAIKU_COMMAND = "haiku.bat";
  private static final String EXPECTED_HAIKU_LOCATION = "bin\\" + HAIKU_COMMAND;

  private static final String BOOTSTRAP_DOT_JAR_SRC_PATH = "/resources/bootstrap.jar";
  private static final String BOOTSTRAP_DOT_JAR_TGT_PATH = "/lib/bootstrap.jar";

  private static final String HAIKU_RT_DOT_JAR_SRC_PATH = "/resources/haikuRT.jar";
  private static final String HAIKU_RT_DOT_JAR_TGT_PATH = "/lib/haikuRT.jar";

  private static final List<String> HAIKU_JAR_LOCATIONS = ImmutableList.of("lib/pc/",
      "lib/examples/", "lib/nxt");

  public enum BuildProblem {
    NONE, MISSING_LOOP_OR_SETUP
  };

  public static IFile getFile(IStructuredSelection structuredSelection) {
    final Object obj = structuredSelection.getFirstElement();
    IFile file = (IFile) Platform.getAdapterManager().getAdapter(obj, IFile.class);
    if (file == null) {
      if (obj instanceof IAdaptable) {
        file = (IFile) ((IAdaptable) obj).getAdapter(IFile.class);
      }
    }
    return file;
  }

  public static String getHaikuBaseDir() throws IOException {
    final ProcessBuilder builder = new ProcessBuilder();
    builder.command(WHICH_COMMAND, HAIKU_COMMAND);
    builder.directory(new File(PreferenceInitializer.getArduinoLibraryPath()));
    final Process process = builder.start();
    final BufferedReader reader =
        new BufferedReader(new InputStreamReader(process.getInputStream()));
    final BufferedReader errorreader =
        new BufferedReader(new InputStreamReader(process.getErrorStream()));
    String line = reader.readLine();
    if (line == null) {
      while ((line = errorreader.readLine()) != null) {
        Utils.tryPrintToConsole(line);
      }
      return null;
    }
    return line.substring(0, line.indexOf(EXPECTED_HAIKU_LOCATION));
  }

  /**
   * Updates the project class path with
   */
  public static void updateClassPathIfNecessary(IProject project, IProgressMonitor monitor)
      throws JavaModelException, IOException {
    // Derived from http://www.vogella.com/tutorials/EclipseJDT/article.html#jdt_classpath
    final IJavaProject javaProject = JavaCore.create(project);
    final String haikuBaseDir = getHaikuBaseDir();
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
      Utils.tryPrintToConsole(ex.toString());
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

  public static void prepare(IContainer container) throws CoreException {
    if (!(container instanceof IFolder)) {
      return;
    }
    final IFolder folder = (IFolder) container;
    if (!folder.exists()) {
      prepare(folder.getParent());
      folder.create(false, false, null);
    }
  }

  public static BuildProblem runProcess(IProject project, String... command) throws IOException,
      PartInitException {
    final ProcessBuilder builder = new ProcessBuilder();
    builder.command(command);
    builder.redirectErrorStream(true);
    builder.directory(new File(PreferenceInitializer.getArduinoLibraryPath()));
    builder.redirectOutput(Redirect.PIPE);
    final MessageConsole console = Utils.getConsole();
    // builder.
    final Process process = builder.start();
    BufferedReader reader = null;
    BuildProblem problem = BuildProblem.NONE;
    try {
      reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line = null;

      while ((line = reader.readLine()) != null) {
        if (line.contains("problem finding method")) {
          problem = BuildProblem.MISSING_LOOP_OR_SETUP;
        }
        console.newMessageStream().println(line);
      }
    } finally {
      reader.close();
    }
    return problem;
  };

  public static void printToConsole(String toWrite) throws IOException {
    getConsole().newOutputStream().write(toWrite);
  }

  public static void tryPrintToConsole(String toWrite) {
    try {
      getConsole().newOutputStream().write(toWrite);
    } catch (final IOException ex) {
    }
  }

  public static MessageConsole showConsole(IProject project) throws PartInitException {
    final MessageConsole console = getConsole();
    ConsolePlugin.getDefault().getConsoleManager().showConsoleView(console);
    return console;
  }

  private static MessageConsole getConsole() {
    final ConsolePlugin plugin = ConsolePlugin.getDefault();
    final IConsoleManager conMan = plugin.getConsoleManager();
    final IConsole[] existing = conMan.getConsoles();
    for (final IConsole element : existing) {
      if (CONSOLE_NAME.equals(element.getName())) {
        return (MessageConsole) element;
      }
    }
    // no console found, so create a new one
    final MessageConsole myConsole = new MessageConsole(CONSOLE_NAME, null);
    conMan.addConsoles(new IConsole[] {myConsole});
    return myConsole;
  }
}
