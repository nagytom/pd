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
package hu.nagytom.pd.pixeldungeon.items.wands;

import hu.nagytom.pd.noosa.audio.Sample;
import hu.nagytom.pd.pixeldungeon.Assets;
import hu.nagytom.pd.pixeldungeon.Dungeon;
import hu.nagytom.pd.pixeldungeon.ResultDescriptions;
import hu.nagytom.pd.pixeldungeon.actors.Actor;
import hu.nagytom.pd.pixeldungeon.actors.Char;
import hu.nagytom.pd.pixeldungeon.actors.blobs.Blob;
import hu.nagytom.pd.pixeldungeon.actors.blobs.Fire;
import hu.nagytom.pd.pixeldungeon.actors.buffs.Buff;
import hu.nagytom.pd.pixeldungeon.actors.buffs.Burning;
import hu.nagytom.pd.pixeldungeon.effects.MagicMissile;
import hu.nagytom.pd.pixeldungeon.effects.particles.FlameParticle;
import hu.nagytom.pd.pixeldungeon.levels.Level;
import hu.nagytom.pd.pixeldungeon.mechanics.Ballistica;
import hu.nagytom.pd.pixeldungeon.scenes.GameScene;
import hu.nagytom.pd.pixeldungeon.utils.GLog;
import hu.nagytom.pd.pixeldungeon.utils.Utils;
import hu.nagytom.pd.utils.Callback;
import hu.nagytom.pd.utils.Random;

public class WandOfFirebolt extends Wand {

    {
        name = "Wand of Firebolt";
    }

    @Override
    protected void onZap( int cell ) {

        int level = power();

        for (int i=1; i < Ballistica.distance - 1; i++) {
            int c = Ballistica.trace[i];
            if (Level.flamable[c]) {
                GameScene.add( Blob.seed( c, 1, Fire.class ) );
            }
        }

        GameScene.add( Blob.seed( cell, 1, Fire.class ) );

        Char ch = Actor.findChar( cell );
        if (ch != null) {

            ch.damage( Random.Int( 1, 8 + level * level ), this );
            Buff.affect( ch, Burning.class ).reignite( ch );

            ch.sprite.emitter().burst( FlameParticle.FACTORY, 5 );

            if (ch == curUser && !ch.isAlive()) {
                Dungeon.fail( Utils.format( ResultDescriptions.WAND, name, Dungeon.depth ) );
                GLog.n( "You killed yourself with your own Wand of Firebolt..." );
            }
        }
    }

    protected void fx( int cell, Callback callback ) {
        MagicMissile.fire( curUser.sprite.parent, curUser.pos, cell, callback );
        Sample.INSTANCE.play( Assets.SND_ZAP );
    }

    @Override
    public String desc() {
        return
            "This wand unleashes bursts of magical fire. It will ignite " +
            "flammable terrain, and will damage and burn a creature it hits.";
    }
}
