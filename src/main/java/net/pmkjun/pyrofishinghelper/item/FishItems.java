package net.pmkjun.pyrofishinghelper.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public class FishItems {
    public static final String[] COMMMON_FISH_LIST = {"평범한 물고기","흰동가리","우럭","청색양쥐돔","붕어","대구","고등어","참치","붉은 불가사리","문어","멍게",
            "랍스타","왕눈이","가시고기","오리너구리","만새기","미꾸라지","가물치","청어","좀비 물고기"};
    public static final String[] UNCOMMON_FISH_LIST = {"잉어","복어","연어","개구리","송어","농어","해삼","성게","푸른 불가사리",
            "피카츄 물고기","별가사리","만타인","붉은 망둥어","네온 블루구피","도리","파스텔 피쉬","정어리"};
    public static final String[] RARE_FISH_LIST = {"바다 거북이","피라냐","노던 파이크","황새치","철갑상어","바다 달팽이","푸른 해파리",
            "두꺼비","아쿠스타","쑥치","심해 아귀","맘복치","애플망고 피쉬","질퍽이","황복어","날개다랑어"};
    public static final String[] EPIC_FISH_LIST = {"아귀","쏨뱅이","장어","붉은 해파리","해마","뿔복","두선생","황금거북이","톱상어","레인보우 물고기","흰수염 물고기","알비노 피쉬"};
    public static final String[] LEGENDARY_FISH_LIST = {"덤보문어","스컬 피쉬","메기","엔젤 피쉬","바닷가재","범피","핑핑이","디어라이트 피쉬","스타더스트 피쉬","니모"};
    public static final String[] MYTHIC_FISH_LIST = {"개복치","사자 물고기","실러캔스","대왕쥐가오리","깃대돔","장미 해파리","크라켄","투탕카 해마","고래 상어","피카소 피쉬"};
    public static final Item[] COMMON_FISH = new Item[20];
    public static final Item[] UNCOMMON_FISH = new Item[17];
    public static final Item[] RARE_FISH = new Item[16];
    public static final Item[] EPIC_FISH = new Item[12];
    public static final Item[] LEGENDARY_FISH = new Item[10];
    public static final Item[] MYTHIC_FISH = new Item[10];
    public static void register(){
        int i;
        for(i = 0; i<COMMON_FISH.length; i++){
            COMMON_FISH[i] = new Item(new Item.Settings());
            Registry.register((Registry)Registries.ITEM, new Identifier("pyrofishinghelper", "common_"+i), COMMON_FISH[i]);
        }
        for(i = 0; i<UNCOMMON_FISH.length; i++){
            UNCOMMON_FISH[i] = new Item(new Item.Settings());
            Registry.register((Registry)Registries.ITEM, new Identifier("pyrofishinghelper", "uncommon_"+i), UNCOMMON_FISH[i]);
        }
        for(i = 0; i<RARE_FISH.length; i++){
            RARE_FISH[i] = new Item(new Item.Settings());
            Registry.register((Registry)Registries.ITEM, new Identifier("pyrofishinghelper", "rare_"+i), RARE_FISH[i]);
        }
        for(i = 0; i<EPIC_FISH.length; i++){
            EPIC_FISH[i] = new Item(new Item.Settings());
            Registry.register((Registry)Registries.ITEM, new Identifier("pyrofishinghelper", "epic_"+i), EPIC_FISH[i]);
        }
        for(i = 0; i<LEGENDARY_FISH.length; i++){
            LEGENDARY_FISH[i] = new Item(new Item.Settings());
            Registry.register((Registry)Registries.ITEM, new Identifier("pyrofishinghelper", "legendary_"+i), LEGENDARY_FISH[i]);
        }
        for(i = 0; i<MYTHIC_FISH.length; i++){
            MYTHIC_FISH[i] = new Item(new Item.Settings());
            Registry.register((Registry)Registries.ITEM, new Identifier("pyrofishinghelper", "mythic_"+i), MYTHIC_FISH[i]);
        }

    }
    public static Item getFishItem(ItemStack itemStack){
        String name = itemStack.getName().getString();
        int index;

        if(!(itemStack.getItem().toString().equals("cod"))) return null;

        index = Arrays.stream(COMMMON_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return COMMON_FISH[index];

        index = Arrays.stream(UNCOMMON_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return UNCOMMON_FISH[index];

        index = Arrays.stream(RARE_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return RARE_FISH[index];

        index = Arrays.stream(EPIC_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return EPIC_FISH[index];

        index = Arrays.stream(LEGENDARY_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return LEGENDARY_FISH[index];

        index = Arrays.stream(MYTHIC_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return MYTHIC_FISH[index];

        return null;
    }
}
