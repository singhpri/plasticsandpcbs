package haiku.eclipse.plugin.builder;

import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.google.common.collect.Lists;

public class HaikuNature implements IProjectNature {

  public static final String NATURE_ID = "haiku.eclipse.plugin.builder.haikuNature";

  private IProject project;

  @Override
  public void configure() throws CoreException {

    final IProjectDescription desc = project.getDescription();
    for (final ICommand command : desc.getBuildSpec()) {
      if (command.getBuilderName().equals(HexBuilder.BUILDER_ID)) {
        return;
      }
    }

    final List<ICommand> commands = Lists.newArrayList(desc.getBuildSpec());
    final ICommand command = desc.newCommand();
    command.setBuilderName(HexBuilder.BUILDER_ID);
    commands.add(command);
    desc.setBuildSpec(commands.toArray(new ICommand[] {}));
    project.setDescription(desc, null);
  }

  @Override
  public IProject getProject() {
    return project;
  }

  @Override
  public void setProject(IProject project) {
    this.project = project;
  }

  @Override
  public void deconfigure() throws CoreException {
    // Nothing to do. We have a configure() implementation so the asymmetry deserves an explanation.
    // Eclipse is smart enough to remove the builder from the project when the nature is
    // removed, so we don't have to do anything in this method. Unfortunately, it's not equally
    // smart to add the builder when the nature is added, hence the asymmetry.
  }
}
