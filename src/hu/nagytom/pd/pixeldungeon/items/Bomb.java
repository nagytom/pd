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
package hu.nagytom.pd.pixeldungeon.items;

import hu.nagytom.pd.noosa.audio.Sample;
import hu.nagytom.pd.pixeldungeon.Assets;
import hu.nagytom.pd.pixeldungeon.Dungeon;
import hu.nagytom.pd.pixeldungeon.ResultDescriptions;
import hu.nagytom.pd.pixeldungeon.actors.Actor;
import hu.nagytom.pd.pixeldungeon.actors.Char;
import hu.nagytom.pd.pixeldungeon.actors.buffs.Buff;
import hu.nagytom.pd.pixeldungeon.actors.buffs.Paralysis;
import hu.nagytom.pd.pixeldungeon.effects.CellEmitter;
import hu.nagytom.pd.pixeldungeon.effects.particles.BlastParticle;
import hu.nagytom.pd.pixeldungeon.effects.particles.SmokeParticle;
import hu.nagytom.pd.pixeldungeon.levels.Level;
import hu.nagytom.pd.pixeldungeon.scenes.GameScene;
import hu.nagytom.pd.pixeldungeon.sprites.ItemSpriteSheet;
import hu.nagytom.pd.pixeldungeon.utils.GLog;
import hu.nagytom.pd.pixeldungeon.utils.Utils;
import hu.nagytom.pd.utils.Random;

public class Bomb extends Item {

    {
        name = "bomb";
        image = ItemSpriteSheet.BOMB;
        defaultAction = AC_THROW;
        stackable = true;
    }

    @Override
    protected void onThrow( int cell ) {
        if (Level.pit[cell]) {
            super.onThrow( cell );
        } else {
            Sample.INSTANCE.play( Assets.SND_BLAST, 2 );

            if (Dungeon.visible[cell]) {
                CellEmitter.center( cell ).burst( BlastParticle.FACTORY, 30 );
            }

            boolean terrainAffected = false;
            for (int n : Level.NEIGHBOURS9) {
                int c = cell + n;
                if (c >= 0 && c < Level.LENGTH) {
                    if (Dungeon.visible[c]) {
                        CellEmitter.get( c ).burst( SmokeParticle.FACTORY, 4 );
                    }

                    if (Level.flamable[c]) {
                        Dungeon.level.destroy( c );
                        GameScene.updateMap( c );
                        terrainAffected = true;
                    }

                    Char ch = Actor.findChar( c );
                    if (ch != null) {
                        int dmg = Random.Int( 1 + Dungeon.depth, 10 + Dungeon.depth * 2 ) - Random.Int( ch.dr() );
                        if (dmg > 0) {
                            ch.damage( dmg, this );
                            if (ch.isAlive()) {
                                Buff.prolong( ch, Paralysis.class, 2 );
                            } else if (ch == Dungeon.hero) {
                                Dungeon.fail( Utils.format( ResultDescriptions.BOMB, Dungeon.depth ) );
                                GLog.n( "You killed yourself with a bomb..." );
                            }
                        }
                    }
                }
            }

            if (terrainAffected) {
                Dungeon.observe();
            }
        }
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public Item random() {
        quantity = Random.IntRange( 1, 3 );
        return this;
    }

    @Override
    public int price() {
        return 10 * quantity;
    }

    @Override
    public String info() {
        return
            "This is a relatively small bomb, filled with black powder. Conveniently, its fuse is lit automatically when the bomb is thrown.";
    }
}
