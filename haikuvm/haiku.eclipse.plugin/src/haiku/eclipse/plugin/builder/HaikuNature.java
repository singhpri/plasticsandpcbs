package haiku.eclipse.plugin.builder;

import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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
    desc.setBuildSpec((ICommand[]) commands.toArray());
    project.setDescription(desc, null);
    /*
     * final ICommand[] commands = desc.getBuildSpec();
     *
     * for (final ICommand command : commands) { if
     * (command.getBuilderName().equals(HexBuilder.BUILDER_ID)) { return; } }
     *
     * final ICommand[] newCommands = new ICommand[commands.length + 1]; System.arraycopy(commands,
     * 0, newCommands, 0, commands.length); final ICommand command = desc.newCommand();
     * command.setBuilderName(HexBuilder.BUILDER_ID); newCommands[newCommands.length - 1] = command;
     * desc.setBuildSpec(newCommands); project.setDescription(desc, null);
     */
  }

  @Override
  public void deconfigure() throws CoreException {
    final IProjectDescription description = getProject().getDescription();
    ICommand commandToRemove = null;
    for (final ICommand command : description.getBuildSpec()) {
      if (command.getBuilderName().equals(HexBuilder.BUILDER_ID)) {
        commandToRemove = command;
        break;
      }
    }
    final Set<ICommand> commands = Sets.newHashSet(description.getBuildSpec());
    commands.remove(commandToRemove);
    description.setBuildSpec((ICommand[]) commands.toArray());
    project.setDescription(description, null);

    /*
     * final ICommand[] commands = description.getBuildSpec(); for (int i = 0; i < commands.length;
     * ++i) { if (commands[i].getBuilderName().equals(HexBuilder.BUILDER_ID)) { final ICommand[]
     * newCommands = new ICommand[commands.length - 1]; System.arraycopy(commands, 0, newCommands,
     * 0, i); System.arraycopy(commands, i + 1, newCommands, i, commands.length - i - 1);
     * description.setBuildSpec(newCommands); project.setDescription(description, null); return; } }
     */
  }

  @Override
  public IProject getProject() {
    return project;
  }

  @Override
  public void setProject(IProject project) {
    this.project = project;
  }
}
