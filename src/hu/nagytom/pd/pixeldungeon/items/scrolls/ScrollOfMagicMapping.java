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
import hu.nagytom.pd.pixeldungeon.Dungeon;
import hu.nagytom.pd.pixeldungeon.actors.buffs.Invisibility;
import hu.nagytom.pd.pixeldungeon.effects.CellEmitter;
import hu.nagytom.pd.pixeldungeon.effects.Speck;
import hu.nagytom.pd.pixeldungeon.effects.SpellSprite;
import hu.nagytom.pd.pixeldungeon.levels.Level;
import hu.nagytom.pd.pixeldungeon.levels.Terrain;
import hu.nagytom.pd.pixeldungeon.scenes.GameScene;
import hu.nagytom.pd.pixeldungeon.utils.GLog;

public class ScrollOfMagicMapping extends Scroll {

	private static final String TXT_LAYOUT = "You are now aware of the level layout.";

	{
		name = "Scroll of Magic Mapping";
	}

	@Override
	protected void doRead() {

		int length = Level.LENGTH;
		int[] map = Dungeon.level.map;
		boolean[] mapped = Dungeon.level.mapped;
		boolean[] discoverable = Level.discoverable;

		boolean noticed = false;

		for (int i=0; i < length; i++) {

			int terr = map[i];

			if (discoverable[i]) {

				mapped[i] = true;
				if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {

					Level.set( i, Terrain.discover( terr ) );
					GameScene.updateMap( i );

					if (Dungeon.visible[i]) {
						GameScene.discoverTile( i, terr );
						discover( i );

						noticed = true;
					}
				}
			}
		}
		Dungeon.observe();

		GLog.i( TXT_LAYOUT );
		if (noticed) {
			Sample.INSTANCE.play( Assets.SND_SECRET );
		}

		SpellSprite.show( curUser, SpellSprite.MAP );
		Sample.INSTANCE.play( Assets.SND_READ );
		Invisibility.dispel();

		setKnown();

		readAnimation();
	}

	@Override
	public String desc() {
		return
			"When this scroll is read, an image of crystal clarity will be etched into your memory, " +
			"alerting you to the precise layout of the level and revealing all hidden secrets. " +
			"The locations of items and creatures will remain unknown.";
	}

	@Override
	public int price() {
		return isKnown() ? 25 * quantity : super.price();
	}

	public static void discover( int cell ) {
		CellEmitter.get( cell ).start( Speck.factory( Speck.DISCOVER ), 0.1f, 4 );
	}
}
