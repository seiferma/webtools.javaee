/*******************************************************************************
 * Copyright (c) 2008, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.jst.j2ee.internal.componentcore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jst.j2ee.internal.archive.JavaEEEMFZipFileLoadAdapterImpl;
import org.eclipse.jst.j2ee.internal.plugin.J2EEPlugin;
import org.eclipse.jst.jee.archive.ArchiveOpenFailureException;
import org.eclipse.jst.jee.archive.IArchiveResource;
import org.eclipse.jst.jee.archive.internal.ArchiveUtil;
import org.eclipse.wst.common.componentcore.internal.resources.VirtualArchiveComponent;

/**
 * @author jsholl
 *
 */
public class JavaEEBinaryComponentLoadAdapter extends JavaEEEMFZipFileLoadAdapterImpl {

	private java.io.File file = null;
	private IPath archivePath;
	private boolean physicallyOpen = true;
	
	private VirtualArchiveComponent archiveComponent;
	
	public JavaEEBinaryComponentLoadAdapter(VirtualArchiveComponent archiveComponent) throws ArchiveOpenFailureException {
		super();
		this.archiveComponent = archiveComponent;
		IPath archivePath = recomputeArchivePath();
		if( archivePath != null ) {
			try{
				resetZipFile(archivePath);
			} catch (ZipException e) {
				ArchiveOpenFailureException openFailureException = new ArchiveOpenFailureException(archivePath, file.getAbsolutePath(), e);
				throw openFailureException;
			} catch (IOException e) {
				ArchiveOpenFailureException openFailureException = new ArchiveOpenFailureException(archivePath, file.getAbsolutePath(), e);
				throw openFailureException;
			}
		} else {
			ArchiveOpenFailureException openFailureException = new ArchiveOpenFailureException(new String() + null);
			throw openFailureException;
		}
	}
	
	private void resetZipFile(IPath archivePath)  throws ZipException, IOException {
		file = new java.io.File(archivePath.toOSString());
		ZipFile zipFile = ArchiveUtil.newZipFile(file);
		setZipFile(zipFile);
		setArchivePath(archivePath);	
	}
	
	private IPath recomputeArchivePath() {
		java.io.File f = (java.io.File) archiveComponent.getAdapter(java.io.File.class);
		if (f == null || !f.exists()) {
			IFile wbFile = (IFile) archiveComponent.getAdapter(IFile.class);
			if (wbFile != null) {
				f = new File(wbFile.getLocation().toOSString());
			}
		}
		return f == null ? null : new Path(f.getAbsolutePath());
	}
	
	private void setArchivePath(IPath archivePath) {
		this.archivePath = archivePath;
	}
	
	public IPath getArchivePath() {
		return archivePath;
	}
	
	public boolean isPhysicallyOpen(){
		return physicallyOpen;
	}
	
	public void physicallyOpen() throws ZipException, IOException{
		if(!isPhysicallyOpen()){
			if(file != null && file.exists()){
				setZipFile(ArchiveUtil.newZipFile(file));
			} else { 
				//check if the file has moved -- this can happen when
				//checking into ClearCase.
				IPath newPath = recomputeArchivePath();
				if(newPath == archivePath || newPath == null || newPath.equals(archivePath)){
					String loc = archivePath == null ? null : archivePath.toOSString();
					throw new FileNotFoundException(loc);
				}
				resetZipFile(newPath);
			}
			physicallyOpen = true;
		}
	}
	
	public void physicallyClose(){
		if(isPhysicallyOpen()){
			physicallyOpen = false;
			try{
				zipFile.close();
			}
			catch (Throwable t) {
				//Ignore
			}
		} 
	}

	@Override
	public boolean containsArchiveResource(IPath resourcePath) {
		final boolean isPhysciallyOpen = isPhysicallyOpen();
		Exception caughtException = null;
		try {
			if (!isPhysciallyOpen) {
				physicallyOpen();
			}
			try{
				return super.containsArchiveResource(resourcePath);
			} catch(Exception e){
				J2EEPlugin.logError(caughtException);
			}
		} catch (Exception e) {
			caughtException = e;
			J2EEPlugin.logError(caughtException);
		} finally {
			if (caughtException != null) {
				if (!isPhysciallyOpen) {
					physicallyClose();
				}
			}
		}
		return false;
	}
	
	@Override
	public IArchiveResource getArchiveResource(IPath filePath) {
		final boolean isPhysciallyOpen = isPhysicallyOpen();
		Exception caughtException = null;
		try {
			if (!isPhysciallyOpen) {
				physicallyOpen();
			}
			try {
				return super.getArchiveResource(filePath);
			} catch(Exception e){
				J2EEPlugin.logError(caughtException);
			}
		} catch (Exception e) {
			caughtException = e;
			J2EEPlugin.logError(caughtException);
		} finally {
			if (caughtException != null) {
				if (!isPhysciallyOpen) {
					physicallyClose();
				}
			}
		}
		return null;
	}
	
	@Override
	public List getArchiveResources() {
		final boolean isPhysciallyOpen = isPhysicallyOpen();
		Exception caughtException = null;
		try {
			if (!isPhysciallyOpen) {
				physicallyOpen();
			}
			try {
				return super.getArchiveResources();
			} catch(Exception e){
				J2EEPlugin.logError(caughtException);
			}
		} catch (Exception e) {
			caughtException = e;
			J2EEPlugin.logError(caughtException);
		} finally {
			if (caughtException != null) {
				if (!isPhysciallyOpen) {
					physicallyClose();
				}
			}
		}
		return Collections.EMPTY_LIST;
	}
	
	
	@Override
	public java.io.InputStream getInputStream(IArchiveResource aFile) throws IOException, FileNotFoundException {
		final boolean isPhysciallyOpen = isPhysicallyOpen();
		Exception caughtException = null;
		try {
			if (!isPhysciallyOpen) {
				physicallyOpen();
			}
			IPath path = aFile.getPath();
			String uri = path.toString();
			ZipEntry entry = getZipFile().getEntry(uri);
			if (entry == null)
				throw new FileNotFoundException(uri);

			return new java.io.BufferedInputStream(getZipFile().getInputStream(entry)) {
				@Override
				public void close() throws IOException {
					super.close();
					if (!isPhysciallyOpen ) {
						physicallyClose();
					}
				}
			};
		} catch (FileNotFoundException e) {
			caughtException = e;
			throw e;
		} catch (IllegalStateException zipClosed) {
			caughtException = zipClosed;
			throw new IOException(zipClosed.toString());
		} catch (Exception e) {
			caughtException = e;
			throw new IOException(e.toString());
		} finally {
			if (caughtException != null) {
				if (!isPhysciallyOpen) {
					physicallyClose();
				}
			}
		}
	}

	public VirtualArchiveComponent getArchiveComponent() {
		return archiveComponent;
	}	
}