package me.superhb.mobdrops.content;

import javax.annotation.Nullable;
import me.superhb.mobdrops.models.ModelBatSuit;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MDBatSuit extends ItemArmor {
    public MDBatSuit (ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
    	super(materialIn, renderIndexIn, equipmentSlotIn);
    	
    	this.maxStackSize = 1;
        this.setMaxDamage(432);
        this.addPropertyOverride(new ResourceLocation("broken"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply (ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return MDBatSuit.isBroken(stack) ? 0.0F : 1.0F;
            }
        });
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
	}
    
    /*
    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel (EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped defaultModel) {
    	//return new ModelBatSuit();
    }*/
    
	public static boolean isBroken (ItemStack stack) {
        return stack.getItemDamage() < stack.getMaxDamage() - 1;
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
	@Override
    public boolean getIsRepairable (ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == MDItems.batFur;
    }

	@Override
    public ActionResult<ItemStack> onItemRightClick (ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(itemStackIn);
        ItemStack itemstack = playerIn.getItemStackFromSlot(entityequipmentslot);

        if (itemstack == null) {
            playerIn.setItemStackToSlot(entityequipmentslot, itemStackIn.copy());
            itemStackIn.stackSize = 0;
            return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
        } else {
            return new ActionResult(EnumActionResult.FAIL, itemStackIn);
        }
    }
}
