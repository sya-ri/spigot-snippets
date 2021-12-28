import net.minecraft.nbt.NBTCompressedStreamTools
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInput
import java.io.DataInputStream
import java.io.DataOutput
import java.io.DataOutputStream
import java.io.IOException
import java.math.BigInteger
import net.minecraft.world.item.ItemStack as NMSItemStack

/**
 * アイテムを Base64 に変換します
 * @param item 対象アイテム
 * @return [String]
 */
fun itemStackToBase64(item: ItemStack): String? {
    val outputStream = ByteArrayOutputStream()
    val nbtTagCompound = NBTTagCompound().apply {
        CraftItemStack.asNMSCopy(item).save(this)
        NBTTagList().add(this)
    }
    try {
        NBTCompressedStreamTools.a(nbtTagCompound, DataOutputStream(outputStream) as DataOutput)
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
    return BigInteger(1, outputStream.toByteArray()).toString(32)
}

/**
 * Base64 をアイテムに変換します
 * @param base64 Base64 データ
 * @return [ItemStack]?
 */
fun itemStackFromBase64(base64: String): ItemStack? {
    val inputStream = ByteArrayInputStream(BigInteger(base64, 32).toByteArray())
    val nbtTagCompound = try {
        NBTCompressedStreamTools.a(DataInputStream(inputStream) as DataInput)
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
    val nmsItem = NMSItemStack.a(nbtTagCompound)
    return CraftItemStack.asBukkitCopy(nmsItem)
}
