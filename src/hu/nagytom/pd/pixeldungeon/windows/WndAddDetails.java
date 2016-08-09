package hu.nagytom.pd.pixeldungeon.windows;

import hu.nagytom.pd.noosa.BitmapText;
import hu.nagytom.pd.noosa.BitmapTextMultiline;
import hu.nagytom.pd.pixeldungeon.Dungeon;
import hu.nagytom.pd.pixeldungeon.items.Item;
import hu.nagytom.pd.pixeldungeon.items.armor.Armor;
import hu.nagytom.pd.pixeldungeon.items.armor.Armor.Glyph;
import hu.nagytom.pd.pixeldungeon.items.weapon.Weapon;
import hu.nagytom.pd.pixeldungeon.items.weapon.Weapon.Enchantment;
import hu.nagytom.pd.pixeldungeon.items.weapon.melee.MeleeWeapon;
import hu.nagytom.pd.pixeldungeon.items.weapon.missiles.Boomerang;
import hu.nagytom.pd.pixeldungeon.items.weapon.missiles.MissileWeapon;
import hu.nagytom.pd.pixeldungeon.scenes.PixelScene;
import hu.nagytom.pd.pixeldungeon.sprites.ItemSprite;
import hu.nagytom.pd.pixeldungeon.ui.RedButton;
import hu.nagytom.pd.pixeldungeon.ui.Window;
import hu.nagytom.pd.pixeldungeon.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class WndAddDetails extends Window {

    private static final int WIDTH            = 120;
    private static final float GAP            = 2;
    private static final float BUTTON_WIDTH   = 36;
    private static final float BUTTON_HEIGHT  = 16;
    private static final String TXT_ADD       = "ADD";
    private static final String TXT_LVL       = "Level:";
    private static final String TXT_ENCH      = "Enchantment:";
    private static final String TXT_MINUS     = "-";
    private static final String TXT_PLUS      = "+";
    private static final String TXT_LEFT      = "<";
    private static final String TXT_RIGHT     = ">";
    private static final String TXT_NOTHING   = "nothing";
    private static final String TXT_ERROR     = "error";

    private final Item item;

    private List<String> enchNames;

    private int level = 0;
    private BitmapText txtLevelVal;
    private float txtLevelCenter;

    private int enchIndex = -1;
    private BitmapText txtEnchVal;
    private float txtEnchCenter;

    public WndAddDetails(Item itemArg) {
        item = itemArg;
        initNamesArray();
        initUI();
    }

    @SuppressWarnings("unchecked")
    private void initNamesArray() {
        if (!isEnchantable()) {
            enchNames = null;
            return;
        }
        if (item instanceof Armor) {
            enchNames = new ArrayList<String>(Glyph.glyphs.length);
            for (int i = 0; i < Glyph.glyphs.length; i++) {
                enchNames.add(TXT_ERROR);
            }
            int i = 0;
            for (Class<?> c : Glyph.glyphs) {
                try {
                    String[] split = ((Class<? extends Glyph>) c)
                            .newInstance().name("").split("of");
                    enchNames.set(i, split[split.length - 1]);
                } catch (Exception ex) {}
                i++;
            }
        }
        if (item instanceof Weapon) {
            enchNames = new ArrayList<String>(Enchantment.enchants.length);
            for (int i = 0; i < Enchantment.enchants.length; i++) {
                enchNames.add(TXT_ERROR);
            }
            int i = 0;
            for (Class<?> c : Enchantment.enchants) {
                try {
                    String[] split = ((Class<? extends Enchantment>) c)
                            .newInstance().name("").split(" ");
                    enchNames.set(i, split[0]);
                } catch (Exception ex) {}
                i++;
            }
        }
    }

    private void initUI() {
        float pos = 0;

        IconTitle titlebar = new IconTitle();
        titlebar.icon(new ItemSprite(item.image(), item.glowing()));
        titlebar.label(Utils.capitalize(item.trueName()), TITLE_COLOR);
        titlebar.setRect(0, pos, WIDTH, 0);
        pos = titlebar.bottom() + GAP;
        add(titlebar);

        BitmapTextMultiline txtInfo = PixelScene.createMultiline(item.info(), 6);
        txtInfo.maxWidth = WIDTH;
        txtInfo.measure();
        txtInfo.x = titlebar.left();
        txtInfo.y = pos;
        pos = txtInfo.y + txtInfo.height() + GAP;
        add(txtInfo);

        if (item.isUpgradable()) {
            BitmapText txtLevel = PixelScene.createText(TXT_LVL, 7);
            txtLevel.measure();
            add(txtLevel);

            RedButton btnMinus = new RedButton(TXT_MINUS) {
                @Override
                protected void onClick() {
                    if (level > -99) {
                        level--;
                        fixTxtLevelVal();
                    }
                }
            };
            btnMinus.setSize(BUTTON_HEIGHT, BUTTON_HEIGHT);
            add(btnMinus);

            txtLevelVal = PixelScene.createText(" ", 7);
            txtLevelVal.measure();
            add(txtLevelVal);

            RedButton btnPlus = new RedButton(TXT_PLUS) {
                @Override
                protected void onClick() {
                    if (level < 99) {
                        level++;
                        fixTxtLevelVal();
                    }
                }
            };
            btnPlus.setSize(BUTTON_HEIGHT, BUTTON_HEIGHT);
            add(btnPlus);

            float maxHeight = max(new float[] { txtLevel.height(), btnMinus.height(),
                txtLevelVal.height(), btnPlus.height() });

            txtLevel.x = 0;
            txtLevel.y = pos + maxHeight / 2 - txtLevel.height() / 2;
            btnMinus.setPos(txtLevel.x + txtLevel.width() + GAP, pos + maxHeight / 2 - btnMinus.height() / 2);
            btnPlus.setPos(WIDTH - btnPlus.width(), pos + maxHeight / 2 - btnPlus.height() / 2);
            txtLevelVal.y = pos + maxHeight / 2 - txtLevelVal.height() / 2;
            txtLevelCenter = (btnPlus.left() - btnMinus.right()) / 2 + btnMinus.right();
            fixTxtLevelVal();
            pos += maxHeight + GAP;
        }

        if (isEnchantable()) {
            BitmapText txtEnch = PixelScene.createText(TXT_ENCH, 7);
            txtEnch.measure();
            add(txtEnch);

            RedButton btnLeft = new RedButton(TXT_LEFT) {
                @Override
                protected void onClick() {
                    if (item instanceof Armor && enchIndex == -1) {
                        enchIndex = Glyph.glyphs.length - 1;
                    } else if (item instanceof Weapon && enchIndex == -1) {
                        enchIndex = Enchantment.enchants.length - 1;
                    } else {
                        enchIndex--;
                    }
                    fixTxtEnchVal();
                }
            };
            btnLeft.setSize(BUTTON_HEIGHT, BUTTON_HEIGHT);
            add(btnLeft);

            txtEnchVal = PixelScene.createText(" ", 7);
            txtEnchVal.measure();
            add(txtEnchVal);

            RedButton btnRight = new RedButton(TXT_RIGHT) {
                @Override
                protected void onClick() {
                    if ((item instanceof Armor && enchIndex == Glyph.glyphs.length - 1) ||
                        (item instanceof Weapon && enchIndex == Enchantment.enchants.length - 1)) {

                        enchIndex = -1;
                    } else {
                        enchIndex++;
                    }
                    fixTxtEnchVal();
                }
            };
            btnRight.setSize(BUTTON_HEIGHT, BUTTON_HEIGHT);
            add(btnRight);

            float maxHeight = max(new float[] { txtEnch.height(), btnLeft.height(),
                txtEnchVal.height(), btnRight.height() });

            txtEnch.x = 0;
            txtEnch.y = pos + maxHeight / 2 - txtEnch.height() / 2;
            btnLeft.setPos(txtEnch.x + txtEnch.width() + GAP, pos + maxHeight / 2 - btnLeft.height() / 2);
            btnRight.setPos(WIDTH - btnRight.width(), pos + maxHeight / 2 - btnRight.height() / 2);
            txtEnchVal.y = pos + maxHeight / 2 - txtEnchVal.height() / 2;
            txtEnchCenter = (btnRight.left() - btnLeft.right()) / 2 + btnLeft.right();
            fixTxtEnchVal();
            pos += maxHeight + GAP;
        }

        RedButton btnAdd = new RedButton(TXT_ADD) {
            @SuppressWarnings("unchecked")
            @Override
            protected void onClick() {
                try {
                    Item i = item.getClass().newInstance().identify();

                    if (level > 0) {
                        i.upgrade(level);
                    } else if (level < 0) {
                        i.degrade(-level);
                    }

                    if (enchIndex != -1) {
                        if (i instanceof Weapon) {
                            Enchantment e = ((Class<? extends Enchantment>)
                                    Enchantment.enchants[enchIndex]).newInstance();
                            ((Weapon) i).enchant(e);
                        }
                        if (i instanceof Armor) {
                            Glyph e = ((Class<? extends Glyph>)
                                    Glyph.glyphs[enchIndex]).newInstance();
                            ((Armor) i).inscribe(e);
                        }
                    }

                    if (i instanceof MissileWeapon && !(i instanceof Boomerang)) {
                        i.quantity(10);
                    }

                    Dungeon.level.drop(i, Dungeon.hero.pos).sprite.drop();
                } catch (Exception ex) {

                }
            }
        };
        btnAdd.setRect(0, pos, BUTTON_WIDTH, BUTTON_HEIGHT);
        add(btnAdd);

        resize(WIDTH, (int) btnAdd.bottom());
    }

    private void fixTxtLevelVal() {
        txtLevelVal.text(Integer.toString(level));
        txtLevelVal.measure();
        txtLevelVal.x = txtLevelCenter - txtLevelVal.width() / 2;
    }

    private void fixTxtEnchVal() {
        String text = TXT_NOTHING;
        if (enchIndex != -1) {
            text = enchNames.get(enchIndex);
        }
        txtEnchVal.text(text);
        txtEnchVal.measure();
        txtEnchVal.x = txtEnchCenter - txtEnchVal.width() / 2;
    }

    private float max(float[] args) {
        float max = Float.NEGATIVE_INFINITY;
        for (float f : args) {
            if (f > max) {
                max = f;
            }
        }
        return max;
    }

    private boolean isEnchantable() {
        return (item instanceof MeleeWeapon || item instanceof Boomerang || item instanceof Armor);
    }

}
