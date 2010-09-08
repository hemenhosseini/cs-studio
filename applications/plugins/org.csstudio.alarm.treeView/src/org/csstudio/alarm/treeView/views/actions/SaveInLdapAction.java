/*
 * Copyright (c) 2010 Stiftung Deutsches Elektronen-Synchrotron, Member of the Helmholtz
 * Association, (DESY), HAMBURG, GERMANY.
 *
 * THIS SOFTWARE IS PROVIDED UNDER THIS LICENSE ON AN "../AS IS" BASIS. WITHOUT WARRANTY OF ANY
 * KIND, EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE. SHOULD THE SOFTWARE PROVE DEFECTIVE IN ANY RESPECT, THE USER ASSUMES
 * THE COST OF ANY NECESSARY SERVICING, REPAIR OR CORRECTION. THIS DISCLAIMER OF WARRANTY
 * CONSTITUTES AN ESSENTIAL PART OF THIS LICENSE. NO USE OF ANY SOFTWARE IS AUTHORIZED HEREUNDER
 * EXCEPT UNDER THIS DISCLAIMER. DESY HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
 * ENHANCEMENTS, OR MODIFICATIONS. THE FULL LICENSE SPECIFYING FOR THE SOFTWARE THE REDISTRIBUTION,
 * MODIFICATION, USAGE AND OTHER RIGHTS AND OBLIGATIONS IS INCLUDED WITH THE DISTRIBUTION OF THIS
 * PROJECT IN THE FILE LICENSE.HTML. IF THE LICENSE IS NOT INCLUDED YOU MAY FIND A COPY AT
 * HTTP://WWW.DESY.DE/LEGAL/LICENSE.HTM
 */
package org.csstudio.alarm.treeView.views.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.annotation.Nonnull;

import org.csstudio.alarm.treeView.views.AlarmTreeModificationException;
import org.csstudio.alarm.treeView.views.ITreeModificationItem;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPartSite;

/**
 * Saves the recent tree modifications in LDAP.
 *
 *
 * @author bknerr
 * @author $Author$
 * @version $Revision$
 * @since 17.06.2010
 */
public final class SaveInLdapAction extends Action {

    private final IWorkbenchPartSite _site;
    private final Queue<ITreeModificationItem> _ldapModifications;

    /**
     * Constructor.
     * @param site
     * @param ldapModifications
     */
    SaveInLdapAction(@Nonnull final IWorkbenchPartSite site,
                     @Nonnull final Queue<ITreeModificationItem> ldapModifications) {
        _site = site;
        _ldapModifications = ldapModifications;
    }

    @Override
    public void run() {
        final List<String> notAppliedMods= new ArrayList<String>();
        String failedMod = "";
        final List<String> appliedMods = new ArrayList<String>();
        synchronized (_ldapModifications) {
            try {
                /*
                  Note: although a concurrent queue is utilised, it has to be explicitly inhibited
                  that items are added, removed, or modified by any other thread during the following
                  queue traversal for the 'save in LDAP' action.
                  Hence, a synchronised block is necessary on the queue - that nonetheless leaves a tiny time window in between the
                  the user's 'save in LDAP' activation and the start of this block for the queue to be
                  modified!
                 */
                while (!_ldapModifications.isEmpty()) {
                    final ITreeModificationItem item = _ldapModifications.poll();
                    failedMod = item.getDescription();
                    item.apply();
                    appliedMods.add(item.getDescription() + "\n");
                }

                final String summary = appliedMods.isEmpty() ? "No LDAP Modifications!" :
                                                               "Applied Modifications:\n" + appliedMods;

                MessageDialog.openConfirm(_site.getShell(),
                                          "LDAP persistence status of recent tree modification.",
                                          summary);

                setEnabled(false);

            } catch (final AlarmTreeModificationException e) {

                for (final ITreeModificationItem item : _ldapModifications) {
                    notAppliedMods.add("\n-" + item.getDescription());
                }
                MessageDialog.openInformation(_site.getShell(),
                                              "LDAP persistence status of recent tree modification.",
                                              "Applied Modifications:\n" + appliedMods + "\n\nFailed Modification:\n" + failedMod + "\n\nNot Applied Modifications:\n\n" + notAppliedMods);


            }
        }
    }
}
