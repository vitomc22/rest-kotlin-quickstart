import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.event.Observes
import jakarta.inject.Singleton
import org.jboss.logging.Logger

@Singleton
class StartupLogger {
    private val logger: Logger = Logger.getLogger(this.javaClass)

    fun onStart(@Observes event: StartupEvent) {
        val profile = System.getProperty("quarkus.profile") ?: "dev"
        logger.info(" \uD83D\uDD27Perfil ativo: $profile")
    }
}