package haiku.eclipse.plugin.builder;

import haiku.eclipse.plugin.common.Utils;
import haiku.eclipse.plugin.preferences.PreferenceInitializer;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;


public class SetJavaEntryPoint extends AbstractHandler {

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    try {
      final ISelection selection = HandlerUtil.getCurrentSelection(event);
      if (selection instanceof IStructuredSelection) {
        final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
        final Object obj = structuredSelection.getFirstElement();
        if (obj instanceof ICompilationUnit) {
          final IResource resource = ((ICompilationUnit) obj).getResource();
          PreferenceInitializer
              .setEntryPointJavaFile(resource.getProject(), resource.getLocation());
        }
      }
    } catch (final Exception ex) {
      Utils.tryPrintToConsole(null, ex.toString());
    }
    return null;
  }
}
