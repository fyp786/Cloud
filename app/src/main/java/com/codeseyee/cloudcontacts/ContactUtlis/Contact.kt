/*
 * Copyright 2016 Tamir Shomer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codeseye.cloudcontacts.contactutils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.codeseye.cloudcontacts.models.User
import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.Objects

/**
 * Represents a compound contact, aggregating all phones, emails, and photos a contact has.
 */
@Entity
data class Contact(

    var id: Long = 0,
    var displayName: String? = null,
    var givenName: String? = null,
    var familyName: String? = null,
    var lastUpdatedAt: String? = null,
    @PrimaryKey
    @NonNull
    var phoneNumbers: String = "",
    var photoUri: String? = null,
    var emails: String? = null,
    var birthday: String? = null,
    var companyName: String? = null,
    var companyTitle: String? = null,
    var websites: String? = null,
    var addresses: String? = null,
    var note: String? = null,
    var updated_at: String? = null,
    var updated_at_long: Long = 0,


    @JsonIgnore
    var isFavourite: Int = 0,

    @JsonIgnore
    var isSynced: Boolean = false,


    @JsonIgnore
    @Ignore
    var isSelected: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Contact) return false
        return isSynced == other.isSynced &&
                displayName == other.displayName &&
                phoneNumbers == other.phoneNumbers &&
                emails == other.emails &&
                birthday == other.birthday &&
                companyName == other.companyName &&
                companyTitle == other.companyTitle &&
                websites == other.websites &&
                addresses == other.addresses
    }

    override fun hashCode(): Int {
        return Objects.hash(
            isSynced,
            displayName,
            phoneNumbers,
            emails,
            birthday,
            companyName,
            companyTitle,
            websites,
            addresses
        )
    }

    fun getUpdatedAt(): String? {
        return updated_at
    }

    fun setUpdatedAt(updated_at: String?) {
        this.updated_at = updated_at
        // this.updated_at_long = AppUtils.fromUTC(updated_at)
    }

    interface AbstractField {
        fun getMimeType(): String?
        fun getColumn(): String?
    }

    enum class Field(val mimeType: String?, val column: String) : AbstractField {
        ContactId(null, ContactsContract.RawContacts.CONTACT_ID),
        DisplayName(null, ContactsContract.Data.DISPLAY_NAME),
        GivenName(
            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME
        ),
        FamilyName(
            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME
        ),
        PhoneNumber(
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        ),
        PhoneType(
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Phone.TYPE
        ),
        PhoneLabel(
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Phone.LABEL
        ),

        @SuppressLint("InlinedApi")
        PhoneNormalizedNumber(
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER
        ),
        Email(
            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Email.ADDRESS
        ),
        EmailType(
            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Email.TYPE
        ),
        EmailLabel(
            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Email.LABEL
        ),
        PhotoUri(null, ContactsContract.Data.PHOTO_URI),
        UpdatedAt(null, ContactsContract.Data.CONTACT_LAST_UPDATED_TIMESTAMP),
        EventStartDate(
            ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Event.START_DATE
        ),
        EventType(
            ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Event.TYPE
        ),
        EventLabel(
            ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Event.LABEL
        ),
        CompanyName(
            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Organization.COMPANY
        ),
        CompanyTitle(
            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Organization.TITLE
        ),
        Website(
            ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Website.URL
        ),
        Note(
            ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE,
            ContactsContract.Data.CONTACT_LAST_UPDATED_TIMESTAMP
        ),
        Address(
            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS
        ),
        AddressType(
            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.StructuredPostal.TYPE
        ),
        AddressStreet(
            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.StructuredPostal.STREET
        ),
        AddressCity(
            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.StructuredPostal.CITY
        ),
        AddressRegion(
            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.StructuredPostal.REGION
        ),
        AddressPostcode(
            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE
        ),
        AddressCountry(
            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY
        ),
        AddressLabel(
            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.StructuredPostal.LABEL
        );

        override fun getColumn(): String {
            return column
        }

        override fun getMimeType(): String? {
            return mimeType
        }
    }

    enum class InternalField(val mimeType: String?, val column: String) : AbstractField {
        MimeType(null, ContactsContract.Data.MIMETYPE);

        override fun getColumn(): String {
            return column
        }

        override fun getMimeType(): String? {
            return mimeType
        }
    }

    fun setId(id: Long) {
        this.id = id
    }

    fun addDisplayName(displayName: String?): Contact {
        this.displayName = displayName
        return this
    }

    fun addGivenName(givenName: String?): Contact {
        this.givenName = givenName
        return this
    }

    fun addFamilyName(familyName: String?): Contact {
        this.familyName = familyName
        return this
    }

    fun addCompanyName(companyName: String?): Contact {
        this.companyName = companyName
        return this
    }

    fun addCompanyTitle(companyTitle: String?): Contact {
        this.companyTitle = companyTitle
        return this
    }

    fun addWebsite(website: String?): Contact {
        this.websites = website
        return this
    }

    fun addNote(note: String?): Contact {
        this.note = note
        return this
    }

    fun addUpdatedAt(updatedAt: String?): Contact {
        this.lastUpdatedAt = updatedAt
        return this
    }

    /**
     * Gets the phone contact id.
     *
     * @return contact id.
     */
    fun getId(): Long {
        return id
    }

    /**
     * Gets the display name of the contact.
     *
     * @return Display Name.
     */
    fun getDisplayName(): String? {
        return displayName
    }

    /**
     * Gets the given name of the contact.
     *
     * @return Given Name.
     */
    fun getGivenName(): String? {
        return givenName
    }

    /**
     * Gets the family name of the contact.
     *
     * @return Family Name.
     */
    fun getFamilyName(): String? {
        return familyName
    }

    /**
     * Gets the contact's photo URI.
     *
     * @return Photo URI.
     */
    fun getPhotoUri(): String? {
        return photoUri
    }

    fun getBirthday(): String? {
        return birthday
    }

    /**
     * Gets the name of the company the contact works for.
     *
     * @return the company name.
     */
    fun getCompanyName(): String? {
        return companyName
    }

    /**
     * Gets the job title of the contact.
     *
     * @return the job title.
     */
    fun getCompanyTitle(): String? {
        return companyTitle
    }

    /**
     * Gets the list of all websites the contact has.
     *
     * @return A list of websites.
     */
    fun getWebsites(): String? {
        return websites
    }

    /**
     * Gets the note of the contact.
     *
     * @return the note.
     */
    fun getNote(): String? {
        return note
    }

    /**
     * Gets the last updated timestamp of the contact.
     *
     * @return the last updated timestamp.
     */
    fun getLastUpdatedAt(): String? {
        return lastUpdatedAt
    }

    companion object {
        private var context: Context? = null

        @JvmStatic
        fun initialize(context: Context) {
            this.context = context.applicationContext
        }
    }
}
