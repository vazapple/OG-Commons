/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.collect.io;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.opengamma.collect.ArgChecker;

/**
 * A map of key-value properties.
 * <p>
 * This class represents a map of key to value.
 * Multiple values may be associated with each key.
 * <p>
 * This class is generally created by reading an INI or properties file.
 * See {@link IniFile} and {@link PropertiesFile}.
 */
public final class PropertySet {
  // this class is common between IniFile and PropertiesFile

  /**
   * The empty instance.
   */
  public static final PropertySet EMPTY = new PropertySet(ImmutableListMultimap.of());

  /**
   * The key-value pairs.
   */
  private final ImmutableListMultimap<String, String> keyValueMap;

  //-------------------------------------------------------------------------
  /**
   * Obtains an instance from a map.
   * <p>
   * The returned instance will have one value for each key.
   * 
   * @param keyValues  the key-values to create the instance with
   * @return the property set
   */
  public static PropertySet of(Map<String, String> keyValues) {
    ArgChecker.notNull(keyValues, "keyValues");
    ImmutableListMultimap.Builder<String, String> builder = ImmutableListMultimap.builder();
    for (Entry<String, String> entry : keyValues.entrySet()) {
      builder.put(entry);
    }
    return new PropertySet(builder.build());
  }

  /**
   * Obtains an instance from a map allowing for multiple values for each key.
   * <p>
   * The returned instance may have more than one value for each key.
   * 
   * @param keyValues  the key-values to create the instance with
   * @return the property set
   */
  public static PropertySet of(Multimap<String, String> keyValues) {
    ArgChecker.notNull(keyValues, "keyValues");
    return new PropertySet(ImmutableListMultimap.copyOf(keyValues));
  }

  //-------------------------------------------------------------------------
  /**
   * Restricted constructor.
   * 
   * @param keyValues  the key-value pairs
   */
  private PropertySet(ImmutableListMultimap<String, String> keyValues) {
    this.keyValueMap = keyValues;
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the set of keys of this property set.
   * <p>
   * The iteration order of the map matches that of the input data.
   * 
   * @return the set of keys
   */
  public ImmutableSet<String> keys() {
    return ImmutableSet.copyOf(keyValueMap.keySet());
  }

  /**
   * Returns the property set as a map.
   * <p>
   * The iteration order of the map matches that of the input data.
   * 
   * @return the key-value map
   */
  public ImmutableListMultimap<String, String> asMap() {
    return keyValueMap;
  }

  //-------------------------------------------------------------------------
  /**
   * Checks if this property set is empty.
   * 
   * @return true if the set is empty
   */
  public boolean isEmpty() {
    return keyValueMap.isEmpty();
  }

  /**
   * Checks if this property set contains the specified key.
   * 
   * @param key  the key name
   * @return true if the key exists
   */
  public boolean contains(String key) {
    ArgChecker.notNull(key, "key");
    return keyValueMap.containsKey(key);
  }

  /**
   * Gets a single value from this property set.
   * <p>
   * This returns the value associated with the specified key.
   * If more than one value, or no value, is associated with the key an exception is thrown.
   * 
   * @param key  the key name
   * @return the value
   * @throws IllegalArgumentException if the key does not exist, or if more than one value is associated
   */
  public String getValue(String key) {
    ArgChecker.notNull(key, "key");
    ImmutableList<String> values = keyValueMap.get(key);
    if (values.size() == 0) {
      throw new IllegalArgumentException("Unknown key: " + key);
    }
    if (values.size() > 1) {
      throw new IllegalArgumentException("Multiple values for key: " + key);
    }
    return values.get(0);
  }

  /**
   * Gets the list of values associated with the specified key.
   * <p>
   * A key-values instance may contain multiple values for each key.
   * This method returns that list of values.
   * The iteration order of the map matches that of the input data.
   * The returned list may be empty.
   * 
   * @param key  the key name
   * @return the list of values associated with the key
   */
  public ImmutableList<String> getValueList(String key) {
    ArgChecker.notNull(key, "key");
    return MoreObjects.firstNonNull(keyValueMap.get(key), ImmutableList.<String>of());
  }

  //-------------------------------------------------------------------------
  /**
   * Combines this property set with another.
   * <p>
   * The specified property set takes precedence.
   * 
   * @param other  the other property set
   * @return the combined property set
   */
  public PropertySet combinedWith(PropertySet other) {
    ArgChecker.notNull(other, "other");
    if (other.isEmpty()) {
      return this;
    }
    if (isEmpty()) {
      return other;
    }
    ListMultimap<String, String> map = ArrayListMultimap.create(keyValueMap);
    for (String key : other.asMap().keySet()) {
      map.removeAll(key);
      map.putAll(key, other.getValueList(key));
    }
    return new PropertySet(ImmutableListMultimap.copyOf(map));
  }

  //-------------------------------------------------------------------------
  /**
   * Checks if this property set equals another.
   * <p>
   * The comparison checks the content.
   * 
   * @param obj  the other section, null returns false
   * @return true if equal
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof PropertySet) {
      return keyValueMap.equals(((PropertySet) obj).keyValueMap);
    }
    return false;
  }

  /**
   * Returns a suitable hash code for the property set.
   * 
   * @return the hash code
   */
  @Override
  public int hashCode() {
    return keyValueMap.hashCode();
  }

  /**
   * Returns a string describing the property set.
   * 
   * @return the descriptive string
   */
  @Override
  public String toString() {
    return keyValueMap.toString();
  }

}
