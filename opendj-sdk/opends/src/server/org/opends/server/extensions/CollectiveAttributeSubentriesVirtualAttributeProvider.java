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
 *      Copyright 2009 Sun Microsystems, Inc.
 */

package org.opends.server.extensions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.opends.messages.Message;
import org.opends.server.admin.std.server.
        CollectiveAttributeSubentriesVirtualAttributeCfg;
import org.opends.server.api.VirtualAttributeProvider;
import org.opends.server.core.DirectoryServer;
import org.opends.server.core.SearchOperation;
import org.opends.server.config.ConfigException;
import org.opends.server.loggers.debug.DebugTracer;
import org.opends.server.types.*;

import static org.opends.messages.ExtensionMessages.*;
import static org.opends.server.loggers.debug.DebugLogger.getTracer;

/**
 * This class implements a virtual attribute provider to serve the
 * collectiveAttributeSubentries operational attribute as described
 * in RFC 3671.
 */
public class CollectiveAttributeSubentriesVirtualAttributeProvider
        extends VirtualAttributeProvider<
        CollectiveAttributeSubentriesVirtualAttributeCfg>
{
  /**
   * The tracer object for the debug logger.
   */
  private static final DebugTracer TRACER = getTracer();

  /**
   * Creates a new instance of this HasSubordinates virtual attribute provider.
   */
  public CollectiveAttributeSubentriesVirtualAttributeProvider()
  {
    super();

    // All initialization should be performed in the
    // initializeVirtualAttributeProvider method.
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  public void initializeVirtualAttributeProvider(
          CollectiveAttributeSubentriesVirtualAttributeCfg configuration)
          throws ConfigException, InitializationException
  {
    // No initialization is required.
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  public boolean isMultiValued()
  {
    return true;
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  public Set<AttributeValue> getValues(Entry entry,
                                       VirtualAttributeRule rule)
  {
    Set<AttributeValue> valueSet = new HashSet<AttributeValue>();
    List<SubEntry> subentries =
            DirectoryServer.getSubentryManager().getCollectiveSubentries(entry);

    AttributeType dnAttrType =
            DirectoryServer.getAttributeType("2.5.4.49");
    for (SubEntry subentry : subentries)
    {
      if (subentry.isCollective())
      {
        DN subentryDN = subentry.getDN();
        AttributeValue value = AttributeValues.create(
                dnAttrType, subentryDN.toString());
        valueSet.add(value);
      }
    }

    return valueSet;
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  public boolean isSearchable(VirtualAttributeRule rule,
                              SearchOperation searchOperation)
  {
    return false;
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  public void processSearch(VirtualAttributeRule rule,
                            SearchOperation searchOperation)
  {
    searchOperation.setResultCode(ResultCode.UNWILLING_TO_PERFORM);

    Message message =
            ERR_COLLECTIVEATTRIBUTESUBENTRIES_VATTR_NOT_SEARCHABLE.get(
            rule.getAttributeType().getNameOrOID());
    searchOperation.appendErrorMessage(message);
  }
}
