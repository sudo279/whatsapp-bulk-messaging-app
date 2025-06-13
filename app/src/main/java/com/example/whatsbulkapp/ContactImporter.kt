package com.example.whatsbulkapp

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log

class ContactImporter(private val context: Context) {

    data class Contact(
        val id: String,
        val name: String,
        val phoneNumber: String
    )

    fun getContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val contentResolver: ContentResolver = context.contentResolver
        var cursor: Cursor? = null

        try {
            cursor = contentResolver.query(
                Phone.CONTENT_URI,
                arrayOf(
                    Phone._ID,
                    Phone.DISPLAY_NAME,
                    Phone.NUMBER
                ),
                null,
                null,
                Phone.DISPLAY_NAME + " ASC"
            )

            cursor?.let { c ->
                val idIndex = c.getColumnIndex(Phone._ID)
                val nameIndex = c.getColumnIndex(Phone.DISPLAY_NAME)
                val numberIndex = c.getColumnIndex(Phone.NUMBER)

                while (c.moveToNext()) {
                    try {
                        if (idIndex >= 0 && nameIndex >= 0 && numberIndex >= 0) {
                            val id = c.getString(idIndex)
                            val name = c.getString(nameIndex) ?: "Unknown"
                            val phoneNumber = c.getString(numberIndex)?.replace("\\s".toRegex(), "") ?: ""

                            if (phoneNumber.isNotEmpty()) {
                                contacts.add(Contact(id, name, phoneNumber))
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error processing contact: ${e.message}")
                        continue
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error querying contacts: ${e.message}")
        } finally {
            cursor?.close()
        }

        return contacts.distinctBy { it.phoneNumber }
    }

    companion object {
        private const val TAG = "ContactImporter"
    }
}
