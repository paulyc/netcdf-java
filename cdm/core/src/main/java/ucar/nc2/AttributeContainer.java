/*
 * Copyright (c) 1998-2018 John Caron and University Corporation for Atmospheric Research/Unidata
 * See LICENSE for license information.
 */

package ucar.nc2;

import java.util.Iterator;
import javax.annotation.Nullable;

/**
 * An immutable Container of Attributes.
 * Use AttributeContainerMutable if you want a mutable container.
 */
public interface AttributeContainer extends Iterable<Attribute> {

  /** Find an Attribute by exact match on name. */
  @Nullable
  Attribute findAttribute(String attName);

  /** Determine if named attribute exists (exact match). */
  default boolean hasAttribute(String attName) {
    return null != findAttribute(attName);
  }

  /** Find an Attribute by name, first doing an exact match, then ignoring case. */
  @Nullable
  Attribute findAttributeIgnoreCase(String attName);

  /** Determine if named attribute exists, ignoring case. */
  default boolean hasAttributeIgnoreCase(String attName) {
    return null != findAttributeIgnoreCase(attName);
  }

  /**
   * Find a Numeric Attribute by name (ignore case), return the double value of the Attribute.
   *
   * @return the attribute value, or defaultValue if not found
   */
  double findAttributeDouble(String attName, double defaultValue);

  /**
   * Find a Numeric Attribute by name (ignore case), return the integer value of the Attribute.
   *
   * @return the attribute value, or defaultValue if not found
   */
  int findAttributeInteger(String attName, int defaultValue);

  /**
   * Find a String Attribute by name (ignore case), return the String value of the Attribute.
   *
   * @return the attribute value, or defaultValue if not found
   */
  String findAttributeString(String attName, String defaultValue);

  /** Get the (optional) name of the AttributeContainer. */
  @Nullable
  String getName();

  /** An unordered iterator over the contained attributes. */
  @Override
  default Iterator<Attribute> iterator() {
    return getAttributes().iterator();
  }

  /** @deprecated use findAttributeString(). */
  @Deprecated
  default String findAttValueIgnoreCase(String attName, String defaultValue) {
    return findAttributeString(attName, defaultValue);
  }

  ///// will be removed in version 6 to make AttributeContainer immutable
  /**
   * Returns immutable list of attributes.
   *
   * @deprecated use Iterable<Attribute>
   */
  @Deprecated
  java.util.List<Attribute> getAttributes();

  /**
   * Add all; replace old if has same name
   *
   * @deprecated will be removed in version 6.
   */
  @Deprecated
  void addAll(Iterable<Attribute> atts);

  /**
   * Add new or replace old if has same name
   *
   * @param att add this Attribute
   * @return the added attribute
   * @deprecated will be removed in version 6.
   */
  @Deprecated
  Attribute addAttribute(Attribute att);

  /**
   * Remove an Attribute : uses the attribute hashCode to find it.
   *
   * @param a remove this attribute
   * @return true if was found and removed
   * @deprecated will be removed in version 6.
   */
  @Deprecated
  boolean remove(Attribute a);

  /**
   * Remove an Attribute by name.
   *
   * @param attName if exists, remove this attribute
   * @return true if was found and removed
   * @deprecated will be removed in version 6.
   */
  @Deprecated
  boolean removeAttribute(String attName);

  /**
   * Remove an Attribute by name, ignoring case
   *
   * @param attName if exists, remove this attribute
   * @return true if was found and removed
   * @deprecated will be removed in version 6.
   */
  @Deprecated
  boolean removeAttributeIgnoreCase(String attName);
}
