package co.finema.thaidotidbyfinema

import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64

fun generateSalt(): String {
  val random = SecureRandom()
  val salt = ByteArray(16)
  random.nextBytes(salt)
  return Base64.getEncoder().encodeToString(salt)
}

fun hashedPasscode(password: String, salt: String): String {
  val combined = password + salt
  val messageDigest = MessageDigest.getInstance("SHA-256")
  val hashedBytes = messageDigest.digest(combined.toByteArray())
  return Base64.getEncoder().encodeToString(hashedBytes)
}

fun verifyPasscode(password: String, storedHash: String, salt: String): Boolean {
  val hashedInput = hashedPasscode(password, salt)
  return hashedInput == storedHash
}
