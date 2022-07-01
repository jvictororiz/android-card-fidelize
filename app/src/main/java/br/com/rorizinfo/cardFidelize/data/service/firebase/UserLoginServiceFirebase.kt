package br.com.rorizinfo.cardFidelize.data.service.firebase

import br.com.rorizinfo.cardFidelize.data.model.RegisterUserRequest
import br.com.rorizinfo.cardFidelize.data.model.RegisterUserResponse
import br.com.rorizinfo.cardFidelize.data.service.Constants
import br.com.rorizinfo.cardFidelize.data.service.contract.UserLoginService
import br.com.rorizinfo.cardFidelize.data.service.firebase.exception.AccountNotCreated
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.BuildConfig
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class UserLoginServiceFirebase(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
) : UserLoginService {
    private val database by lazy { firebaseDatabase.getReference(Constants.TABLE_USERS) }
    private val actionCodeSettings = ActionCodeSettings.newBuilder()
        .setAndroidPackageName(BuildConfig.APPLICATION_ID, true, "1")
        .setUrl("")
        .setHandleCodeInApp(true)
        .build()
    
    override suspend fun saveUser(user: RegisterUserRequest): Result<RegisterUserResponse> {
        return try {
            val resultUser = firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).await()
            val idUser = database.push().key
            database.setValue(idUser, user.apply { id = idUser ?: "" }).await()
            Result.success(RegisterUserResponse(resultUser.user?.isEmailVerified == true, user.email))
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
    
    override suspend fun verifyConfirmationAccount(email: String, password: String): Boolean {
        return firebaseAuth.signInWithEmailAndPassword(email, password).await().user?.isEmailVerified ?: false
    }
    
    override suspend fun doLogin(email: String, password: String): Result<RegisterUserResponse> {
        return try {
            val resultLogin = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(RegisterUserResponse(resultLogin.user?.isEmailVerified == true, email))
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
    
    override suspend fun sendEmailVerification(): Result<Void?> {
        return try {
            val currentUser = firebaseAuth.currentUser ?: return Result.failure(AccountNotCreated())
            currentUser.sendEmailVerification(actionCodeSettings).await()
            Result.success(null)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
    
    suspend fun sendPasswordResetEmail(email: String): Result<Void?> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email, actionCodeSettings).await()
            Result.success(null)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
    
    override suspend fun confirmPasswordReset(code: String, email: String): Result<Void?> {
        return try {
            firebaseAuth.confirmPasswordReset(code, email).await()
            Result.success(null)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}