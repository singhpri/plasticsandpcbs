package haiku.eclipse.plugin.builder;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class LoadArduinoCommand extends AbstractHandler {

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
          HexBuilder.buildProject(project, "arduinoIDEUpload");;
        } catch (final Exception e) {
          throw new ExecutionException("Failed to toggle nature", e);
        }
      }
    }
    return null;
  }
}
