package br.com.rorizinfo.cardFidelize.data.service.firebase

import br.com.rorizinfo.cardFidelize.data.model.ClientRequest
import br.com.rorizinfo.cardFidelize.data.model.RegisterUserRequest
import br.com.rorizinfo.cardFidelize.data.model.RegisterUserResponse
import br.com.rorizinfo.cardFidelize.data.service.Constants
import br.com.rorizinfo.cardFidelize.data.service.contract.ClientService
import br.com.rorizinfo.cardFidelize.data.service.contract.UserLoginService
import br.com.rorizinfo.cardFidelize.data.service.firebase.exception.AccountAlreadyExists
import br.com.rorizinfo.cardFidelize.data.service.firebase.exception.AccountNotCreated
import br.com.rorizinfo.cardFidelize.domain.model.User
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.BuildConfig
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class ClientServiceFirebase(
    private val firebaseDatabase: FirebaseDatabase,
) : ClientService {
    
    private val database by lazy { firebaseDatabase.getReference(Constants.TABLE_CLIENTS) }
    
    override suspend fun saveOrUpdateClient(clientRequest: ClientRequest): Result<Void?> {
        return try {
            database.child(clientRequest.userRequest.id).setValue(clientRequest).await()
            Result.success(null)
        } catch (exception: Exception) {
            if (exception is FirebaseAuthUserCollisionException) Result.failure(AccountAlreadyExists())
            else Result.failure(exception)
        }
    }
}