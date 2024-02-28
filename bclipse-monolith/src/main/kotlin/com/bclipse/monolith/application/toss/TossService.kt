package com.bclipse.monolith.application.toss

import org.springframework.stereotype.Service

@Service
class TossService {
    fun validateApiKey(clientKey: String, secretKey: String) {
        require(
            clientKey.startsWith("test_ck_") or
            clientKey.startsWith("live_ck_")
        ) { "올바르지 않은 포맷의 ClientKey입니다! - ${clientKey}" }

        require(
            secretKey.startsWith("test_sk_") or
            secretKey.startsWith("live_sk_")
        ) { "올바르지 않은 포맷의 SecretKey입니다!" }

        //TODO 실제 API호출을 통해 키 유효성 검사하기
    }
}
