package codeasus.projects.bank.eco.core.ui.shared.view.utils

/**
 * Maps ISO 3166-1 alpha-2 country codes to their corresponding flag emojis
 */
object CountryFlags {

    private val countryFlagMap = mapOf(
        "AD" to "🇦🇩", // Andorra
        "AE" to "🇦🇪", // United Arab Emirates
        "AF" to "🇦🇫", // Afghanistan
        "AG" to "🇦🇬", // Antigua and Barbuda
        "AI" to "🇦🇮", // Anguilla
        "AL" to "🇦🇱", // Albania
        "AM" to "🇦🇲", // Armenia
        "AO" to "🇦🇴", // Angola
        "AQ" to "🇦🇶", // Antarctica
        "AR" to "🇦🇷", // Argentina
        "AS" to "🇦🇸", // American Samoa
        "AT" to "🇦🇹", // Austria
        "AU" to "🇦🇺", // Australia
        "AW" to "🇦🇼", // Aruba
        "AX" to "🇦🇽", // Åland Islands
        "AZ" to "🇦🇿", // Azerbaijan
        "BA" to "🇧🇦", // Bosnia and Herzegovina
        "BB" to "🇧🇧", // Barbados
        "BD" to "🇧🇩", // Bangladesh
        "BE" to "🇧🇪", // Belgium
        "BF" to "🇧🇫", // Burkina Faso
        "BG" to "🇧🇬", // Bulgaria
        "BH" to "🇧🇭", // Bahrain
        "BI" to "🇧🇮", // Burundi
        "BJ" to "🇧🇯", // Benin
        "BL" to "🇧🇱", // Saint Barthélemy
        "BM" to "🇧🇲", // Bermuda
        "BN" to "🇧🇳", // Brunei
        "BO" to "🇧🇴", // Bolivia
        "BQ" to "🇧🇶", // Caribbean Netherlands
        "BR" to "🇧🇷", // Brazil
        "BS" to "🇧🇸", // Bahamas
        "BT" to "🇧🇹", // Bhutan
        "BV" to "🇧🇻", // Bouvet Island
        "BW" to "🇧🇼", // Botswana
        "BY" to "🇧🇾", // Belarus
        "BZ" to "🇧🇿", // Belize
        "CA" to "🇨🇦", // Canada
        "CC" to "🇨🇨", // Cocos Islands
        "CD" to "🇨🇩", // Democratic Republic of the Congo
        "CF" to "🇨🇫", // Central African Republic
        "CG" to "🇨🇬", // Republic of the Congo
        "CH" to "🇨🇭", // Switzerland
        "CI" to "🇨🇮", // Côte d'Ivoire
        "CK" to "🇨🇰", // Cook Islands
        "CL" to "🇨🇱", // Chile
        "CM" to "🇨🇲", // Cameroon
        "CN" to "🇨🇳", // China
        "CO" to "🇨🇴", // Colombia
        "CR" to "🇨🇷", // Costa Rica
        "CU" to "🇨🇺", // Cuba
        "CV" to "🇨🇻", // Cape Verde
        "CW" to "🇨🇼", // Curaçao
        "CX" to "🇨🇽", // Christmas Island
        "CY" to "🇨🇾", // Cyprus
        "CZ" to "🇨🇿", // Czech Republic
        "DE" to "🇩🇪", // Germany
        "DJ" to "🇩🇯", // Djibouti
        "DK" to "🇩🇰", // Denmark
        "DM" to "🇩🇲", // Dominica
        "DO" to "🇩🇴", // Dominican Republic
        "DZ" to "🇩🇿", // Algeria
        "EC" to "🇪🇨", // Ecuador
        "EE" to "🇪🇪", // Estonia
        "EG" to "🇪🇬", // Egypt
        "EH" to "🇪🇭", // Western Sahara
        "ER" to "🇪🇷", // Eritrea
        "ES" to "🇪🇸", // Spain
        "ET" to "🇪🇹", // Ethiopia
        "FI" to "🇫🇮", // Finland
        "FJ" to "🇫🇯", // Fiji
        "FK" to "🇫🇰", // Falkland Islands
        "FM" to "🇫🇲", // Micronesia
        "FO" to "🇫🇴", // Faroe Islands
        "FR" to "🇫🇷", // France
        "GA" to "🇬🇦", // Gabon
        "GB" to "🇬🇧", // United Kingdom
        "GD" to "🇬🇩", // Grenada
        "GE" to "🇬🇪", // Georgia
        "GF" to "🇬🇫", // French Guiana
        "GG" to "🇬🇬", // Guernsey
        "GH" to "🇬🇭", // Ghana
        "GI" to "🇬🇮", // Gibraltar
        "GL" to "🇬🇱", // Greenland
        "GM" to "🇬🇲", // Gambia
        "GN" to "🇬🇳", // Guinea
        "GP" to "🇬🇵", // Guadeloupe
        "GQ" to "🇬🇶", // Equatorial Guinea
        "GR" to "🇬🇷", // Greece
        "GS" to "🇬🇸", // South Georgia and the South Sandwich Islands
        "GT" to "🇬🇹", // Guatemala
        "GU" to "🇬🇺", // Guam
        "GW" to "🇬🇼", // Guinea-Bissau
        "GY" to "🇬🇾", // Guyana
        "HK" to "🇭🇰", // Hong Kong
        "HM" to "🇭🇲", // Heard Island and McDonald Islands
        "HN" to "🇭🇳", // Honduras
        "HR" to "🇭🇷", // Croatia
        "HT" to "🇭🇹", // Haiti
        "HU" to "🇭🇺", // Hungary
        "ID" to "🇮🇩", // Indonesia
        "IE" to "🇮🇪", // Ireland
        "IL" to "🇮🇱", // Israel
        "IM" to "🇮🇲", // Isle of Man
        "IN" to "🇮🇳", // India
        "IO" to "🇮🇴", // British Indian Ocean Territory
        "IQ" to "🇮🇶", // Iraq
        "IR" to "🇮🇷", // Iran
        "IS" to "🇮🇸", // Iceland
        "IT" to "🇮🇹", // Italy
        "JE" to "🇯🇪", // Jersey
        "JM" to "🇯🇲", // Jamaica
        "JO" to "🇯🇴", // Jordan
        "JP" to "🇯🇵", // Japan
        "KE" to "🇰🇪", // Kenya
        "KG" to "🇰🇬", // Kyrgyzstan
        "KH" to "🇰🇭", // Cambodia
        "KI" to "🇰🇮", // Kiribati
        "KM" to "🇰🇲", // Comoros
        "KN" to "🇰🇳", // Saint Kitts and Nevis
        "KP" to "🇰🇵", // North Korea
        "KR" to "🇰🇷", // South Korea
        "KW" to "🇰🇼", // Kuwait
        "KY" to "🇰🇾", // Cayman Islands
        "KZ" to "🇰🇿", // Kazakhstan
        "LA" to "🇱🇦", // Laos
        "LB" to "🇱🇧", // Lebanon
        "LC" to "🇱🇨", // Saint Lucia
        "LI" to "🇱🇮", // Liechtenstein
        "LK" to "🇱🇰", // Sri Lanka
        "LR" to "🇱🇷", // Liberia
        "LS" to "🇱🇸", // Lesotho
        "LT" to "🇱🇹", // Lithuania
        "LU" to "🇱🇺", // Luxembourg
        "LV" to "🇱🇻", // Latvia
        "LY" to "🇱🇾", // Libya
        "MA" to "🇲🇦", // Morocco
        "MC" to "🇲🇨", // Monaco
        "MD" to "🇲🇩", // Moldova
        "ME" to "🇲🇪", // Montenegro
        "MF" to "🇲🇫", // Saint Martin
        "MG" to "🇲🇬", // Madagascar
        "MH" to "🇲🇭", // Marshall Islands
        "MK" to "🇲🇰", // North Macedonia
        "ML" to "🇲🇱", // Mali
        "MM" to "🇲🇲", // Myanmar
        "MN" to "🇲🇳", // Mongolia
        "MO" to "🇲🇴", // Macao
        "MP" to "🇲🇵", // Northern Mariana Islands
        "MQ" to "🇲🇶", // Martinique
        "MR" to "🇲🇷", // Mauritania
        "MS" to "🇲🇸", // Montserrat
        "MT" to "🇲🇹", // Malta
        "MU" to "🇲🇺", // Mauritius
        "MV" to "🇲🇻", // Maldives
        "MW" to "🇲🇼", // Malawi
        "MX" to "🇲🇽", // Mexico
        "MY" to "🇲🇾", // Malaysia
        "MZ" to "🇲🇿", // Mozambique
        "NA" to "🇳🇦", // Namibia
        "NC" to "🇳🇨", // New Caledonia
        "NE" to "🇳🇪", // Niger
        "NF" to "🇳🇫", // Norfolk Island
        "NG" to "🇳🇬", // Nigeria
        "NI" to "🇳🇮", // Nicaragua
        "NL" to "🇳🇱", // Netherlands
        "NO" to "🇳🇴", // Norway
        "NP" to "🇳🇵", // Nepal
        "NR" to "🇳🇷", // Nauru
        "NU" to "🇳🇺", // Niue
        "NZ" to "🇳🇿", // New Zealand
        "OM" to "🇴🇲", // Oman
        "PA" to "🇵🇦", // Panama
        "PE" to "🇵🇪", // Peru
        "PF" to "🇵🇫", // French Polynesia
        "PG" to "🇵🇬", // Papua New Guinea
        "PH" to "🇵🇭", // Philippines
        "PK" to "🇵🇰", // Pakistan
        "PL" to "🇵🇱", // Poland
        "PM" to "🇵🇲", // Saint Pierre and Miquelon
        "PN" to "🇵🇳", // Pitcairn Islands
        "PR" to "🇵🇷", // Puerto Rico
        "PS" to "🇵🇸", // Palestine
        "PT" to "🇵🇹", // Portugal
        "PW" to "🇵🇼", // Palau
        "PY" to "🇵🇾", // Paraguay
        "QA" to "🇶🇦", // Qatar
        "RE" to "🇷🇪", // Réunion
        "RO" to "🇷🇴", // Romania
        "RS" to "🇷🇸", // Serbia
        "RU" to "🇷🇺", // Russia
        "RW" to "🇷🇼", // Rwanda
        "SA" to "🇸🇦", // Saudi Arabia
        "SB" to "🇸🇧", // Solomon Islands
        "SC" to "🇸🇨", // Seychelles
        "SD" to "🇸🇩", // Sudan
        "SE" to "🇸🇪", // Sweden
        "SG" to "🇸🇬", // Singapore
        "SH" to "🇸🇭", // Saint Helena
        "SI" to "🇸🇮", // Slovenia
        "SJ" to "🇸🇯", // Svalbard and Jan Mayen
        "SK" to "🇸🇰", // Slovakia
        "SL" to "🇸🇱", // Sierra Leone
        "SM" to "🇸🇲", // San Marino
        "SN" to "🇸🇳", // Senegal
        "SO" to "🇸🇴", // Somalia
        "SR" to "🇸🇷", // Suriname
        "SS" to "🇸🇸", // South Sudan
        "ST" to "🇸🇹", // São Tomé and Príncipe
        "SV" to "🇸🇻", // El Salvador
        "SX" to "🇸🇽", // Sint Maarten
        "SY" to "🇸🇾", // Syria
        "SZ" to "🇸🇿", // Eswatini
        "TC" to "🇹🇨", // Turks and Caicos Islands
        "TD" to "🇹🇩", // Chad
        "TF" to "🇹🇫", // French Southern Territories
        "TG" to "🇹🇬", // Togo
        "TH" to "🇹🇭", // Thailand
        "TJ" to "🇹🇯", // Tajikistan
        "TK" to "🇹🇰", // Tokelau
        "TL" to "🇹🇱", // Timor-Leste
        "TM" to "🇹🇲", // Turkmenistan
        "TN" to "🇹🇳", // Tunisia
        "TO" to "🇹🇴", // Tonga
        "TR" to "🇹🇷", // Turkey
        "TT" to "🇹🇹", // Trinidad and Tobago
        "TV" to "🇹🇻", // Tuvalu
        "TW" to "🇹🇼", // Taiwan
        "TZ" to "🇹🇿", // Tanzania
        "UA" to "🇺🇦", // Ukraine
        "UG" to "🇺🇬", // Uganda
        "UM" to "🇺🇲", // United States Minor Outlying Islands
        "US" to "🇺🇸", // United States
        "UY" to "🇺🇾", // Uruguay
        "UZ" to "🇺🇿", // Uzbekistan
        "VA" to "🇻🇦", // Vatican City
        "VC" to "🇻🇨", // Saint Vincent and the Grenadines
        "VE" to "🇻🇪", // Venezuela
        "VG" to "🇻🇬", // British Virgin Islands
        "VI" to "🇻🇮", // United States Virgin Islands
        "VN" to "🇻🇳", // Vietnam
        "VU" to "🇻🇺", // Vanuatu
        "WF" to "🇼🇫", // Wallis and Futuna
        "WS" to "🇼🇸", // Samoa
        "YE" to "🇾🇪", // Yemen
        "YT" to "🇾🇹", // Mayotte
        "ZA" to "🇿🇦", // South Africa
        "ZM" to "🇿🇲", // Zambia
        "ZW" to "🇿🇼"  // Zimbabwe
    )

    /**
     * Get flag emoji for a given ISO 3166-1 alpha-2 country code
     * @param countryCode Two-letter country code (case-insensitive)
     * @return Flag emoji or null if country code not found
     */
    fun getFlagEmoji(countryCode: String?): String? {
        return countryCode?.uppercase()?.let { code ->
            countryFlagMap[code]
        }
    }

    /**
     * Convert country code to flag emoji programmatically
     * This method converts each letter to Unicode Regional Indicator Symbol
     */
    fun countryCodeToEmoji(countryCode: String): String? {
        if (countryCode.length != 2) return null

        val code = countryCode.uppercase()
        val firstChar = code[0]
        val secondChar = code[1]

        // Convert to Regional Indicator Symbols (U+1F1E6 - U+1F1FF)
        val firstCodePoint = 0x1F1E6 + (firstChar - 'A')
        val secondCodePoint = 0x1F1E6 + (secondChar - 'A')

        return String(intArrayOf(firstCodePoint, secondCodePoint), 0, 2)
    }
}