import com.example.module
import com.example.moduleWithInjectedDependencies
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.eclipse.jetty.http.HttpStatus
import kotlin.test.*

class ExampleTest{
    @Test
    fun `getting users not possible without logging in`() = withTestApplication({
        moduleWithInjectedDependencies(kodein = setupTestsKodeinDI(), testing = true)
    }){
        with(handleRequest(HttpMethod.Get, "api/users/")){
            assertEquals(HttpStatusCode.Unauthorized, response.status())
        }
    }
}