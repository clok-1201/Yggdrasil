import org.bukkit.configuration.YamlConfiguration
import org.slf4j.LoggerFactory
import java.io.File


object ServerInfo {
    lateinit var minestomData: MinestomData
    private val logger= LoggerFactory.getLogger(ServerInfo::class.java)

    fun displayServerInfo() {
        // 输出网络信息
        logger.info("Network Information:")
        logger.info("   IP: {}", minestomData.network.ip)
        logger.info("   Port: {}", minestomData.network.port)
        logger.info("   Open to LAN: {}", minestomData.network.openToLan)

        // 输出代理信息
        logger.info("Proxy Information:")
        logger.info("   Enabled: {}", minestomData.proxy.enable)
        logger.info("   Type: {}", minestomData.proxy.type)
        logger.info("   Secret: {}", minestomData.proxy.secret)

        // 输出服务器信息
        logger.info("Server Information:")
        logger.info("   Ticks Per Second: {}", minestomData.server.tickPreSecond)
        logger.info("   Chunk View Distance: {}", minestomData.server.chunkViewDistance)
        logger.info("   Entity View Distance: {}", minestomData.server.entityViewDistance)
        logger.info("   Online Mode: {}", minestomData.server.onlineMode)
        logger.info("   Terminal: {}", minestomData.server.terminal)
        logger.info("   Benchmark: {}", minestomData.server.benchmark)
    }

    fun setData() {
        val file: File = File("yggdrasil.yaml")
        if (!file.exists()) {
            extractResource("yggdrasil.yaml")
        }
        val config = YamlConfiguration.loadConfiguration(file)
// 从配置文件中读取各个字段，提供默认值
        val network = config.getConfigurationSection("network")?.let { networkSection ->
            MinestomData.Network(
                ip = networkSection.getString("ip", "127.0.0.1"),
                port = networkSection.getInt("port", 25565),
                openToLan = networkSection.getBoolean("open-to-lan", false)
            )
        } ?: MinestomData.Network("127.0.0.1", 25565, false)

        val proxy = config.getConfigurationSection("proxy")?.let { proxySection ->
            MinestomData.Proxy(
                enable = proxySection.getBoolean("enabled", false),
                type = proxySection.getString("type", "velocity"),
                secret = proxySection.getString("secret", "")
            )
        } ?: MinestomData.Proxy(false, "velocity", "")

        val server = config.getConfigurationSection("server")?.let { serverSection ->
            MinestomData.Server(
                tickPreSecond = serverSection.getInt("ticks-per-second", 20),
                chunkViewDistance = serverSection.getInt("chunk-view-distance", 8),
                entityViewDistance = serverSection.getInt("entity-view-distance", 8),
                onlineMode = serverSection.getBoolean("online-mode", true),
                terminal = serverSection.getBoolean("terminal", true),
                benchmark = serverSection.getBoolean("benchmark", false),
                threads = serverSection.getInt("dispatcher-threads", 3)
            )
        } ?: MinestomData.Server(20, 8, 8, true, true, false, 3)
        minestomData = MinestomData(network, proxy, server)
    }

}

open class MinestomData(
    val network: Network, val proxy: Proxy, val server: Server
) {
    class Network(
        val ip: String, val port: Int, val openToLan: Boolean
    )

    class Proxy(val enable: Boolean, val type: String, val secret: String = "")
    class Server(val tickPreSecond: Int,
                 val chunkViewDistance: Int,
                 val entityViewDistance: Int,
                 val onlineMode: Boolean,
                 val terminal: Boolean,
                 val benchmark: Boolean,
                 val threads: Int)



}