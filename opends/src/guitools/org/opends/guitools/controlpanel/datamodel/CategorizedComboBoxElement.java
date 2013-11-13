/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License, Version 1.0 only
 * (the "License").  You may not use this file except in compliance
 * with the License.
 *
 * You can obtain a copy of the license at
 * trunk/opends/resource/legal-notices/OpenDS.LICENSE
 * or https://OpenDS.dev.java.net/OpenDS.LICENSE.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at
 * trunk/opends/resource/legal-notices/OpenDS.LICENSE.  If applicable,
 * add the following below this CDDL HEADER, with the fields enclosed
 * by brackets "[]" replaced with your own identifying information:
 *      Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 *
 *
 *      Copyright 2008 Sun Microsystems, Inc.
 */

package org.opends.guitools.controlpanel.datamodel;

/**
 * Class used in the combo box models.  It is used to have special rendering in
 * the combo boxes.
 */
public class CategorizedComboBoxElement
{
  private Object value;
  private Type type;
  private int hashCode;

  /**
   * The type of the element.
   *
   */
  public enum Type
  {
    /**
     * Category type (in a combo box containing base DNs the backends are of
     * type category, for instance).
     */
    CATEGORY,
    /**
     * Regular type.
     */
    REGULAR
  };

  /**
   * Constructor.
   * @param value the value of the element.
   * @param type the type of the element.
   */
  public CategorizedComboBoxElement(Object value, Type type)
  {
    this.value = value;
    this.type = type;
    this.hashCode = this.value.hashCode() + this.type.hashCode();
  }

  /**
   * Returns the value.
   * @return the value.
   */
  public Object getValue()
  {
    return value;
  }

  /**
   * Returns the type of the element.
   * @return the type of the element.
   */
  public Type getType()
  {
    return type;
  }

  /**
   * {@inheritDoc}
   */
  public boolean equals(Object o)
  {
    boolean equals = false;
    if (o != null)
    {
      if (o instanceof CategorizedComboBoxElement)
      {
        CategorizedComboBoxElement desc = (CategorizedComboBoxElement)o;
        equals = (desc.getType() == getType()) &&
        (getValue().equals(desc.getValue()));
      }
    }
    return equals;
  }

  /**
   * {@inheritDoc}
   */
  public int hashCode()
  {
    return hashCode;
  }
}