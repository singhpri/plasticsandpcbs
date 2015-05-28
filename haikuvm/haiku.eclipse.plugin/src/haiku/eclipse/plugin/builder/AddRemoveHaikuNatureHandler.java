package haiku.eclipse.plugin.builder;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;

public class AddRemoveHaikuNatureHandler extends AbstractHandler {

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    final ISelection selection = HandlerUtil.getCurrentSelection(event);
    if (!(selection instanceof IStructuredSelection)) {
      return null;
    }
    for (final Iterator<?> it = ((IStructuredSelection) selection).iterator(); it.hasNext();) {
      final Object element = it.next();
      IProject project = null;
      if (element instanceof IProject) {
        project = (IProject) element;
      } else if (element instanceof IAdaptable) {
        project = (IProject) ((IAdaptable) element).getAdapter(IProject.class);
      }
      if (project != null) {
        try {
          toggleNature(project);
        } catch (final CoreException e) {
          throw new ExecutionException("Failed to toggle nature", e);
        }
      }
    }
    return null;
  }

  private void toggleNature(IProject project) throws CoreException {
    final IProjectDescription description = project.getDescription();
    final Set<String> natures = Sets.newHashSet(description.getNatureIds());

    if (natures.contains(HaikuNature.NATURE_ID)) {
      natures.remove(HaikuNature.NATURE_ID);
    } else {
      natures.add(HaikuNature.NATURE_ID);
    }
    description.setNatureIds(FluentIterable.from(natures).toArray(String.class));
    project.setDescription(description, null);
    /*
     * for (int i = 0; i < natures.length; ++i) { if (HaikuNature.NATURE_ID.equals(natures[i])) {
     * final String[] newNatures = new String[natures.length - 1]; System.arraycopy(natures, 0,
     * newNatures, 0, i); System.arraycopy(natures, i + 1, newNatures, i, natures.length - i - 1);
     * description.setNatureIds(newNatures); project.setDescription(description, null); return; } }
     */

    /*
     * final String[] newNatures = new String[natures.length + 1]; System.arraycopy(natures, 0,
     * newNatures, 0, natures.length); newNatures[natures.length] = HaikuNature.NATURE_ID;
     * description.setNatureIds(newNatures); project.setDescription(description, null);
     */
  }

}
