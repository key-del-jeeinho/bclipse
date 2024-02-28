package com.bclipse.monolith.application.donate.repository

import com.bclipse.monolith.application.donate.entity.document.ManualAccountTransferDonateDocument
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ManualAccountTransferDonateRepository
    : MongoRepository<ManualAccountTransferDonateDocument, ObjectId>