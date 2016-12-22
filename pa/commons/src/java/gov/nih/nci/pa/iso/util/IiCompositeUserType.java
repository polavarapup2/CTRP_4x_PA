/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The COPPA PO
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This COPPA PO Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the COPPA PO Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the COPPA PO Software; (ii) distribute and
 * have distributed to and by third parties the COPPA PO Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.iso.util;

import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.IdentifierScope;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.NullableType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

/**
 * Hibernate user type to directly persist Ii to the db with full query capability.
 */
public class IiCompositeUserType implements CompositeUserType {

    // Constants for the position of the attribute in the composite type
    /** Positional param. */
    public static final int NULLFLAVOR = 0;
    /** Positional param. */
    public static final int DISPLAYABLE = 1;
    /** Positional param. */
    public static final int EXTENSION = 2;
    /** Positional param. */
    public static final int IDENTIFIER_NAME = 3;
    /** Positional param. */
    public static final int RELIABILITY = 4;
    /** Positional param. */
    public static final int ROOT = 5;
    /** Positional param. */
    public static final int SCOPE = 6;

    /**
     * {@inheritDoc}
     */
    public Object assemble(Serializable cached, SessionImplementor si, Object owner) {
        return cached;
    }

    /**
     * {@inheritDoc}
     */
    public Object deepCopy(Object value) {
        if (value == null) {
            return null;
        }

        Ii snapshot = (Ii) value;
        return snapshot.clone();
    }

    /**
     * {@inheritDoc}
     */
    public Serializable disassemble(Object value, SessionImplementor si) {
        return (Serializable) value;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.CompareObjectsWithEquals")  // intentional comparison with ==
    public boolean equals(Object x, Object y) {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }

        Ii i1 = (Ii) x;
        Ii i2 = (Ii) y;

        return i1.equals(i2);
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode(Object x) {

        Ii ii = (Ii) x;

        return ii.hashCode();    }

    /**
     * {@inheritDoc}
     */
    public String[] getPropertyNames() {
        return new String[] {"nullFlavor", "displayable", "extension", "identifierName",
                             "reliability", "root", "scope"};
    }

    /**
     * {@inheritDoc}
     */
    public Type[] getPropertyTypes() {
        return new Type[] {Hibernate.STRING, Hibernate.BOOLEAN, Hibernate.STRING,
                           Hibernate.STRING, Hibernate.STRING, Hibernate.STRING, Hibernate.STRING};
    }

    /**
     * {@inheritDoc}
     */
    public Object getPropertyValue(Object component, int property) {
        Ii var = (Ii) component;
        switch (property) {
        case NULLFLAVOR:
            return (var.getNullFlavor() != null) ? var.getNullFlavor().toString() : null;
        case DISPLAYABLE:
            return var.getDisplayable();
        case EXTENSION:
            return var.getExtension();
        case IDENTIFIER_NAME:
            return var.getIdentifierName();
        case RELIABILITY:
            return (var.getReliability() != null) ? var.getReliability().toString() : null;
        case ROOT:
            return var.getRoot();
        case SCOPE:
            return (var.getScope() != null) ? var.getScope().toString() : null;
        default:
            throw new HibernateException("Property " + property + " is invalid");
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setPropertyValue(Object component, int property, Object value) {
        Ii ii = (Ii) value;
        switch (property) {
        case NULLFLAVOR:
            ii.setNullFlavor(NullFlavor.valueOf((String) component));
            break;
        case DISPLAYABLE:
            ii.setDisplayable((Boolean) component);
            break;
        case EXTENSION:
            ii.setExtension((String) component);
            break;
        case IDENTIFIER_NAME:
            ii.setIdentifierName((String) component);
            break;
        case RELIABILITY:
            ii.setReliability(IdentifierReliability.valueOf((String) component));
            break;
        case ROOT:
            ii.setRoot((String) component);
            break;
        case SCOPE:
            ii.setScope(IdentifierScope.valueOf((String) component));
            break;
        default:
            throw new HibernateException("Property " + property + " is invalid");
        }
    }

    /**
     * {@inheritDoc}
     */
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
            throws SQLException {

        String nullFlavorStr = rs.getString(names[NULLFLAVOR]);
        String extensionStr = rs.getString(names[EXTENSION]);

        if (nullFlavorStr == null && extensionStr == null) {
            return null;
        }

        Ii result = new Ii();
        if (nullFlavorStr != null) {
            result.setNullFlavor(NullFlavor.valueOf(nullFlavorStr));
        }

        result.setExtension(extensionStr);
        result.setDisplayable((Boolean) rs.getObject(names[DISPLAYABLE]));
        result.setIdentifierName(rs.getString(names[IDENTIFIER_NAME]));
        String reliabilityStr = rs.getString(names[RELIABILITY]);
        if (reliabilityStr != null) {
            result.setReliability(IdentifierReliability.valueOf(reliabilityStr));
        }
        result.setRoot(rs.getString(names[ROOT]));
        String scopeStr = rs.getString(names[SCOPE]);
        if (scopeStr != null) {
            result.setScope(IdentifierScope.valueOf(scopeStr));
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor si)
            throws  SQLException {
        if (value == null) {
            for (int i = 0; i < SCOPE; ++i) {
                statement.setNull(i, ((NullableType) getPropertyTypes()[i]).sqlType());
            }
        } else {
            Ii ii = (Ii) value;
            checkValidState(ii);

            String nullFlavorStr = (ii.getNullFlavor() != null) ? ii.getNullFlavor().toString() : null;
            statement.setString(index + NULLFLAVOR, nullFlavorStr);
            statement.setString(index + EXTENSION, ii.getExtension());
            statement.setObject(index + DISPLAYABLE, ii.getDisplayable());
            statement.setString(index + IDENTIFIER_NAME, ii.getIdentifierName());
            statement.setString(index + RELIABILITY, (ii.getReliability() != null) ? ii.getReliability().toString()
                                                                                   : null);
            statement.setString(index + ROOT, ii.getRoot());
            statement.setString(index + SCOPE, (ii.getScope() != null) ? ii.getScope().toString() : null);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean isMutable() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public Object replace(Object original, Object target, SessionImplementor si, Object owner) {
        return deepCopy(original);
    }

    /**
     * {@inheritDoc}
     */
    public Class<Ii> returnedClass() {
        return Ii.class;
    }

    /**
     * when both 'nullFlavor' and 'extension' are null when encoding a null Ii into columns.
     */
    private static void checkValidState(Ii ii) {
        if (ii.getNullFlavor() == null && ii.getExtension() == null) {
            throw new IllegalArgumentException("Ii cannot have both 'nullFlavor' and 'extension' be null");
        }
    }
}
