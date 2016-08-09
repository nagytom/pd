package hu.nagytom.pd.pixeldungeon.windows;

import hu.nagytom.pd.noosa.BitmapText;
import hu.nagytom.pd.noosa.ui.Component;
import hu.nagytom.pd.pixeldungeon.PixelDungeon;
import hu.nagytom.pd.pixeldungeon.items.ArmorKit;
import hu.nagytom.pd.pixeldungeon.items.Bomb;
import hu.nagytom.pd.pixeldungeon.items.Item;
import hu.nagytom.pd.pixeldungeon.items.LloydsBeacon;
import hu.nagytom.pd.pixeldungeon.items.Torch;
import hu.nagytom.pd.pixeldungeon.items.Weightstone;
import hu.nagytom.pd.pixeldungeon.items.armor.ClothArmor;
import hu.nagytom.pd.pixeldungeon.items.armor.LeatherArmor;
import hu.nagytom.pd.pixeldungeon.items.armor.MailArmor;
import hu.nagytom.pd.pixeldungeon.items.armor.PlateArmor;
import hu.nagytom.pd.pixeldungeon.items.armor.ScaleArmor;
import hu.nagytom.pd.pixeldungeon.items.bags.ScrollHolder;
import hu.nagytom.pd.pixeldungeon.items.bags.SeedPouch;
import hu.nagytom.pd.pixeldungeon.items.bags.WandHolster;
import hu.nagytom.pd.pixeldungeon.items.food.Food;
import hu.nagytom.pd.pixeldungeon.items.keys.GoldenKey;
import hu.nagytom.pd.pixeldungeon.items.keys.IronKey;
import hu.nagytom.pd.pixeldungeon.items.keys.SkeletonKey;
import hu.nagytom.pd.pixeldungeon.items.potions.Potion;
import hu.nagytom.pd.pixeldungeon.items.rings.Ring;
import hu.nagytom.pd.pixeldungeon.items.scrolls.Scroll;
import hu.nagytom.pd.pixeldungeon.items.scrolls.ScrollOfWipeOut;
import hu.nagytom.pd.pixeldungeon.items.wands.Wand;
import hu.nagytom.pd.pixeldungeon.items.wands.WandOfMagicMissile;
import hu.nagytom.pd.pixeldungeon.items.weapon.melee.BattleAxe;
import hu.nagytom.pd.pixeldungeon.items.weapon.melee.Dagger;
import hu.nagytom.pd.pixeldungeon.items.weapon.melee.Glaive;
import hu.nagytom.pd.pixeldungeon.items.weapon.melee.Knuckles;
import hu.nagytom.pd.pixeldungeon.items.weapon.melee.Longsword;
import hu.nagytom.pd.pixeldungeon.items.weapon.melee.Mace;
import hu.nagytom.pd.pixeldungeon.items.weapon.melee.Quarterstaff;
import hu.nagytom.pd.pixeldungeon.items.weapon.melee.ShortSword;
import hu.nagytom.pd.pixeldungeon.items.weapon.melee.Spear;
import hu.nagytom.pd.pixeldungeon.items.weapon.melee.Sword;
import hu.nagytom.pd.pixeldungeon.items.weapon.melee.WarHammer;
import hu.nagytom.pd.pixeldungeon.items.weapon.missiles.Boomerang;
import hu.nagytom.pd.pixeldungeon.items.weapon.missiles.CurareDart;
import hu.nagytom.pd.pixeldungeon.items.weapon.missiles.Dart;
import hu.nagytom.pd.pixeldungeon.items.weapon.missiles.IncendiaryDart;
import hu.nagytom.pd.pixeldungeon.items.weapon.missiles.Javelin;
import hu.nagytom.pd.pixeldungeon.items.weapon.missiles.Shuriken;
import hu.nagytom.pd.pixeldungeon.items.weapon.missiles.Tamahawk;
import hu.nagytom.pd.pixeldungeon.scenes.GameScene;
import hu.nagytom.pd.pixeldungeon.scenes.PixelScene;
import hu.nagytom.pd.pixeldungeon.sprites.ItemSprite;
import hu.nagytom.pd.pixeldungeon.ui.ScrollPane;
import hu.nagytom.pd.pixeldungeon.ui.Window;
import hu.nagytom.pd.pixeldungeon.utils.Utils;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class WndAdd extends Window {

    private static final int WIDTH_P        = 112;
    private static final int HEIGHT_P       = 160;
    private static final int WIDTH_L        = 128;
    private static final int HEIGHT_L       = 144;
    private static final int ITEM_HEIGHT    = 18;
    private static final String TXT_TITLE   = "Add Item";

    private ListItem[] items;

    public WndAdd() {
        initList();

        if (PixelDungeon.landscape()) {
            resize(WIDTH_L, HEIGHT_L);
        } else {
            resize(WIDTH_P, HEIGHT_P);
        }

        BitmapText txtTitle = PixelScene.createText(TXT_TITLE, 9);
        txtTitle.hardlight(Window.TITLE_COLOR);
        txtTitle.measure();
        txtTitle.x = PixelScene.align( PixelScene.uiCamera, (width - txtTitle.width()) / 2 );
        add(txtTitle);

        Component content = new Component();
        float pos = 0;
        for (ListItem item : items) {
            item.setRect(0, pos, width, ITEM_HEIGHT);
            content.add(item);
            pos += item.height();
        }
        content.setSize(width, pos);

        ScrollPane list = new ScrollPane(content) {
            @Override
            public void onClick(float x, float y) {
                for (int i = 0; i < items.length; i++) {
                    if (items[i].inside(x, y)) {
                        items[i].onClick();
                        break;
                    }
                }
            }
        };
        add(list);
        list.setRect(0, txtTitle.height(), width, height - txtTitle.height());
    }

    private void initList() {
        List<Class<? extends Item>> list = new LinkedList<Class<? extends Item>>();

        list.add(ShortSword.class);
        list.add(Dagger.class);
        list.add(Knuckles.class);
        list.add(Quarterstaff.class);
        list.add(Spear.class);
        list.add(Mace.class);
        list.add(Sword.class);
        list.add(Longsword.class);
        list.add(BattleAxe.class);
        list.add(WarHammer.class);
        list.add(Glaive.class);
        list.add(Weightstone.class);

        list.add(Dart.class);
        list.add(IncendiaryDart.class);
        list.add(Shuriken.class);
        list.add(CurareDart.class);
        list.add(Javelin.class);
        list.add(Tamahawk.class);
        list.add(Boomerang.class);
        list.add(Bomb.class);

        list.add(ClothArmor.class);
        list.add(LeatherArmor.class);
        list.add(MailArmor.class);
        list.add(ScaleArmor.class);
        list.add(PlateArmor.class);
        list.add(ArmorKit.class);

        Collections.addAll(list, (Class<Item>[]) Wand.wands);
        list.add(WandOfMagicMissile.class);
        Collections.addAll(list, (Class<Item>[]) Ring.rings);
        Collections.addAll(list, (Class<Item>[]) Scroll.scrolls);
        list.add(ScrollOfWipeOut.class);
        Collections.addAll(list, (Class<Item>[]) Potion.potions);

        list.add(ScrollHolder.class);
        list.add(SeedPouch.class);
        list.add(WandHolster.class);

        list.add(IronKey.class);
        list.add(SkeletonKey.class);
        list.add(GoldenKey.class);

        list.add(LloydsBeacon.class);
        list.add(Torch.class);
        list.add(Food.class);

        items = new ListItem[list.size()];
        int i = 0;
        for (Class<? extends Item> item : list) {
            items[i] = new ListItem(item);
            i++;
        }
    }

    private static class ListItem extends Component {

        private Item item;
        private ItemSprite sprite;
        private BitmapText label;

        public ListItem(Class<? extends Item> cl) {
            try {
                item = cl.newInstance();
                sprite.view(item.image(), null);
                label.text(Utils.capitalize(item.trueName()));
            } catch (Exception e) {
                // Do nothing
            }
        }

        @Override
        protected void createChildren() {
            sprite = new ItemSprite();
            add(sprite);
            label = PixelScene.createText(8);
            add(label);
        }

        @Override
        protected void layout() {
            sprite.y = PixelScene.align(y + (height - sprite.height) / 2);
            label.x = sprite.x + sprite.width;
            label.y = PixelScene.align(y + (height - label.baseLine()) / 2);
        }

        public void onClick() {
            item.identify();
            GameScene.show(new WndAddDetails(item));
        }

    }

}
