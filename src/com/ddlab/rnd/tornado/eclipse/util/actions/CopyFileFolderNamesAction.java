package com.ddlab.rnd.tornado.eclipse.util.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.ddlab.rnd.tornado.eclipse.util.PluginLogger;

public class CopyFileFolderNamesAction implements IObjectActionDelegate {
	/**
	 * For selection
	 */
	private ISelection selection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		/*
		 * For executing the functionality
		 */
		try {
			List<String> pathsList = getFileFolderPath();
			StringBuilder sb = new StringBuilder();
			for( String paths : pathsList ) {
				sb.append(paths).append("\r\n");
			}
			copyToClipBoard(new String[] {sb.toString()});
		} catch (NullPointerException npe) {
			PluginLogger.error(npe);
			MessageDialog.openError(new Shell(), "Error", npe.getMessage()
					+ " " + "\nReport to debadatta.mishra@gmail.com");
		} catch (Exception e) {
			PluginLogger.error(e);
			MessageDialog.openError(new Shell(), "Error", e.getMessage() + " "
					+ "\nReport to debadatta.mishra@gmail.com");
		}
	}

	/**
	 * Method used to copy files and folders to ClipBoard
	 * 
	 * @param data
	 */
	public void copyToClipBoard(String[] data) {
		try {
			if (data.length != 0) {
				Display display = Display.getCurrent();
				Clipboard clipboard = new Clipboard(display);
				clipboard.setContents(data,
						new Transfer[] { TextTransfer.getInstance() });
				clipboard.dispose();
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(new Shell(), "Error",
					"UnExpected Error while copying files and folders to ClipBoard."
					+ "\nReport to debadatta.mishra@gmail.com");
			MessageDialog.openError(new Shell(), "Error",
					"UnExpected Error while copying files and folders to ClipBoard."
					+ "\nReport to debadatta.mishra@gmail.com");
		}
	}

	/**
	 * Method to get the list of files
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<String> getFileFolderPath() throws Exception {
		List<String> resourceList = new ArrayList<String>();

		Iterator<?> itr = ((IStructuredSelection) selection).iterator();
		try {
			while (itr.hasNext()) {
				Object selctionElement = itr.next();
				if (selctionElement instanceof IAdaptable) {
					IAdaptable adaptable = (IAdaptable) selctionElement;
					IResource resource = (IResource) adaptable
					.getAdapter(IResource.class);
					resourceList.add(resource.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			PluginLogger
			.error(
					"UnExpected Excepton while copying files and folders ...\n",
					e);
			MessageDialog.openError(new Shell(), "Error",
					"Exception while copying files and folders ."
					+ "\nReport to debadatta.mishra@gmail.com");
		}
		return resourceList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.
	 * action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction arg0, IWorkbenchPart arg1) {

	}
}
