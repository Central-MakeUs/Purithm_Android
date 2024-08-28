package com.cmc.purithm.domain.exception

sealed class MemberException : BaseException() {
    class NeedTermOfServiceException() : MemberException()
    class RejoinRestrictedException() : MemberException()
}