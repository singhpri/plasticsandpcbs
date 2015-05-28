package haiku.eclipse.plugin.builder;

import haiku.eclipse.plugin.common.Utils;
import haiku.eclipse.plugin.preferences.PreferenceInitializer;

import java.io.IOException;

import org.eclipse.core.commands.*;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.osgi.service.prefs.BackingStoreException;

public class SetJavaEntryPoint extends AbstractHandler {

  public Object execute(ExecutionEvent event) throws ExecutionException {
    try {
      ISelection selection = HandlerUtil.getCurrentSelection(event);
      if (selection instanceof IStructuredSelection) {
        IStructuredSelection structuredSelection = (IStructuredSelection) selection;
        Object obj = structuredSelection.getFirstElement();
        if (obj instanceof ICompilationUnit) {
          IResource resource = ((ICompilationUnit) obj).getResource();
          PreferenceInitializer.setEntryPointJavaFile(resource.getProject(), resource.getLocation());
        } else {
          Utils.tryPrintToConsole("\nPlease select a Java file");
        }
      }
    } catch (Exception ex) {
      Utils.tryPrintToConsole(ex.toString());
    }
    return null;
  }
}
