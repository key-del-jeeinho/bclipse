package com.bclipse.monolith.application.donate.entity.document

import com.bclipse.monolith.application.donate.entity.DonateStatus
import com.bclipse.monolith.application.donate.entity.DonateType
import com.bclipse.monolith.common.entity.Base64UUID
import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
class DonateDocument(
    @Id val id: ObjectId,
    @Indexed(unique = true)
    val donateId: Base64UUID,
    @Indexed val applicationId: Base64UUID,
    @Indexed val donorId: String,
    val amount: Int,
    val type: DonateType,
    status: DonateStatus,
) {
    var status: DonateStatus = status
        private set

    fun confirm() { status = DonateStatus.CONFIRMED }
}