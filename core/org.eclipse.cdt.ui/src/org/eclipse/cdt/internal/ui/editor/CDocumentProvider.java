package org.eclipse.cdt.internal.ui.editor;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */

import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.AbstractDocument;
import org.eclipse.jface.text.DefaultLineTracker;
import org.eclipse.jface.text.GapTextStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.editors.text.FileDocumentProvider;

public class CDocumentProvider extends FileDocumentProvider {

	static private class RegisteredReplace {
		IDocumentListener fOwner;
		IDocumentExtension.IReplace fReplace;
			
		RegisteredReplace(IDocumentListener owner, IDocumentExtension.IReplace replace) {
			fOwner= owner;
			fReplace= replace;
		}
	};
	/**
	 * Bundle of all required informations to allow working copy management. 
	 */
	protected class CDocument extends AbstractDocument {
		
		/**
		 * Creates a new empty document.
		 */
		public CDocument() {
			super();
			setTextStore(new GapTextStore(50, 300));
			setLineTracker(new DefaultLineTracker());
			completeInitialization();
		}
		
		/**
		 * Creates a new document with the given initial content.
		 *
		 * @param initialContent the document's initial content
		 */
		public CDocument(String initialContent) {
			super();
			setTextStore(new GapTextStore(50, 300));
			setLineTracker(new DefaultLineTracker());	
			getStore().set(initialContent);
			getTracker().set(initialContent);
			completeInitialization();
		}
	};
	
	/**
	 * @see AbstractDocumentProvider#createDocument(Object)
	 */ 
	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document;
			
		if (element instanceof IStorageEditorInput) {
			IStorage storage= ((IStorageEditorInput) element).getStorage();
			
			document= new CDocument();
			setDocumentContent(document, storage.getContents(), getDefaultEncoding());
		} else {
			return null;
		}
		//IDocument document= super.createDocument(element);
		if (document != null) {
			IDocumentPartitioner partitioner= CUIPlugin.getDefault().getTextTools().createDocumentPartitioner();
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}
	
	/*
	 * @see AbstractDocumentProvider#createAnnotationModel(Object)
	 */
	protected IAnnotationModel createAnnotationModel(Object element) throws CoreException {
		if (element instanceof IFileEditorInput) {
			IFileEditorInput input= (IFileEditorInput) element;
			return new CMarkerAnnotationModel(input.getFile());
		} else if (element instanceof IStorageEditorInput) {
			// Fall back on the adapter.
			IStorageEditorInput input = (IStorageEditorInput) element;
			IResource res = (IResource)input.getAdapter(IResource.class);
			if (res != null && res.exists()) {
				return new CMarkerAnnotationModel(res);
			}
		}
		
		return super.createAnnotationModel(element);
	}
}
