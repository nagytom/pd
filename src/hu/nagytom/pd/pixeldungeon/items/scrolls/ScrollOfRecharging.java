/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package hu.nagytom.pd.pixeldungeon.items.scrolls;

import hu.nagytom.pd.noosa.audio.Sample;
import hu.nagytom.pd.pixeldungeon.Assets;
import hu.nagytom.pd.pixeldungeon.actors.buffs.Invisibility;
import hu.nagytom.pd.pixeldungeon.actors.hero.Hero;
import hu.nagytom.pd.pixeldungeon.effects.SpellSprite;
import hu.nagytom.pd.pixeldungeon.effects.particles.EnergyParticle;
import hu.nagytom.pd.pixeldungeon.utils.GLog;

public class ScrollOfRecharging extends Scroll {

    {
        name = "Scroll of Recharging";
    }

    @Override
    protected void doRead() {

        int count = curUser.belongings.charge( true );
        charge( curUser );

        Sample.INSTANCE.play( Assets.SND_READ );
        Invisibility.dispel();

        if (count > 0) {
            GLog.i( "a surge of energy courses through your pack, recharging your wand" + (count > 1 ? "s" : "") );
            SpellSprite.show( curUser, SpellSprite.CHARGE );
        } else {
            GLog.i( "a surge of energy courses through your pack, but nothing happens" );
        }
        setKnown();

        readAnimation();
    }

    @Override
    public String desc() {
        return
            "The raw magical power bound up in this parchment will, when released, " +
            "recharge all of the reader's wands to full power.";
    }

    public static void charge( Hero hero ) {
        hero.sprite.centerEmitter().burst( EnergyParticle.FACTORY, 15 );
    }

    @Override
    public int price() {
        return isKnown() ? 40 * quantity : super.price();
    }
}
