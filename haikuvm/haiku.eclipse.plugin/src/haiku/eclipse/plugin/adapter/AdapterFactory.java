package haiku.eclipse.plugin.adapter;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;

public class AdapterFactory implements IAdapterFactory {

  @SuppressWarnings("rawtypes")
  private static final Class[] adapterList = new Class[] {EntryPointClass.class};

  @SuppressWarnings("rawtypes")
  @Override
  public Object getAdapter(Object resource, Class arg1) {
    if (resource instanceof IResource) {
      return new EntryPointClass((IResource) resource);
    }
    return null;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Class[] getAdapterList() {
    return adapterList;
  }
}
