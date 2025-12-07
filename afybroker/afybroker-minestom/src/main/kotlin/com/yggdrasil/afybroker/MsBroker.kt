package com.yggdrasil.afybroker

import com.alipay.remoting.LifeCycleException
import com.alipay.remoting.exception.RemotingException
import extractResource
import net.afyer.afybroker.client.Broker
import net.afyer.afybroker.client.BrokerClient
import net.afyer.afybroker.core.BrokerClientType
import net.afyer.afybroker.core.BrokerGlobalConfig
import net.afyer.afybroker.core.MetadataKeys
import net.afyer.afybroker.core.util.BoltUtils
import net.minestom.server.MinecraftServer
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.YamlConfiguration
import org.slf4j.Logger
import java.io.File
import java.util.*

open class MsBroker(val logger: Logger) {

    var brokerClient: BrokerClient? = null

    fun init() {
        val file: File = File("broker.yml")
        if (!file.exists()) {
            extractResource("broker.yml")
        }
        val config = YamlConfiguration.loadConfiguration(file)
        try {
            val serverAddress = String.format(
                "%s:%s",
                config.getString("server.host"),
                config.getInt("server.port")
            )
            val builder = BrokerClient. newBuilder ()
                .host(config.getString("broker.host", BrokerGlobalConfig.BROKER_HOST))
                .port(config.getInt("broker.port", BrokerGlobalConfig.BROKER_PORT))
                .name(
                    config.getString("broker.name", "bukkit-%unique_id%")
                        .replace("%unique_id%", UUID.randomUUID().toString().substring(0, 8))
                        .replace("%hostname%", Objects.toString(System.getenv("HOSTNAME")))
                )
                .addTags(config.getStringList("tags"))
                .addMetadata(MetadataKeys.MC_SERVER_ADDRESS, serverAddress)
                .type(BrokerClientType.SERVER)
            val metadata: ConfigurationSection = config.getConfigurationSection("metadata")
            for (key in metadata.getKeys(false)) {
                builder.addMetadata(key, metadata.getString(key))
            }
            for (buildAction in Broker.getBuildActions()) {
                buildAction.accept(builder)
            }
            brokerClient = builder.build()
            Broker.setClient(brokerClient)
            BoltUtils.initProtocols()
            brokerClient!!.startup()
            brokerClient!!.printInformation(logger)
            brokerClient!!.ping()
            MinecraftServer.getSchedulerManager().buildShutdownTask {
                brokerClient?.shutdown()
                BoltUtils.clearProtocols()
            }
        }  catch (error: LifeCycleException) {
            logger.error("Broker client startup failed!", error);
            MinecraftServer.stopCleanly()
        } catch (e: RemotingException) {
            logger.error("Ping to the broker server failed!");
        }catch (e: InterruptedException) {
            logger.error("Ping to the broker server failed!");
        }

    }

}