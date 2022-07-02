package br.com.rorizinfo.cardFidelize.data.service.firebase

import br.com.rorizinfo.cardFidelize.data.model.CompanyRequest
import br.com.rorizinfo.cardFidelize.data.service.Constants
import br.com.rorizinfo.cardFidelize.data.service.contract.CompanyService
import br.com.rorizinfo.cardFidelize.data.service.firebase.exception.AccountAlreadyExists
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class CompanyServiceFirebase(
    private val firebaseDatabase: FirebaseDatabase,
) : CompanyService {
    
    private val database by lazy { firebaseDatabase.getReference(Constants.TABLE_CLIENTS) }
    
    override suspend fun saveOrUpdateCompany(companyRequest: CompanyRequest): Result<Void?> {
        return try {
            database.child(companyRequest.userId).setValue(companyRequest).await()
            Result.success(null)
        } catch (exception: Exception) {
            if (exception is FirebaseAuthUserCollisionException) Result.failure(AccountAlreadyExists())
            else Result.failure(exception)
        }
    }
}