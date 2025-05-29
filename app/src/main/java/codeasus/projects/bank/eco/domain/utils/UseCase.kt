package codeasus.projects.bank.eco.domain.utils

interface UseCase<in P, out R> {
    suspend operator fun invoke(data: P): R
}