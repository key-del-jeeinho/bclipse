package com.bclipse.economy

import com.bclipse.economy.adapter.inbound.command.BalancePresenter
import com.bclipse.economy.adapter.outbound.BalanceInMemoryAdapter
import com.bclipse.economy.adapter.outbound.BalanceUnitStaticAdapter
import com.bclipse.economy.application.BalanceCommandService
import com.bclipse.economy.application.BalanceQueryService
import com.bclipse.economy.domain.BalanceRepository
import com.bclipse.economy.domain.BalanceUnitRepository
import org.koin.dsl.module

val appModule = module {
    single<BalanceRepository> { BalanceInMemoryAdapter() }
    single<BalanceUnitRepository> { BalanceUnitStaticAdapter() }
    single<BalanceQueryService> { BalanceQueryService() }
    single<BalanceCommandService> { BalanceCommandService() }
    single<BalancePresenter> { BalancePresenter() }
}