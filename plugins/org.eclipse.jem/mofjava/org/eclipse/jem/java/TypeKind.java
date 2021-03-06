/*******************************************************************************
 * Copyright (c) 2001, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jem.java;
/*


 */
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Type Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * UNDEFINED=1 CLASS=2 INTERFACE=3 EXCEPTION=4
 * <!-- end-model-doc -->
 * @see org.eclipse.jem.java.JavaRefPackage#getTypeKind()
 * @model
 * @generated
 */
public final class TypeKind extends AbstractEnumerator
{
	/**
	 * The '<em><b>UNDEFINED</b></em>' literal value.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #UNDEFINED_LITERAL
	 * @model
	 * @generated
	 * @ordered
	 */
  public static final int UNDEFINED = 0;

	/**
	 * The '<em><b>CLASS</b></em>' literal value.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #CLASS_LITERAL
	 * @model
	 * @generated
	 * @ordered
	 */
  public static final int CLASS = 1;

	/**
	 * The '<em><b>INTERFACE</b></em>' literal value.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #INTERFACE_LITERAL
	 * @model
	 * @generated
	 * @ordered
	 */
  public static final int INTERFACE = 2;

	/**
	 * The '<em><b>EXCEPTION</b></em>' literal value.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #EXCEPTION_LITERAL
	 * @model
	 * @generated
	 * @ordered
	 */
  public static final int EXCEPTION = 3;

	/**
	 * The '<em><b>UNDEFINED</b></em>' literal object.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>UNDEFINED</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @see #UNDEFINED
	 * @generated
	 * @ordered
	 */
  public static final TypeKind UNDEFINED_LITERAL = new TypeKind(UNDEFINED, "UNDEFINED", "UNDEFINED");

	/**
	 * The '<em><b>CLASS</b></em>' literal object.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>CLASS</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @see #CLASS
	 * @generated
	 * @ordered
	 */
  public static final TypeKind CLASS_LITERAL = new TypeKind(CLASS, "CLASS", "CLASS");

	/**
	 * The '<em><b>INTERFACE</b></em>' literal object.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>INTERFACE</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @see #INTERFACE
	 * @generated
	 * @ordered
	 */
  public static final TypeKind INTERFACE_LITERAL = new TypeKind(INTERFACE, "INTERFACE", "INTERFACE");

	/**
	 * The '<em><b>EXCEPTION</b></em>' literal object.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>EXCEPTION</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @see #EXCEPTION
	 * @generated
	 * @ordered
	 */
  public static final TypeKind EXCEPTION_LITERAL = new TypeKind(EXCEPTION, "EXCEPTION", "EXCEPTION");

	/**
	 * An array of all the '<em><b>Type Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  private static final TypeKind[] VALUES_ARRAY =
		new TypeKind[] {
			UNDEFINED_LITERAL,
			CLASS_LITERAL,
			INTERFACE_LITERAL,
			EXCEPTION_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Type Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Type Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public static TypeKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TypeKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Type Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TypeKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TypeKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Type Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public static TypeKind get(int value) {
		switch (value) {
			case UNDEFINED: return UNDEFINED_LITERAL;
			case CLASS: return CLASS_LITERAL;
			case INTERFACE: return INTERFACE_LITERAL;
			case EXCEPTION: return EXCEPTION_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private TypeKind(int value, String name, String literal) {
		super(value, name, literal);
	}

} //TypeKind
