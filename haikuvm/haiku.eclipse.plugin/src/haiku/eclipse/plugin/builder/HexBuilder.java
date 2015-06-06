package haiku.eclipse.plugin.builder;

import haiku.eclipse.plugin.Activator;
import haiku.eclipse.plugin.common.BuildProblem;
import haiku.eclipse.plugin.common.MarkerUtils;
import haiku.eclipse.plugin.common.Utils;
import haiku.eclipse.plugin.preferences.PreferenceInitializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.MessageConsole;

public class HexBuilder extends IncrementalProjectBuilder {

  public static final String BUILDER_ID = "haiku.eclipse.plugin.builder.hexBuilder";
  private static Path temporaryDirectory;

  @SuppressWarnings("rawtypes")
  @Override
  protected IProject[] build(int kind, Map args, IProgressMonitor monitor) throws CoreException {

    try {
      if (Activator.getDefault() == null) {
        return null;
      }
      super.forgetLastBuiltState();
      Utils.updateClassPathIfNecessary(getProject(), monitor);

      if (MarkerUtils.checkCondition(PreferenceInitializer.hasMissingPreferences(), getProject(),
          MarkerUtils.MISSING_ARDUINO_LIB_PATH_OR_HAIKUVM_PATH)) {
        return null;
      }
      buildProject(getProject(), "arduinoIDE");
    } catch (final IOException ex) {
      Utils.tryPrintToConsole(getProject(), ex.toString());
    }
    return null;
  }

  public static void buildProject(IProject project, String configSwitch) throws CoreException,
      IOException {
    final IResource resource =
        project.findMember(PreferenceInitializer.getEntryPointJavaFile(project, true));
    if (MarkerUtils.checkCondition(resource == null, resource,
        MarkerUtils.MISSING_JAVA_ENTRY_POINT_MARKER)) {
      return;
    }

    final String entryPointJavaPath = PreferenceInitializer.getEntryPointJavaFile(project, false);
    Path targetPath =
        Paths.get(PreferenceInitializer.getArduinoLibraryPath() + "/" + project.getName());
    Path projectTemporaryDir =
        Paths.get(getTemporaryDirectory().toAbsolutePath().toString(), project.getName());
    if (!projectTemporaryDir.toFile().exists()) {
      Files.createDirectory(projectTemporaryDir);
    }
    synchronized (project) {
      if (targetPath.toFile().exists()) {
        FileUtils.deleteDirectory(targetPath.toFile());
      }
      final BuildProblem problem =
          runHaikuVM(project, projectTemporaryDir.toFile(), entryPointJavaPath, configSwitch);
      if (!MarkerUtils.checkCondition(problem != null && !"".equals(problem.getError()), resource,
          MarkerUtils.HAIKU_BUILD_ERROR, problem.getError())) {
        FileUtils.copyDirectory(
            Paths.get(projectTemporaryDir.toAbsolutePath().toString(), "HaikuVM").toFile(),
            targetPath.toFile());
      }
    }
  }

  private static BuildProblem runHaikuVM(IProject project, File workingDir,
      String entryPointJavaPath, String configSwitch) throws IOException, PartInitException {
    final ProcessBuilder builder = new ProcessBuilder();
    builder.command(Utils.tryAndInferHaikuVMBaseDir(project) + "\\bin\\haiku.bat", "-v",
        "--Config", configSwitch, entryPointJavaPath);
    builder.directory(workingDir);
    builder.redirectOutput(Redirect.PIPE);
    final MessageConsole console = Utils.getConsole(project);
    final Process process = builder.start();
    BufferedReader reader = null;
    BuildProblem problem = null;
    try {
      reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line = null;

      while ((line = reader.readLine()) != null) {
        console.newMessageStream().println(line);
      }
    } finally {
      reader.close();
    }
    try {
      reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
      String line = null;
      StringBuilder errorBuilder = new StringBuilder();
      while ((line = reader.readLine()) != null) {
        errorBuilder.append(line + "\n");
      }
      problem = new BuildProblem(errorBuilder.toString());
    } finally {
      reader.close();
    }
    return problem;
  };

  private static Path getTemporaryDirectory() throws IOException {
    if (temporaryDirectory != null) {
      return temporaryDirectory;
    }
    temporaryDirectory = Files.createTempDirectory("HaikuVMPlugin");
    return temporaryDirectory;
  }
}
