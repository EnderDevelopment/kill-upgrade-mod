package com.msmalika9.killupgrademod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "killupgrademod")
public
class MaceItem extends Item {
    private static final Map<Tier, Integer> maceTiers = new HashMap<>();

    static {
        maceTiers.put(Tiers.WOOD, 1);
        maceTiers.put(Tiers.STONE, 2);
        maceTiers.put(Tiers.IRON, 3);
        maceTiers.put(Tiers.NETHERITE, 4);
        maceTiers.put(Tiers.GOLD, 5);
        maceTiers.put(Tiers.DIAMOND, 6);
        maceTiers.put(Tiers.MANPEARR, 7);
        maceTiers.put(Tiers.ENDER_DRAGON, 8);
        maceTiers.put(Tiers.GOD, 9);
    }

    public MaceItem(Properties properties) {
        super(properties);
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player && event.getEntity() instanceof Player) {
            ItemStack mainHandItem = player.getMainHandItem();
            if (mainHandItem.getItem() instanceof MaceItem) {
                Tier currentTier = ((MaceItem) mainHandItem.getItem()).getTier();
                int currentLevel = maceTiers.getOrDefault(currentTier, 1);
                if (currentLevel < 9) {
                    Tier nextTier = getNextTier(currentTier);
                    mainHandItem.setTag(nextTier.getTag());
                    applyEffects(player, nextTier);
                } else {
                    banPlayer((Player) event.getEntity());
                }
            }
        }
    }

    private static Tier getNextTier(Tier currentTier) {
        for (Map.Entry<Tier, Integer> entry : maceTiers.entrySet()) {
            if (entry.getValue() == maceTiers.get(currentTier) + 1) {
                return entry.getKey();
            }
        }
        return Tiers.WOOD;
    }

    private static void applyEffects(Player player, Tier tier) {
        switch (tier) {
            case WOOD:
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0));
            break;
            case STONE:
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 1));
            break;
            case IRON:
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 2));
            break;
            case NETHERITE:
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 3));
            break;
            case GOLD:
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 4));
            break;
            case DIAMOND:
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 5));
            break;
            case MANPEARR:
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 6));
            break;
            case ENDER_DRAGON:
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 7));
            break;
            case GOD:
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 8));
            break;
        }
    }

    private static void banPlayer(Player player) {
        player.getServer().getPlayerList().getBans().add(new net.minecraft.server.players.UserBanListEntry(player.getGameProfile(), null, "Killed by God Mace", null, "KillUpgradeMod"));
    }
}
