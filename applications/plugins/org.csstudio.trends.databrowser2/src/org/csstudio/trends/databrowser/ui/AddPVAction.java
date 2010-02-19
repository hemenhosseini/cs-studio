package org.csstudio.trends.databrowser.ui;

import org.csstudio.platform.model.IArchiveDataSource;
import org.csstudio.swt.xygraph.undo.OperationsManager;
import org.csstudio.trends.databrowser.Activator;
import org.csstudio.trends.databrowser.Messages;
import org.csstudio.trends.databrowser.model.AxisConfig;
import org.csstudio.trends.databrowser.model.Model;
import org.csstudio.trends.databrowser.propsheet.AddAxisCommand;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

/** Context menu action that adds a PV to the Model
 *  @author Kay Kasemir
 */
public class AddPVAction extends Action
{
    final private OperationsManager operations_manager;
    final private Shell shell;
    final private Model model;
    
    /** Initialize
     *  @param operations_manager OperationsManager where command will be reg'ed
     *  @param trace_table Table of ModelItems, used to get Shell
     *  @param model Model were PVs will be added
     */
    @SuppressWarnings("nls")
    public AddPVAction(final OperationsManager operations_manager,
            final Shell shell, final Model model)
    {
        super(Messages.AddPV,
              Activator.getDefault().getImageDescriptor("icons/add.gif"));
        this.operations_manager = operations_manager;
        this.shell = shell;
        this.model = model;
    }

    @Override
    public void run()
    {
        runWithSuggestedName(null, null);
    }

    /** Run the 'add PV' dialog with optional defaults
     *  @param name Suggested PV name, for example from drag-n-drop
     *  @param archive Archive data source for the new PV
     */
    public void runWithSuggestedName(final String name, final IArchiveDataSource archive)
    {
        // Prompt for PV name
        final String existing_names[] = new String[model.getItemCount()];
        for (int i=0; i<existing_names.length; ++i)
            existing_names[i] = model.getItem(i).getName();
        final String axes[] = new String[model.getAxisCount()];
        for (int i=0; i<axes.length; ++i)
            axes[i] = model.getAxisConfig(i).getName();
        final AddPVDialog dlg = new AddPVDialog(shell, existing_names, axes);
        dlg.setName(name);
        if (dlg.open() != AddPVDialog.OK)
            return;
        
        AxisConfig axis = null;
        // Locate axis
        if (dlg.getAxis() != null)
            for (int i=0;  i<model.getAxisCount();  ++i)
                if (model.getAxisConfig(i).getName().equals(dlg.getAxis()))
                {
                    axis  = model.getAxisConfig(i);
                    break;
                }
        // If necessary, add axis, which adds another undo-able command
        if (axis == null)
            axis = new AddAxisCommand(operations_manager, model).getAxis();
        
        // Create item
        AddModelItemCommand.forPV(shell, operations_manager, model,
                dlg.getName(), dlg.getScanPeriod(), axis, archive);
    }
}
