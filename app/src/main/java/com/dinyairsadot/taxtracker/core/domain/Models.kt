package com.dinyairsadot.taxtracker.core.domain

import java.time.Instant
import java.time.LocalDate

enum class PaymentStatus {
    PAID_FULL,
    NOT_PAID,
    PAID_CREDIT
}

enum class CustomFieldType {
    TEXT,
    NUMBER,
    DATE,
    BOOLEAN
}

data class Category(
    val id: Long,
    val name: String,
    val colorHex: String,
    val description: String? = null
)

/**
 * Defines a custom field in a given category's schema.
 * Example: "Meter ID" (TEXT), "Billing period start" (DATE)
 */
data class CustomFieldDefinition(
    val id: Long,
    val categoryId: Long,
    val name: String,
    val type: CustomFieldType,
    val isRequired: Boolean,
    val isArchived: Boolean = false, // future soft-delete support
    val order: Int = 0
)

/**
 * Represents a single invoice / bill entry.
 */
data class Invoice(
    val id: Long,
    val categoryId: Long,
    val invoiceNumber: String,
    val amount: Double,
    val paymentStatus: PaymentStatus,
    val dueDate: LocalDate? = null,
    val paymentDate: LocalDate? = null,
    val consumptionValue: Double? = null,
    val consumptionUnit: String? = null, // e.g. "kWh", "m³"
    val notes: String? = null
)

/**
 * Stores the value of a custom field for a specific invoice.
 * The value is stored as String and interpreted according to CustomFieldType.
 */
data class InvoiceCustomFieldValue(
    val id: Long,
    val invoiceId: Long,
    val fieldDefinitionId: Long,
    val value: String
)

/**
 * Attachment (photo / image) of an invoice.
 */
data class InvoiceImage(
    val id: Long,
    val invoiceId: Long,
    val uri: String,             // URI/path to the image in app storage
    val createdAt: Instant = Instant.now()
)
