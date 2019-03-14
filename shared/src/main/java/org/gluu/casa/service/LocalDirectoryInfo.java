package org.gluu.casa.service;

/**
 * Provides access to information such as the location of the most important entities of Gluu Server in a tree
 * directory structure.
 */
public interface LocalDirectoryInfo {

    /**
     * Returns the DN of a person in your local Gluu Server LDAP.
     * @param id ID of person (<code>inum</code> attribute value). No checks are made with regard to the value passed actually
     *           representing an existing LDAP entry
     * @return A string value
     */
    String getPersonDn(String id);

    /**
     * Returns the DN of the <i>people</i> branch in your local Gluu Server LDAP.
     * @return A string value
     */
    String getPeopleDn();

    /**
     * Returns the DN of the <i>groups</i> branch in your local Gluu Server LDAP.
     * @return A string value
     */
    String getGroupsDn();

    /**
     * Returns the DN of the <i>scopes</i> branch in your local Gluu Server LDAP.
     * @return A string value
     */
    String getScopesDn();

    /**
     * Returns the DN of the <i>clients</i> branch in your local Gluu Server LDAP.
     * @return A string value
     */
    String getClientsDn();

    /**
     * Returns the DN of the <i>scripts</i> branch in your local Gluu Server LDAP.
     * @return A string value
     */
    String getCustomScriptsDn();

    /**
     * Returns the ID (<code>inum</code> attribute value) of the <i>o</i> entry that contains most of Gluu Server
     * LDAP branches like <i>people, groups, clients, etc.</i>.
     * @return A string value
     */
    String getOrganizationInum();

    /**
     * Returns the URL of (this) authorization server. Typically, it has the form https://host
     * @return A string value
     */
    String getIssuerUrl();

}