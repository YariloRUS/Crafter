package mod.wurmunlimited.npcs;

import com.wurmonline.server.behaviours.BehaviourDispatcher;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.CrafterTrade;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemList;
import com.wurmonline.server.players.Player;
import com.wurmonline.server.villages.DbVillage;
import com.wurmonline.server.villages.FakeVillage;
import mod.wurmunlimited.CrafterObjectsFactory;
import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.junit.jupiter.api.BeforeEach;

public class CrafterTest {
    protected CrafterObjectsFactory factory;
    protected Player player;
    protected Player owner;
    protected Creature crafter;
    protected Item tool;
    protected Item forge;
    protected CrafterTrade trade;

    @BeforeEach
    void setUp() throws Exception {
        factory = new CrafterObjectsFactory();
        BehaviourDispatcher.reset();
        ReflectionUtil.setPrivateField(null, CrafterMod.class.getDeclaredField("skillCap"), 99.99999f);
        ReflectionUtil.setPrivateField(null, CrafterMod.class.getDeclaredField("basePrice"), 1);
        player = factory.createNewPlayer();
        tool = factory.createNewItem(ItemList.pickAxe);
        player.getInventory().insertItem(tool);
        owner = factory.createNewPlayer();
        crafter = factory.createNewCrafter(owner, new CrafterType(CrafterType.allMetal), 50);
        factory.createVillageFor(owner, crafter);
        forge = factory.createNewItem(ItemList.forge);
        setForgeWithoutPathing();
    }

    protected void setForgeWithoutPathing() {
        try {
            CrafterAIData data = (CrafterAIData)crafter.getCreatureAIData();
            ReflectionUtil.setPrivateField(data, CrafterAIData.class.getDeclaredField("forge"), forge);
            data.getWorkBook().setForge(forge);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }


    }
}
