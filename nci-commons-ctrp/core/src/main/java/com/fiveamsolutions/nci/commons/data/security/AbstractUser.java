/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The nci-commons
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This nci-commons Software License (the License) is between NCI and You. You (or
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
 * its rights in the nci-commons Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the nci-commons Software; (ii) distribute and
 * have distributed to and by third parties the nci-commons Software and any
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
package com.fiveamsolutions.nci.commons.data.security;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;
import org.hibernate.validator.Email;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import com.fiveamsolutions.nci.commons.search.Searchable;
import com.fiveamsolutions.nci.commons.util.AcceptablePassword;

/**
 * Base user class.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public abstract class AbstractUser implements PersistentObject, Principal, Comparable<AbstractUser> {

    private static final long serialVersionUID = -7901879701327831646L;
    /**
     * Nonce timeout period.  This is a positive value.
     */
    public static final int REQUEST_TIMEOUT_HOURS = 24;

    /**
     * The ID of the Admin role.
     */
    public static final String ADMIN_ROLE_NAME = "administrator";

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Password password;
    private Password previousPassword;
    private Date passwordExpirationDate;
    private String email;
    private AccountStatus status = AccountStatus.ACTIVE;
    private AccountStatus previousStatus;
    private Set<ApplicationRole> roles = new HashSet<ApplicationRole>();
    private Set<UserGroup> groups = new HashSet<UserGroup>();
    private Set<PasswordReset> passwordResets = new HashSet<PasswordReset>();

    /**
     * {@inheritDoc}
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * @param id db id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the firstName
     */
    @NotEmpty
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the lastName
     */
    @NotEmpty
    public String getLastName() {
        return lastName;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(Password password) {
        this.password = password;
    }

    /**
     * @return the password
     */
    @NotNull
    @AcceptablePassword
    @Type(type = "com.fiveamsolutions.nci.commons.util.PasswordUserType")
    @Columns(columns = {@Column(name = "password") })
    public Password getPassword() {
        return password;
    }

    /**
     * @return the previousPassword
     */
    @Type(type = "com.fiveamsolutions.nci.commons.util.PasswordUserType")
    @Columns(columns = {@Column(name = "password", updatable = false, insertable = false, nullable = false) })
    public Password getPreviousPassword() {
        return previousPassword;
    }

    /**
     * @param previousPassword the previousPassword to set
     */
    public void setPreviousPassword(Password previousPassword) {
        this.previousPassword = previousPassword;
    }

    /**
     * @return the passwordExpirationDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "password_expiration_date")
    public Date getPasswordExpirationDate() {
        return passwordExpirationDate;
    }

    /**
     * @param passwordExpirationDate the passwordExpirationDate to set
     */
    public void setPasswordExpirationDate(Date passwordExpirationDate) {
        this.passwordExpirationDate = passwordExpirationDate;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the email
     */
    @NotEmpty
    @Email
    public String getEmail() {
        return email;
    }

    /**
     * @return the security principal, which in this case is the username
     */
    @Transient
    public String getName() {
        return getUsername();
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo(AbstractUser o) {
        return new CompareToBuilder().append(getUsername(), o.getUsername()).toComparison();
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Note that there is a case insensitive unique constraint created in plain sql for this column.
     * @return the username
     */
    @NotEmpty
    public String getUsername() {
        return username;
    }

    /**
     * @return the previous status
     */
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", updatable = false, insertable = false, nullable = false)
    public AccountStatus getPreviousStatus() {
        return previousStatus;
    }

    /**
     * @param previousStatus the previous status.
     */
    public void setPreviousStatus(AccountStatus previousStatus) {
        this.previousStatus = previousStatus;
    }

    /**
     * @return the status
     */
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Searchable
    public AccountStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    /**
     * @return application security roles
     */
    @ManyToMany
    @JoinTable(name = "user_roles",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ForeignKey(name = "USER_ROLE_USER_FK",
                inverseName = "USER_ROLE_ROLE_FK")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public Set<ApplicationRole> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(Set<ApplicationRole> roles) {
        this.roles = roles;
    }

    /**
     * @return user groups
     */
    @ManyToMany
    @JoinTable(name = "user_group",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "group_id"))
    @ForeignKey(name = "USER_GROUP_USER_FK",
                inverseName = "USER_GROUP_GROUP_FK")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public Set<UserGroup> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(Set<UserGroup> groups) {
        this.groups = groups;
    }

    /**
     * @return the passwordResets
     */
    @OneToMany(mappedBy = "user")
    @Cascade(value = {CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public Set<PasswordReset> getPasswordResets() {
        return passwordResets;
    }

    @SuppressWarnings("unused")
    private void setPasswordResets(Set<PasswordReset> passwordResets) {
        this.passwordResets = passwordResets;
    }

    /**
     * Get the display name for the current user, which is "last, first (institute code [, institute code]*)".
     * @return the display name for this current user
     */
    @Transient
    public String getDisplayName() {
        StringBuffer displayName = new StringBuffer();
        displayName.append(StringUtils.trimToEmpty(getLastName()));
        displayName.append(", ");
        displayName.append(StringUtils.trimToEmpty(getFirstName()));
        return StringUtils.trimToEmpty(displayName.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (id == null) {
            return false;
        }
        if (!(o instanceof AbstractUser)) {
            return false;
        }
        AbstractUser u = (AbstractUser) o;

        return id.equals(u.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        if (id == null) {
            return System.identityHashCode(this);
        }
        return id.hashCode();
    }

    /**
     * @param nonce the nonce string to check
     * @return whether this is a valid nonce at this time for this user
     */
    @Transient
    public boolean checkNonce(String nonce) {
        if (nonce != null) {
            Calendar expiryDate = Calendar.getInstance();
            expiryDate.add(Calendar.HOUR, -REQUEST_TIMEOUT_HOURS);
            for (PasswordReset pr : getPasswordResets()) {
                if (pr.getNonce().equals(nonce) && pr.getCreateDate().after(expiryDate.getTime())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return boolean representing whether the user is an admin or not an admin
     */
    @Transient
    public boolean isAdmin() {
        List<String> roleNames = new ArrayList<String>();
        for (ApplicationRole r : getRoles()) {
            roleNames.add(r.getName());
        }
        return roleNames.contains(ADMIN_ROLE_NAME);
    }
}
