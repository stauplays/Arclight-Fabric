package net.verox.arclight.item.custom;

import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.verox.arclight.block.ModBlocks;
import net.verox.arclight.entity.mob.EntityTypes;
import net.verox.arclight.entity.mob.custom.AngelEntity;

import javax.annotation.Nullable;
import java.util.List;

public class AngelSpawnItem extends Item {

    public AngelSpawnItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.literal("Right Click on Boss-Spawn Block to summon the Boss!").formatted(Formatting.LIGHT_PURPLE));
        } else {
            tooltip.add(Text.literal("Press Shift!").formatted(Formatting.DARK_GRAY));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Block blockBelow = null;
        if (!context.getWorld().isClient()) {
            BlockPos positionClicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            boolean foundBlock = false;

            for (int i = 0; i <= positionClicked.getY() + 2; i++) {
                blockBelow = context.getWorld().getBlockState(positionClicked.down(i)).getBlock();

                if (isValuableBlock(blockBelow)) {
                    World world = context.getWorld();
                    AngelEntity spawnAngel = EntityTypes.ANGEL.create(world);
                    spawnAngel.refreshPositionAndAngles((double) positionClicked.getX() + 0.5D, (double) positionClicked.getY()
                            + 15.05D, (double) positionClicked.getZ() + 0.5D, 0.0F, 0.0F);
                    world.spawnEntity(spawnAngel);
                    break;

                }
            }
        }
        if (isValuableBlock(blockBelow)) {
            context.getStack().damage(1, context.getPlayer(),
                    (player) -> player.sendToolBreakStatus(player.getActiveHand()));
        }

        return super.useOnBlock(context);

    }

    private boolean isValuableBlock(Block block) {
        return block == ModBlocks.BOSS_SPAWNER;
    }
}
