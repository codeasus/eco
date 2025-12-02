package codeasus.projects.bank.eco.security

import android.util.Base64
import java.nio.charset.StandardCharsets

private const val FLAG_B64 = Base64.NO_WRAP
private val CHARSET = StandardCharsets.UTF_8

// b64, B64 -> base64, Base64
// data -> ByteArray

fun uTF8StrToData(utf8Str: String): ByteArray {
    return utf8Str.toByteArray(CHARSET)
}

fun dataToUTF8Str(data: ByteArray): String {
    return String(data, CHARSET)
}

fun b64StrToData(str: String): ByteArray {
    return Base64.decode(str, FLAG_B64)
}

fun dataToB64Str(data: ByteArray): String {
    return Base64.encodeToString(data, FLAG_B64)
}