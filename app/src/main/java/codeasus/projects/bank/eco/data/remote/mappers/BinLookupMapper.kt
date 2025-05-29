package codeasus.projects.bank.eco.data.remote.mappers

import codeasus.projects.bank.eco.data.remote.model.BinLookupResponseDto
import codeasus.projects.bank.eco.domain.remote.model.banking.BinLookupModel
import codeasus.projects.bank.eco.domain.remote.model.banking.Country

object BinLookupMapper {
    fun toDomain(dto: BinLookupResponseDto): BinLookupModel {
        return BinLookupModel(
            status = dto.status,
            scheme = dto.scheme,
            type = dto.type,
            issuer = dto.issuer,
            cardTier = dto.cardTier,
            luhn = dto.luhn ?: false,
            country = dto.country?.let { countryDto ->
                Country(
                    a2 = countryDto.a2,
                    a3 = countryDto.a3,
                    n3 = countryDto.n3,
                    isd = countryDto.isd,
                    name = countryDto.name,
                    cont = countryDto.cont,
                )
            }
        )
    }
}