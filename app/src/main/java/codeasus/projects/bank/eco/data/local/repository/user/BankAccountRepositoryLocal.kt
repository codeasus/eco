package codeasus.projects.bank.eco.data.local.repository.user

import codeasus.projects.bank.eco.data.local.dao.BankAccountDao
import codeasus.projects.bank.eco.data.local.mappers.toBankAccountEntity
import codeasus.projects.bank.eco.data.local.mappers.toUserBankAccountModel
import codeasus.projects.bank.eco.domain.local.model.user.UserBankAccountModel
import codeasus.projects.bank.eco.domain.local.repository.user.BankAccountRepository
import codeasus.projects.bank.eco.security.AESKeyProtector
import codeasus.projects.bank.eco.security.b64StrToData
import codeasus.projects.bank.eco.security.dataToB64Str
import codeasus.projects.bank.eco.security.dataToUTF8Str
import codeasus.projects.bank.eco.security.uTF8StrToData
import javax.inject.Inject

class BankAccountRepositoryLocal @Inject constructor(private val bankAccountDao: BankAccountDao) : BankAccountRepository {

    fun encryptAndEncode(value: String): String {
        val dataValue = uTF8StrToData(value)
        val encryptedValue = AESKeyProtector.encrypt(dataValue)
        return dataToB64Str(encryptedValue)
    }

    fun decodeAndDecrypt(value: String): String {
        val dataValue = b64StrToData(value)
        val decryptedValue = AESKeyProtector.decrypt(dataValue)
        return dataToUTF8Str(decryptedValue)
    }

    override suspend fun insertBankAccount(account: UserBankAccountModel) {
        val encryptedNumber = encryptAndEncode(account.number)
        val encryptedNumberLastChunk = encryptAndEncode(account.number.takeLast(4))
        val encryptedCVV = encryptAndEncode(account.cvv)

        val encryptedAccountModel = account.copy(
            number = encryptedNumber,
            cvv = encryptedCVV,
        )
        val accountEntity = encryptedAccountModel.toBankAccountEntity().apply {
            numberLastChunk = encryptedNumberLastChunk
        }
        bankAccountDao.saveBankAccount(accountEntity)
    }

    override suspend fun getBankAccountForPublicById(id: Long): UserBankAccountModel? {
        val bankAccountEntity = bankAccountDao.getBankAccountById(id) ?: return null
        return bankAccountEntity.toUserBankAccountModel().copy(
            number = decodeAndDecrypt(bankAccountEntity.numberLastChunk),
            cvv = "★★★"
        )
    }

    override suspend fun getBankAccountForPrivateById(id: Long): UserBankAccountModel? {
        val bankAccountEntity = bankAccountDao.getBankAccountById(id) ?: return null
        return bankAccountEntity.toUserBankAccountModel().copy(
            number = decodeAndDecrypt(bankAccountEntity.number),
            cvv = decodeAndDecrypt(bankAccountEntity.cvv)
        )
    }

    override suspend fun getBankAccounts(): List<UserBankAccountModel> {
        val bankAccountEntities = bankAccountDao.getAllBankAccounts()

        return bankAccountEntities.map { entity ->
            entity.toUserBankAccountModel().copy(
                number = decodeAndDecrypt(entity.numberLastChunk),
                cvv = "★★★"
            )
        }
    }

    override suspend fun deleteBankAccount(id: Long) {
        bankAccountDao.deleteBankAccount(id)
    }

    override suspend fun deleteBankAccounts() {
        bankAccountDao.deleteBankAccounts()
    }
}