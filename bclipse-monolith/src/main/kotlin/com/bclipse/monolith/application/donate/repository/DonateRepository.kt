package com.bclipse.monolith.application.donate.repository

import com.bclipse.monolith.application.donate.entity.document.DonateDocument
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface DonateRepository: MongoRepository<DonateDocument, ObjectId> {
    fun findByDonateId(donateId: String): DonateDocument?
}