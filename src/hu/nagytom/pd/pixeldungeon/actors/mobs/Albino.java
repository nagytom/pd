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
package hu.nagytom.pd.pixeldungeon.actors.mobs;

import hu.nagytom.pd.pixeldungeon.Badges;
import hu.nagytom.pd.pixeldungeon.actors.Char;
import hu.nagytom.pd.pixeldungeon.actors.buffs.Bleeding;
import hu.nagytom.pd.pixeldungeon.actors.buffs.Buff;
import hu.nagytom.pd.pixeldungeon.sprites.AlbinoSprite;
import hu.nagytom.pd.utils.Random;

public class Albino extends Rat {

	{
		name = "albino rat";
		spriteClass = AlbinoSprite.class;
		
		HP = HT = 15;
	}
	
	@Override
	public void die( Object cause ) {
		super.die( cause );
		Badges.validateRare( this );
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		if (Random.Int( 2 ) == 0) {
			Buff.affect( enemy, Bleeding.class ).set( damage );
		}
		
		return damage;
	}
}
