/*
 * cred-manager is available under the MIT License (2008). See http://opensource.org/licenses/MIT for full text.
 *
 * Copyright (c) 2018, Gluu
 */
package org.gluu.credmanager.core.ldap;

import com.unboundid.ldap.sdk.DN;
import com.unboundid.ldap.sdk.ReadOnlyEntry;
import com.unboundid.ldap.sdk.persist.FilterUsage;
import com.unboundid.ldap.sdk.persist.LDAPEntryField;
import com.unboundid.ldap.sdk.persist.LDAPField;
import com.unboundid.ldap.sdk.persist.LDAPObject;
import org.gluu.credmanager.misc.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class provides an implementation of an object that can be used to
 * represent gluuOrganization objects in the directory.
 * It was generated by the generate-source-from-schema tool provided with the
 * UnboundID LDAP SDK for Java.  It may be customized as desired to better suit
 * your needs.
 */
@LDAPObject(structuralClass="gluuOrganization",
        superiorClass="top")
public class gluuOrganization {

    // The field to use to hold a read-only copy of the associated entry.
    @LDAPEntryField
    private ReadOnlyEntry ldapEntry;

    // The field used for RDN attribute o.
    @LDAPField(inRDN=true,
            filterUsage= FilterUsage.ALWAYS_ALLOWED,
            requiredForEncode=true)
    private String[] o;

    // The field used for optional attribute displayName.
    @LDAPField
    private String displayName;

    // The field used for optional attribute gluuManagerGroup.
    @LDAPField
    private DN[] gluuManagerGroup;

    /**
     * Retrieves the value for the field associated with the
     * displayName attribute, if present.
     *
     * @return  The value for the field associated with the
     *          displayName attribute, or
     *          {@code null} if the field does not have a value.
     */
    public String getDisplayName()
    {
        return displayName;
    }

    /**
     * Retrieves the values for the field associated with the
     * gluuManagerGroup attribute as DNs.
     * @return A list of com.unboundid.ldap.sdk.DN objects (empty if gluuManagerGroup attribute not present).
     */
    public List<DN> getGluuManagerGroupDNs() {
        return Utils.listfromArray(gluuManagerGroup);
    }

}
