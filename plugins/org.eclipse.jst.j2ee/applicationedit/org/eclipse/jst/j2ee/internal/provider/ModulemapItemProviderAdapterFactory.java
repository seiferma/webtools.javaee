/*******************************************************************************
 * Copyright (c) 2003, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.internal.provider;



import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITableItemLabelProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.jst.j2ee.internal.earcreation.modulemap.ModulemapAdapterFactory;



/**
 * This is the factory that is used to provide the interfaces needed to support
 * {@link org.eclipse.jface.viewers.Viewer}s. The adapters generated by this factory convert EMF
 * adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}. The adapters
 * also support property sheets, see {@link org.eclipse.ui.views.properties}. Note that most of the
 * adapters are shared among multiple instances.
 */
public class ModulemapItemProviderAdapterFactory extends ModulemapAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier {
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link #com.ibm.etools.emf.edit.provider.IChangeNotifier}.
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This keeps track of all the supported types checked by
	 * {@link #isFactoryForType isFactoryForType}.
	 */
	protected Collection supportedTypes = new ArrayList();

	/**
	 * This constructs an instance.
	 */
	public ModulemapItemProviderAdapterFactory() {
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemPropertySource.class);
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(ITableItemLabelProvider.class);
	}

	/**
	 * This keeps track of the one adapter used for all
	 * {@link org.eclipse.jst.j2ee.internal.internal.internal.earcreation.modulemap.ModuleMapping}
	 * instances.
	 */
	protected ModuleMappingItemProvider moduleMappingItemProvider;

	/**
	 * This creates an adapter for a
	 * {@link org.eclipse.jst.j2ee.internal.internal.internal.earcreation.modulemap.ModuleMapping}.
	 */
	public Adapter createModuleMappingAdapter() {
		if (moduleMappingItemProvider == null) {
			moduleMappingItemProvider = new ModuleMappingItemProvider(this);
		}

		return moduleMappingItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all
	 * {@link org.eclipse.jst.j2ee.internal.internal.internal.earcreation.modulemap.EARProjectMap}
	 * instances.
	 */
	protected EARProjectMapItemProvider earProjectMapItemProvider;

	/**
	 * This creates an adapter for a
	 * {@link org.eclipse.jst.j2ee.internal.internal.internal.earcreation.modulemap.EARProjectMap}.
	 */
	public Adapter createEARProjectMapAdapter() {
		if (earProjectMapItemProvider == null) {
			earProjectMapItemProvider = new EARProjectMapItemProvider(this);
		}

		return earProjectMapItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all
	 * {@link org.eclipse.jst.j2ee.internal.internal.internal.earcreation.modulemap.UtilityJARMapping}
	 * instances.
	 */
	protected UtilityJARMappingItemProvider utilityJARMappingItemProvider;

	/**
	 * This creates an adapter for a
	 * {@link org.eclipse.jst.j2ee.internal.internal.internal.earcreation.modulemap.UtilityJARMapping}.
	 */
	public Adapter createUtilityJARMappingAdapter() {
		if (utilityJARMappingItemProvider == null) {
			utilityJARMappingItemProvider = new UtilityJARMappingItemProvider(this);
		}

		return utilityJARMappingItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 */
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 */
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	public boolean isFactoryForType(Object type) {
		return super.isFactoryForType(type) || supportedTypes.contains(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 */
	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	public Object adapt(Object object, Object type) {
		// This is a kludge to deal with enumerators, which crash the doSwitch.
		//
		if (object instanceof EObject && ((EObject) object).eClass() == null) {
			return null;
		}

		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class) || (((Class) type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	/**
	 * This adds a listener. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * 
	 * @generated
	 */
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier}and to {@link #parentAdapterFactory}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This delegates to {@link #changeNotifier}and to {@link #parentAdapterFactory}.
	 */
	/*
	 * public void fireNotifyChanged(Object object, int eventType, Object feature, Object oldValue,
	 * Object newValue, int index) { changeNotifier.fireNotifyChanged(object, eventType, feature,
	 * oldValue, newValue, index);
	 * 
	 * if (parentAdapterFactory != null) { parentAdapterFactory.fireNotifyChanged(object, eventType,
	 * feature, oldValue, newValue, index); } }
	 */
}
