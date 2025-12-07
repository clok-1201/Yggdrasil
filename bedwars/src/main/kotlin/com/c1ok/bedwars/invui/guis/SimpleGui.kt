package com.c1ok.bedwars.invui.guis

import com.c1ok.bedwars.invui.CommodityItem
import net.minestom.server.item.Material
import net.minestom.server.item.enchant.Enchantment
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.gui.TabGui
import xyz.xenondevs.invui.gui.structure.Markers
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem
import xyz.xenondevs.invui.item.impl.controlitem.TabItem

class SimpleGui(
    // 主构造函数：接收最多9个商品配置
    private val commodityConfigs: List<CommodityConfig> = defaultCommodityConfigs()
) {

    // 验证配置数量不超过9个
    init {
        require(commodityConfigs.size <= 9) { "最多支持9个标签页" }
        require(commodityConfigs.isNotEmpty()) { "至少需要1个标签页配置" }
    }

    // 商品配置数据类
    data class CommodityConfig(
        val tabName: String,           // 标签页名称（用于标识）
        val activeIcon: ItemBuilder,    // 激活状态的图标
        val inactiveIcon: ItemBuilder, // 非激活状态的图标
        val commodities: List<CommodityItem> // 该标签页的商品列表
    )

    // 自定义TabItem类
    open class MyTabItem(
        private val tab: Int,
        val origin: ItemBuilder,
        val after: ItemBuilder
    ) : TabItem(tab) {
        override fun getItemProvider(gui: TabGui): ItemProvider {
            return if (gui.currentTab == tab) origin else after
        }
    }

    // 边框物品
    val border = SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE))

    // 动态创建GUI列表（每个配置对应一个GUI）
    private val guis = commodityConfigs.map { Gui.empty(9, 3) }

    // 构建TabGui结构
    val tabGui = TabGui.normal()
        .setStructure(buildStructureString(commodityConfigs.size))
        .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
        .addIngredient('#', border)
        .apply {
            // 动态添加TabItem和Tab页
            commodityConfigs.forEachIndexed { index, config ->
                if (index < 9) { // 确保不超过9个
                    val tabChar = (index + 1).toString()[0]
                    addIngredient(tabChar, MyTabItem(index, config.activeIcon, config.inactiveIcon))
                    addTab(guis[index])
                }
            }
        }
        .build()

    init {
        // 初始化每个GUI的商品
        commodityConfigs.forEachIndexed { index, config ->
            guis[index].addItems(*config.commodities.toTypedArray())
        }
    }

    // 动态构建结构字符串的方法
    private fun buildStructureString(tabCount: Int): String {
        // 第一行：标签按钮（1-9），后面用#填充
        val firstLine = buildString {
            for (i in 1..tabCount) {
                append("$i ")
            }
            // 用#填充剩余位置（9个位置）
            repeat(9 - tabCount) { append("# ") }
            // 移除最后一个多余的空格
            if (isNotEmpty()) deleteCharAt(length - 1)
        }

        // 后续行保持不变
        return """
            $firstLine
            x x x x x x x x x
            x x x x x x x x x
            x x x x x x x x x
        """.trimIndent()
    }

    companion object {
        // 默认商品配置（示例）
        private fun defaultCommodityConfigs(): List<CommodityConfig> {
            return listOf(
                CommodityConfig(
                    tabName = "武器",
                    activeIcon = ItemBuilder(Material.DIAMOND_SWORD),
                    inactiveIcon = ItemBuilder(Material.DIAMOND_SWORD)
                        .addEnchantment(Enchantment.FLAME.asValue(), 1, false),
                    commodities = listOf(
                        CommodityItem(
                            ItemBuilder(Material.WOODEN_SWORD),
                            ItemBuilder(Material.WOODEN_SWORD),
                            "level",
                            20
                        ),
                        CommodityItem(
                            ItemBuilder(Material.STONE_SWORD),
                            ItemBuilder(Material.STONE_SWORD),
                            "level",
                            30
                        )
                    )
                ),
                CommodityConfig(
                    tabName = "方块",
                    activeIcon = ItemBuilder(Material.DIAMOND_BLOCK),
                    inactiveIcon = ItemBuilder(Material.DIAMOND_BLOCK)
                        .addEnchantment(Enchantment.FLAME.asValue(), 1, false),
                    commodities = listOf(
                        CommodityItem(
                            ItemBuilder(Material.SANDSTONE),
                            ItemBuilder(Material.SANDSTONE),
                            "level",
                            1
                        ),
                        CommodityItem(
                            ItemBuilder(Material.BRICKS),
                            ItemBuilder(Material.BRICKS),
                            "level",
                            2
                        )
                    )
                )
            )
        }
    }
}