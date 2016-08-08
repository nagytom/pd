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
package hu.nagytom.pd.pixeldungeon.items.food;

import hu.nagytom.pd.pixeldungeon.actors.buffs.Buff;
import hu.nagytom.pd.pixeldungeon.actors.buffs.Burning;
import hu.nagytom.pd.pixeldungeon.actors.buffs.Hunger;
import hu.nagytom.pd.pixeldungeon.actors.buffs.Paralysis;
import hu.nagytom.pd.pixeldungeon.actors.buffs.Poison;
import hu.nagytom.pd.pixeldungeon.actors.buffs.Roots;
import hu.nagytom.pd.pixeldungeon.actors.buffs.Slow;
import hu.nagytom.pd.pixeldungeon.actors.hero.Hero;
import hu.nagytom.pd.pixeldungeon.sprites.ItemSpriteSheet;
import hu.nagytom.pd.pixeldungeon.utils.GLog;
import hu.nagytom.pd.utils.Random;

public class MysteryMeat extends Food {

    {
        name = "mystery meat";
        image = ItemSpriteSheet.MEAT;
        energy = Hunger.STARVING - Hunger.HUNGRY;
        message = "That food tasted... strange.";
    }

    @Override
    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals( AC_EAT )) {

            switch (Random.Int( 5 )) {
            case 0:
                GLog.w( "Oh it's hot!" );
                Buff.affect( hero, Burning.class ).reignite( hero );
                break;
            case 1:
                GLog.w( "You can't feel your legs!" );
                Buff.prolong( hero, Roots.class, Paralysis.duration( hero ) );
                break;
            case 2:
                GLog.w( "You are not feeling well." );
                Buff.affect( hero, Poison.class ).set( Poison.durationFactor( hero ) * hero.HT / 5 );
                break;
            case 3:
                GLog.w( "You are stuffed." );
                Buff.prolong( hero, Slow.class, Slow.duration( hero ) );
                break;
            }
        }
    }

    @Override
    public String info() {
        return "Eat at your own risk!";
    }

    public int price() {
        return 5 * quantity;
    };
}
