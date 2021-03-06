import com.example.api.user.DevelopmentUserApiService
import com.example.api.user.UserApiService
import com.example.auth.authentication.InMemoryUserSessionHandler
import com.example.auth.authentication.UserSessionHandler
import com.example.domain.repository.MongoHandler
import com.example.domain.repository.user.InMemoryUserRepositoryService
import com.example.domain.repository.user.UserRepositoryService
import com.example.domain.user.DevelopmentUserDomainService
import com.example.domain.user.UserDomainService
import com.example.utils.AttributeKeysContainer
import io.ktor.application.Application
import io.ktor.config.ApplicationConfig
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

fun Application.setupTestsKodeinDI() =
    Kodein {
        bind<UserDomainService>() with singleton { DevelopmentUserDomainService(kodein) }
        bind<UserApiService>() with singleton { DevelopmentUserApiService(kodein) }
        bind<UserRepositoryService>() with singleton { InMemoryUserRepositoryService() }
        bind<MongoHandler>() with singleton { MongoHandler(kodein) }
        bind<ApplicationConfig>() with singleton { environment.config }
        bind<AttributeKeysContainer>() with singleton { AttributeKeysContainer }
        bind<UserSessionHandler>() with singleton { InMemoryUserSessionHandler }
    }