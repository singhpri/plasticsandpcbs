package haiku.eclipse.plugin.preferences;

import haiku.eclipse.plugin.Activator;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that is contributed to the Preferences dialog. By
 * subclassing <samp>FieldEditorPreferencePage</samp>, we can use the field support built into JFace
 * that allows us to create a page that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the preference store that
 * belongs to the main plug-in class. That way, preferences can be accessed directly via the
 * preference store.
 */

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

  public PreferencePage() {
    super(GRID);
    setPreferenceStore(Activator.getDefault().getPreferenceStore());
    setDescription("Preferences for the HaikuVM plugin");
  }

  /**
   * Creates the field editors.
   */
  @Override
  public void createFieldEditors() {
    addField(new DirectoryFieldEditor(PreferenceInitializer.ARDUINO_LIB_PATH,
        "&Arduino Library Folder:", getFieldEditorParent()));
    addField(new DirectoryFieldEditor(PreferenceInitializer.HAIKUVM_PATH, "&Haiku VM Folder:",
        getFieldEditorParent()));
  }

  @Override
  public void init(IWorkbench workbench) {

  }
}
