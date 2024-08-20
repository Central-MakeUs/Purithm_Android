package com.cmc.purithm.domain.usecase.member

import com.cmc.purithm.domain.entity.member.Account
import com.cmc.purithm.domain.repository.MemberRepository
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(): Account {
        return memberRepository.getAccount()
    }
}